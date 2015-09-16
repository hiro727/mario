package Mario;

import java.awt.geom.Rectangle2D;

public class EmptyShape {
	private Rectangle2D block;
	private int x = 320, y = 180;
	private double []xcoor={x,x+20,x+20,x};
	private	double []ycoor={y,y,y+20,y+20};
	private boolean broke = false;
	public EmptyShape() {
		block = createShape();
	}
	public Rectangle2D createShape(){
		return new Rectangle2D.Double(xcoor[0],ycoor[0],20,20);
	}
	
	public void checkLowerCollision(Mario m){
		if(new Rectangle2D.Double(m.getX(), m.getY(), m.getsX(), m.getsY()).intersects(block)){
			System.out.println("intersects downwall");
			m.setY(y+20);
			m.setdY(0);
		}
	}
	public void checkUpperCollision(Mario m){
		if(new Rectangle2D.Double(m.getX(), m.getY(), m.getsX(), m.getsY()).intersects(block)){
			System.out.println("intersects upperwall");
			if(!m.getCurrent().equals("small"))		broke = true;
			m.setY(y+20);
			m.setdY(0);
		}
	}
	public void checkLeftSideCollision(Mario m){
		if(new Rectangle2D.Double(m.getX(), m.getY(), m.getX()+m.getsX(), m.getY()+m.getsY()).intersects(block)){
			System.out.println("intersects leftwall");
			m.setX(x-m.getsX());
		}
	}
	public void checkRightSideCollision(Mario m){
		if(new Rectangle2D.Double(m.getX(), m.getY(), m.getX()+m.getsX(), m.getY()+m.getsY()).intersects(block)){
			System.out.println("intersects rightwall");
			m.setX(x+20);
		}
	}
	public boolean isBroke() {
		return broke;
	}
}
