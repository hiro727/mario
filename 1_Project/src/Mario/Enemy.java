package Mario;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy { 
	Ground g;
	BasicEnemyItemBlockInfo b;
	private BufferedImage enemy;
	private boolean[][] white;
	private int type;
	private int x, y, h, tempx;
	private double clipx=0,speed, dx, dy, tempy, kickx;
	private int min, max, eachsize, kickedtime;
	private boolean appeared = false, stomped = false, transformed = false, kicked = false, touchable = false, killed = false, amazing = false;//, fired = false;
	private double deadcounter = 0, transformcounter = 0;
	
	public Enemy(Frames f, Ground g, Pictures p, BasicEnemyItemBlockInfo b, int x, int y){
		white  	 = p.getWhiteenemy(x);
		enemy  	 = Pictures.enemy[x];
		type   	 = x;
		this.g 	 = g;
		this.b 	 = b;
		this.x 	 = BasicEnemyItemBlockInfo.xE[x][y];
		this.y   = BasicEnemyItemBlockInfo.yE[x][y];
		this.h   = enemy.getHeight();
		min	     = BasicEnemyItemBlockInfo.minE[x];
		max	     = BasicEnemyItemBlockInfo.maxE[x];
		eachsize = BasicEnemyItemBlockInfo.eachsizeE[x];
		speed	 = BasicEnemyItemBlockInfo.speedE[x];
		dx		 = BasicEnemyItemBlockInfo.dxE[x];
		if(type==2){
			tempy 	= 0;
			dy 		= .5;
		}
	}
	
	public boolean checkAppear(int x){
		if(x > this.x-200){
			appeared = true;	return true;	//finish
		}
		else if(x > this.x)		return true;
		else{appeared = false;	return false;}
	}
	
	private BufferedImage updateimage(BufferedImage img, Frames f, int x, int y, int startingx, int finishingx){
        int h = img.getHeight();//System.out.println("updating shape"+startingy+" to "+finishingy);
		for(int i=0;i<h;i++){
			for(int j=startingx;j<finishingx;j++){
				if (white[i][j]){//System.out.println(x+j+"\t"+y+i);
					img.setRGB(j,i,f.getColor(x+j, y+i)); 
				}
			}
		}
		return img;
	}
	private BufferedImage updateimage(BufferedImage img, Frames f, int x, int y, int startingx, int finishingx, int startingy, int finishingy){
		for(int i=startingy;i<finishingy;i++){
			for(int j=startingx;j<finishingx;j++){
				if (white[i][j]){//
					img.setRGB(j,i,f.getColor(x+j, y+i)); 
				}
			}
		}
		return img;
	}
	public void update(Mario m){
		int tester = (int)(clipx + speed);//System.out.println(tester);
		if(tester < max){
			clipx += speed;
		}else{
			tester=min;clipx = min;
		}
		
		if(type==2){
			if(tempy>40){//System.out.println(x);
				if(m.getX()>x-32&&m.getX()<x+37+5){}
				else{
					dy=-dy;tempy += dy;
				}
			}else{
				if(tempy==0.0&&dy<0){
					dy/=100;
				}	
				else if(tempy<=-0.05){
					dy=-dy;
				}
				else if(tempy==1.734723475976807E-18&&dy>0){
					dy*=100;
				}
				tempy += dy;
				if(m.getSpecial()==null)					checkCollision(m.getX(), m.getY(), m.getsX(), m.getsY(), m, 1);
				else if(m.getSpecial().equals("starman"))	specialOperation(m.getX(), m.getY(), m.getsX(), m.getsY(), m);
			}
		}else{
			x += dx;//System.out.println(type+" tempy "+tempy+" & dy ="+dy);
			if(!amazing){
				sidewallOperation(x, y);
				fallingOperation(x, y);
			}
			if(m.getSpecial()==null||m.getSpecial().equals("recovering"))checkCollision(m.getX(), m.getY(), m.getsX(), m.getsY(), m, 0);
			else if(m.getSpecial().equals("starman"))	specialOperation(m.getX(), m.getY(), m.getsX(), m.getsY(), m);
		}
		
	}
	private void sidewallOperation(int x, int y){
		if(dx>0){
			if(y>g.ground[x+eachsize]){//System.out.println("MOVE TO LEFT");
				while(x%20>24-eachsize){
					x--;//System.out.println(x);
				}
				max = min;
				min -= 2;
				clipx = min;
				this.x  = x;
				dx = - dx;
			}
		}else if(dx<0){
			if((x<0||x==0)){
    			x=0;
				dx = - dx;
				min = max;
				max += 2;
				clipx = min;
			}
			if(y>g.ground[x]){//System.out.println("MOVE TO RIGHT");
				while(x%20<19){
					x++;//System.out.println(x);
				}x++;
				min = max;
				max += 2;
				clipx = min;
				dx = - dx;
				this.x = x;
			}
		}
	}   
	private void checkCollision(int mx, int my, int sx, int sy, Mario m, int n) {
		if((mx+sx>x+1&&mx+1<x+eachsize)){
			
			if(my>=y-h&&my-sy<y&&n==0){
				if(m.getFlying()){
					if(m.getdY()<0&&my>y-h/2&&m.getSpecial()==null)	m.powerdown();
					else			stompedOperation(type, m);
				}else if(m.getSpecial()==null)	m.powerdown();
				
			}else if(n==1&&my>y+tempy-h){
				stompedOperation(type, m);
			}else if(my-sy<0&&y>290){
				amazing = true;
				y = 300;
				if(dx<0){
					dx = -dx;
					min = max;
					max += 2;
					clipx = min;
				}
				stompedOperation(type, m);
			}
			
			
		}
	}
	private void fallingOperation(int x, int y) {
		if(y<g.ground[x]&&dx>0&&!Ground.thereisblock[x]){
			//for(int i=0;i<)
			dy += .25*10;
			y  += dy*.25+.5*10*.25*.25;
			if(y>g.ground[x]){
				y = g.ground[x];
			}this.y = y;
		}else if(y<g.ground[x+eachsize]&&dx<0&&!Ground.thereisblock[x+eachsize]){
				dy += .25*10;
				y  += dy*.25+.5*10*.25*.25;
			if(y>g.ground[x+eachsize]){
				y = g.ground[x+eachsize];
			}this.y = y;
		}
		
		//System.out.println(y);
		
	}
	
	private void specialOperation(int mx, int my, int sx, int sy, Mario m){
		if((mx+sx>x+1&&mx+1<x+eachsize&&my>y-h)){
			killed = true;
		}
	}
	
	
	private void stompedOperation(int t, Mario m) {
		if(t==0){
			stomped = true;
			m.setdY(-30);
			clipx = enemy.getWidth()/eachsize-1;
		}else if(t==1){
			transformed = true;
			m.setdY(-30);
			clipx = enemy.getWidth()/eachsize-2;
		}else if(t==2){
			m.powerdown();
		}else if(t==3){
			transformed = true;
			m.setdY(-30);
			clipx = enemy.getWidth()/eachsize-2;
		}else if(t==4){
			stomped = true;
			m.setdY(-30);
			clipx = enemy.getWidth()/eachsize-1;
		}else if(t==5){
			m.powerdown();
		}
	}
	public void stompupdate(){
		deadcounter++;
		if(deadcounter==10)	killed = true;
	}
	public void transformupdate(Mario m, String direction){
		
		if(!kicked){
			kickedtime = 0;
			transformcounter++;//System.out.println(clipx);
			if(transformcounter>=300){
				if(transformcounter==300){
					clipx++;
				}if(transformcounter==308){
					clipx--;
				}else if(transformcounter==316){
					clipx++;
				}else if(transformcounter==324){
					clipx--;
				}else if(transformcounter==332){
					clipx++;
				}else if(transformcounter==340){
					clipx++;
				}else if(transformcounter==348){
					clipx--;
				}else if(transformcounter==356){
					transformcounter = 0;
					transformed = false;
				}
			}
			kickCollision(m, direction);
			//if(type==1)System.out.println("not kicked");
		}else{
			x += kickx;	
			if(kickedtime<16)	kickedtime++;
			siderollingOperation(x, y);
			if(kickedtime>15)	rollingCollision(m.getX(), m.getY(), m.getsX(), m.getsY(), m);
		}
	}	
	private void kickCollision(Mario m, String direction) {
			if(m.getY()>y-h){
				if(direction.equals("RIGHT")&&m.getX()+m.getsX()>x+1&&m.getX()+m.getsX()<x+eachsize+1){
					System.out.println("kicked from left");
					kickx = 5;	kicked = true;
				}else if(direction.equals("LEFT")&&m.getX()<x+eachsize-1&&m.getX()>x-1){
					System.out.println("kicked from right");
					kickx = -5;	kicked = true;
				}
			}
		}
	private void rollingCollision(int mx, int my, int sx, int sy, Mario m) {
			if((mx+sx>x+1&&mx+1<x+eachsize)){
				if(m.getFlying()&&m.getdY()>0){
					if(my>y-h){
						kicked = false;transformcounter = 0;	m.setdY(-30);
					}
				}
				else if(!m.isOnblock()&&!m.getFlying()){
					m.powerdown();
				}
			}
		}private void siderollingOperation(int x, int y){
			if(kickx>0){
				if(y>g.ground[x+eachsize]){//System.out.println("MOVE TO LEFT");
					while(x%20>24-eachsize){
						x--;//System.out.println(x);
					}this.x  = x;
					kickx = - kickx;
				}
			}else if(kickx<0){
				if((x<0||x==0)){
		   			x=0;
		   			kickx = - kickx;
				}
				if(y>g.ground[x]){//System.out.println("MOVE TO RIGHT");
					while(x%20<19){
						x++;//System.out.println(x);
					}x++;
					kickx = - kickx;
					this.x = x;
				}
			}
		}

/*	private static void enemy_enemy(){
		
	}
*/
	public void paint(Graphics g, Frames f, Mario m){
		if(m.getX()>250&&m.getX()<m.getWidth()-250){
			tempx = m.getX()-250;
		}else if(m.getX()>m.getWidth()-300){
			tempx = m.getX()-250;
		}else{
			tempx = 0;
		}
		
		if(type==2){//pirahana
			updateimage(enemy,f,x-(int)clipx*eachsize,y-h+(int)tempy,(int)clipx*eachsize,(int)clipx*eachsize+eachsize,0,h-(int)tempy);
			g.drawImage(enemy, x-tempx, y-h+(int)tempy, x-tempx+eachsize, y, (int)clipx*eachsize, 0, (int)clipx*eachsize+eachsize, h-(int)tempy, f);
		}else{
			if(y>300&&y-h<300){
				System.out.println(y+"\t"+h+"\t"+(h-y+300));
				updateimage(enemy, f, x-(int)clipx*eachsize, y-h, (int)clipx*eachsize, (int)clipx*eachsize+eachsize, 0, h-y+300);
				
			}else if(y-h<300){
				updateimage(enemy, f, x-(int)clipx*eachsize, y-h, (int)clipx*eachsize, (int)clipx*eachsize+eachsize);
				g.drawImage(enemy, x-tempx, y-h, x+eachsize-tempx, y, (int)clipx*eachsize, 0, (int)clipx*eachsize+eachsize, h, f);//System.out.println("Painting item");
			}else	killed = true;
				
			
			
		}
		//g.drawImage(item, x, y, f);
	}

	public boolean isAppeared() {
		return appeared;
	}
	public boolean isKilled() {
		return killed;
	}
	public boolean isStomped() {
		return stomped;
	}
	public boolean isTransformed() {
		return transformed;
	}
	public boolean isKickable() {
		return touchable;
	}public void setTransformcounter(double add) {
		//System.out.println("adding");
		transformcounter += add;
		if(transformcounter>20)	touchable = true;
	}
}