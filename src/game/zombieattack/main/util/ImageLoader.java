package game.zombieattack.main.util;

import java.awt.image.BufferedImage; 

import javax.imageio.ImageIO;

class ImageLoader
{
	protected BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(getClass().getResource("/" + path + ".png"));
		} catch (Exception e) {
			System.out.println("Error! Couldn't find texture at location " + "res/" + path + ".png" + " Ensure that the png file is named correctly and is placed within the correct folder.");
		}
		return null;
	} 
}