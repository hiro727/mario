package smEnemies;

import models.GameObject;
import specialEffects.Explodable;
import superMarioModels.SMEnemy;

public class Goomba extends SMEnemy implements Explodable {

	public Goomba() {
		super("Goomba");
	}

	@Override
	public void update() {
		
//		System.out.println("\t\tupdating " + getName());
		
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean applyDamage(GameObject npc) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
