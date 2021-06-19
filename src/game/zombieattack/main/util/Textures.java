package game.zombieattack.main.util;

import java.awt.image.BufferedImage;

public class Textures 
{
	private Textures() {}

	private static ImageLoader imageLoader = new ImageLoader();
	
	//GroundItems (64x64)
	public static final BufferedImage HEART_TEXTURE = loadImage("heart");
	public static final BufferedImage GRENADE_TEXTURE = loadImage("grenade");
	public static final BufferedImage MONEY_TEXTURE = loadImage("money");
	
	//Player
	public static final BufferedImage PLAYER_TEXTURE = loadImage("player");

	private static BufferedImage loadImage(String path)
	{
		return imageLoader.loadImage(path);
	}
}
