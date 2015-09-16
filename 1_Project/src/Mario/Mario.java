package Mario;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;


public class Mario {
	Frames f;
	private int width,height;
	private Ground g;
	private Enemy[][] e;
	private BufferedImage[] mario = new BufferedImage[30];
	private int x=200,y=260,state=0;
	private double dx, dy, dt=.25, gravity = 15;
	private int[] sX = new int[100],sY = new int[100];
	private boolean[][][] white;
	private boolean flying = false, onblock = false, tempdead = false, dead = false, transforming = false;
	private String current = "small", special = null; 
	private int btype, recoveringtimer, startimer;
	private int[] bnum;
	
	private int right0,right1,right2,right3,left0,left1,left2,left3,rightJ,leftJ,die;
	private ActionListener recover = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			recoveringtimer++;
			System.out.println("recovering :"+recoveringtimer);
			if(recoveringtimer==70){
				recoveringtimer = 0;
				special = null;
				attacked.stop();
			}
		}
	};
	private Timer attacked = new Timer(50,recover);
	private ActionListener star = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			startimer++;
			System.out.println("staring :"+startimer);
			if(startimer==150){
				special = null;
				attacked.stop();
			}
		}
	};
	private Timer ate = new Timer(50,recover);
	
	
	public int getHeight() {
		return height;
	}
	public ActionListener getStar() {
		return star;
	}
	public Mario(Frames f, Ground g, Enemy[][] e, Pictures p, int btype, int[] bnum){
		mario  = p.getMario();
		this.g = g;
		this.e = e;
		for(int i=0;i<mario.length;i++){
			sX[i] = mario[i].getWidth();
			sY[i] = mario[i].getHeight();
			white = p.getWhitemario();
		}
		if(f.j.isControllerConnected())dx = 3;
		else	dx = 3.3;
		dy = 0;
		width  = f.width;
		height = f.height;
		this.f = f;
		this.bnum = bnum;
		this.btype = btype;
		changeMario();
		state  = right0;
	}
	public void checkFlying(int d){
		if((y<g.ground[x]&&y<g.ground[x+sX[state]])&&!onblock){
			flying = true;
			if(d==0)	state = rightJ;
			else		state = leftJ;
		}else if(onblock){
			if(d==0)	state = right0;
			else		state = left0;
			flying = false;
		}else	flying = false;
/*		//System.out.println(g.ground[x]+" & "+y+sY[state]+" "+state);
		if(y<g.ground[x]&&!onblock&&d==0){
			flying = true;
			state = rightJ;
		}else if(y<g.ground[x+sX[state]/2]&&!onblock&&d==1){
			flying = true;
			state = leftJ;
		}else	flying = false;
*/	}
	public boolean getFlying(){
		return flying;
	}public void setFlying(boolean flying) {
		this.flying = flying;
	}public boolean istempDead() {
		return tempdead;
	}public void settempDead(boolean dead) {
		this.tempdead = dead;
	}public boolean isDead() {
		return dead;
	}public void setDead(boolean dead) {
		this.dead = dead;
	}public boolean isOnblock() {
		return onblock;
	}public void setOnblock(boolean onblock) {
		this.onblock = onblock;
	}public double getDt() {
		return dt;
	}public double getGravity() {
		return gravity;
	}
	
	public void update(String xmove, String ymove, String d, int count, Frames f, Objects o[][]){
		//System.out.println("Updating Mario at "+System.currentTimeMillis());
/*		if(special!=null){System.out.println("STARMAN");
			atetime++;
			if(special.equals("starman")){
				if(atetime == 2000)	special = null;
			}else{
				if(atetime == 500){	special = null;	atetime=0;}
			}
		}
*/		
		if(d.equals("RIGHT")){
			if(ymove.equals("JUMP")){//System.out.println("jumping");
				if(xmove.equals("WALK")){
					x += dx;
				}else if(xmove.equals("RUN")){
					x += dx*2;
				}
				if(dy==0&&!flying){
					dy = -55;
					onblock = false;	flying = true;
				}else{
					dy += dt*gravity;//System.out.println(dy);
					y  += dy*dt +.5*gravity*dt*dt;//System.out.println(tempdy);
				}
				state = rightJ;
				checkBlockCollision(o);
				sidewallOperation(x, y, d.toString());
				underwallOperation(x, y, d.toString());
				
			}else if(ymove.equals("CROUCH")){
				state = die;
			}else if(ymove.equals("STOP")){
				if((xmove.equals("WALK")||xmove.equals("RUN"))){
					//System.out.println("changing state");
					if(count==0)		state = right2;
					else if(count==1)	state = right1;
					else{count = 0;		state = right3;}
				if(xmove.equals("WALK"))	x += dx;
				else						x += dx*2;
				
				}else{
					state = right1;
				}
				if(flying){
					dy += dt*gravity;//System.out.println(dy);
					y  += dy*dt +.5*gravity*dt*dt;
					state = rightJ;
					checkBlockCollision(o);
					
					//upperwallOperation(x, y-sY[state], d.toString());
					underwallOperation(x, y, d.toString());
				}else if(onblock&&!Ground.thereisblock[x+sX[state]]){
					onblock = false;	flying = true;
				}
				sidewallOperation(x, y, d.toString());
			}checkFlying(0);
		}else if(d.equals("LEFT")){
			
			if(ymove.equals("JUMP")){
				if(xmove.equals("WALK")){
					x -= dx;
				}else if(xmove.equals("RUN")){
					x -= dx*2;
				}if(x<0){
					x=0;//System.out.println("<0");
				}
				if(dy==0&&!flying){
					dy = -55;
					onblock = false;	flying = true;
				}
				else{
					dy += dt*gravity;//System.out.println(dy);
					y  += dy*dt +.5*gravity*dt*dt;//System.out.println(tempdy);
				}
				state = leftJ;
				checkBlockCollision(o);
				sidewallOperation(x, y, d.toString());
				underwallOperation(x, y, d.toString());
			}else if(ymove.equals("CROUCH")){
				state = die;
			}else if(ymove.equals("STOP")){
				if((xmove.equals("WALK")||xmove.equals("RUN"))){
					if(count==0)	state = left2;
					else{count = 0;	state = left3;}
				if(xmove.equals("WALK"))	x -= dx;
				else						x -= dx*2;
				if(x<0){
					x=0;//System.out.println("<0");
				}
				}else{
					state = left1;
				}
				if(flying){
					dy += dt*gravity;//System.out.println(dy);
					y  += dy*dt +.5*gravity*dt*dt;
					state = leftJ;
					checkBlockCollision(o);
					//upperwallOperation(x, y-sY[state], d.toString());
					underwallOperation(x, y, d.toString());
				}else if(onblock&&!Ground.thereisblock[x+sX[state]/2]){
					onblock = false;	flying = true;
				}
				sidewallOperation(x, y, d.toString());
			}
			if(x<0){
				x=0;//System.out.println("<0");
			}
			checkFlying(1);
		}
		for(int i=0;i<Pictures.maxE;i++){
			for(int j=0;j<BasicEnemyItemBlockInfo.eneNum[i];j++){
				if(!e[i][j].isAppeared()){
					boolean found = e[i][j].checkAppear(x);
					if(found)break;
				}
			}
		}
		//System.out.println(x/20+1);
	}
	private void checkBlockCollision(Objects[][] o){
		if(Ground.thereisblock[x]||Ground.thereisblock[x+sX[state]]){
			for(int i=0;i<btype;i++){
				for(int j=0;j<bnum[i];j++){
					if(x+sX[state]/2>o[i][j].getX()&&x<o[i][j].getX()+20){
						if(i==0){
							if(!o[i][j].isBroke()){
								if(dy<=0)		o[i][j].checkUpperCollision(this);
								else			o[i][j].checkLowerCollision(this);
							}
						}else if(i!=3){//System.out.println("checking collision");
							if(dy<=0)		o[i][j].checkUpperCollision(this);
							else			o[i][j].checkLowerCollision(this);
						}else{
							if(dy<=0)		o[i][j].checkUpperCollision(this);
							else{
								if(o[i][j].isBroke())
									o[i][j].checkLowerCollision(this);
							}
						}
						break;
					}
				}
			}
		}
	}
	
	public void stop(String s){
		if(s.equals("RIGHT")){
			state = right0;
		}else if(s.equals("LEFT")){
			state = left0;
		}else if(s.equals("CROUCH")){
			state = die;
		}
	}
	private void sidewallOperation(int x, int y, String s){
		if(s.equals("RIGHT")){
			if(y>g.ground[x+sX[state]]){
				this.x = ((int)(x/20)+1)*20-sX[state];//System.out.println(x/20);
				//System.out.println("MOVE TO LEFT");
				
			}
		}else if(s.equals("LEFT")){
			if(y>g.ground[x]){//System.out.println("MOVE TO RIGHT");
				while(x%20<19){
					x++;//System.out.println(x);
				}x++;
				
				this.x = x;
			}
		}
	}
	private void underwallOperation(int x, int y, String s){
		
		if(y>g.ground[x]&&s.equals("RIGHT")&&dy==0){
			this.y = g.ground[x];
			state = right0;		dy = 0;
			flying = false;
		}else if(y>g.ground[x+sX[state]]&&s.equals("RIGHT")&&dy>0){
			this.y = g.ground[x+sX[state]];
			state = right0;		dy = 0;
			flying = false;
		}else if(y>g.ground[x+sX[state]]&&s.equals("LEFT")&&dy==0){
			this.y = g.ground[x+sX[state]];
			state = left0;		dy = 0;
			flying = false;
		}else if(y>g.ground[x]&&s.equals("LEFT")&&dy>0){
			this.y = g.ground[x];
			state = left0;		dy = 0;
			flying = false;
		}
	}
	public void deadOperation(){
		if(y<f.height+sY[state]){
			dy += dt*10;
			y  += dy*dt +.5*10*dt*dt;
		}else{
			dead = true;System.out.println("mario dead : true");
		}
	}
	public void powerdown(){
		if(current.equals("small")){
			tempdead = true;
			dy = -30;
		}else if(current.equals("super")||current.equals("fire")){
			System.out.println("mario powered down");
			special = "recovering";
			recoveringtimer = 0;
			
			attacked.start();
			current = "small";
			changeMario();
		}
	}
	public void powerup(int n){	//n:0 = super mushroom
		if(!current.equals("fire")){//n:1 = fireman
			if(n==1){
				current = "fire";
				changeMario();
			}else if(current.equals("small")){
				current = "super";
				changeMario();
			}
			transforming = true;
		}System.out.println("mario powered up "+transforming);
	}
	public void updateshape(String d){
		System.out.println("updating shape");
		if(d.equals("RIGHT"))	state = right0;
		else					state = left0;
		transforming = false;
	}
	public void ateStar(){
		special = "starman";
		startimer = 0;
		ate.start();
	}
	private void changeMario(){
		
		if(current.equals("small")){
			right0 = 0;
			right1 = 1;
			right2 = 2;
			right3 = 3;
			left0  = 4;
			left1  = 5;
			left2  = 6;
			left3  = 7;
			rightJ = 8;
			leftJ  = 9;
			die    = 10;
		}else if(current.equals("super")){
			right0 = 11;
			right1 = 12;
			right2 = 13;
			right3 = 14;
			left0  = 15;
			left1  = 16;
			left2  = 17;
			left3  = 18;
			rightJ = 19;
			leftJ  = 20;
		}else if(current.equals("fire")){
			right0 = 23;
			right1 = 24;
			right2 = 25;
			right3 = 26;
			left0  = 27;
			left1  = 28;
			left2  = 29;
			left3  = 30;
			rightJ = 31;
			leftJ  = 32;
		}
		
		
	}
	

	private BufferedImage updateimage(BufferedImage img, int counter, Frames f, int x, int y, int startingx){
		int w = img.getWidth();
        int h = img.getHeight();
		for(int i=0;i<h;i++){
			for(int j=startingx;j<w;j++){
				if (white[counter][i][j]){
					img.setRGB(j,i,f.getColor(x+j, y+i)); 
				}
			}
		}
		return img;
	}
	private BufferedImage updatedeadimage(BufferedImage img, Frames f, int x, int y){
		int w = img.getWidth();
		int h;
		if(y>f.height){
			h = 14-y+f.height;//System.out.println(y+"  "+sY[10]+" + "+h);
		}
		else			h = img.getHeight();
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				if (white[10][i][j]){
					img.setRGB(j,i,f.getColor(x+j, y-sY[10]+i)); 
				}
			}
		}
		return img;
	}
	private BufferedImage updateimage(BufferedImage img, int counter, int x, int startingy, int finishingy, Frames f){
		int w = img.getWidth();
//        int h = img.getHeight();
		for(int i=startingy;i<finishingy;i++){
			for(int j=0;j<w;j++){
				if (white[counter][i][j]){
					img.setRGB(j,i,f.getColor(x+j, i-startingy)); 
				}
			}
		}
		return img;
	}
	public void paint(Graphics g, Frames f){
		//System.out.println(x+" "+sX[state]+" & "+y+" "+sY[state]);
		if(y-sY[state]<0)	mario[state] = updateimage(mario[state], state, x, 0,sY[state]-y, f);
		else if(y>300){
							mario[state] = updateimage(mario[state], state, x, y-sY[state], sY[state]-y+300, f);
		if(y-sY[state]>=300)tempdead = true;
		}
		else				mario[state] = updateimage(mario[state], state, f, x, y-sY[state], 0);
		if(special==null){
			if(x<250)				g.drawImage(mario[state], x, y-sY[state], f);
			else if(x>width-250)	g.drawImage(mario[state], width-x, y-sY[state], f);
			else 					g.drawImage(mario[state], 250, y-sY[state], f);
		}else if(special.equals("recovering")){
			if(recoveringtimer%2==0){
				if(x<250)				g.drawImage(mario[state], x, y-sY[state], f);
				else if(x>width-250)	g.drawImage(mario[state], width-x, y-sY[state], f);
				else 					g.drawImage(mario[state], 250, y-sY[state], f);
			}
		}else if(special.equals("starman")){
			if(recoveringtimer%2==0){
				if(x<250)				g.drawImage(mario[state], x, y-sY[state], f);
				else if(x>width-250)	g.drawImage(mario[state], width-x, y-sY[state], f);
				else 					g.drawImage(mario[state], 250, y-sY[state], f);
			}
		}
	}
	public void paintdead(Graphics g, Frames f){
		mario[10] = updatedeadimage(mario[10], f, x, y);
		if(x<250)				g.drawImage(mario[10], x, y-sY[10], f);
		else if(x>width-250)	g.drawImage(mario[10], width-x, y-sY[10], f);
		else 					g.drawImage(mario[10], 250, y-sY[10], f);
	}
	public int getX()	 			{	return x;}
	public void setX(int x) 		{	this.x = x;}
	public int getY()				{	return y;}
	public void setY(int y) 		{	this.y = y;}
	public int getsX() 				{	return sX[state];}
	public int getsY() 				{	return sY[state];}
	public int getdX() 				{	return (int) dx;}
	public int getdY() 				{	return (int) dy;}
	public void setdY(int dy)		{	this.dy = dy;}
	public int getWidth() 			{	return width;}
	public String getSpecial() 		{	return special;}
	public String getCurrent() 		{	return current;}
	public boolean isTransforming() {	return transforming;}
}
