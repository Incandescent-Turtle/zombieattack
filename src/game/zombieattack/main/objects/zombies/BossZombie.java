package game.zombieattack.main.objects.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.objects.Bullet;
import game.zombieattack.main.objects.Grenade;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Util;

public class BossZombie extends AbstractZombie
{

	private Grenade lastGrenade = null;
	
	public BossZombie(Point pos, Player player, Game game, GameObjectHandler handler)
	{
		super(pos, player, game, handler, Color.ORANGE, ZombieType.BOSS, 10, 20, 2, 25, 100, 100);
	}

	@Override
	protected void drawZombie(Graphics2D g) 
	{
		g.fill(Util.newCircle(xPos-width/2, yPos-height/2, width, height));
		g.drawString(health + "", 100, 100);
	}

	@Override
	protected void drawHealthBar(Graphics2D g) 
	{
		//sets colour for the healthbar outline
		g.setColor(Color.BLACK);
		//zombie's healthbar
		g.draw(Util.newRectangle(xPos-width/2+2, yPos+height/2+5, width-4, 10));
		//setting healthbar color
		g.setColor((int)((width-5)*((double)health/maxHealth)) > ((width-5)/4)*3 ? Color.GREEN : (int)((width-5)*((double)health/maxHealth)) > (width-5)/2-4 ? Color.YELLOW : Color.RED);
		//fills the health bar depending on zombie health
		g.fill(new Rectangle((int) (xPos-width/2+3), (int) (yPos+height/2+6), (int) Util.clamp((width-5)*((double)health/maxHealth), 0, width-3), 9));
	}

	@Override
	protected void zombieDieBy(GameObject source) 
	{
		game.changeMoney(source instanceof Bullet ? 1000 : source instanceof Grenade ? 800 : 0);
	}

	@Override
	protected void blowUpZombie(Grenade grenade)
	{
		if(!(lastGrenade != null && lastGrenade.equals(grenade)))
		{
			damage(10, grenade);
			lastGrenade = grenade;
		}
	}
}
