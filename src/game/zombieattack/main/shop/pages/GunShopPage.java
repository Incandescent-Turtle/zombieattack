package game.zombieattack.main.shop.pages;

import java.awt.Color;
import java.awt.Point;

import game.zombieattack.main.Game;
import game.zombieattack.main.objects.gunstuff.AbstractGun;
import game.zombieattack.main.shop.ShopButton;
import game.zombieattack.main.util.Textures;

public class GunShopPage extends ShopPage 
{
	@SuppressWarnings("serial")
	public GunShopPage(final Game game, final AbstractGun gun, final GunPageHelper bulletSpeedHelper, final GunPageHelper damageHelper, final GunPageHelper amountHelper, final GunPageHelper reloadSpeedHelper) 
	{
		super(game, gun.getName(), Color.BLACK);
				
		//bullet speed
		add(new ShopButton(game, new Point(100, 120), Textures.BULLET_SPEED_BUTTON, bulletSpeedHelper.price, bulletSpeedHelper.limit, "Increase Bullet Speed (+" + bulletSpeedHelper.amountToIncrease + ")") 
		{
			@Override
			public void clickAction() 
			{ 
				gun.changeBulletSpeed(bulletSpeedHelper.amountToIncrease);
			}
		});
				
		//damage
		add(new ShopButton(game, new Point(300, 120), Textures.BULLET_DAMAGE_BUTTON, damageHelper.price, damageHelper.limit, "Increase Bullet Damage (+" + (int) damageHelper.amountToIncrease + ")") 
		{
			@Override
			public void clickAction() 
			{ 
				gun.changeDamage((int) damageHelper.amountToIncrease); 
			}
		});
		
		//bullets
		add(new ShopButton(game, new Point(500, 120), Textures.AMMO_BUTTON, amountHelper.price, "Increase Bullet Count  (+" + (int) amountHelper.amountToIncrease + ")") 
		{
			@Override
			public void clickAction() 
			{ 
				gun.changeBulletAmount((int) amountHelper.amountToIncrease);
			}
			
			@Override
			protected boolean canBuy() 
			{
				return gun.getBulletAmount() != gun.getInitialMagSize();
			}
		});
		
		//reload speed
		add(new ShopButton(game, new Point(700, 120), Textures.RELOAD_SPEED_BUTTON, reloadSpeedHelper.price, reloadSpeedHelper.limit, "Increase Reload Speed  (+" + (int) reloadSpeedHelper.amountToIncrease + ")") 
		{
			@Override
			public void clickAction() 
			{ 
				gun.changeReloadSpeed((int) -reloadSpeedHelper.amountToIncrease);
			}
		});
	}
	
	public static class GunPageHelper
	{
		public double amountToIncrease;
		public int price;
		public int limit;
		
		public GunPageHelper(double amountToIncrease, int price, int limit)
		{
			this.amountToIncrease = amountToIncrease;
			this.price = price;
			this.limit = limit;
		}
		
		public GunPageHelper(double amountToIncrease, int price)
		{
			this(amountToIncrease, price, 0);
		}
	}
}