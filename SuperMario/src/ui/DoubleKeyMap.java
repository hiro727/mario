package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Enemy;

public class DoubleKeyMap implements Cloneable{
	
	private Map<Integer, Map<Integer, List<Enemy>>> map;
	
	public DoubleKeyMap() {
		map = new HashMap<Integer, Map<Integer, List<Enemy>>>();
	}
	
	public void add(int key1, int key2, Enemy go) {
		if (map.containsKey(key1)) {
			if (map.get(key1).containsKey(key2)) {
				map.get(key1).get(key2).add(go);
			} else {
				ArrayList<Enemy> list = new ArrayList<Enemy>();
				list.add(go);
				map.get(key1).put(key2, list);
			}
		} else {
			Map<Integer, List<Enemy>> map1 = new HashMap<Integer, List<Enemy>>();
			ArrayList<Enemy> list = new ArrayList<Enemy>();
			list.add(go);
			map1.put(key2, list);
			map.put(key1, map1);
			
		}
	}
	
	public DoubleKeyMap clone() { //throws CloneNotSupportedException {
		DoubleKeyMap dkm = new DoubleKeyMap();
		
		Map<Integer, List<Enemy>> map1;
		List<Enemy> enemies;
		
		for (Integer key1 : map.keySet()) {
			
			map1 = new HashMap<Integer, List<Enemy>>();
			
			for (Integer key2 : map.get(key1).keySet()) {
				
				enemies = new ArrayList<Enemy>();
				
				for (Enemy enemy : map.get(key1).get(key2)) {
					
					enemies.add(enemy.clone());
					
				}
				
				map1.put(key2, enemies);
			
			}
			
			dkm.map.put(key1, map1);
			
		}
		
		return dkm;
	}
}