package Mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Coin {

	BasicEnemyItemBlockInfo b;
	private BufferedImage coin;
	private boolean[][] white;
	private int x, y, h, tempx;
	private double clipx=0,speed;
	private int eachsize;
	private boolean apppear = false, collected = false;
	
	public Coin(Frames f, Pictures p, BasicEnemyItemBlockInfo b){
		white  	 = p.getWhiteitem(0);
		coin   	 = Pictures.item[0];
		this.b 	 = b;
		//this.x 	 = BasicEnemyItemBlockInfo.xC[x];
		//this.y   = BasicEnemyItemBlockInfo.yC[x];
		this.h   = coin.getHeight();
		eachsize = BasicEnemyItemBlockInfo.eachsizeI[0];
		speed	 = BasicEnemyItemBlockInfo.speedI[0];
	}
	public BufferedImage updateimage(BufferedImage img, Frames f, int x, int y, int startingx, int finishingx){
        int h = img.getHeight();//System.out.println("updating shape"+startingy+" to "+finishingy);
		for(int i=0;i<h;i++){
			for(int j=startingx;j<finishingx;j++){
				if (white[i][j]){
					img.setRGB(j,i,f.getColor(x+j, y+i)); 
				}
			}
		}
		return img;
	}
	public void update(Frames f, Mario m){
		int tester = (int)(clipx + speed);//System.out.println(tester);
		if(tester < 2){
			clipx += speed;
		}else{
			tester=0;clipx = 0;
		}
		checkCollision(f, m);
		if(m.getX()>250&&m.getX()<m.getWidth()-250){
			tempx = m.getX()-250;
		}else if(m.getX()>m.getWidth()-300){
			tempx = m.getX()-250;
		}else{
			tempx = 0;
		}
	}
	private void checkCollision(Frames f, Mario m){//System.out.println("identifying...item");
		if((m.getX()+m.getsX()>x+1&&m.getX()+1<x+eachsize)){
			if(m.getY()-7>y-h&&m.getY()-m.getsY()-7<=y){
				collectedOperation();
			}
		}
	}
	private void collectedOperation() {
		// TODO Auto-generated method stub
		
		collected = true;
	}
	public boolean isApppear() {
		return apppear;
	}
	public boolean isCollected() {
		return collected;
	}
	public void paint(Graphics g, Frames f){
		updateimage(coin, f, x-(int)clipx*eachsize, y, (int)clipx*eachsize, (int)clipx*eachsize+eachsize);
		g.drawImage(coin, x-tempx, y, x+eachsize-tempx, y+h, (int)clipx*eachsize, 0, (int)clipx*eachsize+eachsize, h, f);//System.out.println("Painting item");
		//g.drawImage(item, x, y, f);
	}
}
