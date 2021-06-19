package game.zombieattack.main.factories;

import java.awt.geom.Point2D; 
import java.awt.image.BufferedImage;
import java.util.Random;

import game.zombieattack.main.Game;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.objects.GroundItem;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Textures;
import game.zombieattack.main.util.Util;
import game.zombieattack.main.util.Util.VariableMethod;

public class GroundItemFactory 
{
	private Game game;
	private Player player;
	private int ticksSinceLastSpawn;
	private Random random;
	private GameObjectHandler handler;
	
	public GroundItemFactory(Game game, GameObjectHandler handler, Player player)
	{
		this.game = game;
		this.player = player;
		this.handler = handler;
		random = new Random();
	}
	
	//called from the tick method in the game class. Spawns zombies every 300 ticks
	public void spawnGroundItems()
	{
		ticksSinceLastSpawn++;
		if(ticksSinceLastSpawn > 300)
		{
			//functional interface allows for a method to be tied to a variable
			VariableMethod pickUpAction;
			//the image for the item
			BufferedImage texture;
			//item width and height
			int width, height;
			//the 2 represents the amount of items that can spawn. Chooses an item to spawn randomey
			switch(random.nextInt(3)+1)
			{
				//heart item
				case 1:
					//sets the texture to heart
					texture = Textures.HEART_TEXTURE;
					//sets width/height to 40
					width = height = 40;
					//sets the pickUpAction to change the player health by 25
					pickUpAction = new Util.VariableMethod() 
					{
						public void method() 
						{
							player.changeHealth(25);
						}
					};
					break;
					
				//grenade item
				case 2: 
					//sets the texture to grenade
					texture = Textures.GRENADE_TEXTURE;
					//width and height equal to 64
					width = height = 64;
					//sets the pickUpAction to increase the grenade count by two
					pickUpAction = new Util.VariableMethod() 
					{
						public void method() 
						{
							player.changeGrenadeCount(2);
						}
					};
					break;
					
				case 3:
					//sets the texture to money
					texture = Textures.MONEY_TEXTURE;
					width = height = 40;
					pickUpAction = new Util.VariableMethod() 
					{
						@Override
						public void method() 
						{
							game.changeMoney(50);
						}
					};
					
					break;
				default:
					texture = Textures.GRENADE_TEXTURE;
					width = height = 1000;
					pickUpAction = new Util.VariableMethod()	
					{
						public void method()
						{
						
						}
					};
			}
			//adds the new object with helper method
			handler.addObject(newGroundItem(width, height, texture, pickUpAction));
			ticksSinceLastSpawn = 0;
		}
	}
	
	//returns a 
	private Point2D.Double createPosition(int width, int height)
	{
		return new Point2D.Double(random.nextDouble()*(Game.WIDTH-width), random.nextDouble()*(Game.HEIGHT-height));
	}
	
	//helper to create a new GroundItem with a random location
	private GroundItem newGroundItem(int width, int height, BufferedImage texture, VariableMethod pickUpAction)
	{
		//returns a new GroundItem with all the new parameters and methods
		return new GroundItem(game, player, handler, createPosition(width, height), width, height, texture) {
			@Override
			protected void pickUpAction() {
				pickUpAction.method();
			}
		};
	}
}
