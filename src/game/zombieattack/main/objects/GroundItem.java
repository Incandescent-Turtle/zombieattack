package game.zombieattack.main.objects;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import game.zombieattack.main.Combustable;
import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;

//to render and implement the logic for items you can pick up on the ground
//pickups are detected from the CollisionHandler
public abstract class GroundItem extends GameObject implements Combustable
{
	//game
	protected Game game;
	//player
	protected Player player;
	//the image (texture) the item is displayed as. 64x64
	private BufferedImage[] textures;
	//current texture
	private int currentTextureIndex;
	//ticks since it changes textures
	private int ticksSinceTextureChanged = 0;
		
		
	public GroundItem(Game game, Point2D.Double pos, int width, int height, BufferedImage[] textures)
	{
		super(pos.getX(), pos.getY(), width, height, game);
		this.textures = textures;
		this.game = game;
		this.currentTextureIndex = 0;
	}
	
	//to specify what happens when the item is picked up
	protected abstract void pickUpAction();

	//unused. Only rendered. Collisions handled in collsionhandler
	@Override
	public void tick()
	{
		ticksSinceTextureChanged++;
	}

	//renders the specified image
	@Override
	public void render(Graphics2D g) 
	{
		//boolean d = false;
		if(ticksSinceTextureChanged > 10)
		{
			//d=true;
			//shear+=.0012;
			
			changeTexture(g);
			ticksSinceTextureChanged = 0;
		}
		/*Graphics2D g2 = (Graphics2D) g.create();
		Random rand = new Random();
		if(d) g2.shear(1,1);
		g2.drawImage(textures[0], (int) (xPos-width/2f), (int) (yPos-height/2f), width, height, null);
		g2.dispose();
		*/
		g.drawImage(textures[0], (int) (xPos-width/2f), (int) (yPos-height/2f), width, height, null);

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
	
	private void changeTexture(Graphics2D g)
	{
		currentTextureIndex+=1;
		if(currentTextureIndex > textures.length) currentTextureIndex -= textures.length;
	}
}
