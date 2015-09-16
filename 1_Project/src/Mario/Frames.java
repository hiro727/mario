package Mario;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.applet.Applet;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import net.java.games.input.Controller;

import javazoom.jl.decoder.JavaLayerException;

public class Frames extends Applet implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
	
	
	Jinputjoystick j;
	Executor joystick = Executors.newSingleThreadExecutor();
	
	Thread t;
	Image image;
	Graphics doubleG;
	
	Pictures p;
	int stagex, stagey;
	static String mainWorld = "world";
	static String[] names = {"1-1","1-2","1-3","1-4","2-1","2-2","2-3","2-4"};
	static int worldcounter = 0;  //1-1
	//static String world = "1", stage = "1";
	musicPlayer mp;
	Executor soundtrack = Executors.newSingleThreadExecutor();
	//String[] list = {"main","underworld","starman","castle","castle-complete","level-complete","game-over","hurry-underground"};
	int musicChooser = 0;
	
	BasicEnemyItemBlockInfo b;
	
	Mario m;
	boolean isMoving     = false;
	boolean isStanding   = true;
	boolean isDead	     = false;
	boolean rightpressed = false;
	boolean leftpressed  = false;
	enum ymove {STOP,JUMP,CROUCH};		//y movement
	ymove y = ymove.STOP;
	enum direction {RIGHT, LEFT};		//x direction
	direction d = direction.RIGHT;
	enum xmove {STOP, WALK, RUN};		//x movement
	xmove x = xmove.STOP;
	int counter = 0;
	
	
	Objects[][] o;
	
	Coin[]	  c;
	Item[][]  i;
	Enemy[][] e;
	int   	Inum,Enum,Bnum;
	int[] 	Ieachnum,Eeachnum,Beachnum;
	int[][]	blkX,blkY;
	
	Ground g;
	int height, width, h = 300, w = 400;
	BufferedImage bg;
	java.awt.Rectangle r;
	
	//public static String fullPath;
	
	BufferedImage screen;
	ActionListener main = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			maxtime--;
			if(maxtime==0){
				maintimer.stop();
			}//System.out.println(maxtime);
			repaint();
		}
	};
	Timer maintimer = new Timer(1000,main);
	int subtimecounter=0,maxtime = 400;
	
	long ctr = 0;
	public int getColor(int x, int y){
		return bg.getRGB(x, y);
	}
	
	@Override
	public void init() {
		try {
			int pathChar = getCodeBase().getPath().length();
			//fullPath = getCodeBase().getPath().substring(0, pathChar-5)+"/";
			//screen = ImageIO.read(new URL(getCodeBase(),fullPath+"images/screen/reset&load.bmp"));
			System.out.println(pathChar+"  "+getCodeBase());
			screen = ImageIO.read(new URL(getCodeBase(),"images/screen/reset&load.bmp"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		j = new Jinputjoystick(Controller.Type.GAMEPAD, Controller.Type.STICK);
		setSize(w,h);
		setFocusable(true);
		p = new Pictures(this);
			Inum = Pictures.maxI;
			Enum = Pictures.maxE;
			Bnum = 4;
		mp = new musicPlayer();
		musicChooser = 0;System.out.println("initializing music");
		executeBGM();
		initialize();
		r = new java.awt.Rectangle(0,0,w,h);
/*		System.out.println(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
		System.out.println("before "+w/h);
		double yscale = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/h;
		setMaximumSize(new Dimension((int)(w*yscale), java.awt.Toolkit.getDefaultToolkit().getScreenSize().height));//.width/w*h));
		//setMaximumSize(new Dimension(800,700));
		addComponentListener(new ComponentAdapter() {
	            public void componentResized(ComponentEvent e) {
	                // This is only called when the user releases the mouse button.
	            	//System.out.println(getSize()+"\n"+java.awt.Toolkit.getDefaultToolkit().getScreenSize());
	            	if(getWidth()==java.awt.Toolkit.getDefaultToolkit().getScreenSize().width){
	            		//System.out.println(getMaximumSize());
	            		setSize(getMaximumSize());System.out.println("after  "+getWidth()/getHeight());
	            	}
	                
	            }
	        });
		//System.out.println(width+" "+height);
		
		//setMaximumSize(new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/w*h));
*/	}
	public void initialize(){
		try{
			//bg = ImageIO.read(new URL(getCodeBase(),fullPath+"images/world "+names[worldcounter]+".png"));
			bg = ImageIO.read(new URL(getCodeBase(),"images/world "+names[worldcounter]+".png"));
			width = bg.getWidth();
			height = bg.getHeight();
			System.out.println("Initializing Background  "+width+ " & "+height);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		g = new Ground(this);
		b = new BasicEnemyItemBlockInfo(p, g);
			Ieachnum = BasicEnemyItemBlockInfo.itemNum;//System.out.println(Ieachnum.length);
			i = new Item[Inum][];
			
			Eeachnum = BasicEnemyItemBlockInfo.eneNum;
			e = new Enemy[Enum][];
			
			Beachnum = BasicEnemyItemBlockInfo.blkNum;
			o = new Objects[Bnum][];
					
		
		
		for(int x=0;x<Bnum;x++){
			o[x] = new Objects[Beachnum[x]];
			i[x] = new Item[Beachnum[x]];
			for(int y=0;y<Beachnum[x];y++){
				o[x][y] = new Objects(this, g, i[x][y], p, x, y);
			}
		}//System.out.println(e.length);
		for(int x=0;x<Enum;x++){
			e[x] = new Enemy[Eeachnum[x]];
			for(int y=0;y<Eeachnum[x];y++){
				e[x][y] = new Enemy(this, g, p, b, x, y);
			}
		}
		m = new Mario(this, g, e, p, Bnum, Beachnum);
		for(int x=1;x<Bnum;x++){
			i[x] = new Item[Beachnum[x]];
		}
		isMoving     = false;
		isStanding   = true;
		isDead	     = false;
		rightpressed = false;
		leftpressed  = false;
		y = ymove.STOP;
		x = xmove.STOP;
		if(!j.isControllerConnected()){
			System.out.println("controller is not connected\nkeyboard not available...");
			addKeyListener(this);
		}else{
			System.out.println("controller is connected\njoystick available...");
			joystick.execute(checkjoystickinput);
		}
		maintimer.start();
	}
	public Runnable bgmplayer = new Runnable(){
		public void run() {System.out.println("executing thread for soundtrack");
			try {
				playBGM();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		}		
	};
	public Runnable effectsoundplayer = new Runnable(){
		public void run() {System.out.println("executing thread for sound effect");
			try {
				playsound();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		}		
	};
	@Override
	public void start() {
		
		t = new Thread(this);
		t.start();
	}
	public void executeBGM(){
		System.out.println("playing music");
		soundtrack.execute(bgmplayer);
	}public void playBGM() throws JavaLayerException{
		System.out.println(" music played");
		mp.playsoundtrack(musicChooser);
	}public void stopBGM() throws IOException, JavaLayerException{
		System.out.print(musicChooser);
		System.out.println("music stoped");
		mp.stopsoundtrack(musicChooser);
		musicChooser = 6;
		//execute(0);		//play music
	}public void musicpause() throws IOException{
		mp.pause(musicChooser);
	}public void resumeBGM(){
		
	}public void executesound(){
		soundtrack.execute(effectsoundplayer);
	}public void playsound() throws JavaLayerException{
		System.out.println(" music played");
		mp.playsound(musicChooser);
	}
	
	@Override
	public void stop() {
		System.out.println("Applet has stoped");
	}
	@Override
	public void destroy() {
		
	}
	@Override
	public void run() {
		while(true){
			if(!m.istempDead()&&!m.isDead()){
				if((x == xmove.WALK||x == xmove.RUN)&&(leftpressed||rightpressed))isMoving=true;
				//if(!m.getFlying())System.out.println(m.getFlying()+" "+isMoving);
				if(isMoving){
					m.update(x.toString(), y.toString(), d.toString(), counter++/3, this, o);
					if(counter==8)counter = 0;//System.out.println(counter);
				}else if(m.getFlying()){
					m.update(x.toString(), y.toString(), d.toString(), counter++/3, this, o);
					if(counter==8)counter = 0;//System.out.println(counter);
				}else if(m.isTransforming()){
					m.updateshape(d.toString());
				}
/*				for(int x=0;x<Inum;x++){
					for(int y=0;y<Ieachnum[x];y++){
						if(i[x][y].isApppear()&&!i[x][y].isCollected()){
							i[x][y].update(this, m);
						}
					}
				}
*/				for(int x=0;x<Enum;x++){
					for(int y=0;y<Eeachnum[x];y++){
						if(e[x][y].isAppeared()){
						if(!e[x][y].isKilled()&&!e[x][y].isStomped()&&!e[x][y].isTransformed()){
							e[x][y].update(m);
						}else if(e[x][y].isKilled()){
							
						}else if(e[x][y].isTransformed()){
							if(e[x][y].isKickable())	e[x][y].transformupdate(m, d.toString());
							else						e[x][y].setTransformcounter(1);
						}else if(e[x][y].isStomped()){
							e[x][y].stompupdate();
						}
						}
					}
				}
				for(int x=0;x<Bnum;x++){
					for(int y=0;y<Beachnum[x];y++){
						o[x][y].update(m);
					}
				}
				
			}else if(!m.isDead()){//System.out.println("mario dead operation"+musicChooser);//
				if(musicChooser!=6){
					try {
						removeKeyListener(this);
						validate();
						stopBGM();
						soundtrack.execute(bgmplayer);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}		//stop music
					
				}
				m.deadOperation();	
			}
			
			
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void update(Graphics g) {
		if(image==null){
			image = createImage(this.getSize().width, this.getSize().height);
			doubleG = image.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		doubleG.setColor(getForeground());
		paint(doubleG);
		//g.drawImage(image, 0, 0, this);
		g.drawImage(image, 0, 0, (int)r.getMaxX(), (int)r.getMaxY(), 0, 0, w, h, this);
	}
	@Override
	public void paint(Graphics g) {
		if(!m.isDead()){
		if(m.getX()<250)				g.drawImage(bg, 0, 0, this);
		else if(m.getX()>width-300)		g.drawImage(bg, -width+300, 0, this);
		else							g.drawImage(bg, -m.getX()+250, 0, this);
		
		/*for(int i=0;i<e.getnum();i++){
			e.paint(g, this,i);
		}
		e.paint(g, this,0);
		*/
		for(int x=0;x<Bnum;x++){
			for(int y=0;y<Beachnum[x];y++){
				//System.out.println(x+" "+y);
					o[x][y].paint(g, this, m);
			}
		}
/*		for(int x=0;x<Inum;x++){
			for(int y=0;y<Ieachnum[x];y++){
				//System.out.println(x+" "+y);
				if(i[x][y].isApppear()&&!i[x][y].isCollected())
					i[x][y].paint(g, this);
			}
		}
*/		for(int x=0;x<Enum;x++){
			for(int y=0;y<Eeachnum[x];y++){
				//System.out.println(x+" "+y);
				if(e[x][y].isAppeared()&&!e[x][y].isKilled()){
					e[x][y].paint(g, this, m);
				}
				
			}
		}
		if(!m.istempDead()&&!m.isDead()){
			m.paint(g, this);
		}
		else {//System.out.println("Mario is dead");
			m.paintdead(g, this);
		}
		g.setFont(new Font(null,Font.BOLD,15));
		g.setColor(Color.white);
		g.drawString("TIME", getWidth()-100, 30);
		g.drawString(""+maxtime, getWidth()-100, 50);
		}else{
			if(ctr==0)	ctr = System.currentTimeMillis();
			while(System.currentTimeMillis()<ctr+1500){
				
			}
			if(System.currentTimeMillis()<ctr+3000||(System.currentTimeMillis()>ctr+3003&&System.currentTimeMillis()<ctr+4000)){
				//System.out.println("black screen at "+(c+4000-System.currentTimeMillis()));
				blackScreen(g);
			}else if(System.currentTimeMillis()>=ctr+4000){System.out.println("Mario is dead");
				ctr = 0;
				initialize();
			}else{
				System.out.println("initializing music");
				blackScreen(g);
				mp = new musicPlayer();
				musicChooser = 0;
				executeBGM();
			}
			
		}
		java.awt.Rectangle window = g.getClipBounds(getBounds());
		if(!window.equals(r)){
			System.out.println("resized");
			r = new java.awt.Rectangle(0, 0, getWidth(), getHeight());
		}//System.out.println("window  "+g.getClipBounds(getBounds())+"\ninitial "+g.getClipBounds(r));
		//System.out.println(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width+"  "+getWidth());if(isMaximumSizeSet())System.out.println("max size");
		repaint();
	}
	
	public void blackScreen(Graphics g){
		g.clearRect(0, 0, width, height);
		g.drawImage(screen, 0, 0, this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_SHIFT:
			x = xmove.RUN;
		}
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			y = ymove.JUMP;//System.out.println("up pressed");
			isMoving = true;
			break;
		case KeyEvent.VK_U:
			y = ymove.JUMP;//System.out.println("up pressed");
			isMoving = true;
			break;
		case KeyEvent.VK_SPACE:
			y = ymove.JUMP;//System.out.println("up pressed");
			isMoving = true;
			break;
		case KeyEvent.VK_DOWN:
			y = ymove.CROUCH;
			isMoving = true;
			break;
		case KeyEvent.VK_D:
			y = ymove.CROUCH;
			isMoving = true;
			break;
		}
		switch(e.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			d = direction.RIGHT;
			if(x != xmove.RUN)x = xmove.WALK;
			rightpressed = true;	isMoving = true;
			break;
		case KeyEvent.VK_R:
			d = direction.RIGHT;
			if(x != xmove.RUN)x = xmove.WALK;
			rightpressed = true;	isMoving = true;
			break;
		case KeyEvent.VK_LEFT:
			d = direction.LEFT;
			if(x != xmove.RUN)x = xmove.WALK;
			leftpressed = true;	isMoving = true;
			break;
		case KeyEvent.VK_L:
			d = direction.LEFT;
			if(x != xmove.RUN)x = xmove.WALK;
			leftpressed = true;	isMoving = true;
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_SHIFT:
			x = xmove.STOP;
			isMoving = false;
		}
		switch(e.getKeyCode()){
		case KeyEvent.VK_RIGHT:
			x = xmove.STOP;
			rightpressed = false;	isMoving = false;
			m.stop(d.toString());
			break;
		case KeyEvent.VK_R:
			x = xmove.STOP;
			rightpressed = false;	isMoving = false;
			m.stop(d.toString());
			break;
		case KeyEvent.VK_LEFT:
			x = xmove.STOP;
			leftpressed = false;	isMoving = false;
			m.stop(d.toString());
			break;
		case KeyEvent.VK_L:
			x = xmove.STOP;
			leftpressed = false;	isMoving = false;
			m.stop(d.toString());
			break;
		}switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			y = ymove.STOP;
			isMoving = false;
			break;
		case KeyEvent.VK_U:
			y = ymove.STOP;
			isMoving = false;
			break;
		case KeyEvent.VK_SPACE:
			y = ymove.STOP;
			isMoving = false;
			break;
		case KeyEvent.VK_DOWN:
			y = ymove.STOP;
			isMoving = false;
			break;
		case KeyEvent.VK_D:
			y = ymove.STOP;
			isMoving = false;
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	public void setMusicChooser(int musicChooser) {this.musicChooser = musicChooser;}
	public void stopMoving(){
		//System.out.println("stoping");
		counter = 0;
		m.stop(d.toString());
	}
	public Runnable checkjoystickinput = new Runnable(){
		public void run() {//System.out.println("executing thread for soundtrack");
			while(true){
				if(!j.pollController()){
					System.out.println("controller disconnected");
					break;
				}//System.out.println("checking jinput");
				if(m.isDead()){System.out.println("controller disconnected");
					break;
				}
				
				int xValuePercentageLeftJoystick  = j.getX_LeftJoystick_Percentage();
				int yValuePercentageLeftJoystick  = j.getY_LeftJoystick_Percentage();
				float hatSwitchPosition 		  = j.getHatSwitchPosition();
				ArrayList<Boolean> buttonsValues  = j.getButtonsValues();
				//System.out.println(buttonsValues.size());
			/*	for(int i=0;i<buttonsValues.size();i++){
					System.out.println("button"+i+" = "+buttonsValues.get(i));
			 	}
				
				System.out.print("left  x: "+xValuePercentageLeftJoystick +" & y: "+yValuePercentageLeftJoystick+"  ");
			*/	if(buttonsValues.get(2)){
					//System.out.println("~ button pressed");
					x = xmove.RUN;
				}else if(buttonsValues.get(7)){
					//System.out.println("R1 button pressed");
					x = xmove.RUN;
				}
				if(buttonsValues.get(0)){
					//System.out.println("¢ button pressed");
					y = ymove.JUMP;
				}else if(buttonsValues.get(1)){
					//System.out.println("› button pressed");
					y = ymove.JUMP;
					isMoving = true;
				}else if(!buttonsValues.get(0)&&!buttonsValues.get(1)){
					isMoving = false;
					y = ymove.STOP;
				}if(buttonsValues.get(3)){
					System.out.println("  button pressed");
				}if(buttonsValues.get(8)){
					System.out.println("select button pressed");
				}else if(buttonsValues.get(9)){
					System.out.println("start button pressed");
				}else if(buttonsValues.get(12)){
					System.out.println("home button pressed");
				}
				
				
				
				if(xValuePercentageLeftJoystick>49||(hatSwitchPosition>0.25&&hatSwitchPosition<0.75)){
					if(x!=xmove.RUN)	x = xmove.WALK;
					d = direction.RIGHT;
					rightpressed = true;	leftpressed = false;
					isMoving = true;
				}else if(xValuePercentageLeftJoystick<49||(hatSwitchPosition>0.75)){
					if(x!=xmove.RUN)	x = xmove.WALK;
					d = direction.LEFT;
					leftpressed = true;		rightpressed = false;
					isMoving = true;
				}else if(yValuePercentageLeftJoystick>49||hatSwitchPosition==0.75){
					y = ymove.CROUCH;
					isMoving = true;
				}else if(y!=ymove.JUMP){
					rightpressed = false;	leftpressed = false;
					isMoving = false;
					x = xmove.STOP;
					//stopMoving();
				}if((xValuePercentageLeftJoystick==49&&hatSwitchPosition==0)&&y==ymove.STOP){
					stopMoving();//System.out.println(xValuePercentageLeftJoystick+"  "+hatSwitchPosition);
				}
				
				//System.out.println(hatSwitchPosition);
				try {
	                Thread.sleep(20);
	            } catch (InterruptedException ex) {
	                Logger.getLogger(Frames.class.getName()).log(Level.SEVERE, null, ex);
	            }
			}
		}
	};
}
