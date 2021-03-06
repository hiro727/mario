package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Characters {
	private Frames f;
	private int insetL;
	private int insetT;
	private Image character;
	private Map map;
	private int width;
	
	
	
	private int w, h;
	private int x;
	private double y, dx, dy, tempy, dt = .35, gravity = 9.81;
	private double RIGHT, LEFT, UP;
	private double counter = 0;
	private int[] sx = new int[3], cropx = new int[3];
	
	private boolean right = true, left, up, jump, fall, max, swim, stopped;
	private boolean run, walk;
	public Characters(Frames f) {
		map = new Map(this);
		this.f = f;
		x  = 0;
		y  = 0;
		RIGHT = 4.8;
		LEFT  = -4.8;
		UP	  = -4.0;
		dy = 0;
		insetL = f.getInsets().left;
		insetT = f.getInsets().top;
		width = f.getTotalwidth();System.out.println("width:: "+width);
		try {
			////character = ImageIO.read(new File("test/images/characters/m0.bmp"));
			Image temp = ImageIO.read(new File("test/images/characters/m0.bmp"));
			character = Pictures.makeColorTransparent(temp, Color.white);
			w = character.getWidth(f);
			h = character.getHeight(f);System.out.println("w+h: "+w+" "+h);
			cropx[0] = 0;
			cropx[1] = 13;
			cropx[2] = 29;
			sx[0] = 13;
			sx[1] = 16;
			sx[2] = 17;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){//System.out.println(RIGHT+" "+LEFT);
		dx = 0;
		dy = 0;
		if(walk||run){
			if(right){
				if(walk)
					dx = RIGHT;
				else
					dx = RIGHT*2;
				if(jump||fall)
					dx = dx/5*3;
			}
			else if(left){
				if(walk)
					dx = LEFT;
				else
					dx = LEFT*2;
				if(jump||fall)
					dx = dx/5*3;
				if(x-dx<=0)
					dx = -x;
			}
			if(!(jump||fall)){
				if(walk)
					counter+=.3;
				else
					counter+=0.5;
			if((int)counter==2)
				counter=0;
			}
		}//System.out.println(jump+" "+fall+" & "+stopped);
		if(jump||fall||max){//System.out.print(tempy+" ");
			tempy = tempy+dt*gravity;//System.out.print(tempy+" ");
			dy = tempy*dt+.5*gravity*dt*dt;//System.out.println(tempy);
			counter = 2;
		}
		
		//System.out.println(dx+" "+dy);
		int[] xy = map.checkCollision(x, (int) y, sx[(int)counter], h, dx, dy);
		if(jump){//System.out.println(stopped);
			if(xy[1]==y){System.out.println("reached max");
				jump=false;
				max=true;
			}
		}else if(max){System.out.println("floating");
			max = false;
			fall = true;
		}else if(fall){//System.out.println(stopped);
			if(xy[1]==y){
				fall=false;System.out.println("landed");
				counter = 0;
				dy = 0;
				if(stopped){
					stopped = false;
					//if(!(walk||run))
					stop();
				}
			}
		}
		x = xy[0];
		y = xy[1];
		if(x < 0)
			x = 0;
		else if(x+sx[(int)counter]>width)
			x = width-sx[(int)counter];
		checkScreenPosition();//System.out.println(jump+" "+fall+" "+max);
		
		
	}
	public void moveRight(int n) {
		if(jump||fall){
			if(left)
				return;
		}
		if(n==0)
			walk = true;
		else
			run = true;
		if(dx == 0)
			counter = 1;
		left  = false;
		right = true;
	}
	public void moveLeft(int n) {
		if(jump||fall){
			if(right)
				return;
		}
		if(n==0)
			walk = true;
		else
			run = true;
		if(dx == 0)
			counter = 1;
		right = false;
		left  = true;
	}
	public void moveUp() {
		if(!(jump||fall)){
			tempy = -45;
			jump = true;
			counter = 2;
		}
	}
	public void moveDown() {
		
	}
	public void stop(){
		if(!(jump||fall)){
			walk = false;
			run  = false;
			tempy = 0;
			dx = 0;
			counter = 0;
		}else{
			System.out.println("stopped");
			stopped = true;
		}
	}
	private void checkScreenPosition() {//System.out.println("x: "+x+"\t y: "+y);
		if(x<300)
			f.setScreenx(0);
		else if(x>width-300)
			f.setScreenx(width-600);
		else
			f.setScreenx(x-300);
	}
	public int getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public double getTempy() {
		return tempy;
	}
	public double getDt() {
		return dt;
	}
	public double getGravity() {
		return gravity;
	}
	public int getCounter() {
		return (int) counter;
	}
	public void setTempy(double tempy) {
		this.tempy = tempy;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public void setFall(boolean fall) {
		this.fall = fall;
	}
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	public void setWalk(boolean walk) {
		this.walk = walk;
	}
	public void setRun(boolean run) {
		this.run = run;
	}
	public boolean isJump() {
		return jump;
	}
	public boolean isFall() {
		return fall;
	}
	public boolean isMax() {
		return max;
	}
	public void setCounter(double counter) {
		this.counter = counter;
	}
	public Image getCharacter() {
		return character;
	}
	public void paint(Graphics g){
		if(right){
		if(x<=300)
			g.drawImage(character, insetL+x, insetT+(int)y, insetL+x+sx[(int)counter], insetT+(int)y+h, cropx[(int)counter],0,cropx[(int)counter]+sx[(int)counter],h,f);
		else if(x>=width-300)
			g.drawImage(character, insetL+600+x-width, insetT+(int)y, insetL+600+x-width+sx[(int)counter], (int) (insetT+y+h), cropx[(int)counter],0,cropx[(int)counter]+sx[(int)counter],h,f);
		else
			g.drawImage(character, insetL+300, insetT+(int)y, insetL+300+sx[(int)counter], (int) (insetT+y+h), cropx[(int)counter],0,cropx[(int)counter]+sx[(int)counter],h,f);
		}else if(left){
			if(x<=300)
				g.drawImage(character, insetL+x+sx[(int)counter], insetT+(int)y, insetL+x, (int) (insetT+y+h), cropx[(int)counter],0,cropx[(int)counter]+sx[(int)counter],h,f);
			else if(x>=width-300)
				g.drawImage(character, insetL+600+x-width+sx[(int)counter], insetT+(int)y, insetL+600+x-width, (int) (insetT+y+h), cropx[(int)counter],0,cropx[(int)counter]+sx[(int)counter],h,f);
			else
				g.drawImage(character, insetL+300+sx[(int)counter], insetT+(int)y, insetL+300, (int) (insetT+y+h), cropx[(int)counter],0,cropx[(int)counter]+sx[(int)counter],h,f);
		}
/*		if(x<300)
			g.fillRect(insetL+x, insetT+y, sx[(int)counter], h);
		else
			g.fillRect(insetL+300, insetT+y, sx[(int)counter], h);
		g.setColor(Color.white);
		g.drawString("x:  "+x, f.getWidth()-70, 50);
		g.drawString("y:  "+y, f.getWidth()-70, 60);
		g.drawString("dx: "+dx, f.getWidth()-70, 70);
		g.drawString("dy: "+dy, f.getWidth()-70, 80);
*/	}

	public boolean isRight() {
		return right;
	}
}
