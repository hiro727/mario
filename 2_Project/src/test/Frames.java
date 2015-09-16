package test;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class Frames extends JFrame implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
	Thread t;
	Image offimage;
	Graphics doubleG;
	int w, h;
	Rectangle r;
	Insets insets;
	
	boolean allMoving = true;
	int pipe = 0;
	
	Background background;
	Image image;
	
	Characters mario;
	int screenx, screeny;
	Image character;
	
	Enemies enemies;
	
	int world = 1, area = 1;
	
	public static void main(String[] args){
		new Frames();
	}
	public Insets getInsets() {
		return insets;
	}
	public Frames() {
		w = 600;
		h = 375;
		screenx = 0;
		screeny = 0;
		JFrame f = new JFrame();
		f.pack();
		insets = f.getInsets();System.out.println("insets: "+insets.top+" "+insets.left);
		f = null;
		setSize(new Dimension(insets.left + w, insets.top + h));
		//setSize(w, h);
		
		getRootPane().setDoubleBuffered(true);
		((JComponent)getContentPane()).setDoubleBuffered(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Pictures();
		new Blocks(this);
		new Map(this);
		
		enemies = new Enemies(this);
		background = new Background();
		image = background.getImage();
		totalwidth = background.getScalew()*background.getWidth();
		
		mario = new Characters(this);
		character = mario.getCharacter();
		
		Enemies.addCharacter(mario);
		
		setFocusable(true);
		addKeyListener(this);
		setVisible(true);
		//createBufferStrategy(2);
		
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {}
		Thread t = new Thread(this);
		t.start();
	}
	
	public void loadNewLevel(){
		Map.resetAll();
		Blocks.resetAll();
		background.loadNewLevel(world, area);
	}
	@Override
	public void run() {
		while(true){
			if(pipe == 0){
			Blocks.update();
			Enemies.update();
			mario.update();
			}else if(pipe == 1){
				mario.movinInto_OutOfPipes();
			}else if(pipe == 2){
				Blocks.update();
				Enemies.update();
				mario.movinInto_OutOfPipes();
			}
			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {}
		}
		
	}

	int offsetx, offsety;
	//double buffering
	public void paint(Graphics g) {
		if(offimage == null){
			offimage = createImage(getWidth(), getHeight());
			doubleG = offimage.getGraphics();
		}else if(offimage.getHeight(this) != getHeight() || offimage.getWidth(this) != getWidth()){
			System.out.println("resized");
			double width = getWidth()-insets.left, height = getHeight()-insets.top;
			System.out.println(width+" "+height+" & "+w+" "+h);
			offimage = createImage(getWidth(), getHeight());
			doubleG = offimage.getGraphics();
			if(width > height/h*w){
				offsetx = (int) ((width - height/h*w)/2);
				offsety = 0;
				g.fillRect(0, 0, offsetx, getHeight());
				g.fillRect(getWidth()-offsetx, 0, offsetx, getHeight());
			}else{
				offsety = (int) ((height - width/w*h)/2);
				offsetx = 0;
				g.fillRect(0, 0, getHeight(), offsety);
				g.fillRect(0, getHeight()-offsety, getWidth(), offsetx);
			}
			//g.fillRect(0, 0, getWidth(), getHeight());
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, getWidth(), getHeight());
		doubleG.setColor(getForeground());
		paintOffscreen(doubleG);
		g.drawImage(offimage, offsetx+insets.left, offsety+insets.top, getWidth()-offsetx, getHeight()-offsety, insets.left, insets.top, insets.left+w, insets.top+h, this);
	}

	public void paintOffscreen(Graphics g) {//System.out.print(insets.left+" "+insets.top+" & ");
//		System.out.println("Screen Width: "+getWidth()+" & Screen Height: "+getHeight());
		g.drawImage(image, insets.left, insets.top, insets.left+w, insets.top+h, screenx, screeny, screenx+w, screeny+h, this);
		mario.paint(g);
		Blocks.paintAll(g);
		Enemies.paint(g);
/*		Map.paint(g);
		if(screenx==0)
			g.drawImage(character, insets.left+mario.getX(), (int) (insets.top+mario.getY()), this);
		else
			g.drawImage(character, insets.left+300, (int) (insets.top+mario.getY()), this);
*/		
	}
	public void setScreenx(int screenx) {
		if(this.screenx != screenx)
			Blocks.checkFirstShown();
		this.screenx = screenx;
	}
	public void setScreeny(int screeny) {
		this.screeny = screeny;
	}
	public int getScreenx() {
		return screenx;
	}
	public int getScreeny() {
		return screeny;
	}
	int totalwidth;
	public int getTotalwidth() {
		return totalwidth;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(e.isShiftDown())
				mario.moveRight(1);
			else
				mario.moveRight(0);
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT)
			if(e.isShiftDown())
				mario.moveLeft(1);
			else
				mario.moveLeft(0);
		else if(e.getKeyCode()==KeyEvent.VK_UP)
			mario.moveUp();
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			mario.moveDown();
	}
	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			mario.stop();
		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
			mario.stop();
		else if(e.getKeyCode()==KeyEvent.VK_UP)
			mario.stop();
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			mario.stop();
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	public void enteringPipes(){
		allMoving = false;
		pipe = 1;
	}
	public void outOfPipes(){
		allMoving = true;
		pipe = 0;
	}
	public void warping() {
		allMoving = false;
		pipe = 2;
	}
}
