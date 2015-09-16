package ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import models.Enemy;
import models.Item;

public interface CustomMap {
	
	public static class EnemyMap extends HashMap<String, List<List<ImageIcon>>> {

		private static final long serialVersionUID = 1452464627807052411L;

		public Image getImage(Object key, int type, int state, Enemy enemy) {
			Image img = get(key).get(type).get(state).getImage();
			enemy.setWidth(img.getWidth(null));
			enemy.setHeight(img.getHeight(null));
			return img;
		}
		
	}	
	public static class PlayerMap extends HashMap<String, List<List<ImageIcon>>> {

		private static final long serialVersionUID = 1452464627807052411L;

		public Image getImage(Object key, int type, int state, models.Player character) {
			Image img = get(key).get(type).get(state).getImage();
			character.setWidth(img.getWidth(null));
			character.setHeight(img.getHeight(null));
			return img;
		}
		
	}
	public static class ItemMap extends HashMap<String, ImageIcon> {

		private static final long serialVersionUID = 1452464627807052411L;
		
		public Image getImage(Object key, Item item) {
			Image img = get(key).getImage();
			item.setWidth(img.getWidth(null));
			item.setHeight(img.getHeight(null));
			return img;
		}
	}
}
