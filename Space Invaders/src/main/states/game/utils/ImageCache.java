package main.states.game.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageCache {
	public HashMap<String, BufferedImage> map = new HashMap<String, BufferedImage>();
	public BufferedImage load(String file)
	{
		if(map.containsKey(file))
		{
			return map.get(file);
		}else{
			try {
				BufferedImage img =  ImageIO.read(getClass().getClassLoader().getResource(file));
				map.put(file,img);
				return img;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
