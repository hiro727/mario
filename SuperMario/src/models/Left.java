package models;

public interface Left extends Boundary {
	
	public int getLeftOffset();
	public double getLeftGradient();

	public int getLeftAt(int y);
}
