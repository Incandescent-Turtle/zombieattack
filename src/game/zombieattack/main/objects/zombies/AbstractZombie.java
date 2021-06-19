 package game.zombieattack.main.objects.zombies;

import static java.lang.Math.atan2; 

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import game.zombieattack.main.Combustable;
import game.zombieattack.main.Game;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.objects.Grenade;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Util;

//Zombie, is a GameObject
public abstract class AbstractZombie extends GameObject implements Combustable
{
	
	//zombie health, zombie's maximum health, zombie running speed, zombie damage, ticks since it last attacked the player
	protected int health, maxHealth, speed, damage;
	//ticks it has been recovering from being shot for, total time it takes
	protected int ticksRecovering, ticksToRecover;
	//time in between swings at the player, time since last hit player
	protected int attackCoolDownTicks, ticksSinceLastHitPlayer;
	//the normal colour this zombie is displayed as
	protected Color zombieColor;
	protected Player player;
	//angle towards player
	protected double angle;
	//used to determine whether or not the zombie is recovering from damage, and whether it can attack the player or not
	protected boolean recovering = false, onAttackCoolDown = false;
	protected Game game;
	public ZombieType type;
	
	public enum ZombieType 
	{
		REGULAR,
		BOSS;
	}

	
	public AbstractZombie(double xPos, double yPos, Player player, Game game, GameObjectHandler handler, Color color, ZombieType type, int recoveryTicks, int attackCoolDownTicks, int speed, int damage, int size, int maxHealth) 
	{
		super(xPos, yPos, size, handler);
		this.player = player;
		this.speed = speed;
		this.damage = damage;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.zombieColor = color;
		this.game = game;
		this.type = type;
		this.ticksToRecover = recoveryTicks;
		this.attackCoolDownTicks = attackCoolDownTicks;
	}
	
	public AbstractZombie(Point2D.Double pos, Player player, Game game, GameObjectHandler handler, Color color, ZombieType type, int recoveryTicks, int attackCoolDownTicks, int speed, int damage, int size, int maxHealth)
	{
		this(pos.getX(), pos.getY(), player, game, handler, color, type, recoveryTicks, attackCoolDownTicks, speed, damage, size, maxHealth);
	}
	
	public AbstractZombie(Point pos, Player player, Game game, GameObjectHandler handler, Color color, ZombieType type, int recoveryTicks, int attackCoolDownTicks, int speed, int damage, int size, int maxHealth)
	{
		this(pos.getX(), pos.getY(), player, game, handler, color, type, recoveryTicks, attackCoolDownTicks, speed, damage, size, maxHealth);
	}
	
	//overriden from GameObject, called every game render
	@Override
	public void render(Graphics2D g) 
	{
		//sets colour, if recovering it is white, otherwise it is its normal colour
		g.setColor(recovering ? Color.WHITE : zombieColor);
		//draws zombie
		drawZombie(g);
		//draws the healthbar
		drawHealthBar(g);
	}
	
	//for easy implementation. Called from the render method
	protected abstract void drawZombie(Graphics2D g);
	//for easy implementation. Called from the render method
	protected abstract void drawHealthBar(Graphics2D g);
	
	//overriden from GameObject, called every game render
	@Override
	public void tick() 
	{
		if(health<=0) handler.removeObject(this);
 		//when hit with a bullet it moves backward. If touching the player moves backward
		if(!recovering && !getBounds().intersects(player.getBounds())) moveForward();
			else moveBackWard();
		
		//keeps the zombie in bounds
		xPos = Util.clamp(xPos, width/2, Game.WIDTH-width/2-6);
		yPos = Util.clamp(yPos, height/2, Game.HEIGHT-height-4);
		
		//increases the recoveryTicks
		if(recovering) ticksRecovering++;
		//when recovery is over
		//10 is equal to the recovery time
		if(ticksRecovering >= ticksToRecover) 
		{
			ticksRecovering = 0;
			recovering = false;
		}
		//increases the ticks if the zombie is in timeout for nibbling player
		if(onAttackCoolDown) ticksSinceLastHitPlayer++;
		
		//used to make the zombies less violent. Zombies have to wait 20 ticks between nibbling the player
		if(ticksSinceLastHitPlayer > attackCoolDownTicks) 
		{
			ticksSinceLastHitPlayer = 0;
			onAttackCoolDown = false;
		}
		zombieTick();
	}
	
	//any extra stuff that needs to be ticked
	protected void zombieTick() {
		
	}

	//uses angle towards player to move towards the player
	private void moveForward()
	{
		angle = atan2(player.getY() - yPos, player.getX() - xPos); 
		
		xPos += Math.cos(angle) * speed;
		yPos += Math.sin(angle) * speed;	
	}
	
	//uses the angle towards the player + 180 to move away from the player
	public void moveBackWard()
	{
		xPos += Math.cos(angle+180) * (speed);
		yPos += Math.sin(angle+180) * (speed);
	}
	
	//used to damage the zombie (used in CollisionHandler)
	public void damage(int damage, GameObject source)
	{
		recovering = true;
		health -= damage;
		if(health == 0 && source != null) dieBy(source);
	}
	
	public void damage(int damage)
	{
		damage(damage, null);
	}
	//kills the zombie and passes in a source. Such as Bullet or Grenade
	public void dieBy(GameObject source) 
	{
		zombieDieBy(source);
	}
	//for ease of implementation
	protected abstract void zombieDieBy(GameObject source);
	
	//called when the zombie collides with the player
	public void hitPlayer(Player player)
	{
		player.damage(damage);
		ticksSinceLastHitPlayer = 0;
		onAttackCoolDown = true;
		moveBackWard();
	}

	@Override
	public void blowUp(Grenade grenade)
	{
		blowUpZombie(grenade);
	}
	
	//helper
	protected abstract void blowUpZombie(Grenade grenade);
	
	/*
	 * Getters
	 */
	
	public int getHealth() {
		return health;
	}

	public int getDamage() {
		return damage;
	}
	
	public boolean isOnAttackCoolDown()
	{
		return onAttackCoolDown;
	}
}