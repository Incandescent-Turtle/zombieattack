package game.zombieattack.main.handlers;

import java.awt.Graphics2D; 
import java.util.concurrent.CopyOnWriteArrayList;

import game.zombieattack.main.GameObject;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.objects.zombies.AbstractZombie;

//used to tick and render all GameObjects
public class GameObjectHandler {

	//arraylist to iterate over and add/remove objects
	public CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList <>();
	//a class to handle all collision
	private CollisionHandler collisionHandler;
	
	public GameObjectHandler()
	{
		collisionHandler = new CollisionHandler(this);
	}
	
	//called in Game class every game tick
	public void tick()
	{
		//iterates through all objects and ticks them
		for(GameObject object : gameObjects)
		{
			object.tick();
		}			
		//checks for collisions
		collisionHandler.checkCollisions();
	}
	
	public void killZombies()
	{
		for(GameObject obj : gameObjects)
		{
			if(obj instanceof AbstractZombie) removeObject(obj);
		}
	}
	
	//called in Game class every game render
	public void render(Graphics2D g)
	{
		//iterates through all objects and renders them
		for(GameObject object : gameObjects)
		{
			object.render(g);
		}
	}
	
	//used to add objects to the gameObjects list
	public void addObject(GameObject object)
	{
		gameObjects.add(object);
	}
	
	//used to remove objects from the gameObjects list
	public void removeObject(GameObject object)
	{
		gameObjects.remove(object);
	}
	//returns the first instance of player in the handler
	public Player getPlayer()
	{
		Player player = null;
		for(GameObject object : gameObjects)
		{
			if(object instanceof Player)
			{
				player = (Player)object;
			}
		}
		return player;
	}
}
