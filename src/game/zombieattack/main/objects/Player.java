package game.zombieattack.main.objects;

import java.awt.Color;  
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import game.zombieattack.main.Controllable;
import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.util.Mouse;
import game.zombieattack.main.util.Util;

//the main player / survivor in game
//implements Controllable which sends key presses and releases
public class Player extends GameObject implements Controllable 
{
	//used to determine whether or not keys are pressed
	private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, qPressed; 
	//the size of the player
	private int size;
	//health
	private int maxHealth = 100, health = maxHealth;
	//gun stats
	private int fireSpeed = 20, bulletDamage = 1, ticksSinceLastShotBullet = fireSpeed;
	private double movementSpeed = 5, velX, velY, bulletSpeed;
	//grenade stuff
	private int grenadeCount = 10, maxGrenadeCount = 10, ticksSinceLastLaunchedGrenade = 40;
	//gun the player is holding
	@SuppressWarnings("unused")
	private Gun gun;
	//used to differentiate between the gun being used
	private enum Gun
	{
		PISTOL,
		MACHINE_GUN;
	}
	
	public Player(int x, int y, int size, GameObjectHandler handler) {
		super(x, y, size, handler);
		this.size = size;
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
		//shoots
		ticksSinceLastShotBullet++;
		ticksSinceLastLaunchedGrenade++;
		if(spacePressed && ticksSinceLastShotBullet >= fireSpeed || Mouse.mouseDown && ticksSinceLastShotBullet >= fireSpeed) 
		{
			shootBullet();
			ticksSinceLastShotBullet = 0;
		}
		
		if(qPressed && ticksSinceLastLaunchedGrenade >= 50 && grenadeCount > 0)
		{
			launchGrenade();
			ticksSinceLastLaunchedGrenade = 0;
		}
		//keeps the value between the specified values
		health = health > maxHealth ? maxHealth : health;
	}

	//tick method overriden from GameObject called in handler every tick
	@Override
	public void render(Graphics2D g) 
	{
		//draws tank barrel
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.DARK_GRAY);
		Rectangle barrel = new Rectangle(0-5, 0, 10, 50);
		double angle = Math.atan2(xPos - Mouse.mouseX, yPos - Mouse.mouseY)+135;
		g2d.translate(xPos, yPos);
		g2d.rotate(-angle);
		g2d.draw(barrel);
		g2d.fill(barrel);
		g2d.dispose();
		
		//draws player
		g.setColor(Color.BLACK);
		g.fill(Util.newCircle(xPos-width/2, yPos-height/2, width, height));
	}

	@Override
	public void keyPressed(int key) {
		//checks key presses and sets the booleans
		switch(key)
		{
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				upPressed = true;
				break;
			
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				downPressed = true;
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				break;
			
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				break;
				
			case KeyEvent.VK_SPACE:
				spacePressed = true;
				break;
				
			case KeyEvent.VK_Q:
				qPressed = true;
				break;
		}
	}

	@Override
	public void keyReleased(int key) {
		//checks key presses and sets the booleans
		switch(key)
		{
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				upPressed = false;
				break;
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				downPressed = false;
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
				
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;
				
			case KeyEvent.VK_SPACE:
				spacePressed = false;
				break;
				
			case KeyEvent.VK_Q:
				qPressed = false;
				break;
		}
	}
	
	//called when a bullet is shot, creates a bullet
	private void shootBullet()
	{
		handler.addObject(new Bullet(this, handler, 10, 0, bulletSpeed));
	}
	
	//called when a grenade is launched, creates a grenade
	private void launchGrenade() 
	{
		handler.addObject(new Grenade(this, handler));
		grenadeCount--;
	}
	
	public void reset()
	{
		movementSpeed = 5;
		bulletDamage = 1;
		maxHealth = 100;
		health = maxHealth;
		xPos = Game.WIDTH/2;
		yPos = Game.HEIGHT/2;
		fireSpeed = 20;
		bulletSpeed = 15;
		maxGrenadeCount = grenadeCount = 10;
		gun = Gun.PISTOL;
	}
	/*
	 * Getters and setters
	 */
	
	public int getSize()
	{
		return size;
	}
	
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
	
	public int getDamgage()
	{
		return bulletDamage;
	}
	
	public void setDamage(int damage)
	{
		this.bulletDamage = damage;
	}
	
	public void changeDamage(int amount)
	{
		bulletDamage+=bulletDamage;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void changeMaxHealth(int amount)
	{
		maxHealth += amount;
	}
	
	public void changeBulletSpeed(double amount)
	{
		bulletSpeed += amount;
	}
	
	public void changeFireSpeed(int amount)
	{
		fireSpeed += amount;
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
}