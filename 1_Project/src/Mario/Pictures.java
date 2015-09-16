package Mario;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Pictures {
	URL url;
	private static String[] states  = {	"small 0","small 1","small 2","small 3","small 4","small 5","small 6","small 7","small 8","small 9","small 10",
										"large 0","large 1","large 2","large 3","large 4","large 5","large 6","large 7","large 8","large 9","large 10","large 11",
										 "fire 0", "fire 1", "fire 2", "fire 3", "fire 4", "fire 5", "fire 6", "fire 7", "fire 8", "fire 9", "fire 10"};
	private static String[] enemies = {"goomba","gkoopa troopa","piranha plant","buzzy beetle","cheep cheep","spiny"};
	private static String[] items   = {"coin","super mushroom","1UP mushroom","fire flower","starman"};
	private static String[] blocks  = {"breakable","openable","opened"};
	static int  maxM = states.length, maxE = enemies.length, maxI=items.length, maxB=blocks.length;
	static BufferedImage[] mario = new BufferedImage[maxM], enemy = new BufferedImage[maxE],
			item = new BufferedImage[maxI], block = new BufferedImage[4];
	private boolean[][][] whitemario = new boolean[maxM][300][300], whiteenemy = new boolean[maxE][300][300], whiteitem = new boolean[maxI][300][300];
	Frames f;
	public Pictures(Frames f) {
		this.f = f;
		try{
		url = f.getDocumentBase();
		for(int i=0;i<maxM;i++){//System.out.println(i+" "+states[i]);
			//mario[i] = ImageIO.read(new File(Frames.fullPath+"images/Mario/"+states[i]+".bmp"));
			mario[i] = ImageIO.read(new File("images/Mario/"+states[i]+".bmp"));
			transparentmario(mario[i], i, f);
		}
		for(int i=0;i<maxE;i++){
			//enemy[i] = ImageIO.read(new File(Frames.fullPath+"images/Enemies/"+enemies[i]+".bmp"));
			enemy[i] = ImageIO.read(new File("images/Enemies/"+enemies[i]+".bmp"));
			transparentenemy(enemy[i], i, f, new Color(255,174,201));
		}
		for(int i=0;i<maxI;i++){
			//item[i]  = ImageIO.read(new File(Frames.fullPath+"images/Items/"+items[i]+".bmp"));
			item[i]  = ImageIO.read(new File("images/Items/"+items[i]+".bmp"));
			transparentitem(item[i], i, f);
		}
		for(int i=0;i<maxB;i++){
			//block[i]  = ImageIO.read(new File(Frames.fullPath+"images/Blocks/"+blocks[i]+".bmp"));
			block[i]  = ImageIO.read(new File("images/Blocks/"+blocks[i]+".bmp"));
		}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void transparentmario(BufferedImage img, int counter, Frames f){
        int w = img.getWidth();
        int h = img.getHeight();
        int t = Color.white.getRGB();
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				if (img.getRGB(j,i)==t){
					whitemario[counter][i][j]=true;
				}
			}
		}
	}
	private void transparentenemy(BufferedImage img, int counter, Frames f, Color c){
        int w = img.getWidth();
        int h = img.getHeight();
        int t = c.getRGB();
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				if (img.getRGB(j,i)==t){
					whiteenemy[counter][i][j]=true;
				}
			}
		}
	}
	private void transparentitem(BufferedImage img, int counter, Frames f){
        int w = img.getWidth();
        int h = img.getHeight();
        int t = new Color(255,174,201).getRGB();
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				if (img.getRGB(j,i)==t){
					whiteitem[counter][i][j]=true;
				}
			}
		}
	}
	public boolean[][][] getWhitemario() {
		return whitemario;
	}public boolean[][] getWhiteenemy(int n) {
		return whiteenemy[n];
	}public boolean[][] getWhiteitem(int n) {
		return whiteitem[n];
	}public BufferedImage[] getMario() {
		return mario;
	}public int getMaxE() {
		return maxE;
	}public BufferedImage[] getEnemy() {
		return enemy;
	}public BufferedImage getaEnemy() {
		return enemy[0];
	}
}
