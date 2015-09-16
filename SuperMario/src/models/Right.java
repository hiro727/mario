package models;

public interface Right extends Boundary {
	
	public int getRightOffset();
	public double getRightGradient();

	public int getRightAt(int y);
}
