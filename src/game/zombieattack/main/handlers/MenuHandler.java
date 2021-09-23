package game.zombieattack.main.handlers;

import java.awt.Color;
import java.awt.Font; 
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.gui.EndScreen;
import game.zombieattack.main.gui.TitleScreen;
import game.zombieattack.main.shop.Shop;
import game.zombieattack.main.util.Mouse;
import game.zombieattack.main.util.Util;

//a class that renders and ticks different menu types
@SuppressWarnings("incomplete-switch")
public class MenuHandler {

	//Declaring menu types
	private TitleScreen titleScreen;
	private EndScreen endScreen;
	private Shop shop;
	
	private Game game;
	
	public MenuHandler(Game game)
	{
		this.game = game;
		titleScreen = new TitleScreen(game);
		endScreen = new EndScreen(game);
		shop = new Shop(game);
	}
	
	//called from the Game class, when one of the menu types is open
	
	public void render(Graphics2D g)
	{
		//calls the respective render method based on game state
		switch(game.getState())
		{
			//renders the titlescreen instance
			case TitleScreen:
				titleScreen.render(g);
				break;
				//renders the endscreen instance
			case EndScreen:
				endScreen.render(g);
				break;
				//renders the shop instance
			case Shop:
				shop.render(g);
				break;
			//renders text over the game. "Game Paused"
			case Paused:
				g.setFont(new Font("serif", 1, 80));
				Util.drawXCenteredString("Game Paused", Game.WIDTH/2, 60, g);
				break;
		}
	}
	
	//called from the Game class, when one of the menu types is open
	public void tick()
	{
		//calls the respective tick method based on game state
		switch(game.getState())
		{
			//ticks titlescreen instance
			case TitleScreen:
				titleScreen.tick();
				break;
			//ticks endscreen instance
			case EndScreen:
				endScreen.tick();
				break;
			//ticks shop instance
			case Shop:
				shop.tick();
				break;				
		}
	}
	//helper to check if the mouse is within the button and down
	public static boolean buttonClicked(Rectangle button)
	{
		return (Mouse.mouseDown && Mouse.mouseWithin(button));
	}
	//helper to create a button
	public static Rectangle newButton(int x, int y, int width, int height, Graphics2D g, Color original, Color hover)
	{
		Rectangle button = new Rectangle(x, y, width, height);
		g.setColor(Mouse.mouseWithin(button) ? hover : original);
		g.draw(button);
		g.fill(button);
		return button;
	}
	//called on game reset, resets shop and ShopButtons
	public void reset()
	{
		shop.reset();
	}
	
	public Shop getShop()
	{
		return shop;
	}
}
