package smMapObjects;

import models.Bottom;
import models.Left;
import models.MapObject;
import models.Right;
import models.Top;

public class Ground extends MapObject implements Left, Right, Top, Bottom {
	
	public Ground() {
		super("Ground");
	}

	@Override
	public int getLeftOffset() {
		return 0;
	}@Override
	public double getLeftGradient() {
		return 0;
	}@Override
	public int getLeftAt(int y) {
		return (int) (getLeftOffset() + getLeftGradient() * y);
	}
	
	
	@Override
	public int getRightOffset() {
		return width;
	}@Override
	public double getRightGradient() {
		return 0;
	}@Override
	public int getRightAt(int y) {
		return (int) (getRightOffset() + getRightGradient() * y);
	}

	
	@Override
	public int getTopOffset() {
		return 0;
	}@Override
	public double getTopGradient() {
		return 0;
	}@Override
	public int getTopAt(int x) {
		return (int) (getTopOffset() + getTopGradient() * x);
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
