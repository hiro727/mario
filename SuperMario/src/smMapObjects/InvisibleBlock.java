package smMapObjects;

import models.Bottom;
import models.MapObject;

public class InvisibleBlock extends MapObject implements Bottom {
	
	public InvisibleBlock() {
		super("InvisibleBlock");
		System.out.println(left);
		System.out.println(right);
		System.out.println(top);
		System.out.println(bottom);
	}

	@Override
	public int getBottomOffset() {
		return height;
	}@Override
	public double getBottomGradient() {
		return 0;
	}@Override
	public int getBottomAt(int x) {
		return (int) (getBottomOffset() + getBottomGradient() * x);
	}
}
