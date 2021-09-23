package game.zombieattack.main.objects.gunstuff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Mouse;

public class MachineGun extends AbstractGun 
{

	public MachineGun(Game game, Player player) 
	{
		super(game, player, 15, 5, 8, 3000);
	}
	
	@Override
	public String getName() 
	{
		return "Machine Gun";
	}

	@Override
	public int getBarrelHeight() 
	{
		return 45;
	}
	
	@Override
	protected int getShotWidth() 
	{
		return 30;
	}

	@Override
	protected Bullet newBullet() 
	{
		return new Bullet(game, this, 10, Color.DARK_GRAY);
	}

	@Override
	public void render(Graphics2D g) 
	{
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.DARK_GRAY);
		Rectangle barrel = new Rectangle(0-20, 0, 40, 50);
		double angle = Math.atan2(player.getX()- Mouse.mouseX, player.getY() - Mouse.mouseY)+135;
		g2d.translate(player.getX(), player.getY());
		g2d.rotate(-angle);
		g2d.draw(barrel);
		g2d.fill(barrel);
		g2d.dispose();
	}

	@Override
	public double getBulletSpeed() 
	{
		return 15;
	}
}