package game.zombieattack.main.shop.pages;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.zombieattack.main.Game;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.shop.ShopButton;
import game.zombieattack.main.util.Util;

@SuppressWarnings("unused")
public abstract class ShopPage 
{
	private String title;
	private ArrayList<ShopButton> buttons = new ArrayList<>();
	private final Font titleFont = new Font("ariel", 0, 70);
	private final Color pageColor;
	protected Game game;
	protected Player player;
	
	public ShopPage(Game game, String title, Color color) 
	{
		this.title = title;
		this.pageColor = color;
		this.game = game;
		this.player = game.getPlayer();
	}
		
	//adds a button to the page
	public void add(ShopButton button)
	{
		buttons.add(button);
	}
	
	//ticks the correct page
	public void tick()
	{
		for(ShopButton button : buttons)
			button.tick();
	}
	
	//renders the correct page
	public void render(Graphics2D g)
	{	
		g.setColor(pageColor);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(new Color(255, 140, 0));
		g.fillRect(0, Game.HEIGHT-80, Game.WIDTH, 80);
		g.setColor(pageColor == Color.WHITE ? Color.BLACK : Color.WHITE);
		g.setFont(titleFont);
		Util.drawXCenteredString(title, Game.WIDTH/2, 0+Util.getStringHeight(title, g), g);
		
		for(ShopButton button : buttons)
			button.render(g);
	}
	
	//resets all the pages
	public void reset()
	{
		for(ShopButton button : buttons)
			button.reset();
	}
}