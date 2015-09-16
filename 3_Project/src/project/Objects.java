package project;

import java.awt.Graphics;

public class Objects {
	
	protected int category, type;
	protected double counter;
	//category: type of object (character, enemy, item)
	//type: type within the category (mario, luigi)
	//counter: counter of the animation
	protected int x, y;
	protected int w, h;
	protected double dy;
	//x:  absolute x position
	//y:  absolute y position
	//w:  width of the image
	//h:  height of the image
	//dx: delta x
	//dy: delta y
	//u:  initial velocity
	//t:  time elapsed
	
	public Objects(int category, int type, int x, int y) {
		this.category = category;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	public Objects(int category, int type, int x, int y, int w, int h) {
		this.category = category;
		this.type = type;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void update() {
		counter += MyGraphics.getSpeed(category, type, (int) counter);
		if(counter >= MyGraphics.getMax(category, type, (int) counter))
			counter = MyGraphics.getMin(category, type, (int) counter);
	}
	public void move(double dx) {
		int[] temp = null;
		temp = Map.check_for_collision_with_walls(x, y, w, h, dx, dy);
		if (temp.length == 3) {
			dy = (double) temp[2]/10000;
		} else {
			dy = 0;
		}
		x = temp[0];
		y = temp[1];
		
	}
//	public void setU(double u) {
//		this.u = u;
//	}
//	public double getU() {
//		return u;
//	}
//	public void setT(double t) {
//		this.t = t;
//	}
//	public double getT() {
//		return t;
//	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public double getDy() {
		return dy;
	}
	
	
	public void paint(Graphics g) {
		g.drawImage(MyGraphics.getImgs(category, type, (int)counter), x, y, w, h, w * (int)counter, 0, w * ((int)counter + 1), h, Main.main);
		
	}
}