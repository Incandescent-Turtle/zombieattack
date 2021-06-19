package game.zombieattack.main.objects;
 
import static game.zombieattack.main.util.Mouse.mouseX; 
import static game.zombieattack.main.util.Mouse.mouseY;
import static java.lang.Math.atan2;

import java.awt.Color;
import java.awt.Graphics2D;

import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.util.Util;

//player fired bullet
public class Bullet extends GameObject {

	//angle of travel
	private double angle;
	private double speed;
	private int damage;

	public Bullet(Player player, GameObjectHandler handler, int size, int damage, double speed) 
	{
		//spawns bullet in the center of the player
		super(player.getX(), player.getY(), size, handler);
		//sets the angle to the value needed to get to the mouse pointer from the player
		angle = atan2(mouseX - xPos, mouseY - yPos); 
		//moves to top of barrel
		xPos += Math.sin(angle)*53;
		yPos += Math.cos(angle)*53;
		this.speed = speed;
		this.damage = damage;
	}
	
	//tick method called from handler
	@Override
	public void tick() 
	{
		//changing x and y position in relation to the angle
		xPos += Math.sin(angle)*speed;
		yPos += Math.cos(angle)*speed;
		//when the bullet hits the side of the screen, it gets deleted from the handler (no more ticking or rendering)
		if(xPos < 0 || xPos > Game.WIDTH || yPos < 0 || yPos > Game.HEIGHT)
		{
			handler.removeObject(this);
		}
	}

	//render method called from handler
	@Override
	public void render(Graphics2D g) 
	{
		g.setColor(Color.DARK_GRAY);
		//draws bullet. more accurate than first attempt, uses doubles as coords system
		g.fill(Util.newCircle(xPos-width/2, yPos-height/2, 10, 10));
		//g.draw(getBounds());
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void changeDamage(int amount)
	{
		damage += amount;
	}
}
