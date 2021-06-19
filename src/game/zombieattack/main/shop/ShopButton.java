package game.zombieattack.main.shop;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.zombieattack.main.Game;
import game.zombieattack.main.util.Mouse;
import game.zombieattack.main.util.Util;

public abstract class ShopButton extends Rectangle {

	private static final long serialVersionUID = 1L;
	private int xPos, yPos, width, height, initialCost, cost, limit, timesBought;
	private boolean hasLimit = false;
	//colours. Original is normally shown, hover mouse, and the colour of the desc
	private Color original, hover, messageColor;
	//description of button and label
	private String message, label;
	private Game game;
	
	protected int ticksSinceLastBought = 200;
	//limit on how many upgrades can be bought
	public ShopButton(Shop shop, Game game, int x, int y, int width, int height, int cost, int limit, Color original, Color hover, Color messageColor, String message, String label) {
		super(x, y, width, height);
		this.xPos = x;
		this.yPos = y;
		this.width = 100;
		this.height = 100;
		this.initialCost = this.cost = cost;
		this.original = original;
		this.hover = hover;
		this.messageColor = messageColor;
		this.message = message;
		this.game = game;
		this.limit = limit;
		this.hasLimit = true;
		this.label = label;
		shop.buttons.add(this);
	}
	
	//no limit on number of upgrades (health)
	public ShopButton(Shop shop, Game game, int x, int y, int width, int height, int cost, Color original, Color hover, Color messageColor, String message, String label)
	{
		this(shop, game, x, y, width, height, cost, 0, original, hover, messageColor, message, label);
		this.hasLimit = false;
	}
	//black button, light gray on hover, black message. Button has limit to how many times presses
	public ShopButton(Shop shop, Game game, int x, int y, int cost, int limit, String message, String label) {
		this(shop, game, x, y, 100, 100, cost, limit, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, message, label);
	}
	//black button, light gray on hover, black message. Button has NO limit to how many times presses
	public ShopButton(Shop shop, Game game, int x, int y, int cost, String message, String label) {
		this(shop, game, x, y, cost, 0, message, label);
		this.hasLimit = false;
	}
	
	//run when clicked
	protected abstract void clickAction();
	
	//called from shop
	protected void tick()
	{
		//cheaty. Allows users to rapid click to buy. When mouse is lifted it basically tells the program its okay to try and buy another one
		if(!Mouse.mouseDown) ticksSinceLastBought = 1000;
		//checks to make sure that it either doesnt have a limit or that the limit hasnt been exceeded
		if(hasLimit && timesBought < limit || !hasLimit)
		{
			ticksSinceLastBought++;	
			//checks to see if the player is clicking on the button, and if it is it chekcs to see if the player has enough money, and if so it buys it
			if(ticksSinceLastBought > 50 && game.getMoney() >= cost && Mouse.mouseWithin(this) && Mouse.mouseDown)
			{
				game.changeMoney(-cost);
				ticksSinceLastBought = 0;
				timesBought++;
				clickAction();
			}
		}
	}
	//called from shop
	protected void render(Graphics2D g)
	{ 
		g.setColor(original == Color.BLACK ? Color.white : Color.BLACK);
		g.setFont(new Font("serif", 0, 20));
		//sets new width and height based on the button label length
		width = Util.getStringWidth(label, g)+50;
		height = Util.getStringHeight(label, g)+50;	
		this.setBounds(xPos, yPos, width, height);
		//when the mouse is hovering over the shop button
		if(Mouse.mouseWithin(this))
		{
			//checks to make sure the limit either hasnt been reached or doesnt exist
			if(hasLimit && timesBought < limit || !hasLimit)
			{
				g.setColor(messageColor);
				g.setFont(new Font("serif", 0, 20));
				//draws the message centered under the button
				Util.drawXCenteredString(message, xPos+width/2, yPos+height+20, g);
				//draws the cost under the message
				Util.drawXCenteredString("Cost: $" + cost, xPos+width/2, yPos+height+40, g);
			} else {
				g.setColor(messageColor);
				Util.drawXCenteredString("Sorry, you can't buy anymore!", xPos+width/2, yPos+height+20, g);
				
			}
			//beacuse the mouse is within the button, it changes to hover colour
			g.setColor(hover);
		} else {
			//beacuse the mouse isnt within the button, it changes to normal colour
			g.setColor(original);
		}
		//fills/draws the button
		g.fill(this );
		g.setColor(Mouse.mouseWithin(this) ? Color.BLACK : Color.WHITE);
		g.setFont(new Font("serif", 0, 20));
		//draws the label centered in the middle of the button
		Util.drawCenteredString(label, xPos+width/2, yPos+height/2, g);
	}
	//called from the menuHandler class. Called when the game resets
	protected void reset()
	{
		cost = initialCost;
		timesBought = 0;
		ticksSinceLastBought = 0;
	}
}
