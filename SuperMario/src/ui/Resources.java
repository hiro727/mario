package ui;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import models.Enemy;
import models.Game;
import models.Item;

public class Resources {
	
	private static Resources instance;
	
//	private Map<Character, BufferedImage> mapObjects;
//	private Map<String, List<List<BufferedImage>>> enemyImage;
//	private Map<String, List<List<BufferedImage>>> playerImage;
//	private Map<String, List<BufferedImage>> itemImage;

	private Map<Character, ImageIcon> mapObjects;
//	private Map<Character, Character> mapObjectTypes; // for types of each object (GND, OBJ, MTS, ...)
	private CustomMap.EnemyMap enemyImage;
	private CustomMap.PlayerMap playerImage;
	private CustomMap.ItemMap itemImage;
//	private Map<String, List<List<ImageIcon>>> enemyImage;
//	private Map<String, List<List<ImageIcon>>> playerImage;
//	private Map<String, ImageIcon> itemImage;
	
	
	public Resources() {
//		mapObjects = new HashMap<Character, BufferedImage>();
//		enemyImage = new HashMap<String, List<List<BufferedImage>>>();
//		playerImage = new HashMap<String, List<List<BufferedImage>>>();
//		itemImage = new HashMap<String, List<BufferedImage>>();

		mapObjects = new HashMap<Character, ImageIcon>();
//		mapObjectTypes = new HashMap<Character, Character>();
		enemyImage = new CustomMap.EnemyMap();
		playerImage = new CustomMap.PlayerMap();
		itemImage = new CustomMap.ItemMap();
		
		read();
	}
	
	private void read() {
		try {
			ResourceLoader rLoader = ResourceLoader.getInstance();
			
			BufferedReader bReader = rLoader.getBufferedReader();
			
			int T = Integer.parseInt(bReader.readLine());

			String[] line;
			String file;
			
			// Map Objects
			
			for (int i = 0; i < T; i ++) {
				line = bReader.readLine().split(" ");
				file = line[0];
				mapObjects.put(file.charAt(2), new ImageIcon(rLoader.getBufferedImage(file)));
//				mapObjectTypes.put(file.charAt(2), Game.getInstance().getMapObjectTypes().get(line[1]));
				
//				mapObjects.put(file.charAt(2), rLoader.getBufferedImage(file));
//				System.out.println(file.charAt(2) + " " + mapObjects.get(file.charAt(2)));
			}
			
			T = Integer.parseInt(bReader.readLine());
			
			int N;
			
			
			
			ArrayList<List<ImageIcon>> allEImageList;
			ArrayList<ImageIcon> eSingleList;
			
			for (int i = 0; i < T; i ++) {
				line = bReader.readLine().split(" ");
				
				file = line[0];
				N = Integer.parseInt(line[1]);
				int x = Integer.parseInt(line[2]);
				
				allEImageList = new ArrayList<List<ImageIcon>>();

				for (int j = 0; j < N; j ++) {
					eSingleList = new ArrayList<ImageIcon>();
					for (int k = 0; k < x; k ++) {
						String extension = rLoader.exist(file + "_" + j + "_" + k);
						if (extension != null) {
							if (extension.contains(".gif"))
								eSingleList.add(new ImageIcon(rLoader.getURL(extension)));
							else
								eSingleList.add(new ImageIcon(rLoader.getBufferedImage(extension)));
						}
					}
					allEImageList.add(eSingleList);
				}
				
				playerImage.put(line[0], allEImageList);
			}
			
			
			// Enemy Images (GIF, PNG, BMP)
			
			T = Integer.parseInt(bReader.readLine());
			
			for (int i = 0; i < T; i ++) {
				line = bReader.readLine().split(" ");
				
				file = line[0];
				N = Integer.parseInt(line[1]);
				int x = Integer.parseInt(line[2]);
				
				allEImageList = new ArrayList<List<ImageIcon>>();
				for (int j = 0; j < N; j ++) {
					eSingleList = new ArrayList<ImageIcon>();
					for (int k = 0; k < x; k ++) {
						eSingleList.add(new ImageIcon(rLoader.getURL(bReader.readLine())));
					}
					allEImageList.add(eSingleList);
				}
				
				enemyImage.put(file, allEImageList);
			}
			
			T = Integer.parseInt(bReader.readLine());
			
			for (int i = 0; i < T; i ++) {
				
				file = bReader.readLine();
				
				itemImage.put(file, new ImageIcon(rLoader.getURL("ani_1" + file + ".gif")));
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Resources createInstance() {
		instance = new Resources();
		return instance;
	}
	public static Resources getInstance() {
		return instance;
	}
	public Map<Character, ImageIcon> getMapObjects() {
		return mapObjects;
	}
//	public Map<Character, Character> getMapObjectTypes() {
//		return mapObjectTypes;
//	}
//	public Map<String, List<List<ImageIcon>>> getPlayerImage() {
//		return playerImage;
//	}
//	public Map<String, List<List<ImageIcon>>> getEnemyImage() {
//		return enemyImage;
//	}
//
//	public Map<String, ImageIcon> getItemImage() {
//		return itemImage;
//	}
	public Image getPlayerImage(String name, int type, int state, models.Player character) {
		return playerImage.getImage(name, type, state, character);
	}
	public Image getEnemyImage(String name, int type, int state, Enemy enemy) {
		return enemyImage.getImage(name, type, state, enemy);
	}
	public Image getItemImage(String name, Item item) {
		return itemImage.getImage(name, item);
	}
}
