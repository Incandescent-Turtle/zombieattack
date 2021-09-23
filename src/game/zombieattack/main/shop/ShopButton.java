package game.zombieattack.main.shop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.zombieattack.main.Game;
import game.zombieattack.main.util.Mouse;
import game.zombieattack.main.util.Util;

public abstract class ShopButton extends Rectangle 
{
	private static final long serialVersionUID = 1L;
	private int initialCost, cost, limit, timesBought;
	private boolean hasLimit = false;
	//colours. Original is normally shown, hover mouse, and the colour of the desc
	private Color messageColor;
	//description of button and label
	private String message;
	private Game game;
	private final BufferedImage texture;
	private Color highlightColor;
	
	protected int ticksSinceLastBought = 200;
	
	//default is white text and white highlight
	public ShopButton(Game game, Point pos, BufferedImage texture, int cost, int limit, String message)
	{
		super(pos.x, pos.y, texture.getWidth(), texture.getHeight());
		this.initialCost = this.cost = cost;
		this.message = message;
		this.game = game;
		this.limit = limit;
		this.hasLimit = true;
		this.texture = texture;
		setMessageColor(Color.WHITE);
		setButtonHighlightColor(Color.WHITE);
	}
	
	public ShopButton(Game game, Point pos, BufferedImage texture, int cost, String message)
	{
		this(game, pos, texture, cost, 0, message);
		hasLimit = false;
	}
	
	//run when clicked
	protected abstract void clickAction();
	
	//called from shop
	public void tick()
	{
		//cheaty. Allows users to rapid click to buy. When mouse is lifted it basically tells the program its okay to try and buy another one
		if(!Mouse.mouseDown) ticksSinceLastBought = 1000;
		//checks to make sure that it either doesnt have a limit or that the limit hasnt been exceeded
		if(hasLimit && timesBought < limit || !hasLimit)
		{
			ticksSinceLastBought++;	
			//checks to see if the player is clicking on the button, and if it is it chekcs to see if the player has enough money, and if so it buys it
			if(ticksSinceLastBought > 50 && game.getMoney() >= cost && Mouse.mouseWithin(this) && Mouse.mouseDown && canBuy())
			{
				game.changeMoney(-cost);
				ticksSinceLastBought = 0;
				timesBought++;
				clickAction();
			}
		}
	}
	//called from shop
	public void render(Graphics2D g)
	{ 
		g.setFont(new Font("serif", 0, 20));

		//fills/draws the button
		g.drawImage(texture, x, y, null);
		
				
		//when the mouse is hovering over the shop button
		if(Mouse.mouseWithin(this))
		{
			//Highlighting the button
			g.setColor(highlightColor);
			g.fillRect(x, y, width, height);
			
			//checks to make sure the limit either hasnt been reached or doesnt exist
			if(hasLimit && timesBought < limit || !hasLimit)
			{
				g.setColor(messageColor);
				//draws the message centered under the button
				Util.drawXCenteredString(message, x+width/2, y+height+20, g);
				//draws the cost under the message
				Util.drawXCenteredString("Cost: $" + cost, x+width/2, y+height+40, g);
			} else {
				g.setColor(messageColor);
				Util.drawXCenteredString("Sorry, you can't buy anymore!", x+width/2, y+height+20, g);
			}
		}
	}
	
	//called from the menuHandler class. Called when the game resets
	public void reset()
	{
		cost = initialCost;
		timesBought = 0;
		ticksSinceLastBought = 0;
	}
	
	protected boolean canBuy() 
	{
		return true;
	}
	
	public void setMessageColor(Color messageColor)
	{
		this.messageColor = messageColor;
	}
	
	public void setButtonHighlightColor(Color color)
	{
		highlightColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 80);
	}
}