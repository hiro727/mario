package Mario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ground {
	
	Frames f;
	int[] points,ground;
	int n, width, height, flag;
	static boolean[] thereisblock;
	int normal;
	
	public Ground(Frames f){
		try{
			System.out.println("Initializing Ground Info");
			//BufferedReader r = new BufferedReader(new FileReader(Frames.fullPath+"info/stages/"+Frames.names[Frames.worldcounter]+".txt"));
			BufferedReader r = new BufferedReader(new FileReader("info/stages/"+Frames.names[Frames.worldcounter]+".txt"));
			r.readLine();
			this.width  = f.width;
			this.height = f.height;
			this.f 		= f;
			ground =new int [width];
			n = Integer.parseInt(r.readLine());		//Number of unnatural x position
			points = new int[n];					//Y position of unnatural x points in term
			int counter=0;									//of block number
			normal = Integer.parseInt(r.readLine());	//Y position of natural x points
			for(int j=0;j<n;j++){
				points[j] = Integer.parseInt(r.readLine())-1;
			}
			r.readLine();
			
			for(int i=0;i<ground.length;i+=20){
				//System.out.println(n+" :"+counter);
				//System.out.println(points[counter]);
				if(i==points[counter]*20){
					ground[i] = normal-Integer.parseInt(r.readLine());
					//System.out.println("ground["+i+"]"+ ground[i]);
					if(counter<n-1)
						counter++;
				}else{
					ground[i] = normal;
					//System.out.println("ground["+i+"]"+ ground[i]);
				}
				for(int j=i+1;j<i+20;j++){
					ground[j] = ground[i];
					//System.out.println("ground["+j+"]"+ ground[j]);
				}
			}r.readLine();
			flag = (Integer.parseInt(r.readLine())-1)*20;
			r.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		thereisblock = new boolean[width];
		
		
	}
	
}
