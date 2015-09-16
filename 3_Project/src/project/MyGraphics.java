package project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class MyGraphics {
		
	private static BufferedImage bg;
	
	private static int[][][][] rgbs;
	
	private static int[][] img;
	private static int size_w, size_h;
	
	
	
	private static Image[][][] imgs;
	private static double[][][] speed;
	private static int[][][] max, min;
	
	//imgs[0]: characters
	//imgs[1]: enemies
	//imgs[2]: items
	
//	private static Image[][] characters;
	
//	private static Image[][] enemies;
	
//	private static Image[][] items;
	
	
	
	public MyGraphics() {
/*		
		//(int) ' ' = 32		(int) '4' = 52		(int) 'H' = 72		(int) '\' = 92		(int) 'p' = 112
		//(int) '!' = 33		(int) '5' = 53		(int) 'I' = 73		(int) ']' = 93		(int) 'q' = 113
		//(int) '"' = 34		(int) '6' = 54		(int) 'J' = 74		(int) '^' = 94		(int) 'r' = 114
		//(int) '#' = 35		(int) '7' = 55		(int) 'K' = 75		(int) '_' = 95		(int) 's' = 115
		//(int) '$' = 36		(int) '8' = 56		(int) 'L' = 76		(int) '`' = 96		(int) 't' = 116
		//(int) '%' = 37		(int) '9' = 57		(int) 'M' = 77		(int) 'a' = 97		(int) 'u' = 117
		//(int) '&' = 38		(int) ':' = 58		(int) 'N' = 78		(int) 'b' = 98		(int) 'v' = 118
		//(int) ''' = 39		(int) ';' = 59		(int) 'O' = 79		(int) 'c' = 99		(int) 'w' = 119
		//(int) '(' = 40		(int) '=' = 60		(int) 'P' = 80		(int) 'd' = 100		(int) 'x' = 120
		//(int) ')' = 41		(int) '<' = 61		(int) 'Q' = 81		(int) 'e' = 101		(int) 'y' = 121
		//(int) '*' = 42		(int) '>' = 62		(int) 'R' = 82		(int) 'f' = 102		(int) 'z' = 122
		//(int) '+' = 43		(int) '?' = 63		(int) 'S' = 83		(int) 'g' = 103		(int) '{' = 123
		//(int) ',' = 44		(int) '@' = 64		(int) 'T' = 84		(int) 'h' = 104		(int) '|' = 124
		//(int) '-' = 45		(int) 'A' = 65		(int) 'U' = 85		(int) 'i' = 105		(int) '}' = 125
		//(int) '.' = 46		(int) 'B' = 66		(int) 'V' = 86		(int) 'j' = 106		(int) '~' = 126
		//(int) '/' = 47		(int) 'C' = 67		(int) 'W' = 87		(int) 'k' = 107
		//(int) '0' = 48		(int) 'D' = 68		(int) 'X' = 88		(int) 'l' = 108
		//(int) '1' = 49		(int) 'E' = 69		(int) 'Y' = 89		(int) 'm' = 109
		//(int) '2' = 50		(int) 'F' = 70		(int) 'Z' = 90		(int) 'n' = 110
		//(int) '3' = 51		(int) 'G' = 71		(int) '[' = 91		(int) 'o' = 111
*/
//		System.out.println((char)124);
//		System.out.println((int)';');
//		System.out.println((int)':');
		
		/*
		 *  	0		sky
		 *  !	1		block    - flushing  - 1 coin
		 *  "	2		block    - flushing  - 11 coins
		 *  #	3		block    - flushing  - power up item
		 *  $	4		block    - flushing  - star
		 *  %	5		block    - flushing  - 1 up mushroom
		 *  &	6		block    - flushing  - vine
		 *  '	7		block    - breakable - 1 coin
		 *  (	8		block    - breakable - power up item
		 *  )	9		block    - breakable - star
		 *  *	10		block    - breakable - 1 up mushroom
		 *  +	11		block    - regular   - ground
		 *  ,	12		block    - breakable - 11 coins
		 *  -	13		block    - breakable - no item
		 *  .	14		block    - breakable - vine
		 *  /	15		block    - regular   - steps
		 *  0	16		starting point
		 *  1	17		pipe     - left	top		* type
		 *  2	18		pipe     - left	down/middle	* # + WARP #
		 *  3	19		cloud    - left top 		* NUM
		 *  4	20		cloud    - left down		* NUM
		 *  5	21		mountain - left top		* NUM
		 *  6	22		mountain - left down		* NUM
		 *  7	23		grass
		 *  8	24		flag
		 *  9	25		head
		 *  :	26		pole
		 *  ;	27		block	 - invisible - 11 coins
		 *  =	28		block	 - invisible - power up item
		 *  <	29		block	 - invisible - star
		 *  >	30		block	 - invisible - 1 up mushroom
		 *  ?	31		block	 - invisible - vine
		 *  @	32		castle	 - walls & sky
		 *  A	33		castle	 - walls & walls
		 *  B	34		castle	 - gate bottom
		 *  C	35		castle	 - gate top
		 *  D	36		castle	 - walls & gate right
		 *  E	37		castle	 - walls & gate left
		 * 
		 */
		
		
		try {
			read_from_file();
			
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void read_from_file() throws NullPointerException, IOException {
		BufferedReader b = new BufferedReader(new FileReader(new File("res/config.txt")));
		size_w  = (int)b.read() - 50;System.out.println("size_w: "+size_w);
		size_h  = (int)b.read() - 50;System.out.println("size_h: "+size_h);
		int num = (int)b.read() - 50;System.out.println("num: "+num);
		rgbs = new int[num][][][];
		img  = new int[num][];
		int counter = 1;
		String string = null;
		for (int i = 0; i < num; i++) {
			int n = (int)b.read() - 100;System.out.println(n);
			rgbs[i] = new int[n][][];
			rgbs[i][0] = new int[1][1];
			rgbs[i][0][0][0] = Integer.parseInt(b.readLine());
//			System.out.println(rgbs[i][0][0][0]);
			for (int j = 0; j < n - 1; j++) {
				string = b.readLine();
				System.out.println(string);
				BufferedImage img = ImageIO.read(new File("res/"+string));
				int w = img.getWidth(Main.main);
				int h = img.getHeight(Main.main);
				rgbs[i][counter] = new int[w][h];
				for (int y = 0; y < h; y++) {
					for (int x = 0; x < w; x++) {
						rgbs[i][counter][x][y] = img.getRGB(x, y);
					}
				}
				counter++;
			}
			n = (int)b.read() - 50;
			img[i]  = new int[n];
			
			for (int j = 0; j < n; j++) {
				img[i][j] = (int)b.read() - 32;
				System.out.println("img["+i+"]["+j+"]: = "+img[i][j]);
			}
		}
		b.close();
	}
	public static void createBGImg () {
		System.out.println("\tcreating background image");
		Vector<String> map = Levels.getMap();
		int type = Levels.getType();
		String temp = map.get(0);
		int width = temp.length() * size_w;
		int height = map.size() * size_h;
		bg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] data = new int[height * width];
		for (int i = 0; i < map.size(); i++) {
			temp = map.get(i);
//			System.out.println("line "+i+"\t: '"+temp+"': "+temp.length());
			for (int j = 0; j < temp.length(); j++) {
				int code = (int)temp.charAt(j) - 32;
//				System.out.println("\tchar "+j+": "+code);
				int fx = j * size_w;
				int	fy = i * size_h;
				
				switch (img[type][code]) {
				//sky
				case 0:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][0][0][0];
						}
					}
					break;
				//block - flushing
				case 1:
					
					break;
				//block - breakable
				case 2:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][2][x][y];
						}
					}
					break;
				//block - ground
				case 3:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][3][x][y];
						}
					}
					break;
				//block - step
				case 4:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][4][x][y];
						}
					}
					break;
				//pipe - upright top || left/right top
				case 5:
					//check the direction
					if((int)temp.charAt(++j) - 32 == 0) {
						//upright
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < rgbs[type][5].length; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][5][x][y];
							}
						}
					}else if((int)temp.charAt(j) - 32 == 1) {
						//left
						int num = 1;
//						while ((int)temp.charAt(++j) - 32 == 0){
//							num++;
//						}
//						j--;
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < size_w; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][7][x][y];
							}
							for (int n = 0; n < num; n++) {
								for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
									data[(fy + y) * width + fx + x] = rgbs[type][7][x - n * size_w][y];
								}
							}
						}
					}else{
						//right
						int num = 1;
//						while ((int)temp.charAt(++j) - 32 == 0){
//							num++;
//						}
//						j--;
						int size = (num + 1) * size_w;
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < size_w; x++) {
								data[(fy + y) * width + fx + size - x] = rgbs[type][7][x][y];
							}
							for (int n = 0; n < num; n++) {
								for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
									data[(fy + y) * width + fx + size - x] = rgbs[type][7][x - n * size_w][y];
								}
							}
						}
					}
					break;
				//pipe - upright bottom || left/right bottom
				case 6:
					int next = (int)map.get(i - 1).charAt(j + 1) - 32;
					if (next == 0) {
						//upright
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < rgbs[type][6].length; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][6][x][y];
							}
						}
						j++;
					//check for warp
					//(int)temp.charAt(++j)
					} else if (next == 1) {
						//left
						//check for warp
						//(int)temp.charAt(++j)
						int num = 1;
//						while ((int)temp.charAt(++j) - 32 == 0){
//							num++;
//						}
//						j--;
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < size_w; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][8][x][y];
							}
							for (int n = 0; n < num; n++) {
								for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
									data[(fy + y) * width + fx + x] = rgbs[type][8][x - n * size_w][y];
								}
							}
						}
					} else {
						//right
						//check for warp
						//(int)temp.charAt(++j)
						int num = 1;
//						while ((int)temp.charAt(++j) - 32 == 0){
//							num++;
//						}
//						j--;
						int size = (num + 1) * size_w;
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < size_w; x++) {
								data[(fy + y) * width + fx + size - x] = rgbs[type][8][x][y];
							}
							for (int n = 0; n < num; n++) {
								for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
									data[(fy + y) * width + fx + size - x] = rgbs[type][8][x - n * size_w][y];
								}
							}
						}
					}
					
					break;
				//cloud top
				case 9:
					int size = (int)temp.charAt(++j) - 48;
					
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][9][x][y];
						}
					}
					for (int n = 0; n < size; n++) {
						for (int y = 0; y < size_h; y++) {
							for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][9][x - n * size_w][y];
							}
						}
					}
					for (int y = 0; y < size_h; y++) {
						for (int x = (size + 1) * size_w; x < (size + 2) * size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][9][x - (size - 1) * size_w][y];
						}
					}
					j += size;
					
					break;
				//cloud bottom
				case 10:
					size = (int)temp.charAt(++j) - 48;
					
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][10][x][y];
						}
					}
					for (int n = 0; n < size; n++) {
						for (int y = 0; y < size_h; y++) {
							for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][10][x - n * size_w][y];
							}
						}
					}
					for (int y = 0; y < size_h; y++) {
						for (int x = (size + 1) * size_w; x < (size + 2) * size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][10][x - (size - 1) * size_w][y];
						}
					}
					j += size;
					break;
				//mountain top
				case 11:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][11][x][y];
						}
					}
					break;
				//mountain - bottom
				case 12:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < rgbs[type][12].length; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][12][x][y];
						}
					}
					j += 2;
					break;
				//grass
				case 13:
						size = (int)temp.charAt(++j) - 48;
						
						for (int y = 0; y < size_h; y++) {
							for (int x = 0; x < size_w; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][13][x][y];
							}
						}
						for (int n = 0; n < size; n++) {
							for (int y = 0; y < size_h; y++) {
								for (int x = (n + 1) * size_w; x < (n + 2) * size_w; x++) {
									data[(fy + y) * width + fx + x] = rgbs[type][13][x - n * size_w][y];
								}
							}
						}
						for (int y = 0; y < size_h; y++) {
							for (int x = (size + 1) * size_w; x < (size + 2) * size_w; x++) {
								data[(fy + y) * width + fx + x] = rgbs[type][13][x - (size - 1) * size_w][y];
							}
						}
						j += size;
						break;
				//flag
				case 14:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < rgbs[type][14].length; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][14][x][y];
						}
					}
					j++;
					break;
				//head
				case 15:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][15][x][y];
						}
					}
					break;
				//pole
				case 16:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][16][x][y];
						}
					}
					break;
				//castle - wall & sky
				case 17:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][17][x][y];
						}
					}
					break;
				//castle - wall & wall
				case 18:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][18][x][y];
						}
					}
					break;
				//castle - gate bottom
				case 19:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][19][x][y];
						}
					}
					break;
				//castle - gate top
				case 20:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][20][x][y];
						}
					}
					break;
				//castle - wall & gate right
				case 21:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][21][x][y];
						}
					}
					break;
				//castle - wall & gate left
				case 22:
					for (int y = 0; y < size_h; y++) {
						for (int x = 0; x < size_w; x++) {
							data[(fy + y) * width + fx + x] = rgbs[type][22][x][y];
						}
					}
					break;
				default:
					break;
				}
			}
		}
		bg.setRGB(0, 0, width, height, data, 0, width);
	}
	
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, 600, 400, 0, 0, 600, 400, Main.main);
	}
	
	public static BufferedImage getBg() {
		return bg;
	}
//	public static Image[][] getCharacters() {
//		return characters;
//	}
//	public static Image[][] getEnemies() {
//		return enemies;
//	}
//	public static Image[][] getItems() {
//		return items;
//	}
	public static Image getImgs(int category, int type, int counter) {
		return imgs[category][type][counter];
	}
	public static double getSpeed(int category, int type, int counter) {
		return speed[category][type][counter];
	}
	public static int getMax(int category, int type, int counter) {
		return max[category][type][counter];
	}
	public static int getMin(int category, int type, int counter) {
		return min[category][type][counter];
	}
	public static int getSize_h() {
		return size_h;
	}
	public static int getSize_w() {
		return size_w;
	}
}