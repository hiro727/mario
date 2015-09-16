package superMarioModels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import ui.DoubleKeyMap;
import ui.ResourceLoader;
import ui.Resources;
import models.Enemy;
import models.Game;
import models.GameMap;
import models.MapObject;

public class SMGameMap extends GameMap {

	public SMGameMap() {
		
		try {
			BufferedReader bReader = ResourceLoader.getInstance().getBufferedReader();
			
			name = bReader.readLine();
			
			System.out.println("Loading GameMap : " + name);
			
			w = Integer.parseInt(bReader.readLine());
			h = Integer.parseInt(bReader.readLine());
			
			MTS = new Color(Integer.parseInt(bReader.readLine()),
							Integer.parseInt(bReader.readLine()),
							Integer.parseInt(bReader.readLine()));
			
			int T = Integer.parseInt(bReader.readLine());
			mapData = new char[T][];
			objectType = new char[T][];
			mapObjects = new HashMap<Integer, MapObject>();
			
			int max = 0;
			for (int i = 0; i < T; i++) {
				String line = bReader.readLine();
				mapData[i] = line.toCharArray();
				objectType[i] = new char[line.length()];
				max = Math.max(max, mapData[i].length);
				System.out.println("\t" + line);
			}
			mapImg = new BufferedImage(max * w, T * h, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = mapImg.getGraphics();
			graphics.setColor(MTS);
			graphics.fillRect(0, 0, mapImg.getWidth(), mapImg.getHeight());
			
			Map<Character, ImageIcon> items = Resources.getInstance().getMapObjects();
			
//			Map<Character, Character> types = Resources.getInstance().getMapObjectTypes();
			
			int count = 0;
			
			for (int i = 0; i < T; i++) {
				for (int j = 0; j < mapData[i].length; j++) {
					// draw initial map
					
					if (SMGame.MAP_OBJECTS.containsKey(mapData[i][j])) {
//						System.out.println("&&&&&&&&&&&&&&&&&" + SMGame.MAP_OBJECTS.get(mapData[i][j]) + " at : [" + i + "][" + j + "]");
						mapObjects.put(count, (MapObject) SMGame.MAP_OBJECTS.get(mapData[i][j]).newInstance());
					}
					
					if (mapData[i][j] != '0') {
						graphics.drawImage(items.get(mapData[i][j]).getImage(), j * w, i * h, Game.getInstance());
					
//						objectType[i][j] = types.get(Resources.getInstance().getMapObjectTypes().get(mapData[i][j]));
						
						
					}
					count ++;
				}
			}
//			createMapObjectTypes();
			
			
			// "Enemy"
			bReader.readLine();
			T = Integer.parseInt(bReader.readLine());
			enemies = new ArrayList<Enemy>();
			enemyTrigger = new DoubleKeyMap();
			
			for (int i = 0; i < T; i++) {

				SMEnemy enemy;
				String name = bReader.readLine();
				
				enemy = ((SMEnemy) Game.getInstance().getEneMap().get(name));
				
				if (enemy != null) {
					enemy = enemy.getClass().newInstance();
					
					enemy.setName(name);
					enemy.setX(Double.parseDouble(bReader.readLine()));
					enemy.setY(Double.parseDouble(bReader.readLine()));
					int x = Integer.parseInt(bReader.readLine());
					int y = Integer.parseInt(bReader.readLine());
					enemy.setTrigger_x(x);
					enemy.setTrigger_y(y);
					enemyTrigger.add(x, y, enemy);
					System.out.println("\tAccessing data of : " + name);
//					System.out.println(enemy.getName());
//					System.out.println(enemy.getX());
//					System.out.println(enemy.getY());
//					System.out.println(enemy.getTrigger_x());
//					System.out.println(enemy.getTrigger_y());
					
					enemies.add(enemy);
				} else {
					System.out.println("\tAccess Failed : \"UNDEFINED ENEMY ERROR\" : " + name);
				}
			}
			bReader.close();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	
	public void update() {
		
	}
}
