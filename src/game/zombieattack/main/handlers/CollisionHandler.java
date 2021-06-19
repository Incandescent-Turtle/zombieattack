package game.zombieattack.main.handlers;

import game.zombieattack.main.Combustable;  
import game.zombieattack.main.GameObject;
import game.zombieattack.main.objects.Bullet;
import game.zombieattack.main.objects.Grenade;
import game.zombieattack.main.objects.GroundItem;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.objects.zombies.AbstractZombie;

//handles collisions with GameObjects
public class CollisionHandler {
	
	private GameObjectHandler handler;
	
	public CollisionHandler(GameObjectHandler handler)
	{
		this.handler = handler;	
	}

	//called from the handler every game tick
	public void checkCollisions() {
		//iterates through all objects
		for(GameObject firstObject : handler.gameObjects)
		{
			//for each object it iterates again
			for(GameObject secondObject : handler.gameObjects)
			{
				//when a player and a regular zombie collide
				if(areTouching(firstObject, Player.class, secondObject, AbstractZombie.class))
				{
					if(!((AbstractZombie)secondObject).isOnAttackCoolDown())
					{
						((AbstractZombie)secondObject).hitPlayer((Player)firstObject);
					}
				}
				//when a bullet hits a zombie														
				if(areTouching(firstObject, AbstractZombie.class, secondObject, Bullet.class))
				{
					AbstractZombie zombie = (AbstractZombie) firstObject;
					//removes bullet
					handler.removeObject(secondObject);
					//damages zombie
					zombie.damage(handler.getPlayer().getDamgage(), secondObject);
				}
				//when the player picks up an item
				if(areTouching(firstObject, Player.class, secondObject, GroundItem.class))
				{
					((GroundItem)secondObject).pickUp();
				}
				//when a combustable object hits an exploding grenade
				if(areTouching(firstObject, Combustable.class, secondObject, Grenade.class)&& ((Grenade)secondObject).isBlowingUp())
				{
					((Combustable)firstObject).blowUp(((Grenade)secondObject));
				}
			}
		}
	}
	//to help
	private boolean areTouching(GameObject firstObject, Class<?> class1, GameObject secondObject, Class<?> class2)
	{
		return class1.isInstance(firstObject) && class2.isInstance(secondObject) && firstObject.getBounds().intersects(secondObject.getBounds());
	}
}