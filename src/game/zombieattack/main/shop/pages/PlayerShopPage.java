package game.zombieattack.main.shop.pages;

import java.awt.Color;
import java.awt.Point;

import game.zombieattack.main.Game;
import game.zombieattack.main.shop.ShopButton;
import game.zombieattack.main.util.Textures;

@SuppressWarnings("serial")
public class PlayerShopPage extends ShopPage
{	
	public PlayerShopPage(Game game) 
	{
		super(game, "Player Upgrades", Color.BLACK);
		//movement speed
		add(new ShopButton(game, new Point(100, 120), Textures.MOVEMENT_SPEED_BUTTON, 111, 10, "Increase Movement Speed")
		{ 
			@Override
			public void clickAction() 
			{ 
				player.changeMovementSpeed(.1); 
			}
		});
		
		//health
		add(new ShopButton(game, new Point(300, 120), Textures.REGEN_HEALTH_BUTTON, 200,"Regenerate Health (+25)")
		{
			@Override
			public void clickAction() 
			{ 
				player.changeHealth(player.getHealth() <= player.getMaxHealth()-25 ? 25 : player.getMaxHealth() - player.getHealth()); 
			}
			
			@Override
			protected boolean canBuy() 
			{
				return player.getHealth() != player.getMaxHealth();
			}
		});
				
		//max health
		add(new ShopButton(game, new Point(500, 120), Textures.MAX_HEALTH_BUTTON, 250, 20, "Increase Max Health (+10)") 
		{
			@Override
			public void clickAction() 
			{ 
				player.changeMaxHealth(10);
			}
		});
		
		//grenade count
		add(new ShopButton(game, new Point(700, 120), Textures.GRENADE_BUTTON, 100, "Buy Grenades (+2)") 
		{
			@Override
			protected void clickAction() 
			{
				player.changeGrenadeCount(2);
			}
			
			@Override
			protected boolean canBuy() 
			{
				return player.getGrenadeCount() != 10;
			}
		});
	}
}