package Mario;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class musicPlayer{
	
	
	private  String[] bgm = {	"main","underworld","starman","castle","castle-complete","level-complete",
								"you-are-dead","hurry-underground"};
//	private  String[] effect ={	"main","dead","bowserfall","breakblock","coin","flagpole","warning","vine",
//								"stomp","pipe","kick","jump","stage_clear","gameover"};
	//private int musicChooser = 0;
	
	private Player player1;
//	private Player[] players = new Player[effect.length];
	private BufferedInputStream stream, restream;
    private FileInputStream subf;
    private int totaltime, stopedtime;
    
    private FileInputStream[] f = new FileInputStream[bgm.length];
    private Player player;
    private int[] ttime = new int[bgm.length], ctime = new int[bgm.length];
    
    public musicPlayer(){
    	try {
    		for(int i=0;i<bgm.length;i++){
				//f[i] = new FileInputStream(Frames.fullPath+"music/soundtrack/"+bgm[i]+".mp3");
				f[i] = new FileInputStream("music/soundtrack/"+bgm[i]+".mp3");
				ttime[i] = f[i].available();
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
    public void playsoundtrack(int n) throws JavaLayerException {
    	System.out.println("playing music");
		stream = new BufferedInputStream(f[n]);
		player = new Player(stream);
        player.play();
        System.out.println("playing at "+System.currentTimeMillis());
    }   
    public void playsound(int n) throws JavaLayerException {
    	System.out.println("playing sound");
		Player player = new Player(stream);
		this.player = player;
        player.play();
        System.out.println("playing at "+System.currentTimeMillis());
    }
    public  void pause(int n) throws IOException {
    	ctime[n] = f[n].available();
    	player.close();
    }
    public  void resume() throws IOException {
    	subf.skip(totaltime-stopedtime);
    	restream = new BufferedInputStream(subf);
    	try {
			player1 = new Player(restream);
			player1.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public  void stopsoundtrack(int n) throws IOException {
    	player.close();
        System.out.println("stoping at "+System.currentTimeMillis());
    }  
    public  void close() throws IOException {
        if (player1 != null) {
            player1.close();
       }
    }    
    
}
