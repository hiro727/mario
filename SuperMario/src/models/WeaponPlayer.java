package models;

public abstract class WeaponPlayer extends Weapon {

	public WeaponPlayer(String name) {
		super(name);
	}

	public abstract boolean collideWith(Enemy enemy);
}
