package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Borders implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static int ABOVE = 0, BELOW = 1, LEFT = 2, RIGHT = 3;
	
	private static Borders start_left  = null;
	private static Borders start_right = null;
	private static Borders start_above = null;
	private static Borders start_below = null;
	
	private Borders ptr;
	
	private int start_x, start_y, end_x, end_y;
	private Line2D line;
	
	private static boolean hit_left;
	private static boolean hit_right;
	private static boolean hit_above;
	private static boolean hit_below;
	
	public Borders(int start_x, int start_y, int num, int type) {
		this.start_x = start_x * MyGraphics.getSize_w();
		this.start_y = start_y * MyGraphics.getSize_h();
		this.end_x   = (type == ABOVE || type == BELOW ? start_x + num: start_x) * MyGraphics.getSize_w();
		this.end_y   = (type == LEFT  || type == RIGHT ? start_y + num: start_y) * MyGraphics.getSize_h();
		this.line	 = new Line2D.Float(this.start_x, this.start_y, end_x, end_y);
		
		if (type == ABOVE) {
			add_above(this);
		} else if (type == BELOW) {
			this.start_y +=  MyGraphics.getSize_h();
			this.end_y +=  MyGraphics.getSize_h();
			add_below(this);
		} else if (type == LEFT) {
			add_left(this);
		}else if (type == RIGHT) {
			this.start_x +=  MyGraphics.getSize_w();
			this.end_x +=  MyGraphics.getSize_w();
			add_right(this);
		}
	}
	private static void add_left(Borders temp) {
		if (start_left == null) {
			start_left = temp;			
		} else {
			Borders temp1 = start_left;
			Borders temp2 = start_left.ptr;
			while (temp2 != null){
				temp1 = temp1.ptr;
				temp2 = temp2.ptr;
			}
			temp1.ptr = temp;
		}
	}
	private static void add_right(Borders temp) {
		if (start_right == null) {
			start_right = temp;			
		} else {
			Borders temp1 = start_right;
			Borders temp2 = start_right.ptr;
			while (temp2 != null){
				temp1 = temp1.ptr;
				temp2 = temp2.ptr;
			}
			temp1.ptr = temp;
		}
	}
	private static void add_above(Borders temp) {
		if (start_above == null) {
			start_above = temp;
			
		} else if (temp.start_x < start_above.start_x || temp.start_x == start_above.start_x && 
				temp.start_y < start_above.start_y) {
			temp.ptr = start_above;
			start_above = temp;
		} else {
			Borders temp1 = start_above;
			Borders temp2 = start_above.ptr;
			
			while (temp2 != null) {
				int size = temp2.start_x - temp.start_x;
				if (size > 0){
					temp1 = temp1.ptr;
					temp2 = temp2.ptr;
				} else if (size < 0) {
					temp.ptr = temp2;
					temp1.ptr = temp;
					return;
				} else {
					int size_y = temp2.start_y - temp.start_y;
					if (size_y > 0){
						temp1 = temp1.ptr;
						temp2 = temp2.ptr;
					} else if (size_y < 0) {
						temp.ptr = temp2;
						temp1.ptr = temp;
						return;
					} else {
						JOptionPane.showMessageDialog(null, "Unexpected Error");
						throw null;
					}
				}
			}
			temp1.ptr = temp;
		}
	}
	private static void add_below(Borders temp) {
		if (start_below == null) {
			start_below = temp;
			
		} else if (temp.start_x < start_below.start_x || temp.start_x == start_below.start_x && 
				temp.start_y < start_below.start_y) {
			temp.ptr = start_below;
			start_below = temp;
		} else {
			Borders temp1 = start_below;
			Borders temp2 = start_below.ptr;
			
			while (temp2 != null) {
				int size = temp2.start_x - temp.start_x;
				if (size > 0){
					temp1 = temp1.ptr;
					temp2 = temp2.ptr;
				} else if (size < 0) {
					temp.ptr = temp2;
					temp1.ptr = temp;
					return;
				} else {
					int size_y = temp2.start_y - temp.start_y;
					if (size_y > 0){
						temp1 = temp1.ptr;
						temp2 = temp2.ptr;
					} else if (size_y < 0) {
						temp.ptr = temp2;
						temp1.ptr = temp;
						return;
					} else {
						JOptionPane.showMessageDialog(null, "Unexpected Error");
						throw null;
					}
				}
			}
			temp1.ptr = temp;
		}
	}
	
	public static void createBorder(){
		System.out.println("\tcreating borders");
		Vector<String> map = Levels.getMap();
		String[] areas = new String[map.size()];
		for (int i = 0; i < areas.length; i++) {
			areas[i] = map.get(i);
		}
		map.clear();
		map = null;
		
		create_lines_above(areas);
		create_lines_below(areas);
		createVerticalLines(areas);
	}
	private static void createVerticalLines(String[] map) {
		String[] New = new String[map[0].length()];
		for (int i = New.length - 1; i >= 0 ; i--) {
			New[i] = "";
			for (int j = 0; j < map.length; j++) {
				New[i] += map[j].charAt(i);
			}
			//System.out.println("New["+i+"]: \t"+New[i]);
		}
		map = New;
		New = null;
		
		create_lines_left(map);
		create_lines_right(map);
		
	}
	private static void create_lines_left(String[] map) {
		for (int i = 0; i < map.length; i++) {
			//System.out.println(i);
			int j = 0;
			int num = 0;
			while (j < map[i].length() - 1){
				//System.out.println("\t\tstart j: "+j);
				while (j < map[i].length() - 1 && !is_solid(map[i].charAt(j)))
					j++;
				//System.out.println("\t\ttruncated j: "+j+" with "+map[i].charAt(j));
				
				boolean done = false;
				
				int code;
				int start = j;
				num = 0;
				
				while (!done) {
					if ((code = (int) map[i].charAt(j)) == 49) {
						//pipe
						if ((int) map[i + 1].charAt(j) == 32) {
							//upright
							while (++j < map[i].length() - 1 && 
									(int) map[i].charAt(j) == 50 && 
									(i == 0 || !is_solid(map[i - 1].charAt(j)))) {
								num++;
							}
							if (j == map[i].length())
								break;
						} else {
							//left || right
							if (i != 0 || !is_solid(map[i - 1].charAt(j))) {
								num += 2;
								j++;
							} else {
								new Borders(i, start, num, LEFT);
							}
						}
					} else if (!is_solid(code)) {//System.out.println("others");
						new Borders(i, start, num, LEFT);
						break;
					} else if (j == map[i].length() - 1) {
						if (i == 0 || !other_row_solid(map[i-1].charAt(j))) {
							num++;
							new Borders(i, start, num, LEFT);
						} else if (num != 0) {
							new Borders(i, start, num, LEFT);
						}						
						break;
					}else {
						if (i == 0) {
							num++;
							j++;
						} else if ((code = (int) map[i - 1].charAt(j)) == 49) {
							if (num != 0) {
								new Borders(i, start, num, LEFT);
								break;
							}
							if (i == 0) {
								num++;
								j++;
							} else {
								while (map[i - 1].charAt(++j) == 50 && other_row_solid(map[i - 2].charAt(j))){
									j++;
								}
							}
						} else if (!other_row_solid(code)){
							num++;
							j++;
						} else {
							if (num != 0){
								new Borders(i, start, num, LEFT);
							}
							j++;
							break;
						}
					}
				}
			}
		}
	}
	private static void create_lines_right(String[] map) {
		for (int i = 0; i < map.length; i++) {
			//System.out.println(i);
			int j = 0;
			int num = 0;
			while (j < map[i].length() - 1){
				//System.out.println("\t\tstart j: "+j);
				while (j < map[i].length() - 1 && !is_solid(map[i].charAt(j)))
					j++;
				//System.out.println("\t\ttruncated j: "+j+" with "+map[i].charAt(j));
				
				boolean done = false;
				
				int code;
				int start = j;
				num = 0;
				
				while (!done) {
					if ((code = (int) map[i].charAt(j)) == 49) {
						//pipe
						if ((int) map[i + 1].charAt(j) == 32) {
							//upright
							while (++j < map[i].length() - 1 && 
									(int) map[i].charAt(j) == 50 && 
									(i == map.length - 1 || !is_solid(map[i + 1].charAt(j)))) {
								num++;
							}
							if (j == map[i].length())
								break;
						} else {
							//left || right
							if (i == map.length - 1 || !is_solid(map[i + 1].charAt(j))) {
								num ++;
								j++;
							} else {
								new Borders(i, start, num, RIGHT);
								j++;
								break;
							}
						}
					} else if (!is_solid(code)) {
						new Borders(i, start, num, RIGHT);
						j++;
						break;
					} else if (j == map[i].length() - 1) {
						if (i == map.length - 1 || !other_row_solid(map[i + 1].charAt(j))) {
							num++;
							new Borders(i, start, num, RIGHT);
						} else if (num != 0) {
							new Borders(i, start, num, RIGHT);
						}						
						break;
					}else {
						if (i == map.length - 1 || !other_row_solid(map[i + 1].charAt(j))) {
							num++;
							j++;
						} else if ((code = (int) map[i + 1].charAt(j)) == 49) {
							num++;
							j++;
							if (i == 0) {
								num++;
								j++;
							} else {
								while (map[i - 1].charAt(++j) == 50 && other_row_solid(map[i - 2].charAt(j))){
									j++;
								}
							}
						} else {
							if (num != 0){
								new Borders(i, start, num, RIGHT);
							}
							j++;
							break;
						}
					}
				}
			}
		}
	}
	private static void create_lines_above(String[] map) {
		for (int i = 0; i < map.length; i++) {
			int j = 0;
			int num = 0;
			while (j < map[i].length() - 1){
				while (j < map[i].length() - 1 && !is_solid(map[i].charAt(j)))
					j++;
				
				boolean done = false;
				
				int code;
				int start = j;
				num = 0;
				
				while (!done) {
					if ((code = (int) map[i].charAt(j)) == 49) {
						num++;
						//pipe
						if ((int) map[i].charAt(++j) == 32) {
							//upright
							num++;
							j++;
						} else {
							//left || right
							num++;
							while ((int) map[i].charAt(++j) == 49) {
								num++;
							}
						}
					} else if (code == 50) {
						//pipe
						if ((int) map[i - 1].charAt(++j) == 32) {
							//upright
							num++;
							j++;
						} else {
							//left || right
							num++;
							while ((int) map[i].charAt(++j) == 49) {
								num++;
							}
						}
						break;
					} else if (!is_solid(code)) {
						new Borders(start, i, num, ABOVE);
						break;
					} else if (j == map[i].length() - 1) {////System.out.println("\t\t\tlast");
						if (i == 0 || !other_row_solid(map[i-1].charAt(j))) {
							num++;
							new Borders(start, i, num, ABOVE);
						} else if (num != 0) {
							new Borders(start, i, num, ABOVE);
						}						
						break;
					}else {
						if (i == 0 || !other_row_solid(map[i-1].charAt(j))) {
							num++;
							j++;
						} else {
							if (num != 0){
								new Borders(start, i, num, ABOVE);
							}
							j++;
							break;
						}
					}
				}
			}
		}
	}
	private static void create_lines_below(String[] map) {
		for (int i = 0; i < map.length; i++) {
			//System.out.println(i);
			int j = 0;
			int num = 0;
			while (j < map[i].length() - 1){
				while (j < map[i].length() - 1 && !is_solid(map[i].charAt(j)))
					j++;
				
				boolean done = false;
				
				int code;
				int start = j;
				num = 0;
				
				while (!done) {
					if ((code = (int) map[i].charAt(j)) == 49) {
						num++;
						//pipe
						if ((int) map[i].charAt(++j) == 32) {
							//upright
							num++;
							j++;
						} else {
							//left || right
							num++;
							while ((int) map[i].charAt(++j) == 49) {
								num++;
							}
						}
						break;
						
					} else if (code == 50) {
						//pipe
						if ((int) map[i - 1].charAt(++j) == 32) {
							//upright
							num++;
							j++;
						} else {
							//left || right
							num++;
							while ((int) map[i].charAt(++j) == 49) {
								num++;
							}
						}
						break;
						
					} else if (!is_solid(code)) {
						new Borders(start, i, num, BELOW);
						break;
						
					} else if (j == map[i].length() - 1) {//System.out.println("\t\t\tlast");
						if (i == map.length - 1 || !other_row_solid(map[i + 1].charAt(j))) {
							num++;
							new Borders(start, i, num, BELOW);
						} else if (num != 0) {
							new Borders(start, i, num, BELOW);
						}
						break;
						
					}else {
						if (i == map.length - 1 || !other_row_solid(map[i + 1].charAt(j))) {
							num++;
							j++;
						} else {
							if (num != 0){
								new Borders(start, i, num, BELOW);
							}
							j++;
							break;
						}
					}
				}
			}
		}
	}
	private static boolean is_solid(char c) {
		int code = (int) c;
		return is_solid(code);
	}

	private static boolean is_solid(int code) {
		code -= 32;
		if (code == 0)
			return false;
		else if (code < 16)
			return true;
		else if (code == 16)
			return false;
		else if (code < 19)
			return true;
		else if (code < 25)
			return false;
		else if (code < 32)
			return true;
		else
			return false;
	}
	

	private static boolean other_row_solid(char c) {
		int code = (int) c;
		return other_row_solid(code);
	}

	private static boolean other_row_solid(int code) {
		code -= 32;
		if (code == 0)
			return false;
		else if (code < 16)
			return true;
		else if (code < 25)
			return false;
		else if (code < 32)
			return true;
		else
			return false;
	}
	
	
	
	public Line2D getLine() {
		return line;
	}
	
	
	
	public static boolean isHit_left() {
		return hit_left;
	}
	public static void setHit_left(boolean hit_left) {
		Borders.hit_left = hit_left;
	}
	public static boolean isHit_right() {
		return hit_right;
	}
	public static void setHit_right(boolean hit_right) {
		Borders.hit_right = hit_right;
	}
	public static boolean isHit_above() {
		return hit_above;
	}
	public static void setHit_above(boolean hit_above) {
		Borders.hit_above = hit_above;
	}
	public static boolean isHit_below() {
		return hit_below;
	}
	public static void setHit_below(boolean hit_below) {
		Borders.hit_below = hit_below;
	}
	
	
	
	public static void paint(Graphics g) {
		Borders temp = start_above;
		while (temp != null) {
			g.setColor(Color.red);
			g.fillRect(Main.inset.left + temp.start_x, Main.inset.top + temp.start_y - 2, temp.end_x - temp.start_x, 4);
			temp = temp.ptr;
		}
		temp = start_below;
		while (temp != null) {
			g.setColor(Color.blue);
			g.fillRect(Main.inset.left + temp.start_x, Main.inset.top + temp.start_y - 2, temp.end_x - temp.start_x, 4);
			temp = temp.ptr;
		}
		temp = start_left;
		while (temp != null) {
			g.setColor(Color.green);
			g.fillRect(Main.inset.left + temp.start_x - 2, Main.inset.top + temp.start_y, 4, temp.end_y - temp.start_y);
			temp = temp.ptr;
		}
		temp = start_right;
		while (temp != null) {
			g.setColor(Color.yellow);
			g.fillRect(Main.inset.left + temp.start_x - 2, Main.inset.top + temp.start_y, 4, temp.end_y - temp.start_y);
			temp = temp.ptr;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean is_flying(int x, int y, int w) {
		Borders temp = start_above;
		int middle = x + w/2;
		while (temp != null) {
			if (middle <= temp.start_x) {
				temp = temp.ptr;
			} else if (middle < temp.end_x) {
				if (y == temp.start_y) {
					return false;
				} else {
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
		}
		return true;
	}
	public static boolean is_stuck_with_left_wall(int x, int y, int w) {
		Borders temp = start_left;
		int middle = x + w/2;
		while (temp != null) {
			if (middle <= temp.start_x) {
				temp = temp.ptr;
			} else if (middle < temp.end_x) {
				if (y == temp.start_y) {
					return false;
				} else {
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
		}
		return true;
	}
	public static boolean is_stuck_with_right_wall(int x, int y, int w) {
		Borders temp = start_right;
		int middle = x + w/2;
		while (temp != null) {
			if (middle <= temp.start_x) {
				temp = temp.ptr;
			} else if (middle < temp.end_x) {
				if (y == temp.start_y) {
					return false;
				} else {
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * start_y = m * x + c ... (1)
	 * x = (start_y - c)/m ... (2)
	 * start_x <= x <= end_x ... (3)
	 * 
	 * 
	 * @param m - gradient of the movement of the object
	 * @param c - y intercepts of the linear line of the movement of the object
	 * @return the top border of the area that may intersect the object
	 */
	public static int[] get_possible_line_above(int x, int y, double dx, double dy) {
		Borders temp = start_above;
		int end = x + (int) dx;//System.out.println("above walls:\t"+x+"  &  "+ y +"\t-->\t"+end+"  &  "+(y + dy));
		while (temp != null) {
			
			if (end <= temp.start_x) {
				temp = temp.ptr;
			} else if (end <= temp.end_x) {//System.out.println(temp.start_x+" "+temp.end_x+"\tx value ok");
				if (y + dy >= temp.start_y) {//System.out.println(temp.start_y+"\ty value 1 ok");
					if (y <= temp.start_y) {//System.out.println("\ty value 2 ok\n");
						double delta_y = temp.start_y/(y + dy);
						double delta_x = dx * delta_y;
						hit_above = true;
						return new int[]{(int) delta_x + x, temp.start_y};
					} else {//System.out.println("\ty value 2 out\n");
						temp = temp.ptr;
					}
				} else {//System.out.println();
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
		}
		return new int[]{end, y + (int) dy};
	}
	/**
	 * 
	 * @param m - gradient of the movement of the object
	 * @param c - y intercepts of the linear line of the movement of the object
	 * @return the bottom border of the area that may intersect the object
	 */
	public static int[] get_possible_line_below(int x, int y, double dx, double dy) {
		Borders temp = start_below;
		int end = x + (int) dx;//System.out.println("below walls:\t"+x+"  &  "+ y +"\t-->\t"+end+"  &  "+(y + dy));
		while (temp != null) {
			
			if (end < temp.start_x) {
				temp = temp.ptr;
			} else if (end <= temp.end_x) {//System.out.println(temp.start_x+" "+temp.end_x+"\tx value ok");
				if (y + dy <= temp.start_y) {//System.out.println(temp.start_y+"\ty value 1 ok");
					if (y >= temp.start_y) {//System.out.println("\ty value 2 ok\n");
						double delta_y = temp.start_y/(y + dy);
						double delta_x = dx * delta_y;
						hit_below = true;
						return new int[]{(int) delta_x + x, temp.start_y};
					} else {//System.out.println("\ty value 2 out\n");
						temp = temp.ptr;
					}
				} else {//System.out.println();
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
			
		}
			return new int[]{end, y + (int) dy};
	}
	/**
	 * 
	 * @param m - gradient of the movement of the object
	 * @param c - y intercepts of the linear line of the movement of the object
	 * @return the left border of the area that may intersect the object
	 */
	public static int[] get_possible_line_left(int x, int y, double dx, double dy) {
		Borders temp = start_left;
		int end = x + (int) dx;//System.out.println("left walls:\t"+x+"  &  "+ y +"\t-->\t"+end+"  &  "+(y + dy));
		while (temp != null) {
			//System.out.println("\t"+temp.start_x+" : y "+temp.start_y+" "+temp.end_y);
			if (end < temp.start_x) {
				temp = temp.ptr;
			} else if (x <= temp.start_x) {//System.out.println(temp.start_x+"\tx value ok");
				if (y + dy >= temp.start_y && y + dy <= temp.end_y) {//System.out.println(temp.start_y+"\ty value 1 ok");
						double delta_x = temp.start_x/(x + dx);
						double delta_y = dy * delta_x;
						hit_left = true;
						return new int[]{temp.start_x, (int) delta_y + y};
				} else {//System.out.println();
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
		}
		return new int[]{end, y + (int) dy};
	}
	/**
	 * @param m - gradient of the movement of the object
	 * @param c - y intercepts of the linear line of the movement of the object
	 * @return the right border of the area that may intersect the object
	 */
	public static int[] get_possible_line_right(int x, int y, double dx, double dy) {
		Borders temp = start_right;
		int end = x + (int) dx;//System.out.println("left walls:\t"+x+"  &  "+ y +"\t-->\t"+end+"  &  "+(y + dy));
		while (temp != null) {
			//System.out.println("\t"+temp.start_x+" : y "+temp.start_y+" "+temp.end_y);
			if (end > temp.start_x) {
				temp = temp.ptr;
			} else if (x >= temp.start_x) {//System.out.println(temp.start_x+"\tx value ok");
				if (y + dy >= temp.start_y && y + dy <= temp.end_y) {//System.out.println(temp.start_y+"\ty value 1 ok");
						double delta_x = temp.start_x/(x + dx);
						double delta_y = dy * delta_x;
						hit_right = true;
						return new int[]{temp.start_x, (int) delta_y + y};
					
				} else {//System.out.println();
					temp = temp.ptr;
				}
			} else {
				temp = temp.ptr;
			}
			
		}
		return new int[]{end, y + (int) dy};
	}
}