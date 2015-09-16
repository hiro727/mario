package Mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Item {
	Ground g;
	BasicEnemyItemBlockInfo b;
	Frames f;
	private BufferedImage item;
	private boolean[][] white;
	private int type;
	private int x, y, h, tempx;
	private double dx,dy,clipx=0,speed, tempy;
	private int min,max, eachsize;
	private boolean touched = false, collected = false;
	
	public Item(Ground g, Objects o, Pictures p, Frames f, int x, int y, String s) {
		this.g	 = g;									//type 0: coin
		this.f	 = f;									//type 1: coins
		this.x	 = BasicEnemyItemBlockInfo.xB[x][y]+10;	//type 2: super Mario/fire flower
		this.y 	 = BasicEnemyItemBlockInfo.yB[x][y];	//type 3: starman
		System.out.println(x+"  "+y);
		type	 = BasicEnemyItemBlockInfo.itemB[x][y];	//type 4: 1UP Mario
		if(type==1||type==0){									
			item 	 = Pictures.item[0];
			white  	 = p.getWhiteitem(0);
			dx		 = BasicEnemyItemBlockInfo.dxI[0];
			dy		 = BasicEnemyItemBlockInfo.dyI[0];
			min		 = BasicEnemyItemBlockInfo.minI[0];
			max		 = BasicEnemyItemBlockInfo.maxI[0];
			eachsize = BasicEnemyItemBlockInfo.eachsizeI[0];
			speed	 = BasicEnemyItemBlockInfo.speedI[0];
			touched  = true;
			tempy	 = -20;
		}else if(type==2){
			if(s.equals("small")){
				item	 = Pictures.item[1];
				white  	 = p.getWhiteitem(1);
				dx		 = BasicEnemyItemBlockInfo.dxI[1];
				dy		 = BasicEnemyItemBlockInfo.dyI[1];
				min		 = BasicEnemyItemBlockInfo.minI[1];
				max		 = BasicEnemyItemBlockInfo.maxI[1];
				eachsize = BasicEnemyItemBlockInfo.eachsizeI[1];
				speed	 = BasicEnemyItemBlockInfo.speedI[1];
			}else{
				item	 = Pictures.item[3];
				white  	 = p.getWhiteitem(3);
				dx		 = BasicEnemyItemBlockInfo.dxI[3];
				dy		 = BasicEnemyItemBlockInfo.dyI[3];
				min		 = BasicEnemyItemBlockInfo.minI[3];
				max		 = BasicEnemyItemBlockInfo.maxI[3];
				eachsize = BasicEnemyItemBlockInfo.eachsizeI[3];
				speed	 = BasicEnemyItemBlockInfo.speedI[3];
			}
		}else if(type==4){
			item	 = Pictures.item[2];
			white  	 = p.getWhiteitem(2);
			dx		 = BasicEnemyItemBlockInfo.dxI[2];
			dy		 = BasicEnemyItemBlockInfo.dyI[2];
			min		 = BasicEnemyItemBlockInfo.minI[2];
			max		 = BasicEnemyItemBlockInfo.maxI[2];
			eachsize = BasicEnemyItemBlockInfo.eachsizeI[2];
			speed	 = BasicEnemyItemBlockInfo.speedI[2];
		}else if(type==3){
			item	 = Pictures.item[4];
			white  	 = p.getWhiteitem(4);
			dx		 = BasicEnemyItemBlockInfo.dxI[4];
			dy		 = BasicEnemyItemBlockInfo.dyI[4];
			min		 = BasicEnemyItemBlockInfo.minI[4];
			max		 = BasicEnemyItemBlockInfo.maxI[4];
			eachsize = BasicEnemyItemBlockInfo.eachsizeI[4];
			speed	 = BasicEnemyItemBlockInfo.speedI[4];
			
		}
		h		 = item.getHeight();
		this.y -= h;
		this.x -= eachsize/2;
		System.out.println("initializing item");
	}
	public BufferedImage updateimage(BufferedImage img, Frames f, int x, int y, int startingx, int finishingx){
        int h = img.getHeight();//System.out.println("updating shape"+x+" "+startingx+" to "+y+" "+finishingx);
		for(int i=0;i<h;i++){
			for(int j=startingx;j<finishingx;j++){
				if (white[i][j]){
					img.setRGB(j,i,f.getColor(x+j, y+i)); 
				}
			}
		}
		return img;
	}
	private BufferedImage updateimage(BufferedImage img, int x, int startingy, int finishingy, Frames f){
		int w = img.getWidth();
//        int h = img.getHeight();
		for(int i=startingy;i<finishingy;i++){
			for(int j=0;j<w;j++){
				if (white[i][j]){
					img.setRGB(j,i,f.getColor(x+j, i-startingy)); 
				}
			}
		}
		return img;
	}
	public void update(Mario m){
		if(touched&&!collected){
			collectingOperation();
		}else if(!collected){
			int tester = (int)(clipx + speed);
			if(tester < max){
				clipx += speed;
			}else{
				tester=min;clipx = min;
			}
			x += dx;
			checkForCollision(m);
			fallingOperation(x,y);
			sidewallOperation(x, y);
		}
	}
	private void collectingOperation() {
		if(type==0||type==1){
			int tester = (int)(clipx + speed);
			if(tester < max){
				clipx += speed;
			}else{
				tester=min;clipx = min;
			}//System.out.println(tempy);
			if(tempy!=0)tempy += .25*20;
			else		collected = true;
		}
	}
	private void fallingOperation(int x, int y) {
		if(y+h<g.ground[x]&&dx>0&&!Ground.thereisblock[x]){
			//for(int i=0;i<)
			dy += .25*10;
			y  += dy*.25+.5*10*.25*.25;
			if(y+h>g.ground[x]){
				y = g.ground[x]-h;
			}this.y = y;
		}else if(y+h<g.ground[x+eachsize]&&dx<0&&!Ground.thereisblock[x+eachsize]){
				dy += .25*10;
				y  += dy*.25+.5*10*.25*.25;
			if(y+h>g.ground[x+eachsize]){
				y = g.ground[x+eachsize]-h;
			}this.y = y;
		}//System.out.println(y);
		
	}
	private void sidewallOperation(int x, int y){
		if(dx>0){
			if(y>g.ground[x+eachsize]){//System.out.println("MOVE TO LEFT");
				while(x%20>24-eachsize){
					x--;//System.out.println(x);
				}
				this.x  = x;
				dx = - dx;
			}
		}else if(dx<0){
			if((x<0||x==0)){
    			x=0;
				dx = - dx;
			}
			if(y>g.ground[x]){//System.out.println("MOVE TO RIGHT");
				while(x%20<19){
					x++;//System.out.println(x);
				}x++;
				dx = - dx;
				this.x = x;
			}
		}
	}
	private void checkForCollision(Mario m){//System.out.println("identifying...item");
		if((m.getX()+m.getsX()>x+1&&m.getX()+1<x+eachsize)){
			
			if(m.getY()>y&&m.getY()-m.getsY()<=y+h){
				collectedOperation(type, m);
				collected = true;
			}
			
		}
	}
	private void collectedOperation(int type, Mario m) {
		if(type==0){		//coin
			System.out.println("coin collected");
			
		}else if(type==1){
			System.out.println("multiple coins collected");
		}else if(type==2){	//super mushroom	//fire  flower
			if(m.getCurrent().equals("small")){
				m.powerup(0);
				System.out.println("super mushroom collected");
			}else{
				m.powerup(1);
				System.out.println("fire  flower collected");
			}
		}else if(type==3){	//startman
			System.out.println("startman collected");
			m.ateStar();
		}else if(type==4){	//1UP   mushroom
			System.out.println("1UP   mushroom collected");
		}
	}
	public void  paint(Graphics g, Mario m){
		if(touched&&!collected){
			if(m.getX()>250&&m.getX()<m.getWidth()-250){
				tempx = m.getX()-250;
			}else if(m.getX()>m.getWidth()-300){
				tempx = m.getX()-250;
			}else{
				tempx = 0;
			}
			if(type==0||type==1){
				updateimage(item, f, x-(int)clipx*eachsize, y+(int)(tempy*.25 +.5*20*.25*.25), (int)clipx*eachsize, (int)clipx*eachsize+eachsize);
				g.drawImage(item, x-tempx, y+(int)(tempy*.25 +.5*20*.25*.25), x-tempx+eachsize, y+(int)(tempy*.25 +.5*20*.25*.25)+h, (int)clipx*eachsize, 0, (int)clipx*eachsize+eachsize, h, f);
			}
		}else if(!collected){
			if(m.getX()>250&&m.getX()<m.getWidth()-250){
				tempx = m.getX()-250;
			}else if(m.getX()>m.getWidth()-300){
				tempx = m.getX()-250;
			}else{
				tempx = 0;
			}
			//System.out.println(x+" "+y+" "+eachsize+" "+clipx*eachsize);
			if(y+h>300){
						updateimage(item, x, y, h-y+300, f);
						if(y>300)	collected = true;
			}
			else 		updateimage(item, f, x-(int)clipx*eachsize, y, (int)clipx*eachsize, (int)clipx*eachsize+eachsize);
			g.drawImage(item, x-tempx, y, x+eachsize-tempx, y+h, (int)clipx*eachsize, 0, (int)clipx*eachsize+eachsize, h, f);
		}
	}
}
