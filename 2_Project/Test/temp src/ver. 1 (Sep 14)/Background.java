package test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background{
			
	private BufferedImage image;
	private BufferedImage[] imagebg;
	private BufferedImage[] imageob;
	
	private int types;
	private int pattern;
	private int[] color;
	private int scalew;
	private int scaleh;
	private int width;
	private int height;

	private int[] rgb;

	public Background() {
		readGeneralInfo();
		loadImages();
		createImages();
	}
	
	private void readGeneralInfo() {
		File file = new File("test/general information.txt");

		try {
			BufferedReader b = new BufferedReader(new FileReader(file));
			types = Integer.parseInt(b.readLine());
			color = new int[types];
			for(int i=0;i<types;i++){
				color[i] = (Integer.parseInt(b.readLine()) << 16) | (Integer.parseInt(b.readLine()) << 8) | Integer.parseInt(b.readLine());
			}
			scalew  = Integer.parseInt(b.readLine());
			scaleh  = Integer.parseInt(b.readLine());
			width   = Integer.parseInt(b.readLine());
			height  = Integer.parseInt(b.readLine());
			System.out.println("width:  "+width);
			System.out.println("height: "+height);
			System.out.println("scale width:  "+scalew);
			System.out.println("scalw height: "+scaleh);
			System.out.println("total width:  "+width*scalew);
			System.out.println("total height: "+height*scaleh);
			rgb     = new int[width*scalew*height*scaleh];
			
			image = new BufferedImage(width*scalew, height*scaleh, BufferedImage.TYPE_INT_RGB);
			b.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadImages() {
		imagebg = new BufferedImage[26];
		imageob = new BufferedImage[26];
		File[] f = new File("test/images/objects").listFiles();
		for(int i=0;i<f.length;i++){
			char temp = f[i].getName().charAt(0);
			int number = (int)temp;System.out.println(temp+" "+number);
			//96: for lower case
			//64: for upper case
			
			try {
				if(number<=122 & number>=97)
					imagebg[number-97] = ImageIO.read(f[i]);
				else if(number<=90 & number>=65)//lower case
					imageob[number-65] = ImageIO.read(f[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createImages() {
		File data = new File("test/data.txt");
		try {
			BufferedReader b = new BufferedReader(new FileReader(data));
			pattern = Integer.parseInt(b.readLine());
			b.readLine();System.out.println("total width: "+width*scalew);
			for(int i=0;i<height;i++){
				String str = b.readLine();//System.out.println(i+": "+str);
				char[] ch  = str.toCharArray();
				for(int j=0;j<width;j++){
					int temp = (int)ch[j];//System.out.println(i+": "+ch[j]+" "+temp);
					//96: for lower case
					//64: for upper case
					if(temp==103)	//103: for 0
						Map.setPoint(j*scalew, i*scaleh);
					else if(temp == 65)
						new Blocks(j*scalew, i*scaleh, 0);
					else if(temp == 66)
						new Blocks(j*scalew, i*scaleh, 1, 0);
					
					if(temp==48||temp==65||temp==66){//System.out.println(i+" "+j);
						//g.fillRect(j*scalew, i*scaleh, scalew, scaleh);
						int n = i*scalew*width*scaleh+j*scalew;
						for(int l=0;l<scaleh;l++){
							for(int m=0;m<scalew;m++){
								//System.out.println("l: "+l+", m: "+m+", total: "+n+m);
								rgb[n+m] = color[pattern];
							}n += scalew*width;
							
						}
						
					}else if(temp<=122 & temp>=97){//lower case
						//g.drawImage(imagebg[temp-96], 0, 0, width, height, j*scalew, i*scaleh, j*scalew+width, i*scaleh+height, this);
						int[] each = new int[imagebg[temp-97].getWidth()*imagebg[temp-97].getHeight()];
						imagebg[temp-97].getRGB(0, 0, imagebg[temp-97].getWidth(), imagebg[temp-97].getHeight(), each, 0, imagebg[temp-97].getWidth());
						int n = i*scalew*width*scaleh+j*scalew;
						int counter = 0;//System.out.println("n: "+n+", x: "+j*scalew+" & y: "+i*scaleh);
						for(int l=0;l<scaleh;l++){
							for(int m=0;m<scalew;m++){//System.out.println(temp+" "+(n+m));
								rgb[n+m] = each[counter++];
							}n += scalew*width;
							
						}
					}else if(temp<=90 & temp>=65){//upper case
						//g.drawImage(imageob[temp-64], 0, 0, width, height, j*scalew, i*scaleh, j*scalew+width, i*scaleh+height, this);
						
					}
				}
			}Blocks.checkFirstShown();
			image.setRGB(0, 0, width*scalew, height*scaleh, rgb, 0, width*scalew);
			b.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public int getWidth() {
		return width;
	}
	public int getScalew() {
		return scalew;
	}
	public int getScaleh() {
		return scaleh;
	}
	public BufferedImage getImage() {
		return image;
	}
}
