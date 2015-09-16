package Mario;

import java.io.*;

public class BasicEnemyItemBlockInfo {
	
	static int []  itemNum, minI, maxI, eachsizeI;
	static double[] speedI, dxI, dyI;
	static int[][] xI, yI, conditionI;
	static int []  eneNum, minE, maxE, eachsizeE;
	static double[] speedE, dxE;
	static int[][] xE, yE;
	
	static int []  blkNum = new int[4];
	static int[][] xB = new int[4][], yB = new int[4][], itemB = new int[4][];
	
	
	public BasicEnemyItemBlockInfo(Pictures p, Ground g) {
		// BASIC ITEM INFO
		System.out.println("Initializing field items");
		itemNum    = new int[Pictures.maxI];
		minI	   = new int[Pictures.maxI];
		maxI	   = new int[Pictures.maxI];
		eachsizeI  = new int[Pictures.maxI];
		speedI     = new double[Pictures.maxI];
		dxI  	   = new double[Pictures.maxI];
		dyI  	   = new double[Pictures.maxI];
		xI		   = new int[Pictures.maxI][];
		yI		   = new int[Pictures.maxI][];
		conditionI = new int[Pictures.maxI][];
		try {
			//BufferedReader ri = new BufferedReader(new FileReader(Frames.fullPath+"info/items.txt"));
			BufferedReader ri = new BufferedReader(new FileReader("info/items.txt"));
			for(int i=0;i<Pictures.maxI;i++){
				//System.out.println("Initializing field items  "+i);
				ri.readLine();	//... info
				minI[i]		  = Integer.parseInt(ri.readLine());
				maxI[i]		  = Integer.parseInt(ri.readLine());
				eachsizeI[i]  = Integer.parseInt(ri.readLine());
				speedI[i]	  = Double.parseDouble(ri.readLine());
				dxI[i]		  = Double.parseDouble(ri.readLine());
				dyI[i]		  = Double.parseDouble(ri.readLine());
				ri.readLine();
				itemNum[i]   = Integer.parseInt(ri.readLine());
				xI[i]		  = new int[itemNum[i]];
				yI[i]		  = new int[itemNum[i]];
				conditionI[i] = new int[itemNum[i]];
				for(int j=0;j<itemNum[i];j++){//System.out.println(i+" : "+j);
					xI[i][j] 		 = (Integer.parseInt(ri.readLine())-1)*20;
					yI[i][j] 		 =  Integer.parseInt(ri.readLine());
					conditionI[i][j] =  Integer.parseInt(ri.readLine());
				}
			}
			ri.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Initializing field enemies");	
		eneNum     = new int[Pictures.maxE];
		minE	   = new int[Pictures.maxE];
		maxE	   = new int[Pictures.maxE];
		eachsizeE  = new int[Pictures.maxE];
		speedE     = new double[Pictures.maxE];
		dxE   	   = new double[Pictures.maxE];
		xE		   = new int[Pictures.maxE][];
		yE		   = new int[Pictures.maxE][];
		try{
			//BufferedReader re = new BufferedReader(new FileReader(Frames.fullPath+"info/enemies/"+Frames.names[Frames.worldcounter]+".txt"));
			BufferedReader re = new BufferedReader(new FileReader("info/enemies/"+Frames.names[Frames.worldcounter]+".txt"));
			for(int i=0;i<Pictures.maxE;i++){
				//System.out.println("Initializing field enemies  "+i);
				re.readLine();	//... info
				minE[i]		  = Integer.parseInt(re.readLine());//System.out.println(minE[i]);
				maxE[i]		  = Integer.parseInt(re.readLine());//System.out.println(maxE[i]);
				eachsizeE[i]  = Integer.parseInt(re.readLine());//System.out.println(eachsizeE[i]);
				speedE[i]	  = Double.parseDouble(re.readLine());//System.out.println(speedE[i]);
				dxE[i]	 	  = Double.parseDouble(re.readLine());//System.out.println(dxE[i]);
				re.readLine();
				eneNum[i]   = Integer.parseInt(re.readLine());
				xE[i]		  = new int[eneNum[i]];
				yE[i]		  = new int[eneNum[i]];
				for(int j=0;j<eneNum[i];j++){//System.out.println(i+" : "+j);
					xE[i][j] 		 = (Integer.parseInt(re.readLine())-1)*20;
					if(i==2)xE[i][j] += 10;
					yE[i][j] 		 =  Integer.parseInt(re.readLine());
				}
			}
			re.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//BufferedReader rb = new BufferedReader(new FileReader(Frames.fullPath+"info/blocks/"+Frames.names[Frames.worldcounter]+".txt"));
			BufferedReader rb = new BufferedReader(new FileReader("info/blocks/"+Frames.names[Frames.worldcounter]+".txt"));
			rb.readLine();	//get rid of item info
			for(int i=0;i<4;i++){
				rb.readLine();	//get rid of inst
				blkNum[i] = Integer.parseInt(rb.readLine());
				xB[i]	 = new int[blkNum[i]];
				yB[i]	 = new int[blkNum[i]];
				
				if(i!=0){
					itemB[i] = new int[blkNum[i]];
					for(int j=0;j<blkNum[i];j++){//System.out.println(i+"  "+j);
						xB[i][j] 	= (Integer.parseInt(rb.readLine())-1)*20;
						yB[i][j] 	= g.normal-Integer.parseInt(rb.readLine())*20;
						itemB[i][j] = Integer.parseInt(rb.readLine());
						//System.out.println(i+"  "+j+" at "+xB[i][j]+" with "+yB[i][j]);
					}
				}else{
					for(int j=0;j<blkNum[i];j++){
						xB[i][j] 	= (Integer.parseInt(rb.readLine())-1)*20;
						yB[i][j] 	= g.normal-Integer.parseInt(rb.readLine())*20;
						//System.out.println(i+"  "+j+" at "+xB[i][j]+" with "+yB[i][j]);
					}
				}
				
			}
			rb.close();
			//int[] y = new int[g.width];
			int i=0, j=0;
			boolean finish = false;
			while(!finish){
				for(int x=xB[i][j];x<xB[i][j]+20;x++){
					Ground.thereisblock[x] = true;
					//System.out.println(x+" : "+Ground.thereisblock[x]);
				}
				if(j<blkNum[i]-1)	j++;
				else if(i<3){	i++;	j=0;}
				else if(i==3&&j==blkNum[i]-1)finish=true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int getX(int i, int j){
		return xI[i][j];
	}
}
