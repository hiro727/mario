package models;

import java.awt.Graphics;

import ui.Resources;

public abstract class Player extends GameObject {
	
	protected int type = 0;
	protected int life = 3;
	
	protected boolean directionChangeable;
	protected boolean velocityChangeable;
	protected boolean stateChangeable;
	protected boolean idle;
	
	public Player(String name) {
		super(name);
		System.out.println("Initializing - Character\t" + name);
		
		directionChangeable = true;
		velocityChangeable = true;
		stateChangeable = true;
		idle = true;
		
		maxX = 20;
		maxY = 20;
	}

	public void addForce(double fx, double fy) {
		
		if (fx == 0 && fy == 0)
			return;
		
		boolean rightwards = (dx >= 0);
		boolean upwards = (dy <= 0);
		
		if (velocityChangeable) {
			if (rightwards && dx + fx < 0.00005) {
				// turning point towards left
			} else if (!rightwards && dx + fx > -0.00005) {
				// turning point towards right
			}
			if (upwards && dy + fy > -0.00005) {
				// concave down critical point
			}
			if (!upwards && dy + fy < 0.00005) {
				// concave up critical point
			}
			
			if (dx >= 0)
				dx = Math.min(maxX, dx + fx);
			else
				dx = Math.max(-maxX, dx + fx);
			
			if (dy >= 0)
				dy = Math.min(maxY, dy + fy);
			else
				dy = Math.max(-maxY, dy + fy);
			
			idle = false;
			state = 2;
		}
	}
	public void update() {
		int originalXY = Game.getInstance().getGameMap().getScreenCoordinates((int) x, (int) y);
		
		if (!idle)
			System.out.print("\t||\t" + getName() + " : [" + format(x) + "]\t[" + format(y) + "]\t\t["
					+ format(dx) + "]\t[" + format(dy) + "]\t");
		moveX();
		y += dy;
		if (!idle) {
			if (dx > 0) {
				dx -= Game.getInstance().getGameMap().getFriction((int) x, (int) y);
				
				if (dx < 0.00005)
					horizontalStop();
				
			} else if (dx < 0) {
				dx += Game.getInstance().getGameMap().getFriction((int) x, (int) y);
				
				if (dx >= -0.00005)
					horizontalStop();
			}
		}
		
		int newXY = Game.getInstance().getGameMap().getScreenCoordinates((int) x, (int) y);
		if (originalXY != newXY)
			Game.getInstance().updateGameObjectPosition(originalXY, newXY, this);
		Game.getInstance().getGameMap().collisionCheck(this);
	}
	private void horizontalStop() {
		dx = 0;
		if (Math.abs(dy) < 0.00005) {
			dy = 0;
			idle = true;
			state = 1;
			// ... TEST ...
//			if (this instanceof SMCharacter) {
//				if (type == 0)
//					((SMCharacter) this).animateFromTo(0, 1);
//				
//			}
		}
	}
	public void paint(Graphics g, Game game) {
//		drawImage(g, Resources.getInstance().getPlayerImage().get(getName()).get(type).get(state).getImage(), (int) x, (int) y, game);
		g.drawImage(Resources.getInstance().getPlayerImage(getName(), type, state, this), (int) x, (int) y - height, game);
	}
	public abstract void levelUp(Item item);
	public abstract void levelDown(Enemy enemy);
	protected abstract void die();
	
	// ... TODO ...
	// reset
	
	public static String getName(int type) {
		return null;
	}
}
