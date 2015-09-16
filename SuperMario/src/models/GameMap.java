package models;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ui.DoubleKeyMap;

public class GameMap {

	protected String name;
	
	/* type of the game area*/
	protected int type = 0;
	
	protected char[][] mapData;
	protected char[][] objectType;
	
	protected int w, h;
	
	protected Color MTS;
	
	protected BufferedImage mapImg;
	
	protected ArrayList<Enemy> enemies;
	protected DoubleKeyMap enemyTrigger;
	
	protected Map<Integer, MapObject> mapObjects;
	
	protected double gravity;
	
	public String getName() {
		return name;
	}
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	public DoubleKeyMap getEnemyTrigger() {
		return enemyTrigger;
	}

	public void update() {
		
	}
	public BufferedImage getMapImg() {
		return mapImg;
	}
	public int getType() {
		return type;
	}
	public double getFriction(int x, int y) {
		// ... TODO ...
		// ... friction depends on the item object at (x, y) ...
		
		return 5;
	}
	public double getGroundBelow(double x, double y) {
		// ... TODO ...
		// returns the highest y position of any kind of ground below the given (x, y) coordinate
		
		return 175;
	}
	public double getNextObstacleFrom(double x, double y) {
		// ... TODO ...
		// returns the lowest x position of any kind of obstacle to the right of the given (x, y) coordinate
				
		return 600;
	}
	public int getScreenCoordinates(int x, int y) {
		assert(mapData != null);
		return x / w + (y / h) * mapData[0].length;
	}
	int mapWidth;
	public void collisionCheck(GameObject gameObject) {
		
//		MapObject mo = mapObjects.get(getScreenCoordinates((int) go.x, (int) go.y));
//		int xy = getScreenCoordinates((int) go.x, (int) go.y);
//		System.out.println("\n" + mapObjects.get(xy) + " at : " + xy);
		
		mapWidth = mapData[0].length;
		
		int x = (int) gameObject.x;
		int y = (int) gameObject.y;
		int key = x / w + (y / h) * mapWidth; // get absolute pos of Mario
		
		
		List<MapObject> obstaclesList = new ArrayList<MapObject>() {
			private static final long serialVersionUID = -4404812657667555086L;
			@Override
			public boolean add(MapObject e) {
				if (e == null)
					return false;
				return super.add(e);
			}
		};
		String str = "\n";
//		for (int i = -1; i <=1; i++) {
//			for (int j = -1; j <= 1; j++) {
//				// get mapObjects around the current position. Ignore when null is returned
//				boolean added = obstaclesList.add(mapObjects.get(key + j + i * mapWidth));
//				if (added)
//					str += "› ";
//				else
//					str += "~ ";
//			}
//			str += "\n";
//		}
//		str += "------";
//		for (MapObject mapObject : obstaclesList) {
//			mapObject.checkCollision(gameObject);
//		}
		for (int i = -1; i <=1; i++) {
			for (int j = -1; j <= 1; j++) {
				// get mapObjects around the current position. Ignore when null is returned
				MapObject mapObject = mapObjects.get(key + j + i * mapWidth);
				System.out.println((j + 1 + (i + 1) * 3) + " : " + mapObject);
				if (mapObject != null)
					str += mapObject.checkCollision(gameObject, j + 1 + (i + 1) * 3) + " ";
				else
					str += "~ ";
			}
			str += "\n";
		}
		str += "------";
		
		/* ~ ~ ~ 
		 * › › ›
		 * ~ ~ ~ 
		 */
		System.out.println(str);
		
		
		
		if (obstaclesList.size() == 0)
			return;
		
		
	}
}
