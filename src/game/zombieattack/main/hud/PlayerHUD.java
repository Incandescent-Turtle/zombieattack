package game.zombieattack.main.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.zombieattack.main.Game;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Textures;
import game.zombieattack.main.util.Util;

//HUD to show player health. Used during the game and in shop 
public class PlayerHUD 
{

	private Player player;
	private int grenadeHUDHeight;

	public PlayerHUD(Player player)
	{
		this.player = player;
	}
	
	//called every game render, renders the HUD. Also rendered in the shop
	public void render(Graphics2D g)
	{
		//rendering healthbar
		renderHealthBar(g);
		//rendering grenade count
		renderGrenadeCount(g);
		//rendering bullet count for current gun
		renderBulletCount(g);
	}
	
	private void renderHealthBar(Graphics2D g)
	{		
		g.setColor(Color.BLACK);
		//drawing the outline for the healthBar
		g.drawRect((Game.WIDTH/2)-150, Game.HEIGHT-50, 300, 40);
				
		//setting the healthbar colour
		g.setColor(player.getHealth() >= player.getMaxHealth()/4*3 ? Color.GREEN : player.getHealth() > player.getMaxHealth()/3 ? Color.YELLOW : Color.RED);
		//filling the health bar the correct amount (casts xPos and yPos to int because of bouncy motion)
		if(!(player.getHealth() <= 0)) g.fillRect((Game.WIDTH/2)-149, Game.HEIGHT-49, (int) ((player.getHealth()/(double)player.getMaxHealth())*300)-1, 39);
		//drawing the health to the screen
		g.setFont(new Font("serif", 1, 20));
		//displays the numerical value of the health (temporary?)
		g.drawString(""+player.getHealth(), (Game.WIDTH/2)-149, Game.HEIGHT-55);
	}
	
	private void renderGrenadeCount(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("serif", 1, 20));
		String message = player.getGrenadeCount() + "/" + player.getMaxGrenadeCount();
		short length = (short) Util.getStringWidth(message, g);
		g.drawString(message, Game.WIDTH-length-5, Game.HEIGHT-5);
		g.drawImage(Textures.GRENADE, Game.WIDTH-length-35, Game.HEIGHT-35, 40, 40, null);
		grenadeHUDHeight = Util.getStringHeight(message, g);
	}
	
	private void renderBulletCount(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("serif", 1, 20));
		String message = player.getCurrentGun().getBulletAmount() + "/" + player.getCurrentGun().getInitialMagSize();
		short length = (short) Util.getStringWidth(message, g);
		g.drawString(message, Game.WIDTH-length-5, Game.HEIGHT-5-grenadeHUDHeight);
		g.drawImage(Textures.BULLET_ICON, Game.WIDTH-length-40, Game.HEIGHT-35-grenadeHUDHeight, 54, 44, null);
	}
}
