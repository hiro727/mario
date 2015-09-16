package superMarioModels;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

import smItems.FireFlower;
import smItems.Fireball;
import smItems.Mushroom;
import smItems.OneUpMushroom;
import smItems.SuperStar;
import specialEffects.Equippable;
import ui.Resources;
import models.Player;
import models.Enemy;
import models.Game;
import models.Item;
import models.Weapon;
import models.WeaponPlayer;

public abstract class SMPlayer extends Player implements Equippable {
	
	private int suspend = -1;
	
	/** timer for temporary transformation*/
	private Timer timer;
	private ActionListener timerListener;
	protected int timeLimitSec;
	
	private ArrayList<Item> equipments;
	
	public SMPlayer(String name) {
		super(name);
		equipments = new ArrayList<Item>();
		createTimer();
	}

	private void createTimer() {
		// 20 seconds timer
		timeLimitSec = 20;
		
		timerListener = new ActionListener() {
			private int sec = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				sec ++;
				
				if (sec >= timeLimitSec) {
					sec = 0;
					timer.stop();
					timeLimitExceed();
				}
			}
		};
		
		timer = new Timer(1000, timerListener);
	}

	@Override
	public void levelUp(Item item) {
		if (item instanceof Mushroom) {
			if (type < 1)
				animateFromTo(type, (type = 1));
			else if (type == 3)
				suspend = 1;
		} else if (item instanceof FireFlower) {
			if (type < 2)
				animateFromTo(type++, type);
			else if (type == 3)
				suspend = 2;
		} else if (item instanceof SuperStar) {
			if (type != 3)
				animateWithTimeLimit(type, (type = 3), 30000);
		} else if (item instanceof OneUpMushroom) {
			life ++;
		}
			
	}

	/**
	 * 
	 * stops the main thread and gives priority to this animation.
	 * <br>
	 * returns to the main thread only after the animation is finished.
	 * @param type
	 * @param newType
	 */
	public void animateFromTo(int type, int newType) {
		
		System.out.println("\n\t\t\tAnimate " + getName() + " from : " + type + " to : " + newType);
		SMGame game = (SMGame) Game.getInstance();
		BufferedImage MTS = game.getMapImageWithoutPlayer();
		
		Image[] imgs = new Image[2];
		imgs[0] = Resources.getInstance().getPlayerImage(getName(), type, state, this);
		imgs[1] = Resources.getInstance().getPlayerImage(getName(), newType, state, this);
		
		int count = -1;
		int[] update = new int[]{0, 0, 0, -1, -1, 0, 0, -1, 0, -1, 1, -1, 1};
		
		while (true) {
			if (count ++ >= update.length - 1) {
				this.type = newType;
				break;
			}
			System.out.println("\t\t\t\tTemporary Animation Count : " + count);
			
			
			if (update[count] != -1)
//				drawImage(game.overpaint(MTS), imgs[update[count]], (int) x, (int) y, game);
				game.overpaint(MTS).drawImage(imgs[update[count]], (int) x, (int) y - height, game);
			else
				game.overpaint(MTS);
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * changes the type only for a limited time period and <br>
	 * overwrites the paint method to display the character differently from usual.
	 * 
	 * @param type 
	 * @param newType
	 * @param time
	 */
	private void animateWithTimeLimit(int type, int newType, int time) {
		suspend = type;
		
		// ... TODO ...
		// Timer start
		// count time
		// switch back after time limit
		timer.setDelay(time);
		
		if (!timer.isRunning()) {
			timer.start();
		} else {
			// ... TODO ...
			// ... may overwrite current timer and restart it
		}
	}
	/**
	 * called when the timer's count exceeds the time limit
	 * REQUIRES:
	 * <br> 
	 * - timer is stopped
	 * <br>
	 * - suspending type exists that is different from current type
	 */
	private void timeLimitExceed() {
		assert(timer != null && !timer.isRunning());
		assert(suspend > -1 && suspend != type);
		
		type = suspend;
		suspend = -1;
	}

	@Override
	public void levelDown(Enemy enemy) {
		if (type == 1 || type == 2) {
			concurrentAnimationFrom(type, 0);
		} else if (type == 0) {
			die();
		}
	}

	/**
	 * animates this character without interfering the main thread.
	 * @param type old type of this character
	 * @param newType new type of this character
	 */
	private void concurrentAnimationFrom(int type, int newType) {
		
	}

	@Override
	public void update() {
		super.update();
		Game game = Game.getInstance();
		for (Item item : equipments) {
			if (item instanceof Weapon) {
				((Weapon) item).moveRelatively();
				item.paint(game);
			}
			if (item instanceof WeaponPlayer) {
				for (Enemy enemy : Game.getInstance().getEnemyList()) {
					if (((WeaponPlayer) item).collideWith(enemy)) {
						enemy.applyDamage(item);
					}
				}
			}
		}
//		System.out.println("EQUIPMENTS : " + equipments.size());
		for (int i = equipments.size() - 1; i >= 0; i--) {
			if (Math.abs(equipments.get(i).getX() - x) >= Game.getInstance().getWidth()) {
				equipments.remove(i);
			}
		}
		
	}
	
	protected void die() {
		
	}
	
	@Override
	public void equip() {
		launch();
	}
	@Override
	public void launch() {
		// TEST with 0 or 1. 
		// this needs to be 2
		if (type == 1) {
			if (equipments == null)
				equipments = new ArrayList<Item>();
			else {
				if (equipments.size() > 0) {
					if (equipments.get(equipments.size() - 1).getCount() < 50)
						return;
				}
			}
//			Image img = Resources.getInstance().getPlayerImage().get(getName()).get(type).get(state).getImage();
//			if (equipments.size() == 0)
//				equipments.add(new Fireball(x + img.getWidth(null), y - img.getHeight(null) / 2));
			
//			if (equipments.size() == 0)
				equipments.add(new Fireball(x + width, y - height / 2));
		}
		
	}
	@Override
	public ArrayList<Item> getEquipments() {
		return equipments;
	}
	

	public static String getName(int type) {
		if (type == 0)
			return "Mario";
		else if (type == 1)
			return "Luigi";
		
		return null;
	}

}
