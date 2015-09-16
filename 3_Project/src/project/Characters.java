package project;

import java.awt.Color;
import java.awt.Graphics;

public class Characters extends Objects{
	
//	private static int STOP = 0, WALK_RIGHT = 1, WALK_LEFT = 2, RUN_RIGHT = 3, RUN_LEFT = 4;
//	private static int RIGHT = 0, LEFT = 1;
	private boolean flying;
	
	private static double dxx = 1.4;
	private static double[] dx = new double[]{0, dxx, -dxx, dxx * 1.7, -dxx * 1.7};
	
	private int status = 0;
	
	public Characters(int x, int y) {
		super(0, 0, x, y);
		System.out.println("\tcreating character");
		
	}
	public Characters(int x, int y, int w, int h) {
		super(0, 0, x, y, w, h);
		System.out.println("\tcreating character");
		
	}
//	@Override
//	public void update() {
////		super.update();
//		move(dx[status], 0);
//		if (Borders.isHit_left()) {
//			status = 2;
//		} else if (Borders.isHit_right()) {
//			status = 1;
//		}
//	}
	@Override
	public void update() {
		super.move(dx[status]);
		if (Borders.is_flying(x, y + h, w))
			flying = true;
		else
			flying = false;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {System.out.println(status+"\t"+(status == 0 ? "stop" : (status == 1 ? "right walk" : (status == 2 ? "left walk" : (status == 3 ? "right run" : "left run")))));
		this.status = status;
	}
	public boolean isFlying() {
		return flying;
	}
	@Override
	public void paint(Graphics g) {
//		super.paint(g);
		g.setColor(Color.red);
		g.fillOval(Main.inset.left + x, Main.inset.top + y, w, h);
	}
	public void paint_details(Graphics g) {
		g.setColor(Color.white);
		g.drawString("x:  "+ x + ".0",	 Main.inset.left + 10, Main.inset.top + 10);
		g.drawString("y:  "+ y + ".0",	 Main.inset.left + 10, Main.inset.top + 20);
		g.drawString("dx: "+ dx[status], Main.inset.left + 10, Main.inset.top + 30);
		g.drawString("dy: "+ dy, 		 Main.inset.left + 10, Main.inset.top + 40);
	}
	
}
