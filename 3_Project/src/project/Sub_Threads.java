package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Sub_Threads implements Runnable{
	
	private static Main main = null;
	private Thread thread = null;
	private int interval;
	
	
	public Sub_Threads(Main main) {
		Sub_Threads.main = main;
		
	}
	public Sub_Threads(String path) {
		this.thread = new Thread(this);
		
		try {
			read_from_file(path);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	private void read_from_file(String path) throws NullPointerException, IOException{
		BufferedReader b = new BufferedReader(new FileReader(new File(path)));
		interval = Integer.parseInt(b.readLine());
		
		b.close();
	}
	@Override
	public void run() {
		while(true){
			
			
			repaint();
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {}
			
		}
		
	}
	private void repaint() {
		
		main.repaint();
		
	}
	
	
	
	
	
	
	public Thread getThread() {
		return thread;
	}
}