package superMarioModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import smEnemies.Bowser;
import smEnemies.Goomba;
import smMapObjects.Block;
import smMapObjects.Ground;
import smMapObjects.InvisibleBlock;
import smMapObjects.Pipe;
import smPlayers.Luigi;
import smPlayers.Mario;
import models.GameObject;
import models.MapObject;
import models.Player;
import models.Enemy;
import models.Game;
import models.GameMap;

public class SMGame extends Game {
	
	private static final long serialVersionUID = -5089369004110525656L;

	private int count = 0;
	private long eTime = 0;
	
	private static final char BACKGROUND = ' ';
	private static final char GROUND = 'g';
	private static final char OBJECT = 'o';
	private static final char GROUND_BLOCK = 'b';
	private static final char PIPE = 'p';
	
	public static Map<Character, Class<?>> MAP_OBJECTS;
	
	private SMGame(int w, int h) {
		super(w, h);
		
		if (MAP_OBJECTS != null)
			return;
		MAP_OBJECTS = new HashMap<Character, Class<?>>();
		
		MAP_OBJECTS.put(GROUND, Ground.class);
		MAP_OBJECTS.put(OBJECT, Ground.class);
		MAP_OBJECTS.put(GROUND_BLOCK, Block.class);
		MAP_OBJECTS.put(PIPE, Pipe.class);
	}

	private SMGame(int w, int h, boolean resizable) {
		super(w, h, resizable);
		
	}

	public static SMGame createInstance(int w, int h) {
		assert(instance == null);
		
		instance = new SMGame(w, h);
		
		assert(instance instanceof SMGame);
		
		((SMGame) instance).initializeObjectTypes();
		
		return (SMGame) instance;
	}
	public static SMGame createInstance(int w, int h, boolean resizable) {
		assert(instance == null);
		
		instance = new SMGame(w, h, resizable);
		
		assert(instance instanceof SMGame);

		((SMGame) instance).initializeObjectTypes();
		
		return (SMGame) instance;
	}
	
	private void initializeObjectTypes() {
//		mapObjectTypes = new HashMap<String, java.lang.Character>();
//		
//		mapObjectTypes.put("BACKGROUND", BACKGROUND);
//		mapObjectTypes.put("GROUND", GROUND);
//		mapObjectTypes.put("OBJECT", OBJECT);
//		mapObjectTypes.put("GROUND/OBJECT", GROUND_BLOCK);
//		
//		assert(mapObjectTypes != null);
//		
//		assert(mapObjectTypes.size() > 0);
	}

	@Override
	public void setUp() {
		
//		chaMap = new HashMap<String, Character>();
//		eneMap = new HashMap<String, Enemy>();
		Player character = new Mario();
		chaMap.put(character.getName(), character);
		character = new Luigi();
		chaMap.put(character.getName(), character);
		
		Enemy enemy = new Goomba();
		eneMap.put(enemy.getName(), enemy);
		enemy = new Bowser();
		eneMap.put(enemy.getName(), enemy);
		
//		gameMap = new GameMap();
		
		assert(chaMap.size() > 0);
		player = chaMap.get(SMPlayer.getName(0));
	}

	@Override
	public void reset() {
		
		
	}

	@Override
	public void prepare(GameMap map) {
		
		System.out.println("\nPreparing Copy of Game Map : " + map.getName());
		
		enemyList = new ArrayList<Enemy>(map.getEnemies().size());
		
		for (int i = 0; i < map.getEnemies().size(); i++) {
			enemyList.add(map.getEnemies().get(i).clone());
		}

		enemyTrigger = map.getEnemyTrigger().clone();
		
		
		
	}
	@Override
	public void start() {
		assert(gameMap == null);
		
		gameMap = new SMGameMap();
		prepare(gameMap);
		
		eTime = System.currentTimeMillis();
		
		super.start();
	}

	@Override
	public void run() {

		System.out.println("\tSTART.....");
		System.out.println("\t\t  Count\t\t\tTime\t\t||\t\t  X\t  Y\t\t  DX\t  DY");
		while (true) {
			
//			System.out.println("\t\tCount : " + count++ + "\t\tEllapsed Time : " + (System.currentTimeMillis() - eTime));
			System.out.print("\n\t\tCount : " + count++ + "\tEllapsed Time : " + (System.currentTimeMillis() - eTime));
			
			player.update();
			
			for (Enemy e : enemyList) {
				e.update();
			}
			
//			 ... TODO ...
			gameMap.update();
			
			update(getGraphics());
//			repaint();
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
//
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		// ... TODO ...
//		// offset x
//		g.drawImage(gameMap.getMapImg(), 0, 0, width, height, offsetX, offsetY, offsetX + width, offsetY + height, this);
//	}
	@Override
	public void stop() {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}
	
	@Override
	public void updateGameObjectPosition(int xy1, int xy2, GameObject go) {
		// ... TODO ...
		// update the stack 
//		stack.
		
	}
}
