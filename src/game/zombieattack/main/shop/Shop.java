package game.zombieattack.main.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import game.zombieattack.main.Game;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.shop.pages.GunShopPage;
import game.zombieattack.main.shop.pages.GunShopPage.GunPageHelper;
import game.zombieattack.main.shop.pages.PlayerShopPage;
import game.zombieattack.main.shop.pages.ShopPage;
import game.zombieattack.main.util.Util;

//Game shop (in-game)
@SuppressWarnings("unused")
public class Shop extends KeyAdapter
{
	private Game game;
	//list of all the shop pages
	private ArrayList<ShopPage> pages = new ArrayList<>();
	protected Player player;
	private int currentPage = 0;
	
	public Shop(Game game) 
	{
		this.game = game;
		player = game.getPlayer();
		
		add(new PlayerShopPage(game));
		//Pistol Page - speed - damage - amount - reload
		add(new GunShopPage(game, player.getPistol(), new GunPageHelper(1, 50, 4), new GunPageHelper(10, 300, 2), new GunPageHelper(10, 50), new GunPageHelper(2, 200, 2)));
		add(new GunShopPage(game, player.getMachineGun(), new GunPageHelper(1, 50, 4), new GunPageHelper(5, 250, 1), new GunPageHelper(100, 50), new GunPageHelper(2, 400, 3)));

	}

	private void add(ShopPage page)
	{
		pages.add(page);
	}
	
	//called from menu class when the shop is open
	public void tick()
	{ 
		pages.get(currentPage).tick();
	}
	
	//called from menu class when the shop is open
	public void render(Graphics2D g)
	{
		pages.get(currentPage).render(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", 0, 20));
		//draws the player money
		String message = "Money: $" + game.getMoney();
		g.drawString(message, Game.WIDTH-Util.getStringWidth(message, g)-10, 20);
		//creates and renders a healthbar
		game.getPlayerHUD().render(g);
	}
	
	//called when the game is restarted, called from MenuHandler, which is called from Game class, which is inflicted by the Play Again button in EndScreen
	public void reset() 
	{
		for(ShopPage page : pages)
			page.reset();
	}
	
	public Game getGame()
	{
		return game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT) currentPage = currentPage == pages.size()-1 ? 0: currentPage+1;
		if(key == KeyEvent.VK_LEFT) currentPage = currentPage == 0 ? pages.size()-1: currentPage-1;
	}
}