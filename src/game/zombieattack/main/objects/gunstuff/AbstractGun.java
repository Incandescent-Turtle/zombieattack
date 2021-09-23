package game.zombieattack.main.objects.gunstuff;

import java.awt.Graphics2D;

import game.zombieattack.main.Game;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Util;

public abstract class AbstractGun 
{
	protected Game game;
	private GameObjectHandler handler;
	protected Player player;
	
	//speed of the bullet
	protected double bulletSpeed;
	private final double initialBulletSpeed;
	//bullet damage, time between shots, how many bullets the gun can hold
	private final int initialBulletDamage, initialReloadSpeed, initialMagSize;
	//bullet damage, time between shots, how many bullets the gun is holding
	protected int bulletDamage, reloadSpeed = 20, bulletAmount;
	private int ticksSinceLastShot = reloadSpeed;
	
	protected AbstractGun(Game game, Player player, double bulletSpeed, int bulletDamage, int reloadSpeed, int magSize)
	{
		this.handler = game.getObjectHandler();
		this.player = player;
		this.game = game;
		this.initialBulletSpeed = this.bulletSpeed = bulletSpeed;
		this.initialBulletDamage = this.bulletDamage = bulletDamage;
		this.initialReloadSpeed = this.reloadSpeed = reloadSpeed;
		this.initialMagSize = this.bulletAmount = magSize;
	}
	
	protected abstract int getBarrelHeight();
	//for shop page titles etc
	public abstract String getName();

	protected int getShotWidth()
	{
		return 0;
	}
	
	protected abstract Bullet newBullet();

	public double getBulletSpeed()
	{
		return bulletSpeed;
	}
	
	public int getDamage()
	{
		return bulletDamage;
	}
	
	public int getReloadSpeed()
	{
		return reloadSpeed;
	}
	
	public int getBulletAmount()
	{
		return bulletAmount;
	}
	
	public int getInitialMagSize()
	{
		return initialMagSize;
	}

	//tries to shoot a bullet
	public void tryShootBullet()
	{
		if(bulletAmount > 0 && ticksSinceLastShot >= reloadSpeed) 
		{
			bulletAmount-=1;
			handler.addObject(newBullet());
			ticksSinceLastShot = 0;
		}		
	}
	
	public abstract void render(Graphics2D g);
	
	public void tick()
	{
		ticksSinceLastShot++;
		bulletAmount = (int) Util.clamp(bulletAmount, 0, initialMagSize);
	}
	
	public void changeDamage(int amount)
	{
		bulletDamage+=amount;
	}
	
	public void changeBulletSpeed(double amountToIncrease)
	{
		bulletSpeed+=amountToIncrease;
	}
	
	public void changeBulletAmount(int amount)
	{
		bulletAmount += bulletAmount + amount <= initialMagSize ? amount : initialMagSize - bulletAmount ;
	}
	
	public void changeReloadSpeed(int amount)
	{
		reloadSpeed += amount;
	}
	
	//called from player
	public void reset()
	{
		reloadSpeed = initialReloadSpeed;
		bulletDamage = initialBulletDamage;
		bulletSpeed = initialBulletSpeed;
		bulletAmount = initialMagSize;
	}
}