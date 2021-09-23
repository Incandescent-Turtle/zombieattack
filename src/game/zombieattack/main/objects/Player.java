package game.zombieattack.main.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.zombieattack.main.Controllable;
import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.objects.gunstuff.AbstractGun;
import game.zombieattack.main.objects.gunstuff.MachineGun;
import game.zombieattack.main.objects.gunstuff.Pistol;
import game.zombieattack.main.util.Textures;
import game.zombieattack.main.util.Util;

//the main player / survivor in game
//implements Controllable which sends key presses and releases
public class Player extends GameObject implements Controllable 
{
	//used to determine whether or not keys are pressed
	private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, qPressed; 
	//health
	private int maxHealth = 100, health = maxHealth;

	private double movementSpeed = 5, velX, velY;
	//grenade stuff
	private int grenadeCount = 10, maxGrenadeCount = 10, ticksSinceLastLaunchedGrenade = 40;
	//size
	public static final int SIZE = 50;	
	private final AbstractGun guns[];
	private final Pistol pistol;
	private final MachineGun machineGun;
	
	private AbstractGun currentGun; 
	
	public Player(int x, int y, Game game) {
		super(x, y, SIZE, game);
		guns = new AbstractGun[] 
		{
				pistol = new Pistol(game, this),
				machineGun = new MachineGun(game, this)
		};
		currentGun = guns[1];
	}

	//tick method overriden from GameObject called in handler every tick
	@Override
	public void tick() 
	{
		//uses key booleans to move
		velX = velY = 0;
		if(upPressed) velY = -movementSpeed;
		if(downPressed) velY = movementSpeed;
		if(leftPressed) velX = -movementSpeed;
		if(rightPressed) velX = movementSpeed;		
		
		//keeps the player in bounds
		xPos = Util.clamp(xPos+velX, width/2, Game.WIDTH-width/2);
		yPos = Util.clamp(yPos+velY, height/2, Game.HEIGHT-height/2);
		//tries to shoot a bullet
		if(spacePressed) currentGun.tryShootBullet();
		
		//tries to throw nade
		tryLaunchGrenade();
		
		//keeps the value between the specified values
		health = health > maxHealth ? maxHealth : health;
		
		currentGun.tick();
		
		//ends the game if the player dies
		if(health <= 0) game.endGame();
	}

	//tick method overriden from GameObject called in handler every tick
	@Override
	public void render(Graphics2D g) 
	{
		//draws tank barrel
		currentGun.render(g);
		
		//draws player
		g.setColor(Color.BLACK);
		g.drawImage(Textures.PLAYER, (int) (xPos-width/2), (int) (yPos-height/2), null);
	}

	@Override
	public void keyPressed(int key) 
	{
		updateKeyBindings(key, true);
		if(key == KeyEvent.VK_E) nextGun();
	}

	@Override
	public void keyReleased(int key) 
	{
		updateKeyBindings(key, false);
	}	
	
	private void nextGun()
	{
		if(!(currentGun.equals(guns[guns.length-1])))
		{
			for (int i = 0; i < guns.length-1; i++) 
				if (currentGun.equals(guns[i])) 
					currentGun = guns[i+1];
		} else {
			currentGun = guns[0];
		}
	}
	//called when a grenade is launched, creates a grenade
	private void launchGrenade() 
	{
		handler.addObject(new Grenade(game));
		grenadeCount--;
	}
	
	private void tryLaunchGrenade()
	{
		ticksSinceLastLaunchedGrenade++;
		if(qPressed && ticksSinceLastLaunchedGrenade >= 50 && grenadeCount > 0)
		{
			launchGrenade();
			ticksSinceLastLaunchedGrenade = 0;
		}
	}
	
	private void updateKeyBindings(int key, boolean pressed)
	{
		//checks key presses and sets the booleans
		switch(key)
		{
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				upPressed = pressed;
				break;
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				downPressed = pressed;
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				leftPressed = pressed;
				break;
				
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				rightPressed = pressed;
				break;
				
			case KeyEvent.VK_SPACE:
				spacePressed = pressed;
				break;
				
			case KeyEvent.VK_Q:
				qPressed = pressed;
				break;
		}
	}
	public void reset()
	{
		movementSpeed = 3.5;
		maxHealth = 100;
		health = maxHealth;
		xPos = Game.WIDTH/2;
		yPos = Game.HEIGHT/2;
		maxGrenadeCount = grenadeCount = 10;
		for(AbstractGun gun : guns)
		{
			gun.reset();
		}
	}
	/*
	 * Getters and setters
	 */
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int amount)
	{
		health=amount;
	}
	
	public void changeHealth(int amount)
	{
		health = (int) Util.clamp(health + amount, 0, maxHealth);
	}
	
	public void damage(int damage)
	{
		health-=damage;
	}
	
	public void changeMovementSpeed(double change)
	{
		movementSpeed+=change;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void changeMaxHealth(int amount)
	{
		maxHealth += amount;
	}
	
	public int getGrenadeCount()
	{
		return grenadeCount;
	}
	
	public int getMaxGrenadeCount()
	{
		return maxGrenadeCount;
	}
	
	public void changeGrenadeCount(int amount)
	{
		grenadeCount = (int) Util.clamp(grenadeCount + amount, 0, maxGrenadeCount);
	}
	
	public Pistol getPistol()
	{
		return pistol;
	}
	
	public MachineGun getMachineGun()
	{
		return machineGun;
	}
	
	public AbstractGun getCurrentGun()
	{
		return currentGun;
	}
}