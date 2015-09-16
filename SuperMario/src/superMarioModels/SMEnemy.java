package superMarioModels;

import models.Enemy;

public abstract class SMEnemy extends Enemy {

	public SMEnemy(String name) {
		super(name);
		
	}

	public abstract void update();

}
