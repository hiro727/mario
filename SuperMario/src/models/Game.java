package models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import ui.DoubleKeyMap;

public abstract class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = -5274250077445932270L;
	
	protected static Game instance;
	
	protected int width, height;
	protected boolean resizable;

//	private Insets insets;
	private Image image;
	private Graphics doubleG;
	private Rectangle r;
	
	protected int offsetX, offsetY;
	
	protected GameMap gameMap;
	
//	protected Map<String, java.lang.Character> mapObjectTypes;
	
	
	
	protected Map<String, Player> chaMap = new HashMap<String, Player>() {
		private static final long serialVersionUID = 4974616223256861220L;

		public java.util.Set<String> keySet() {
			Set<String> strings = super.keySet();
			System.out.println("\tSetting player to : " + strings.iterator().next());
			return strings;
		};
	};
	protected Map<String, Enemy> eneMap = new HashMap<String, Enemy>() {
		private static final long serialVersionUID = 8045283599311915113L;
		public Enemy put(String key, Enemy value) {
			System.out.println("Initializing - Enemy\t\t" + key);
			return super.put(key, value);
		};
	};
	
	private Thread thread;
	
	protected Player player;
	protected ArrayList<Enemy> enemyList;
	protected DoubleKeyMap enemyTrigger;
	protected GameObjectStack stack;
	
	public Game(int w, int h) {
		width = w;
		height = h;
		
		initialize();
	}
	public Game(int w, int h, boolean resizable) {
		width = w;
		height = h;
		this.resizable = resizable;
		
		initialize();
	}
	
	public static Game getInstance() {
		return instance;
	}
	
	private void initialize() {
		
		JFrame f = new JFrame();
		f.pack();
//		insets = f.getInsets();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
		setSize(width, height);
		setResizable(resizable);
		setLocationRelativeTo(null);
		
		offsetX = 0;
		offsetY = 0;
//		r = new Rectangle(insets.left, insets.top, width, height);
		r = new Rectangle(0, 0, width, height);
		
		
		enemyList = new ArrayList<Enemy>();
		enemyTrigger = new DoubleKeyMap();
		stack = new GameObjectStack();
		
		
	}
	

	@Override
	public void update(Graphics g) {
//		System.out.println("preparing first buffer");
		if(image==null){
			image = createImage(width, height);
			doubleG = image.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, width, height);
		doubleG.setColor(getForeground());
		paint(doubleG);
		//g.drawImage(image, 0, 0, this);
		g.drawImage(image, 0, 0, (int) r.getMaxX(), (int) r.getMaxY(), 0, 0, width, height, this);
	}

	boolean start;
	@Override
	public void paint(Graphics g) {
		// ... TODO ...
		// offset x
		
		g.drawImage(gameMap.getMapImg(), 0, 0, width, height, offsetX, offsetY, offsetX + width, offsetY + height, this);
//		Map<String, List<List<ImageIcon>>> map = Resources.getInstance().getEnemyImage();
		
		player.paint(g, this);
		
		for (Enemy e : enemyList) {
			e.paint(g, this);
		}
		
		
//		g.drawImage(getMapImageWithoutPlayer(), 0, 0, this);
		
//		if (!start) {
//			g.drawImage(gameMap.getMapImg(), 0, 0, this);
//			
//			Map<String, List<List<BufferedImage>>> map = Resources.getInstance().getEnemyImage();
//			
//			for (Enemy e : enemyList) {
//				e.paint(g, this, map);
//			}
//			start = true;
//		}
		
	}
	
	public Graphics overpaint(Image img) {
		if(image==null){
			image = createImage(width, height);
			doubleG = image.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, width, height);
		doubleG.setColor(getForeground());
		doubleG.drawImage(img, 0, 0, this);
		
		getGraphics().drawImage(image, 0, 0, (int) r.getMaxX(), (int) r.getMaxY(), 0, 0, width, height, this);
		
		return getGraphics();
	}
	public Game overlay(Image img) {
		
		doubleG.drawImage(img, 0, 0, this);
		getGraphics().drawImage(image, 0, 0, (int) r.getMaxX(), (int) r.getMaxY(), 0, 0, width, height, this);
		
		return this;
	}
	
	public BufferedImage getMapImageWithoutPlayer() {
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = image.getGraphics();
		g.drawImage(gameMap.getMapImg(), 0, 0, width, height, offsetX, offsetY, offsetX + width, offsetY + height, this);
		
//		Map<String, List<List<ImageIcon>>> map = Resources.getInstance().getEnemyImage();
		
		for (Enemy e : enemyList) {
			e.paint(g, this);
		}
		return image;
	}
	
	public Map<String, Player> getChaMap() {
		return chaMap;
	}
	public Map<String, Enemy> getEneMap() {
		return eneMap;
	}
	public GameMap getGameMap() {
		return gameMap;
	}
//	public Map<String, java.lang.Character> getMapObjectTypes() {
//		return mapObjectTypes;
//	}
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	public abstract void setUp();
	public abstract void reset();
	public abstract void prepare(GameMap map);
	
	public void start() {
		assert(thread == null || thread.isInterrupted());
		
		thread = new Thread(this);
		thread.start();
		
	}
	public abstract void stop();
	public abstract void pause();
	public abstract void resume();
	public abstract void updateGameObjectPosition(int originalXY, int newXY, GameObject go);
}
