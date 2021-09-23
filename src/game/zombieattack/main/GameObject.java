package game.zombieattack.main;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.util.Util;
 
//an abstract class that all GameObjects extend (Bullet, Player, Zombie)
public abstract class GameObject 
{

	protected int width, height;
	//doubles for precision
	protected double xPos, yPos;
	protected final GameObjectHandler handler;

	protected final Game game;
	
	public GameObject(double xPos, double yPos, int width, int height, Game game)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.handler = game.getObjectHandler();
		this.width = width;
		this.height = height;
		this.game = game;
	}
	
	//used for objects that have equal length and height
	public GameObject(double xPos, double yPos, int size, Game game)
	{
		this(xPos, yPos, size, size, game);
	}
	
	//called in handler every game tick
	public abstract void tick();
	//called in handler every game render
	public abstract void render(Graphics2D g);

	//used for collisions, used in CollisionHandler
	public Rectangle2D getBounds()
	{
		return (Rectangle2D)Util.newRectangle(xPos-width/2, yPos-height/2, width, height);
	}

	/*
	 * Getters and setters
	 */
	public double getX()
	{
		return xPos;
	}
	
	public double getY()
	{
		return yPos;
	}
	
	public int getWidth() 
	{
		return width;
	}

	public int getHeight() 
	{
		return height;
	}
}