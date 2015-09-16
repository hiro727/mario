package smItems;

import models.Enemy;
import models.Game;
import models.WeaponPlayer;
import specialEffects.Throwable;

public class Fireball extends WeaponPlayer implements Throwable {

	/** records the last position of interaction between this fireBall and the ground**/
	private double topLimit;
	
	public Fireball(double x, double y) {
		super("Fireball");
		this.x = x;
		this.y = y;
		dx = 3;
		dy = dx - 1;
	}
	
	@Override
	public void moveRelatively() {
		count ++;
		x += dx;
		
		System.out.print("\t||\tFireball : [" + x + "]\t[" + y + "]");
		// ... TODO ...
		/*
		 * 
		 * [45‹]
		 * \    
		 *  \  /\  /\  /\
		 *   \/  \/  \/  \.....
		 *                     
		 *                     
		 */
		
		if (dy > 0) {
			double groundY = Game.getInstance().getGameMap().getGroundBelow(x, y);
			
			if (y + dy >= groundY) {
				// flip vertical direction
				y = groundY;
				dy *= -1;
				topLimit = groundY - 12.5;
			} else {
				// move downwards
				y += dy;
			}
		} else {
			if (y + dy < topLimit) {
				y = topLimit;
				dy *= -1;
			} else {
				y += dy;
			}
		}
//		
//		double obsX = Game.getInstance().getGameMap().getNextObstacleFrom(x, y);
//		if (x >= obsX) {
//			y = (y - dy) + dy * ((dx - x + obsX) / dx);
//			x = obsX;
//			explode();
//		}
		// ... TODO ...
		// ... check effective region's boundary
	}

	@Override
	public void moveAbsolutely() {}
	
	@Override
	public void launch() {}
	
	/**
	 * returns if the enemy is to be destroyed
	 * 
	 */
	@Override
	public boolean collideWith(Enemy enemy) {
		// ... TODO ...
		// checks collision using game map (not yet)
		Game game = Game.getInstance();
		
		if (game.getBounds().intersects(enemy.getBounds())) {
			return enemy.applyDamage(this);
		}
		
		return false;
	}
	
}
