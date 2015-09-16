package project;

import java.awt.Graphics;

public class Enemies extends Objects{
	
	private double dx;
	
	public Enemies(int type, int x, int y) {
		super(1, type, x, y);
		
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