package models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public abstract class GameObject {

	private String name;
	
	protected int state = 0;
	
	protected int width, height;
	
	protected double x, y;
	protected double dx, dy;
	
	protected double maxX = 20, maxY = 20;
	
	protected int xy;
	
	public GameObject(String name) {
		this.name = name;
	}

	public void drawImage(Graphics g, BufferedImage img, int llx, int lly, Game game) {
		
		g.drawImage(img, llx, lly - img.getHeight(), game);
		
	}
	public void drawImage(Graphics g, Image imgs, int llx, int lly, Game game) {
		
		g.drawImage(imgs, llx, lly - imgs.getHeight(null), game);
		
	}

	private static DecimalFormat df = new DecimalFormat("0.0000000#");
	public String format(double a) {
		return df.format(a);
	}
	
	public void moveX() {
		Game game = Game.getInstance();
		
		if (dx < 0) {
			x = Math.max(0, x + dx);
		} else {
//			if (this instanceof Enemy)
//				x = Math.min(game.width - Resources.getInstance().getEnemyImage(name, game.getGameMap().getType(), state).getImage().getWidth(null), x + dx);
//			else if (this instanceof Character)
			x = Math.min(game.width - width, x + dx);
		}
	}
	public Rectangle getBounds() {
		return new Rectangle((int) x + 1, (int) y - height + 1, width - 2, height - 2);//width, height);
	}
	public String getName() {
		return name;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getDx() {
		return dx;
	}
	public double getDy() {
		return dy;
	}
	public int getState() {
		return state;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getXy() {
		return xy;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public void setWidth(int w) {
		this.width = w;
	}
	public void setHeight(int h) {
		this.height = h;
	}
	public void setXy(int xy) {
		this.xy = xy;
	}
}
