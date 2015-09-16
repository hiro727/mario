package models;

import ui.Resources;

public abstract class Item extends GameObject {
	
	public Item(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
//	protected double x, y;
	
	protected int count;
	
	protected double ix, iy;
	
//	protected double dx, dy;
	
	public int getCount() {
		return count;
	}
	
	public abstract void moveRelatively();
	public abstract void moveAbsolutely();
	public void paint(Game game) {
//		drawImage(game.getGraphics(), Resources.getInstance().getItemImage(getName(), this), (int) x, (int) y, game);
		game.getGraphics().drawImage(Resources.getInstance().getItemImage(getName(), this), (int) x, (int) y - height, game);
	}
}
