package project;

import java.awt.Graphics;

public class Items extends Objects{
	
	private double dx;
	
	public Items(int type, int x, int y) {
		super(2, type, x, y);
		
	}
	public void move(){
		super.move(dx);
		
	}
	public void update(){
		super.update();
	}
	
	public void paint(Graphics g){
		super.paint(g);
	}
	
}