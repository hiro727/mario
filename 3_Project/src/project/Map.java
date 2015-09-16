package project;

public class Map {
	
//	private static int hit = 0;
//	private static int N = 0, R = 1, L = 2, U = 3, D = 4, R_U = 5, R_D = 6, L_U = 7, L_D = 8;
	private static double gravity = 9.81;
	private static double dt = .3;
	private static double maxDY = 10;
	
	public static int[] check_for_collision_with_walls(int x, int y, int w, int h, double dx, double dy){
		Borders.setHit_above(false);
		Borders.setHit_below(false);
		Borders.setHit_left(false);
		Borders.setHit_right(false);
		int[] data = new int[]{x + (int) dx, y + (int) dy};
		System.out.println(dx+"\t\t\t"+dy);
		
		if (dx == 0) {//System.out.print("dx == 0");
			if (dy == 0) {//System.out.println(" & dy == 0");
				if (Borders.is_flying(x, y + h, w)) {//System.out.print(dy+"\t");
					dy += gravity * dt;
					return new int[]{x, y, (int)(dy * 10000)};
				}
			} else if (dy > 0) {//System.out.println(" & dy > 0");
				y += h;
				if (Borders.is_flying(x, y, w)) {//System.out.print(true);
					dy += gravity * dt;
					double temp = dy * dt + .5 * gravity * dt * dt;
					if (temp > maxDY) {
						temp = maxDY;
						if (dy > maxDY)
							dy = maxDY;
					}
					data = Borders.get_possible_line_above(x + w/2, y, dx, temp);
					return new int[]{data[0] - w/2, data[1] - h, (int)(dy * 10000)};
				} else {
					data = Borders.get_possible_line_above(x + w/2, y, dx, dy);
					data[0] -= w/2;
					data[1] -= h;
				}
			} else {//System.out.println(" & dy < 0");
				dy += gravity * dt;
				double temp = dy * dt + .5 * gravity * dt * dt;
				if (temp > maxDY) {
					temp = maxDY;
					if (dy > maxDY)
						dy = maxDY;
				}
				data = Borders.get_possible_line_below(x + w/2, y, dx, temp);
				data[0] -= w/2;
				System.out.println("\t\t\t\t\t\t"+Math.abs(data[1] - y - temp));
				if (Math.abs(data[1] - y - temp) < 1)
					return new int[]{data[0], data[1], (int)(dy * 10000)};
			}
		} else if (dx > 0) {//System.out.print("dx > 0");
			if (dy == 0) {//System.out.println(" & dy == 0");
				data = Borders.get_possible_line_left(x + w, y + h/2, dx, dy);
				data[0] -= w;
				data[1] -= h/2;
				if (Borders.is_flying(data[0], data[1] + h, w)) {
					dy += gravity * dt;
					return new int[]{data[0], data[1], (int)(dy * 10000)};
				}
			} else if (dy > 0) {//System.out.println(" & dy > 0");
				if (Borders.is_flying(x, y + h, w)) {
					dy += gravity * dt;
					double temp = dy * dt + .5 * gravity * dt * dt;
					if (temp > maxDY) {
						temp = maxDY;
						if (dy > maxDY)
							dy = maxDY;
					}
					int[] left  = Borders.get_possible_line_left (x + w,   y + h/2, dx, temp);
					int[] above = Borders.get_possible_line_above(x + w/2, y + h,   dx, temp);
					left[0]  -= w;
					left[1]  -= h/2;
					above[0] -= w/2;
					above[1] -= h;
					if ((left[0] - x)/dx > (above[1] - y)/temp) {
						data = above;
					} else {//System.out.println("left\t"+(left[0] - x)/dx+"\nright\t"+(above[1] - y)/dy);
						return new int[]{left[0], left[1], (int)(dy * 10000)};
					}
				} else {
					int[] left  = Borders.get_possible_line_left (x + w,   y + h/2, dx, dy);
					int[] above = Borders.get_possible_line_above(x + w/2, y + h,   dx, dy);
					left[0]  -= w;
					left[1]  -= h/2;
					above[0] -= w/2;
					above[1] -= h;
					if ((double)((left[0] - x)/dx) < (double)((above[1] - y)/dy)) {
						return new int[]{left[0], left[1], (int)(dy * 10000)};
					} else {
						data = above;
					}
				}
			} else {//System.out.println(" & dy < 0");
				dy += gravity * dt;
				double temp = dy * dt + .5 * gravity * dt * dt;
				if (temp > maxDY) {
					temp = maxDY;
					if (dy > maxDY)
						dy = maxDY;
				}
				int[] left  = Borders.get_possible_line_left (x + w,   y + h/2, dx, temp);
				int[] below = Borders.get_possible_line_below(x + w/2, y,		dx, temp);
				left[0]  -= w;
				left[1]  -= h/2;
				below[0] -= w/2;
				System.out.println("\t\t\t\t\tratio: "+((double)(left[0] - x)/dx)+", "+((double)(below[1] - y)/temp));
					
				if (((double)(left[0] - x)/dx) < ((double)(below[1] - y)/temp)) {
					System.out.println("hit wall\t\t\tfrom: ("+x+", "+y+")\tto: ("+left[0]+", "+left[1]+") by move of ("+dx+", "+temp+")");
					return new int[]{left[0], left[1], (int)(dy * 10000)};
				} else {
					System.out.println("right jump\t\t\t\t\t"+Math.abs(below[1] - y - temp));
					if (Math.abs(below[1] - y - temp) < 1)
						return new int[]{below[0], below[1], (int)(dy * 10000)};
					data = below;
				}
			}
		} else {//System.out.print("dx < 0");
			if (dy == 0) {//System.out.println(" & dy == 0");
				data = Borders.get_possible_line_right(x, y + h/2, dx, dy);
				data[1] -= h/2;
				if (data[0] < Main.screen_x)
					data[0] = Main.screen_x;
				if (Borders.is_flying(data[0], data[1] + h, w)) {
					dy += gravity * dt;
					return new int[]{data[0], data[1], (int)(dy * 10000)};
				}
			} else if (dy > 0) {//System.out.println(" & dy > 0");
				if (Borders.is_flying(x, y + h, w)) {
					dy += gravity * dt;
					double temp = dy * dt + .5 * gravity * dt * dt;
					if (temp > maxDY) {
						temp = maxDY;
						if (dy > maxDY)
							dy = maxDY;
					}
					int[] right = Borders.get_possible_line_right(x,	   y + h/2, dx, temp);
					int[] above = Borders.get_possible_line_above(x + w/2, y + h,   dx, temp);
					right[1] -= h/2;
					above[0] -= w/2;
					above[1] -= h;
					if ((double)((right[0] - x)/dx) < (double)((above[1] - y)/temp)) {
						if (right[0] < Main.screen_x)
							right[0] = Main.screen_x;
						return new int[]{right[0], right[1], (int)(dy * 10000)};
					} else {
						right = Borders.get_possible_line_right(above[0], above[1] + h/2, dx, 0);
						data[0] = right[0];
						data[1] = right[1] - h/2;
						if (data[0] < Main.screen_x)
							data[0] = Main.screen_x;
					}
				} else {
					int[] right = Borders.get_possible_line_right(x,	   y + h/2, dx, dy);
					int[] above = Borders.get_possible_line_above(x + w/2, y + h,   dx, dy);
					right[1] -= h/2;
					above[0] -= w/2;
					above[1] -= h;
					if ((right[0] - x)/dx < (above[1] - y)/dy) {
						if (right[0] < Main.screen_x) {
							right[0] = Main.screen_x;
						}
						return new int[]{right[0], right[1], (int)(dy * 10000)};
					} else {
						data = above;
						if (data[0] < Main.screen_x)
							data[0] = Main.screen_x;
					}
				}
			} else {//System.out.println(" & dy < 0");
				dy += gravity * dt;
				double temp = dy * dt + .5 * gravity * dt * dt;
				if (temp > maxDY) {
					temp = maxDY;
					if (dy > maxDY)
						dy = maxDY;
				}
				int[] right = Borders.get_possible_line_right(x,	   y + h/2, dx, temp);
				int[] below = Borders.get_possible_line_below(x + w/2, y,		dx, temp);
				right[1] -= h/2;
				below[0] -= w/2;
				if ((right[0] - x)/dx < (below[1] - y)/temp) {
					if (right[0] < Main.screen_x)
						right[0] = Main.screen_x;
					return new int[]{right[0], right[1], (int)(dy * 10000)};
				} else {
					System.out.println("left jump\t\t\t\t\t"+Math.abs(below[1] - y - temp));
					if (Math.abs(below[1] - y - temp) < 1)
						return new int[]{below[0], below[1], (int)(dy * 10000)};
					data = below;
					if (data[0] < Main.screen_x)
						data[0] = Main.screen_x;
				}
			}
		}
		return data;
	}
}