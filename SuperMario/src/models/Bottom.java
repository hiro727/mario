package models;

public interface Bottom extends Boundary {
	
	public int getBottomOffset();
	public double getBottomGradient();

	public int getBottomAt(int x);
}
