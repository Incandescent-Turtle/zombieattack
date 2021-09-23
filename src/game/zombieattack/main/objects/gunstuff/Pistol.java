package game.zombieattack.main.objects.gunstuff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Mouse;

public class Pistol extends AbstractGun 
{
	public Pistol(Game game, Player player) 
	{
		super(game, player, 15, 10, 20, 50);
	}

	@Override
	public void render(Graphics2D g) 
	{
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.DARK_GRAY);
		Rectangle barrel = new Rectangle(0-5, 0, 10, 50);
		double angle = Math.atan2(player.getX()- Mouse.mouseX, player.getY() - Mouse.mouseY)+135;
		g2d.translate(player.getX(), player.getY());
		g2d.rotate(-angle);
		g2d.draw(barrel);
		g2d.fill(barrel);
		g2d.dispose();
	}
	
	
	@Override
	public String getName() 
	{
		return "Pistol";
	}
	@Override
	protected Bullet newBullet() 
	{
		return new Bullet(game, this, 10, Color.BLACK);
	}

	@Override
	public int getBarrelHeight() 
	{
		return 53;
	}
}
