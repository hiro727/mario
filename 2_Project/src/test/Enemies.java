package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemies {
	private static Frames f;
	private static Characters m;
	private static Shape shape;
	private static Rectangle enemy;
	private static Image[] images;
	private static int[] clipx, h, n;
	private static Enemies start;
	private static Enemies shownStart;
	private static double[] dxs, dys, speed;
	private static int[] killable;
	private Enemies ptr;
	
	private int x, y, appearx;
	private double dx, dy, tempy;
	private int type;
	
	private boolean available = true;
	private boolean onScreen;
	
	private double counter;
	
	public Enemies(Frames f) {
		Enemies.f = f;
		File[] files = new File("test/images/enemies").listFiles();
		try{
			images = new Image[files.length];
			h	   = new int[images.length];
			n	   = new int[images.length];
		}catch (NullPointerException e){
			e.printStackTrace();
			System.exit(0);
		}
		try {
			for(int i=0;i<files.length;i++){
				images[i] = ImageIO.read(files[i]);
				images[i] = Pictures.makeColorTransparent(images[i], Color.white);
				h[i]	  = images[i].getHeight(f);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		enemy = new Rectangle();
	}
	public static void addCharacter(Characters m) {
		Enemies.m = m;
	}
	public static void defineData(double[] dx, double[] dy, int[] clipx, double[] speed, int[] killable){
		Enemies.dxs = dx;
		Enemies.dys = dy;
		Enemies.clipx = clipx;
		Enemies.speed = speed;
		Enemies.killable = killable;
		for(int i=0;i<images.length;i++){
			n[i] = images[i].getWidth(f)/clipx[i];
		}
	}
	public Enemies(int type, int x, int y, int appearx) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.appearx = appearx;
		dx = dxs[type];
		dy = dys[type];
		add(this);
		new Map(this);
	}
	public static void add(Enemies e){//System.out.println(e+" "+e.x);
		if(e.appearx < f.screenx+600)
			e.onScreen = true;//System.out.println(e.onScreen);
		if(start == null){
			start = e;
			return;
		}
		if(e.appearx < start.appearx || (e.appearx == start.appearx && e.x < start.x) || (e.appearx == start.appearx && e.x == start.x && e.y < start.y)){
			e.ptr = start;
			start = e;
			return;
		}

		Enemies temp1 = start;
		Enemies temp2 = start.ptr;
		while(temp2 != null){//System.out.println(temp2);
			if(e.appearx < temp2.appearx || (e.appearx == temp2.appearx && e.x < temp2.x) || (e.appearx == temp2.appearx && e.x == temp2.x && e.y < temp2.y)){
				e.ptr = temp2;
				temp1.ptr = e;
				return;
			}else{
				temp1 = temp1.ptr;
				temp2 = temp2.ptr;
			}
		}
		temp1.ptr = e;
	}
	
	
	public static void update(){//System.out.println("updating enemy...");
		checkShown();
		shape = Blocks.addBlockPositions(Map.getGp(), f.screenx+800);
		Enemies temp = shownStart;
		while(temp != null && temp.onScreen){//System.out.println(temp+" = [x: "+temp.x+", y: "+temp.y+", clipx: "+clipx[temp.type]+", dx: "+dx[temp.type]+", dy: "+dy[temp.type]);
			temp.counter+=speed[temp.type];
			if(temp.available){
				if(temp.counter >= n[temp.type]-1)
					temp.counter = 0;
				temp.checkCollisionWithWalls();
				temp.checkCollisionWithEachOther();
			}else{
				if(temp.counter >= speed[temp.type]*5)
					delete(temp);
			}
			temp = temp.ptr;
		}
	}
	private void checkCollisionWithWalls() {
		enemy.setBounds(x+2, y+1, clipx[type]-4, 16-1);
		if(!shape.intersects(enemy)){
			if(dy == 0){
				dy = 1.8;
			}else{
				tempy = tempy+m.getDt()*9.81;//System.out.print(tempy+" ");
				dy = tempy*m.getDt()+.5*9.81*m.getDt()*m.getDt();//System.out.println(tempy);
			}
		}else
			dy = 0;
		
		double tenthx = dx/10;
		double tenthy = dy/10;
		enemy.setBounds((int) (x+dx+2), (int) (y+dy+1), clipx[type]-4, 16-2);
		if(shape.intersects(enemy)){
			dx = 0;
			dy = 0;
			for(int i=0;i<10;i++){
				dx += tenthx;
				enemy.setBounds((int) (x+dx+2), (int) (y+dy+1), clipx[type]-4, 16-2);
				if(shape.intersects(enemy)){//System.out.println("side collided");
					dx -= tenthx;
					x += dx;
					dx = -tenthx*10;
					break;
				}else if(i==9)
					x += dx;
			}
			for(int i=0;i<10;i++){
				dy += tenthy;
				enemy.setBounds((int) (x+dx+2), (int) (y+dy+1), clipx[type]-4, 16-2);
				if(shape.intersects(enemy)){
					dy -= tenthy;
					y += dy;
					dy = 0;
					break;
				}
			}y += dy;
		}else{
			x += dx;
			y += dy;
		}
		if(x <= 0){
			x = 0;
			dx = -dx;
		}
		//System.out.println("x: "+x+" y: "+y);
	}
	private void checkCollisionWithEachOther(){
		Enemies temp = shownStart;
		while(temp != null && temp.onScreen){
			if(temp != this && temp.available){
				if(y + h[type] > temp.y && temp.y + h[temp.type] > y && dx >= 0){
				if(dx > 0 && temp.dx < 0){
					if(x < temp.x && x + clipx[type] > temp.x){
						dx = -dx;
						temp.dx = -temp.dx;
						return;
					}
				}else if(dx < 0 && temp.dx > 0){
					if(x < temp.x + clipx[temp.type] && x > temp.x){
						dx = -dx;
						temp.dx = -temp.dx;
						return;
					}
				}else if(dx > temp.dx && temp.dx > 0){
					if(x < temp.x && x + clipx[type] > temp.x){
						dx = -dx;
						return;
					}
				}else if(temp.dx > dx && dx > 0){
					if(temp.x < x && temp.x + clipx[temp.type] > x){
						temp.dx = -temp.dx;
						return;
					}
				}else if(dx < temp.dx && temp.dx < 0){
					if(x > temp.x && x < temp.x + clipx[temp.type]){
						temp.dx = -temp.dx;
						return;
					}
				}else if(temp.dx < dx && dx < 0){
					if(temp.x > x && temp.x < x + clipx[type]){
						dx = -dx;
						return;
					}
				}
				}
			}
			temp = temp.ptr;
		}
	}
	
	public static int[] checkCollisionWithCharacter(int cx, double cy, int cw, int ch, int[] cxy) {
		if(shownStart == null)
			return cxy;
		Enemies temp = shownStart;
		double sx = cxy[0] - cx;
		double sy = cxy[1] - cy;
		while(temp != null && temp.onScreen){
			if(temp.available){
				enemy.setBounds(temp.x, temp.y, clipx[temp.type], h[temp.type]);
				if(enemy.intersects(cx+sx, cy+sy, cw, ch)){
					System.out.println("hit enemy at x: "+(cx+sx)+" "+temp.x+" & y: "+(cy+sy)+" "+temp.y);
					double tenthx = sx/10;
					double tenthy = sy/10;
					System.out.println(tenthx+"  "+tenthy);
					if((cy+ch < temp.y && cxy[1]+ch > temp.y)||(cy < temp.y && cxy[1] > temp.y)){
						return hitOperation(temp, ch, cxy);
					}
				}
			}
			temp = temp.ptr;
		}
		return cxy;
	}
	private static int[] hitOperation(Enemies temp, int h, int[] xy) {
		if(killable[temp.type] == 0){
			
			m.setTempy(-20);
			temp.available = false;
			temp.counter = 0;
//			delete(temp);
			return new int[]{xy[0], temp.y-h};
		}
		return xy;
	}
	private static void delete(Enemies old) {
		if(start == old){
			start = old.ptr;
			return;
		}
		Enemies temp1 = start;
		Enemies temp2 = start.ptr;
		while(temp2 != old){
			temp1 = temp1.ptr;
			temp2 = temp2.ptr;
		}temp1.ptr = temp2.ptr;
	}
	public static void checkShown(){
		shownStart = null;
		Enemies temp = start;
		while(temp != null){//System.out.println("checking if onScreen "+m.getX()+" "+temp.appearx);
			if(m.getX() >= temp.appearx && m.getX() < temp.x +500){
				if(shownStart == null)
					shownStart = temp;
				temp.onScreen = true;
			}temp = temp.ptr;
		}
	}
	
	
	public static void setDy(double[] dy) {
		Enemies.dys = dy;
	}
	
	public static void paint(Graphics g){//System.out.println("painting enemies...");
		Enemies temp = shownStart;
		while(temp != null && temp.onScreen){
			if(temp.available){
				if(f.screenx == 0)
					g.drawImage(images[temp.type], f.insets.left+temp.x, f.insets.top+temp.y, f.insets.left+temp.x+clipx[temp.type], f.insets.top+temp.y+h[temp.type], (int)(temp.counter)*clipx[temp.type], 0, ((int)(temp.counter)+1)*clipx[temp.type], h[temp.type], f);
				else
					g.drawImage(images[temp.type], f.insets.left+temp.x-f.screenx, f.insets.top+temp.y, f.insets.left+temp.x-f.screenx+clipx[temp.type], f.insets.top+temp.y+h[temp.type], (int)(temp.counter)*clipx[temp.type], 0, ((int)(temp.counter)+1)*clipx[temp.type], h[temp.type], f);
			}else{
				if(f.screenx == 0)
					g.drawImage(images[temp.type], f.insets.left+temp.x, f.insets.top+temp.y, f.insets.left+temp.x+clipx[temp.type], f.insets.top+temp.y+h[temp.type], (n[temp.type]-1)*clipx[temp.type], 0, n[temp.type]*clipx[temp.type], h[temp.type], f);
				else
					g.drawImage(images[temp.type], f.insets.left+temp.x-f.screenx, f.insets.top+temp.y, f.insets.left+temp.x-f.screenx+clipx[temp.type], f.insets.top+temp.y+h[temp.type], (n[temp.type]-1)*clipx[temp.type], 0, n[temp.type]*clipx[temp.type], h[temp.type], f);
			}
			
			temp = temp.ptr;
		}
	}
	public static void checkIfExistOnTheBlock(int bx, int by) {
		Enemies temp = shownStart;
		while(temp != null && temp.onScreen){
			if(temp.y + h[temp.type] >= by && temp.y < by){
				if((temp.x < bx && temp.x + clipx[temp.type] > bx)||(temp.x > bx && temp.x < bx + 30)){
					hitFromBelowOperation(temp);
				}
			}
			//System.out.println(temp.y+" "+h[temp.type]+" "+temp.x+" "+clipx[temp.type]+" & "+by+" "+bx);
			temp = temp.ptr;
		}
		
	}
	private static void hitFromBelowOperation(Enemies temp) {
		if(killable[temp.type] == 0){
			temp.available = false;
			temp.counter = 0;
		}
		
	}
}
