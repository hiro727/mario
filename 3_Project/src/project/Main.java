package project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Main extends JFrame implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;

	private Image i;
	private Graphics doubleG;
	
	public static Main main;
	
	public static Insets inset;
	
	public static int screen_x = 0;
	
	public static Characters character;
	
//	private final static int SELECTION = 0, PLAY = 1, PAUSE = 2, TONEXT = 3, TOSTART = 4, AGAIN = 5, TERMINATE = 6;
	
	private static boolean shiftDown;
	
	public static double interval = 0.05;
	
	public static void main(String[] args) {
		main = new Main();
	}
	
	public Main(){
		JFrame f = new JFrame();
		f.pack();
		inset = f.getInsets();
		f = null;
		System.out.println(inset);
		
		new MyGraphics();
		new Levels("1-1");
		MyGraphics.createBGImg();
		Borders.createBorder();
		
		character = new Characters(30, 0, 20, 20);
		
		setSize(inset.left + inset.right + 600, inset.top + inset.bottom + 400);
//		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		addKeyListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("running garbage collector");
				System.gc();
				Runtime.getRuntime().runFinalization();
				System.out.println("exit");
				super.windowClosing(e);
			}
		});
		
		
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while(true){
			character.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(i==null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		doubleG.setColor(getForeground());
		paint(doubleG);
		g.drawImage(i, 0, 0, this);
	}
	@Override
	public void paint(Graphics g) {
		g.drawImage(MyGraphics.getBg(), inset.left + screen_x, inset.top,
				inset.left + screen_x + 600, inset.top + 400, screen_x, 0, screen_x + 600, 400, this);
		Borders.paint(g);
		character.paint(g);
		character.paint_details(g);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
//		int c = e.getKeyCode();
//		switch (c) {
//		case KeyEvent.VK_D:
////			System.out.println("move to the right");
//			character.move_horizontal(5);
//			break;
//		
//		case KeyEvent.VK_RIGHT:
////			System.out.println("move to the right");
//			character.move_horizontal(5);
//			break;
//		
//		case KeyEvent.VK_A:
////			System.out.println("move to the left");
//			character.move_horizontal(-5);
//			break;
//		
//		case KeyEvent.VK_LEFT:
////			System.out.println("move to the left");
//			character.move_horizontal(-5);
//			break;
//		
//		case KeyEvent.VK_W:
////			System.out.println("move to the up");
//			character.move_vertical(-5);
//			break;
//		
//		case KeyEvent.VK_UP:
////			System.out.println("move to the up");
//			character.move_vertical(-5);
//			break;
//		
//		case KeyEvent.VK_S:
////			System.out.println("move to the down");
//			character.move_vertical(5);
//			break;
//		
//		case KeyEvent.VK_DOWN:
////			System.out.println("move to the down");
//			character.move_vertical(5);
//			break;
//		
//		case KeyEvent.VK_ESCAPE:
//			System.exit(0);
//			break;
//		
//		default:
//			break;
//		}
		int c = e.getKeyCode();
		if (c == 16){
			shiftDown = true;
			if (character.getStatus() == 1) {
				character.setStatus(3);
			} else if (character.getStatus() == 2) {
				character.setStatus(4);
			}
		} else if (c == 39 || c == 68){
//			System.out.println("move to the right");
			if (shiftDown)
				character.setStatus(3);
			else
				character.setStatus(1);
		
		} else if (c == 37 || c == 65) {
//			System.out.println("move to the left");
			if (shiftDown)
				character.setStatus(4);
			else
				character.setStatus(2);
		
		} else if (c == 38 || c == 32 || c == 87) {
//			System.out.println("move to the up");
			if (!character.isFlying()) {
				character.setDy(-45);
//				character.setU(-10);
			}
			
		} else if (c == 40 || c == 83) {
//			System.out.println("move to the down");
			
		} else if (c == 10) {
//			System.out.println("move to the starting point");
			character.x = 120;
			character.y = 0;
		}
		if(c == 27)	{//escape
			System.gc();
			System.out.println("running garbage collector");
			Runtime.getRuntime().runFinalization();
			System.out.println("exit");
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_SHIFT || e.isShiftDown()){
			shiftDown = false;
		}
		if (c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT){
//			System.out.println("move to the right");
			character.setStatus(0);
		
		} else if (c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT) {
//			System.out.println("move to the left");
			character.setStatus(0);
		
		} else if (c == KeyEvent.VK_W || c == KeyEvent.VK_UP || c == KeyEvent.VK_SPACE) {
//			System.out.println("move to the up");
			
		}
		else if (c == KeyEvent.VK_S || c == KeyEvent.VK_DOWN) {
//			System.out.println("move to the down");
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}