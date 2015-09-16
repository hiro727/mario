package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObjectStack { //implements Iterable<List<GameObject>> {
	
	private Map<GameObject, Integer> index1, index2;
	private Map<Integer, List<GameObject>> xylist;

	public GameObjectStack() {
		index1  = new HashMap<GameObject, Integer>();
		index2  = new HashMap<GameObject, Integer>();
		xylist = new HashMap<Integer, List<GameObject>>();
//		xylist2 = new HashMap<Integer, List<GameObject>>();
	}

	public void addGameObject(int xy1 ,int xy2, GameObject go) {
		if (xylist.containsKey(xy1))
			xylist.get(xy1).add(go);
		else {
			ArrayList<GameObject> newList = new ArrayList<GameObject>();
			newList.add(go);
			xylist.put(xy1, newList);
		}
		if (xylist.containsKey(xy2))
			xylist.get(xy2).add(go);
		else {
			ArrayList<GameObject> newList = new ArrayList<GameObject>();
			newList.add(go);
			xylist.put(xy2, newList);
		}
		index1.put(go, xy1);
		index2.put(go, xy2);
	}
	public static void reset() {
		
	}
	
	public List<GameObject> getGameObjectsAt(int xy) {
		return xylist.get(xy);
	}
	/**
	 * 
	 * @param key1 screen coordinate at left-bottom
	 * @param key2 screen coordinate at right-top
	 * @return
	 */
	public List<GameObject> get(int key1, int key2) {
//		return xylist1.get(key1) && xylist2.get(key2);
		List<GameObject> list1 = xylist.get(key1);
		List<GameObject> list2 = xylist.get(key2);
//		List<GameObject> result = new ArrayList<GameObject>();
		
		list1.addAll(list2);
		return list1;
		
//		for (GameObject go : list1) {
//			for (GameObject go2 : list2) {
//				if (go == go2) {
//					result.add(go);
//					break;
//				}
//			}
//		}
//		return result;
	}
}
