package models;

import java.awt.Graphics;
import ui.Resources;

public abstract class Enemy extends GameObject implements Cloneable {

	protected int trigger_x, trigger_y;
	
	public Enemy(String name) {
		super(name);
	}
	
	public int getTrigger_x() {
		return trigger_x;
	}
	public int getTrigger_y() {
		return trigger_y;
	}
	
	public void setTrigger_x(int trigger_x) {
		this.trigger_x = trigger_x;
	}
	public void setTrigger_y(int trigger_y) {
		this.trigger_y = trigger_y;
	}
	
	public Enemy clone() {
		
		try {
			Enemy enemy = getClass().newInstance();
			
			enemy.setName(getName());
			enemy.x = x;
			enemy.y = y;
			enemy.dx = dx;
			enemy.dy = dy;
			enemy.trigger_x = trigger_x;
			enemy.trigger_y = trigger_y;
			enemy.state = state;

			System.out.println("\tCloning instance of : " + enemy.getName());
			
			return enemy;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String toString() {
		return getName() + " : " + "{" + x + ",\t" + y + ",\t" + trigger_x + ",\t" + trigger_y + "}";
	}
	public abstract void update();
	public void paint(Graphics g, Game game) {
//		drawImage(g, map.get(getName()).get(game.getGameMap().getType()).get(state).getImage(), (int) x, (int) y, Game.getInstance());
		g.drawImage(Resources.getInstance().getEnemyImage(getName(), game.getGameMap().getType(), state, this), (int) x, (int) y - height, game);
	}
	
	public abstract boolean applyDamage(GameObject npc);
	
}
