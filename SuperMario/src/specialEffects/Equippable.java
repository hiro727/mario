package specialEffects;

import java.util.ArrayList;

import models.Item;

public interface Equippable {
	
	public void equip();
	public void launch();
	
	public ArrayList<Item> getEquipments();
}
