package ui;

/*
 * Add the following to the arguments on run
 * 
 * Resources.txt
 * Area1-1.txt
 * 
 */
import superMarioModels.SMGame;
import models.Game;


public class SuperMario {

	private static Game game;
	
	
	public static void main(String[] args) {
		
		new SuperMario(args);
		
	}
	
	public SuperMario(String[] instructions) {
		
		assert(instructions != null);
		assert(instructions.length >= 2);
		
		ResourceLoader.createInstance(instructions[0]);
		
		game = SMGame.createInstance(600, 500);
		
		Resources.createInstance();
		
		ResourceLoader.getInstance().setNextInstruction(instructions[1]);
		
		setUp();
		
		game.setVisible(true);
		
		start();
	}
	
	public void setUp() {
		assert(game != null);
		
		game.setUp();
	}
	
	private void start() {
		assert(game != null);
		
		game.start();
	}
	
}
