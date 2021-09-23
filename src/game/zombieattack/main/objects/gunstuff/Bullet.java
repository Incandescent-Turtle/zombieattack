package game.zombieattack.main.objects.gunstuff;

import static game.zombieattack.main.util.Mouse.mouseX;
import static game.zombieattack.main.util.Mouse.mouseY;
import static java.lang.Math.atan2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.util.Util;

public class Bullet extends GameObject 
{

	//angle of travel
	private final double angle;
	private AbstractGun gun;
	private Color color;
			
	public Bullet(Game game, AbstractGun gun, int size, Color color) 
	{
		//spawns bullet in the center of the player
		super(game.getPlayer().getX(), game.getPlayer().getY(), size, game);
		//sets the angle to the value needed to get to the mouse pointer from the player
		this.gun = gun;
		this.color = color;
		//moves to top of barrel
		Random rand = new Random();
		//calculates the "spray effect"
		double addedAngle = (rand.nextInt(2) == 0 ? -1 : 1) * (Math.atan(rand.nextDouble()*(gun.getShotWidth()/2f)/gun.getBarrelHeight()));
		//sets the angle to bullet has to travel on starting from the players center
		angle = atan2(mouseX - xPos, mouseY - yPos) + addedAngle;
		//moves the bullet to the end on the barrell
		moveToBarrel();
	}
		
	//tick method called from handler
	@Override
	public void tick() 
	{
		double speed = gun.getBulletSpeed();
		//changing x and y position in relation to the angle
		xPos += Math.sin(angle)*speed;
		yPos += Math.cos(angle)*speed;
		//when the bullet hits the side of the screen, it gets deleted from the handler (no more ticking or rendering)
		if(xPos < 0 || xPos > Game.WIDTH || yPos < 0 || yPos > Game.HEIGHT)
		{
			handler.removeObject(this);
		}
	}
	
	@Override
	public void render(Graphics2D g) 
	{
		g.setColor(color);
		g.fill(Util.newCircle(xPos-width/2, yPos-height/2, 10, 10));		
	}
	
	//moves to the end of the barrel
	private void moveToBarrel()
	{
		//gets the height of the barrell
		int barrelHeight = gun.getBarrelHeight();
		//moves the bullet to the end of the barrel
		xPos += Math.sin(angle)*barrelHeight;
		yPos += Math.cos(angle)*barrelHeight;
	}
	
	public AbstractGun getGun()
	{
		return gun;
	}
}