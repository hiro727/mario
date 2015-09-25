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
		
		//assert(instructions != null);
		//assert(instructions.length >= 2);
		
        String inst0 = (instructions != null && instructions.length > 0 ? instructions[0] : "Resources.txt");
        String inst1 = (instructions != null && instructions.length > 1 ? instructions[1] : "Area1-1.txt");
		
        ResourceLoader.createInstance(inst0);
		
		game = SMGame.createInstance(600, 500);
		
		Resources.createInstance();
		
		ResourceLoader.getInstance().setNextInstruction(inst1);
		
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
