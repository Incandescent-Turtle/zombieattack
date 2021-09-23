package game.zombieattack.main.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.handlers.MenuHandler;
import game.zombieattack.main.util.Util;

//game over screen
public class EndScreen {

	private Game game;
	
	public EndScreen(Game game) 
	{
		this.game = game;
	}

	//called from menu class
	public void tick()
	{
	}
	
	//called from menu class
	public void render(Graphics2D g)
	{ 
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.RED);
		g.setFont(new Font("serif", 0, 80));
		Util.drawXCenteredString("GAME OVER", Game.WIDTH/2, Util.getStringHeight("GAME OVER", g)/2+195, g);
		g.setColor(Color.DARK_GRAY);
		//creates a button to restart the game
		Rectangle playAgain = MenuHandler.newButton(Game.WIDTH/2-250, Game.HEIGHT/2-90, 500, 150, g, Color.DARK_GRAY, new Color(53, 53, 53));
		g.fill(playAgain);
		g.setColor(Color.RED);
		g.setFont(new Font("serif", 1, 80));
		Util.drawCenteredString("Play Again", (int) (playAgain.getX()+playAgain.getWidth()/2), (int) (playAgain.getY()+playAgain.getHeight()/2), g);
		//starts the game again
		if(MenuHandler.buttonClicked(playAgain)) game.startGame();
	}
}
