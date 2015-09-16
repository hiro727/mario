package models;

public class MapObject {

	protected String name;
	protected boolean left, right, top, bottom;
	protected int width, height;
	
	
	public MapObject(String name) {
		this.name = name;
		if (this instanceof Left)
			left = true;
		if (this instanceof Right)
			right = true;
		if (this instanceof Top)
			top = true;
		if (this instanceof Bottom)
			bottom = true;
	}
	public double getGradient() {
		return 0;
	}
	
	public boolean hasLeft() {
		return left;
	}
	public boolean hasRight() {
		return right;
	}
	public boolean hasTop() {
		return top;
	}
	public boolean hasBottom() {
		return bottom;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
//	public void checkCollision(GameObject gameObject, int n) {
//		switch (n) {
//		case 0:
//			// top left
//			if (!bottom && !right)
//				return;
//			
//			break;
//		case 1:
//			// top center
//			if (!bottom)
//				return;
//			
//			break;
//		case 2:
//			// top right
//			if (!bottom && !left)
//				return;
//			
//			break;
//		case 3:
//			// middle left
//			if (!right)
//				return;
//			
//			break;
//		case 4:
//			//middle center
//			
//			break;
//		case 5:
//			//middle right
//			if (!left)
//				return;
//			
//			break;
//		case 6:
//			//bottom left
//			if (!top && !right)
//				return;
//			
//			break;
//		case 7:
//			// bottom center
//			if (!top)
//				return;
//			
//			break;
//		case 8:
//			// bottom right
//			if (!top && !left)
//				return;
//			
//			break;
//		default:
//			break;
//		}
//	}
	public static String checkCollision(MapObject[] mapObjects, GameObject gameObject, int xy, int x) {
		
		// n = 0;
		for (int i = 0; i < mapObjects.length; i++) {
			
			if (mapObjects[i] != null) {
				
			}
		}
		// n = 1;
		// n = 2;
		// n = 3;
		// n = 4;
		// n = 5;
		// n = 6;
		// n = 7;
		// n = 8;
		
		
		return "~";
	}

	public String checkCollision(GameObject gameObject, int n) {
		System.out.println(n);
		switch (n) {
		case 0:
			// top left
			if (!bottom && !right)
				return "~";
			
			break;
		case 1:
			// top center
			if (!bottom)
				return "~";
			
			break;
		case 2:
			// top right
			if (!bottom && !left)
				return "~";
			
			break;
		case 3:
			// middle left
			if (!right && !top)
				return "~";
			
			break;
		case 4:
			//middle center
			if (!top)
				return "~";
			System.out.println("\n" + gameObject.y + "\t\t" + ((Top) this).getTopAt(((int) gameObject.x + gameObject.width / 2) % Game.getInstance().getGameMap().w));
			
			break;
		case 5:
			//middle right
			if (!left && !top)
				return "~";
			
			break;
		case 6:
			//bottom left
			if (!top && !right)
				return "~";
			
			break;
		case 7:
			// bottom center
			if (!top)
				return "~";
			System.out.println("\n" + gameObject.y + "\t\t" + ((Top) this).getTopAt(((int) gameObject.x) % Game.getInstance().getGameMap().w));
			break;
		case 8:
			// bottom right
			if (!top && !left)
				return "~";
			
			break;
		default:
			break;
		}
		return  "›";
	}
}
