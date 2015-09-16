package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Levels {
	
	private static int type;
	
	private static Vector<String> map = new Vector<String>();
	
	private static Vector<Enemies> enemy_map = new Vector<Enemies>();
	
	private static Vector<Items> item_map = new Vector<Items>();
	
	
	public Levels(String path) {
		try {
			read_from_file(path);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void read_from_file(String path) throws NullPointerException, IOException{
		map.clear();
//		BufferedInputStream bis; = new BufferedInputStream()
		BufferedReader b = new BufferedReader(new FileReader(new File("res/data "+path+".txt")));
		type = Integer.parseInt(b.readLine());
		while(b.ready()){
			map.add(b.readLine());
		}
		
//		enemy_map.clear();
//		b = new BufferedReader(new FileReader(new File(path+"/emap")));
//		while(b.ready()){
//			enemy_map.add(new Enemies(b.read(), Integer.parseInt(b.readLine()), Integer.parseInt(b.readLine())));
//		}
//		
//		item_map.clear();
//		b = new BufferedReader(new FileReader(new File(path+"/imap")));
//		while(b.ready()){
//			item_map.add(new Items(b.read(), Integer.parseInt(b.readLine()), Integer.parseInt(b.readLine())));
//		}
		b.close();
	}
	public static int getType() {
		return type;
	}
	public static Vector<String> getMap() {
		return map;
	}
	public static Vector<Enemies> getEnemy_map() {
		return enemy_map;
	}
	public static Vector<Items> getItem_map() {
		return item_map;
	}
}