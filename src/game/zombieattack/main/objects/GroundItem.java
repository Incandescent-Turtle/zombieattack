package game.zombieattack.main.objects;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import game.zombieattack.main.Combustable;
import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;

//to render and implement the logic for items you can pick up on the ground
//pickups are detected from the CollisionHandler
public abstract class GroundItem extends GameObject implements Combustable
{
	//game
	protected Game game;
	//player
	protected Player player;
	//the image (texture) the item is displayed as. 64x64
	protected BufferedImage texture;
		
	public GroundItem(Game game, Player player, GameObjectHandler handler, Point2D.Double pos, int width, int height, BufferedImage texture)
	{
		super(pos.getX(), pos.getY(), width, height, handler);
		this.texture = texture;
		this.game = game;
	}
	
	//to specify what happens when the item is picked up
	protected abstract void pickUpAction();

	//unused. Only rendered. Collisions handled in collsionhandler
	@Override
	public void tick(){}

	//renders the specified image
	@Override
	public void render(Graphics2D g) 
	{
		g.drawImage(texture, (int) (xPos-width/2f), (int) (yPos-height/2f), width, height, null);
	}
		
	//called from handlers and such to run the code for the pickup. Removes object when picked up
	public void pickUp() 
	{
		pickUpAction();
		handler.removeObject(this);
	}

	//inherited from the Combustable interface, called from the CollisionHandler to destroy the item when blown up
	@Override
	public void blowUp(Grenade grenade)
	{
		handler.removeObject(this);
	}
}
