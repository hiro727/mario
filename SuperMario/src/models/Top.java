package models;

public interface Top extends Boundary {
	
	public int getTopOffset();
	public double getTopGradient();
	
	public int getTopAt(int x);
}
