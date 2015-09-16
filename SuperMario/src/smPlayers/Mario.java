package smPlayers;

import superMarioModels.SMPlayer;

public class Mario extends SMPlayer {

	public Mario() {
		super("Mario");
		type = 0;
		x = 100;
		y = 175;
		
	}
	
	private double testX = 5.1;
	@Override
	public void update() {
		addForce(testX, 0);
		super.update();
		
		if (x >= 250 && type == 0) {
//			testX = 0;
//			state = 1;
////			if (getEquipments() == null || getEquipments().size() == 0)
//				equip();
		
			animateFromTo(0, 1);
		}
		if (x >= 400 && type == 1) {
			testX = 0;
			state = 1;
//			if (getEquipments() == null || getEquipments().size() == 0)
				equip();
		}
	}
	
}
