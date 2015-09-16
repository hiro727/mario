package Mario;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Objects {
	//Class for floating blocks
	
	private Frames f;
	private Item i;
	private Ground g;
	private Pictures p;
	private int type, num;
	private BufferedImage block, after;
	private int		x, y, item;
	private boolean broke = false, hit = false;
	private double clipx=0, speed=.3, tempy=0, dt=.25, gravity=10;//, hittimer=0;
	private int  tempx;
	
	private Shape b;
	private double []xcoor;
	private	double []ycoor;
	
	public Objects(Frames f, Ground g, Item i, Pictures p, int x, int y) {
		// TODO Auto-generated constructor stub
		type   = x;
		num	   = y;
		this.f = f;
		this.p = p;
		this.g = g;
		this.i = i;
		this.x = BasicEnemyItemBlockInfo.xB[x][y];
		this.y = BasicEnemyItemBlockInfo.yB[x][y];
		if(x!=0){
			after = Pictures.block[2];
			item  = BasicEnemyItemBlockInfo.itemB[x][y];
		}
		if(x==1)	block = Pictures.block[1];
		else		block = Pictures.block[0];
		
		double []xcoor={this.x,this.x+20,this.x+20,this.x};
		double []ycoor={this.y,this.y,this.y+20,this.y+20};
		this.xcoor = xcoor;
		this.ycoor = ycoor;
		b = createShape();
		
	}
	public Shape createShape(){
		GeneralPath gp = new GeneralPath();
		gp.moveTo(xcoor[0], ycoor[0]);
		for(int i=1;i<xcoor.length;i++){
			gp.lineTo(xcoor[i], ycoor[i]);
		}gp.closePath();
		return gp;
	}
	public void checkLowerCollision(Mario m){
		if(b.intersects(new Rectangle2D.Double(m.getX(), m.getY()-m.getsY(), m.getsX(), m.getsY()))){
			//System.out.println("intersects downwall");
			m.setY(y-1);m.setOnblock(true);m.setFlying(false);
			m.setdY(0);
		}
	}
	public void checkUpperCollision(Mario m){//System.out.println("checking upper collision "+m.getX()+"  "+m.getY()+"  "+m.getsX()+"  "+m.getsY()+" against "+xcoor[3]+"  "+ycoor[3]+"  "+xcoor[2]+"  "+ycoor[2]);
		if(b.intersects(new Rectangle2D.Double(m.getX(), m.getY()-m.getsY(), m.getsX(), m.getsY()))){
		//if((new Rectangle2D.Double(m.getX(), m.getY()-m.getsY(), m.getsX(), m.getsY()).intersectsLine(new Line2D.Double(xcoor[3], ycoor[3], xcoor[2], ycoor[2]))||new Rectangle2D.Double(m.getX(), m.getY()-m.getsY(), m.getsX(), m.getsY()).intersectsLine(new Line2D.Double(xcoor[3], ycoor[3]-20, xcoor[2], ycoor[2]-20)))&&m.getdY()<0){
			//System.out.println("intersects upperwall");
			if(!m.getCurrent().equals("small")){
				//System.out.println(m.getCurrent()+" hit");
				if(type==0){
					broke = true;
					for(int i=x;i<x+20;i++){
						Ground.thereisblock[i] = false;
					}
				}
			}else if(type==0&&!hit){
				hit = true;tempy = -20;
			}if(type!=0&&!hit){
				hit = true;tempy = -20;
			}
			m.setY(y+20+m.getsY());
			m.setdY(0);
		}
	}
	public void checkLeftSideCollision(Mario m){
		if(new Rectangle2D.Double(m.getX(), m.getY(), m.getsX(), m.getsY()).intersectsLine(new Line2D.Double(xcoor[0], ycoor[0], xcoor[3], ycoor[3]))){
			//System.out.println("intersects leftwall");
			m.setX(x-m.getsX());
		}
	}
	public void checkRightSideCollision(Mario m){
		if(new Rectangle2D.Double(m.getX(), m.getY(), m.getsX(), m.getsY()).intersectsLine(new Line2D.Double(xcoor[1], ycoor[1], xcoor[2], ycoor[2]))){
			//System.out.println("intersects rightwall");
			m.setX(x+20);
		}
	}
	
	
	public void update(Mario m){
		if(!broke){
			if(type==1&&!hit){
				int tester = (int)(clipx + speed);//System.out.println(tester);
				if(tester < 3){
					clipx += speed;
				}else{
					tester=0;clipx = 0;
				}
			}
			if(hit){
				if(tempy!=0){
					tempy += dt*gravity;
				}else{
					if(type==0)	hit = false;
					else{
						broke = true;
						//System.out.println(type+" "+num);
						if(type!=0)		i = new Item(g, this, p, f, type, num, m.getCurrent());
					}
				}
			}
		}else{
			if(type!=0)	i.update(m);
		}
		
	}
	
	public void paint(Graphics g, Frames f, Mario m){
		if(m.getX()>250&&m.getX()<m.getWidth()-250){
			tempx = m.getX()-250;
		}else if(m.getX()>m.getWidth()-300){
			tempx = m.getX()-250;
		}else{
			tempx = 0;
		}
		if(broke){
			
			if(type!=0){//System.out.println(type+" "+num);
				if(i!=null)		i.paint(g, m);
				g.drawImage(after, x-tempx, y+(int)(tempy*dt +.5*gravity*dt*dt), x-tempx+20, y+(int)(tempy*dt +.5*gravity*dt*dt)+20, 0, 0, 20, 20, f);			
			}
		}else if(hit){
			if(type!=0)	g.drawImage(after, x-tempx, y+(int)(tempy*dt +.5*gravity*dt*dt), x-tempx+20, y+(int)(tempy*dt +.5*gravity*dt*dt)+20, 0, 0, 20, 20, f);
			else		g.drawImage(block, x-tempx, y+(int)(tempy*dt +.5*gravity*dt*dt), x-tempx+20, y+(int)(tempy*dt +.5*gravity*dt*dt)+20, 0, 0, 20, 20, f);
		}else if(type!=3)	g.drawImage(block, x-tempx, y+(int)tempy, x-tempx+20, y+(int)tempy+20, (int)clipx*20, 0, (int)clipx*20+20, 20, f);
		
	}
	public boolean isBroke() 	{return broke;}
	public boolean isHit() 		{return hit;}
	public int getX() 			{return x;}
	public void setX(int tx) 	{this.x = tx;}
	public int getY() 			{return y;}
	public void setY(int ty) 	{this.y = ty;}
	public int getItem() 		{return item;}
}
