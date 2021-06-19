package game.zombieattack.main.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.handlers.MenuHandler;
import game.zombieattack.main.util.Mouse;

//used when game first opened
public class TitleScreen {
	
	//different fonts
	private Font titleFont = new Font("Serif", 0, 80);
	private Font subTitle = new Font("Serif", 0, 40);
	private Font smallerFont = new Font("Serif", 0, 25);
	
	private Game game;
	
	//whether it is on the load screen or the one you get when you hit "Help"
	private enum TITLE_SCREEN_STATE {
		START_MENU,
		HELP_MENU
	}
	
	private TITLE_SCREEN_STATE state;
	
	public TitleScreen(Game game) 
	{
		this.game = game;
		state = TITLE_SCREEN_STATE.START_MENU;
	}

	//called from menu class when game starts or the game restarts, or quit to title screen
	public void tick()
	{
		
	}
	
	//called from menu class when game starts or the game restarts, or quit to title screen
	public void render(Graphics2D g)
	{ 
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		switch(state)
		{
		//when you first boot up the game/when the Play and Help options are displayed
			case START_MENU:
				g.setFont(titleFont);
				g.setColor(Color.BLACK);
				g.drawString("Zombie Attack", Game.WIDTH/2-210, 150);
				//Play button
				Rectangle playButton = newButton(300, g);
				g.setColor(Color.WHITE);
				g.drawString("Play", (int)playButton.getX()+120, (int)(playButton.getY()+playButton.getHeight()/2)+25);
				//onClickAction that starts the game
				if(MenuHandler.buttonClicked(playButton)) game.startGame();
				//help button+
				Rectangle helpButton = newButton(450, g);
				g.setColor(Color.WHITE);
				g.drawString("Help", (int)helpButton.getX()+120, (int)(helpButton.getY()+helpButton.getHeight()/2)+25);
				//onClickAction that changes the state to help
				if(MenuHandler.buttonClicked(helpButton)) state = TITLE_SCREEN_STATE.HELP_MENU;
				break;
				//renders the help menu
			case HELP_MENU: 
				g.setFont(titleFont);
				g.setColor(Color.BLACK);
				g.drawString("Gameplay", Game.WIDTH/2-180, 100);
				//rendering controls
				int spaceBetweenControls = 28;
				int baseControlYPos = 250;
				int controlsXPos = 50;
				g.setFont(subTitle);
				g.drawString("Controls:", controlsXPos+70, baseControlYPos-50);
				g.setFont(smallerFont);
				g.drawString("W/Up Arrow - Move Up", controlsXPos, baseControlYPos);
				g.drawString("A/Left Arrow - Move Left", controlsXPos, baseControlYPos+spaceBetweenControls);
				g.drawString("S/Down Arrow - Move Down", controlsXPos, baseControlYPos+spaceBetweenControls*2);
				g.drawString("D/Right Arrow - Move Right", controlsXPos, baseControlYPos+spaceBetweenControls*3);
				g.drawString("Space - Fire Bullets", controlsXPos, baseControlYPos+spaceBetweenControls*6);
				//rendering instructions
				int instructionsXPos = Game.WIDTH/2+100;
				int baseInstructionsYPos = 250;
				g.setFont(subTitle);
				g.drawString("How to Play:", instructionsXPos+70, baseInstructionsYPos-50);
				g.setFont(smallerFont);
				g.drawString("Shoot the zombies before they attack you!", instructionsXPos, baseInstructionsYPos);
				//makes a back button to return to title screen
				newBackButton(50, Game.HEIGHT-170, g);
				break;
				
			default:
				break;
		}
	}
	//helper to make a back button
	private Rectangle newBackButton(int x, int y, Graphics2D g)
	{
		Rectangle button = new Rectangle(x, y, 400, 100);
		g.setColor(Mouse.mouseWithin(button) ? new Color(53, 53, 53) : Color.BLACK);
		g.draw(button);
		g.fill(button);
		if (MenuHandler.buttonClicked(button)) state = TITLE_SCREEN_STATE.START_MENU;
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("Back", x+110, y+button.height-25);
		return button;
	}
	//helper to make a menu button
	private Rectangle newButton(int y, Graphics2D g)
	{
		return MenuHandler.newButton(430, y, 400, 100, g, Color.BLACK, new Color(53, 53, 53));
	}
}