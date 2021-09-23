package game.zombieattack.main.spawners;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.objects.Grenade;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.objects.gunstuff.Bullet;
import game.zombieattack.main.objects.zombies.AbstractZombie;
import game.zombieattack.main.util.Textures;
import game.zombieattack.main.util.Util;

//used in static form, in Game class
//used to create zombies easily
public class ZombieSpawner {
	
	private Player player;
	private Game game;
	private GameObjectHandler handler;
	private Random rand = new Random();
	//keeps track of ticks since the last time a zombie was spawned in game
	private int ticksSinceLastSpawn;
	
	public ZombieSpawner(Game game, GameObjectHandler handler, Player player)
	{
		this.player = player;
		this.game = game;
		this.handler = handler;
	}
	
	//creates a zombie that spawns on the four sides (left right top bottom)
	private void spawnZombie()
	{
		//determines the type of zombie
		int num = rand.nextInt(3)+1;
		//spawns a zombie type randomly
		handler.addObject(num == 1 ? newNormalZombie() : num == 2 ? newRunnerZombie() : num == 3 ? newBruteZombie() : null);
	}
	
	//used to randomize spawning location
	private Point newSpawnLocation() 
	{
		//location zombie will spawn in
		Point location;
		//determines the location of the zombie
		switch(rand.nextInt(4)+1)
		{
			case 1:
				//top of the screen
				location = new Point(rand.nextInt(Game.WIDTH), 0);
				break;
			
			case 2:
				//right of the screen
				location = new Point(Game.WIDTH, rand.nextInt(Game.HEIGHT));
				break;
				
			case 3:
				//bottom of the screen
				location = new Point(rand.nextInt(Game.WIDTH), Game.HEIGHT);
				break;
				
			case 4: 
				//left of the screen
				location = new Point(0, rand.nextInt(Game.HEIGHT));
				break;
				
			default:
				location = new Point(0, 0);
		}
		return location;
	}
	//used in Game class, uses ticksSinceLastSpawn field to determine when to spawn another zombie, uses createZombie method to create a zombie that spawns on the 4 sides
	
	//called in tick method in Game class. Spawns zombie every 40 ticks
	public void trySpawnZombie()
	{
		if(ticksSinceLastSpawn < 40)
		{
				ticksSinceLastSpawn++;
		} else {
				spawnZombie();
				ticksSinceLastSpawn = 0;
			}
	}
	
	//reset calledon gameOver
	public void reset()
	{
		ticksSinceLastSpawn = 0;
	}
	
	//a class that the casual zombies come from
	//base zombie that the normal ones are based off
	private class BaseZombie extends AbstractZombie
	{
		public BaseZombie(Player player, Game game, GameObjectHandler handler, BufferedImage texture, int speed, int damage, int maxHealth) 
		{
			super(newSpawnLocation(), player, game, handler, texture, ZombieType.REGULAR, 10, 5, speed, damage, maxHealth);
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
			game.changeMoney(source instanceof Bullet ? 5 : 2);
			handler.removeObject(this);
		}

		@Override
		protected void blowUpZombie(Grenade grenade) 
		{
			if(health == 10) dieBy(grenade);
			else health-=10;
		}
	}


	//blue - fast but low damage and health
	public BaseZombie newRunnerZombie()
	{
		return newBaseZombie(Textures.RUNNER_ZOMBIE, 4, 1, 10);
	}
	
	//green - mid speed, attack and damage
	public BaseZombie newNormalZombie()
	{
		return newBaseZombie(Textures.GREEN_ZOMBIE, 3, 2, 20);
	}
	
	//black - the brute - high damage and health, low speed
	public BaseZombie newBruteZombie()
	{
		return newBaseZombie(Textures.BRUTE_ZOMBIE, 2, 4, 40);
	}
	
	//helper
	private BaseZombie newBaseZombie(BufferedImage texture, int speed, int damage, int maxHealth)
	{
		return new BaseZombie(player, game, handler, texture, speed, damage, maxHealth);
	}
}