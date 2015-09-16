package smEnemies;

import models.GameObject;
import superMarioModels.SMEnemy;

public class Bowser extends SMEnemy {

	public Bowser() {
		super("Bowser");
	}

	@Override
	public void update() {
		
//		System.out.println("\t\tupdating " + getName());
		
	}

	@Override
	public boolean applyDamage(GameObject npc) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
}
