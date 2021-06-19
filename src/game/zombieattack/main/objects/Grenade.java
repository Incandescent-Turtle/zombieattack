package game.zombieattack.main.objects;

import static game.zombieattack.main.util.Mouse.mouseX;
import static game.zombieattack.main.util.Mouse.mouseY;
import static java.lang.Math.atan2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.util.Util;

public class Grenade extends GameObject {

	private double angle;
	private byte ticksTravelling = 0;
	private byte ticksExploding = 0;
	private boolean blowingUp = false;
	
	public Grenade(Player player, GameObjectHandler handler) {
		super(player.getX(), player.getY(), 20, 20, handler);
		this.angle = atan2(mouseX - xPos, mouseY - yPos); 
	}

	@Override
	public void tick() 
	{
		if(blowingUp)
		{
			width+=10;
			height+=10;
			ticksExploding++;
			if(ticksExploding >= 20)
			{
				handler.removeObject(this);
			}
		} else {
			ticksTravelling++;
			if(ticksTravelling <= 20)
			{
				xPos += Math.sin(angle)*(10);
				yPos += Math.cos(angle)*(10);
			} else {
				blowingUp = true;
			}
		}
		
	}

	@Override
	public void render(Graphics2D g) 
	{
		if(blowingUp)
		{
			int colourNum = new Random().nextInt(2);
			g.setColor(colourNum == 0 ? new Color(220, 20, 60) : colourNum == 1 ? new Color(255, 140, 0) : new Color(255, 255, 0));
		} else {
			g.setColor(new Color(50, 100, 50));
		}
		g.fill(Util.newCircle(xPos-width/2, yPos-height/2, width, height));

	}
	
	public boolean isBlowingUp()
	{
		return blowingUp;
	}

}
