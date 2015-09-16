package test;

public class Pipes {
	private static Pipes start;
	private Pipes ptr;
	
	private int type;
	private int x, y;
	
	private int destinationw, destinationa, destinationn;
	

	public Pipes(int x, int y) {
		this.x   	 = x;
		this.y    	 = y;
		add(this);
	}
	public Pipes(int type, int x, int y, int dn) {
		this.type	 = type;
		this.x   	 = x;
		this.y    	 = y;
		add(this);
	}
	public Pipes(int type, int x, int y, int dw, int da, int dn) {
		this.type	 = type;
		this.x   	 = x;
		this.y    	 = y;
		destinationw = dw;
		destinationa = da;
		add(this);
	}
	public static void setDestinations(int n, int type, int dw, int da){
		Pipes p = start;
		for(int i=0;i<n;i++){
			p = p.ptr;
		}
		p.destinationw = dw;
		p.destinationa = da;
		p.type = type;
		
	}
	public static void setDestinations(int n, int type, int dn){
		Pipes p = start;
		for(int i=0;i<n;i++){
			p = p.ptr;
		}
		p.type = type;
		p.destinationn = dn;
	}
	public static void add(Pipes p){
		if(start == null){
			start = p;
			return;
		}else if(p.x < start.x || (p.x == start.x && p.y < start.x)){
			p.ptr = start;
			start = p;
			return;
		}
		Pipes temp1 = start;
		Pipes temp2 = start.ptr;
		while(temp2 != null){
			if(p.x < temp2.x || (p.x == temp2.x && p.y < temp2.x)){
				p.ptr = temp2;
				temp1.ptr = p;
				return;
			}else{
				temp1 = temp1.ptr;
				temp2 = temp2.ptr;
			}
		}
	}
	public static void loopAll(){
		Pipes temp = start;
		while(temp != null){System.out.println(temp+" "+temp.x+" "+temp.y);
			temp = temp.ptr;
		}
	}
	public static Pipes ifExist(int x, int y, int w, int h){
		Pipes temp = start;
		while(temp != null){System.out.println(temp+" "+temp.x+" "+temp.y);
			if(temp.type == 0 || temp.type == 2){
				if(x > temp.x && x + w < temp.x + 60){
					if(y + h >= temp.y && y < temp.y){
						System.out.println(true+" at: x: "+temp.x+" & y: "+temp.y);
						return temp;
					}
				}
			}
			temp = temp.ptr;
		}
		return null;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int[] getDestinationXY() {
		Pipes temp = start;
		for(int i=0;i<destinationn;i++){
			temp = temp.ptr;
		}
		return new int[]{temp.x, temp.y};
	}
}
