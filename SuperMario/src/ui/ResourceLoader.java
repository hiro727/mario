package ui;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;

public class ResourceLoader {

	private static ResourceLoader instance;
	
	private String nextInstruction;
	
	
//	private ResourceLoader(String nextInstruction) {
//		System.out.println(nextInstruction);
//		this.nextInstruction = nextInstruction;
//		System.out.println(getClass().getClassLoader().getResource(nextInstruction));
//		System.out.println(getClass().getClassLoader().getResourceAsStream(nextInstruction));
//		System.exit(0);
//	}
	
	public static ResourceLoader createInstance(String inst) {
		
		ResourceLoader rl = new ResourceLoader();
		rl.setNextInstruction(inst);
		
		instance = rl;
		
		return rl;
	}
	
	public static ResourceLoader getInstance() {
		return instance;
	}
	
	public InputStream getInputStream () {
		System.out.println("Streaming from : " + nextInstruction);
		return getClass().getClassLoader().getResourceAsStream(nextInstruction);
	}
	public InputStream getInputStream (String subFile) {
		System.out.println("Streaming from : " + nextInstruction + " : " + subFile);
		return getClass().getClassLoader().getResourceAsStream(subFile);
	}
	public URL getURL (String subFile) {
//		System.out.println("Streaming from : " + nextInstruction + " : " + subFile);
		return getClass().getClassLoader().getResource(subFile);
	}
	public BufferedReader getBufferedReader() {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	public BufferedImage getBufferedImage(String file) {
		try {
			return ImageIO.read(getInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String exist (String subFileWithoutExtension) {
		
		if (getURL(subFileWithoutExtension + ".bmp") != null)
			subFileWithoutExtension += ".bmp";
		else if (getURL(subFileWithoutExtension + ".gif") != null)
			subFileWithoutExtension += ".gif";
		else if (getURL(subFileWithoutExtension + ".png") != null)
			subFileWithoutExtension += ".png";
		
//		System.out.println("\t\t" + subFileWithoutExtension);
		
		return subFileWithoutExtension;
	}
	public void setNextInstruction(String nextInstruction) {
		this.nextInstruction = nextInstruction;
	}
}
