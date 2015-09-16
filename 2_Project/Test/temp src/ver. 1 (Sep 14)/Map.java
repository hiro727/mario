package test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Map {
	private static Frames f;
	private Shape shape;
	private static Shape image;
	private static GeneralPath gp;
	private static GeneralPath gpimage;
	private static Rectangle object;
	
	private static int x, y, w, h;
	private static double dx, dy;
	private Object what;
	
	//private static double dt = .25, gravity = 9.81;
	public Map(Frames f) {
		Map.f = f;
		gp = new GeneralPath();
		gpimage = new GeneralPath();
		image  = gpimage;
		object = new Rectangle();
	}
	public Map(Object what) {
		this.what = what;
		shape  = gp;
	}
	public static void setPoint(int x, int y){//System.out.println("x: "+x+", y: "+y);
		gp.moveTo(x, y);
		gp.lineTo(x+30, y);
		gp.lineTo(x+30, y+25);
		gp.lineTo(x, y+25);
		gp.closePath();
		gpimage.moveTo(x+f.getInsets().left, y+f.getInsets().top);
		gpimage.lineTo(x+f.getInsets().left+30, y+f.getInsets().top);
		gpimage.lineTo(x+f.getInsets().left+30, y+f.getInsets().top+25);
		gpimage.lineTo(x+f.getInsets().left, y+f.getInsets().top+25);
		gpimage.closePath();
	}
	public static void loadMap(){
		File file = new File("test/map.txt");
		try {
			BufferedReader b = new BufferedReader(new FileReader(file));
			
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int[] checkCollision(int x, int y, int w, int h, double dx, double dy){
		shape = Blocks.addBlockPositions(gp, x);
		//System.out.println(x+" "+y+" "+dx+" "+dy);
		if(dy == 0 && !shape.intersects(x+2, y+1, w-4, h-1)){	//check flying
			if(!((Characters) what).isMax()){//System.out.println("turning point");
				dy=1.8;
				((Characters) what).setDy(dy);
				((Characters) what).setFall(true);
			}
		}
		object.setBounds((int) (x+dx+2), (int) (y+dy+1), w-4, h-2);
		if(isValidMovement())
			return new int[]{(int) (x+dx), (int) (y+dy)};
		
		Map.x  = x;
		Map.y  = y;
		Map.w  = w;
		Map.h  = h;
		Map.dx = dx;
		Map.dy = dy;
		
		return appropriateMovement();
		
	}
	private boolean isValidMovement(){
		if(shape.intersects(object))
			return false;
		//System.out.println(true);
		return true;
	}
	private int[] appropriateMovement(){
		if(dy == 0){
			double tenthx = dx/10;
			if(dx > 0){			//moving to right
				//System.out.println("moving to right");
				for(int i=0;i<10;i++){
					dx -= tenthx;
					object.setBounds(x+2, y+1, (int) (dx+w-4), h-2);
					if(isValidMovement())
						return new int[]{(int) (x+dx), (int) (y+dy)};
				}
			}else if(dx < 0){	//moving to left
				//System.out.println("moving to left");
				for(int i=0;i<10;i++){
					dx -= tenthx;
					object.setBounds((int) (x+dx+2), y+1, w-4, h-2);
					if(isValidMovement())
						return new int[]{(int) (x+dx), y};
				}
			}else{				//not moving
				//System.out.println("not moving");
				return new int[]{x, y};	//?
			}
		}else if(dy > 0){		//FALLING
			double tenthx = dx/10;
			double tenthy = dy/10;
			if(dx == 0){		//moving straight down
				//System.out.println("moving straight down");
				dy = 0;
				for(int i=0;i<10;i++){
					dy += tenthy;
					object.setBounds(x+2, y+1, w-4, (int) (dy+h-2));
					if(!isValidMovement()){
						return new int[]{x, (int) (y+dy-tenthy)};
					}
				}return new int[]{x, (int) (y+dy)};
				
			}else{	//moving to right down
				//System.out.println("moving to right/left down");
				dx = 0;
				dy = 0;
				for(int i=0;i<10;i++){
					dx += tenthx;
					object.setBounds((int) (x+dx+2), (int) (y+dy+1), w-4, h-2);
					if(!isValidMovement()){
						dx -= tenthx;
						((Characters) what).setWalk(false);
						((Characters) what).setRun(false);
						break;
					}
				}
				for(int i=0;i<10;i++){
					dy += tenthy;
					object.setBounds((int) (x+dx+2), (int) (y+dy+1), w-4, h-2);
					if(!isValidMovement()){
						dy -= tenthy;
						break;
					}
				}return new int[]{(int) (x+dx), (int) (y+dy)};
			}
		}else{			//JUMPING
			double tenthx = dx/10;
			double tenthy = dy/10;
			if(dx == 0){		//moving straight up
				//System.out.println("moving straight up");
				dy = 0;
				for(int i=0;i<10;i++){
					dy += tenthy;
					object.setBounds(x+2, (int) (y+dy+1), w-4, (int) (h-2));
					if(!isValidMovement()){
						HeadButt();
						((Characters) what).setWalk(false);
						((Characters) what).setRun(false);
						return new int[]{x, (int) (y+dy-tenthy)};
					}
				}return new int[]{x, (int) (y+dy)};
				
			}else{	//moving to right/left up
				//System.out.println("moving to right up");
				dx = 0;	dy = 0;
				for(int i=0;i<10;i++){
					dx += tenthx;
					object.setBounds((int) (x+dx+2), (int) (y+dy+1), w-4, h-2);
					if(!isValidMovement()){
						((Characters) what).setWalk(false);
						((Characters) what).setRun(false);
						dx -= tenthx;	break;
					}
				}
				for(int i=0;i<10;i++){
					dy += tenthy;
					object.setBounds((int) (x+dx+2), (int) (y+dy+1), w-4, h-2);
					if(!isValidMovement()){
						dy -= tenthy;
						HeadButt();
						break;
					}
				}return new int[]{(int) (x+dx), (int) (y+dy)};
				
			}
		}
		return new int[]{x, y};
	}
	
	
	private void HeadButt() {
		((Characters) what).setDx(((Characters) what).getDx()/3*4);
		((Characters) what).setTempy(-((Characters) what).getTempy());
		((Characters) what).setJump(false);
		((Characters) what).setFall(true);
		Blocks.checkIfExist(x+dx, y+dy-10);
	}
	public static void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(image);
/*		g.setColor(Color.white);
		g.drawString("x:  "+x, f.getWidth()-70, 50);
		g.drawString("y:  "+y, f.getWidth()-70, 60);
		g.drawString("dx: "+dx, f.getWidth()-70, 70);
		g.drawString("dy: "+dy, f.getWidth()-70, 80);
*/		
	}
}
