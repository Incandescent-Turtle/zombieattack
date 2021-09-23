package game.zombieattack.main.util;

import java.awt.image.BufferedImage;

public class Textures 
{
	
	private static final ImageLoader imageLoader;

	private Textures() {}

	static
	{
		imageLoader = new ImageLoader();
				
		//GroundItems (64x64)
		HEART = loadImage("heart");
		GRENADE = loadImage("grenade");
		MONEY = loadImage("money");
		AMMO_ITEM = loadImage("ammo item");
				
						
		//Shop Icons (128x128)s
		MOVEMENT_SPEED_BUTTON = loadImage("movement speed button");
		REGEN_HEALTH_BUTTON = loadImage("regen health button");
		MAX_HEALTH_BUTTON = loadImage("max health button");
		GRENADE_BUTTON = loadImage("grenade button");
		BULLET_SPEED_BUTTON = loadImage("bullet speed button");
		BULLET_DAMAGE_BUTTON = loadImage("bullet damage button");
		AMMO_BUTTON = loadImage("ammo button");
		RELOAD_SPEED_BUTTON = loadImage("reload speed button");
		
		//Misc
		BULLET_ICON = loadImage("bullet icon");
		PLAYER = loadImage("player");
		
		//Zombies
		GREEN_ZOMBIE = loadImage("green zombie");
		BRUTE_ZOMBIE = loadImage("brute zombie");
		RUNNER_ZOMBIE = loadImage("runner zombie");
		BOSS_ZOMBIE = loadImage("boss zombie");
	}
	
	//GroundItems (64x64)
	public static final BufferedImage HEART, GRENADE, MONEY, AMMO_ITEM;
	
	//Shop Icons (128x128)
	public static final BufferedImage MOVEMENT_SPEED_BUTTON, REGEN_HEALTH_BUTTON, MAX_HEALTH_BUTTON, GRENADE_BUTTON, BULLET_SPEED_BUTTON, BULLET_DAMAGE_BUTTON, AMMO_BUTTON, RELOAD_SPEED_BUTTON;

	//Misc 
	/* 64x64 */ public static final BufferedImage BULLET_ICON;
	/* 50x50 */ public static final BufferedImage PLAYER;
	
	//Zombies
	//36x36
	public static final BufferedImage GREEN_ZOMBIE, RUNNER_ZOMBIE, BRUTE_ZOMBIE;
	//100x100
	public static final BufferedImage BOSS_ZOMBIE;

	private static BufferedImage loadImage(String path)
	{
		return imageLoader.loadImage(path);
	}
	
	/*private static BufferedImage[] loadImages(String...paths)
	{
		BufferedImage[] array = new BufferedImage[paths.length];
		for(int i = 0; i < paths.length; i++)
		{
			array[i] = loadImage(paths[i-1]);
		}
		return array;
	}*/
}
