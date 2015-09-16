package test;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blocks {
	private static Frames f;
	private static Characters c;
	private static Image[] images;
	private static Blocks start;
	private static Blocks shownStart;
	private Blocks ptr;
	
	private Shape block;
//	private Map map;
	private int type;
	private int x, y;
	private int inside;
	private boolean onScreen;
	private boolean available = true;
	private int squareSize = 15;
	private double dy;
	private double counter;

	public Blocks(Frames f) {
		Blocks.f = f;
		
		try{
			File[] file = new File("test/images/blocks").listFiles();
			images = new Image[file.length];
			for(int i=0;i<file.length;i++){
				images[i] = ImageIO.read(file[i]);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	public Blocks(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		GeneralPath temp = new GeneralPath();
		temp.moveTo(x, y);
		temp.lineTo(x+30, y);
		temp.lineTo(x+30, y+25);
		temp.lineTo(x, y+25);
		temp.closePath();
//		map = new Map(this, temp);
		block = temp;
		add(this);
	}
	public Blocks(int x, int y, int type, int inside) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.inside = inside;
		add(this);
	}
	public static void update(){
		shownStart = null;
		Blocks temp = start;
		while(temp != null){
			if(temp.x < f.getScreenx()+600){
				if(shownStart == null)
					shownStart = temp;
				temp.onScreen = true;
			}
			if(temp.type == 0 && !temp.available){
				temp.dy += .5*9.81;
				temp.y += temp.dy*.5+.5*9.81*.5*.5;
				temp.squareSize += 4;
				if(temp.y-temp.squareSize/2>500)
					delete(temp);
			}else if(temp.type == 1 && temp.onScreen){
				temp.counter+=.4;
				if(temp.counter >= 3)
					temp.counter = 0;
			}temp = temp.ptr;
		}
	}
	public static void checkFirstShown() {
		shownStart = null;
		Blocks temp = start;
		while(temp != null){
			if(temp.x < f.getScreenx()+600){
				if(shownStart == null)
					shownStart = temp;
				temp.onScreen = true;
			}
			temp = temp.ptr;
		}
	}
	public static void add(Blocks b) {
		if(b.x<600)
			b.onScreen = true;
		if(start == null){
			start = b;
			return;
		}
		if(b.x < start.x || (b.x == start.x && b.y < start.y)){
			b.ptr = start;
			start = b;
			return;
		}
		Blocks temp1 = start;
		Blocks temp2 = start.ptr;
		while(temp2 != null){
			if(b.x < temp2.x || (b.x == temp2.x && b.y < temp2.y)){
				b.ptr = temp2;
				temp1.ptr = b;
				return;
			}else{
				temp1 = temp1.ptr;
				temp2 = temp2.ptr;
			}
		}
		temp1.ptr = b;
	}
	
	public static void delete(Blocks deleted){//System.out.println(start.x+" "+start.y);
		if(start == deleted)
			start = deleted.ptr;
		else{
			Blocks temp1 = start;
			Blocks temp2 = start.ptr;
			while(temp2 != deleted){
				temp1 = temp1.ptr;
				temp2 = temp2.ptr;
			}temp1.ptr = temp2.ptr;
			temp2 = null;
		}checkFirstShown();
	}
	
	public static void paintAll(Graphics g){
		if(shownStart == null)	return;
		Blocks temp = shownStart;
		while(temp.onScreen){
			if(temp.available){
				if(temp.type == 0)
					g.drawImage(images[temp.type], temp.x+f.getInsets().left-f.getScreenx(), temp.y+f.getInsets().top, f);
				else if(temp.type == 1)
					g.drawImage(images[temp.type], temp.x+f.getInsets().left-f.getScreenx(), temp.y+f.getInsets().top, temp.x+f.getInsets().left-f.getScreenx()+30, temp.y+f.getInsets().top+25, (int) (temp.counter)*30, 0, (int) (temp.counter)*30+30, 25, f);
			}else{
				g.drawImage(images[2], temp.x-temp.squareSize/2+f.getInsets().left-f.getScreenx(), temp.y-temp.squareSize/2+f.getInsets().top, f);
				g.drawImage(images[2], temp.x+temp.squareSize/2+f.getInsets().left-f.getScreenx(), temp.y-temp.squareSize/2+f.getInsets().top, f);
				g.drawImage(images[2], temp.x-temp.squareSize/2+f.getInsets().left-f.getScreenx(), temp.y+temp.squareSize/2+f.getInsets().top, f);
				g.drawImage(images[2], temp.x+temp.squareSize/2+f.getInsets().left-f.getScreenx(), temp.y+temp.squareSize/2+f.getInsets().top, f);
			}
			temp = temp.ptr;
			if(temp == null)
				return;
		}
	}
	public static Shape addBlockPositions(GeneralPath map, int x) {
		if(shownStart == null)
			return map;
		Blocks temp = shownStart;
		GeneralPath map2 = (GeneralPath) map.clone();
		while(x+15 > temp.x){
			if(temp.available){
			map2.moveTo(temp.x, temp.y);
			map2.lineTo(temp.x+30, temp.y);
			map2.lineTo(temp.x+30, temp.y+25);
			map2.lineTo(temp.x, temp.y+25);
			map2.closePath();
			}
			temp = temp.ptr;
			if(temp == null)
				break;
		}return map2;
		
	}
	public static void checkIfExist(double x, double y) {
		Blocks temp = shownStart;//System.out.println(x+" "+" & "+y);
		while(temp != null && x+17 > temp.x){//System.out.println(x+" "+temp.x+" & "+y+" "+temp.y);
			if(x<temp.x+20 && y>temp.y && y< temp.y+20){
				temp.hitOperation();
				return;
			}temp = temp.ptr;
		}		
	}
	private void hitOperation() {
		if(type == 0){
			//System.out.println("broken");
			available = false;
			dy = -30;
		}else if(type == 1){
			
		}else if(type == 2){
			
		}
	}
}
