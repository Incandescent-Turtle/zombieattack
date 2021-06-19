package game.zombieattack.main.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.zombieattack.main.Game;
import game.zombieattack.main.hud.PlayerHUD;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.util.Util;

//Game shop (in-game)
@SuppressWarnings("unused")
public class Shop {

	private Game game;
	//shop buttons
	private ShopButton movementSpeedButton, damageButton, maxHealthButton, healthButton, bulletSpeedButton, playerFireSpeedButton;
	//array to iterate to render, tick, and stuff
	protected ArrayList<ShopButton> buttons = new ArrayList<>();;

	@SuppressWarnings("serial")
	public Shop(Game game) 
	{
		this.game = game;
		Player player = game.getPlayer();
		
		//itilizing the buttons and filling in the upgrade code
		movementSpeedButton = new ShopButton(this, game, 100, 120, 200, 10, "Increase Movement Speed", "MOVEMENT SPEED") 
		{ 
			@Override
			public void clickAction() 
			{ 
				player.changeMovementSpeed(.1); 
			}
		};
		
		damageButton = new ShopButton(this, game, 100, 300, 800, 1, "Increase Bullet Damage (+1)", "DAMAGE") 
		{
			@Override
			public void clickAction() 
			{ 
				player.changeDamage(1); 
			}
		};
			
		maxHealthButton = new ShopButton(this, game, 100, 480, 250, 10, "Increase Max Health (+10)", "MAX HEALTH") 
		{
			@Override
			public void clickAction() 
			{ 
				player.changeMaxHealth(10);
			}
		};
		
		healthButton = new ShopButton(this, game, 800, 120, 200, "Regenerate Health (+25)", "HEALTH") 
		{
			@Override
			public void clickAction() 
			{ 
				player.changeHealth(player.getHealth() <= player.getMaxHealth()-25 ? 25 : player.getMaxHealth() - player.getHealth()); 
			}
		};
		
		bulletSpeedButton = new ShopButton(this, game, 800, 300, 50, 8, "Increase Bullet Speed", "BULLET SPEED") 
		{
			@Override
			public void clickAction() 
			{ 
				player.changeBulletSpeed(1);
			}
			
		};
	}

	//called from menu class when the shop is open
	public void tick()
	{ 
		//iterating through the buttons
		for(ShopButton button : buttons)
		{
			button.tick();
		}
	}
	
	//called from menu class when the shop is open
	public void render(Graphics2D g)
	{
		g.setColor(Color.DARK_GRAY);
		//renders the shop background
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		//iterates through the shop buttons to render them all
		for(ShopButton button : buttons)
		{
			button.render(g);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", 1, 80));
		//draws shop title
		Util.drawCenteredString("SHOP", Game.WIDTH/2, 35, g);
		g.setFont(new Font("serif", 1, 20));
		g.setColor(Color.WHITE);
		//draws the player money
		g.drawString("Money: $" + game.getMoney(), Game.WIDTH-Util.getStringWidth("Money: $" + game.getMoney(), g)-10, 20);
		//creates and renders a healthbar
		new PlayerHUD(game.getPlayer()).render(g);
	}
	//called when the game is restarted, called from MenuHandler, which is called from Game class, which is inflicted by the Play Again button in EndScreen
	public void reset() 
	{
		for(ShopButton button : buttons)
		{
			button.reset();
		}
	}
}
