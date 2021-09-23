package game.zombieattack.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Map;

import javax.swing.JFrame;

import game.zombieattack.main.handlers.GameObjectHandler;
import game.zombieattack.main.handlers.KeyInputHandler;
import game.zombieattack.main.handlers.MenuHandler;
import game.zombieattack.main.hud.PlayerHUD;
import game.zombieattack.main.objects.Player;
import game.zombieattack.main.spawners.GroundItemSpawner;
import game.zombieattack.main.spawners.ZombieSpawner;
import game.zombieattack.main.util.Mouse;
import game.zombieattack.main.util.Util;
//the main class
public class Game extends Canvas implements Runnable 
{
	private static final long serialVersionUID = 1L;
	public static  int WIDTH = 1200, HEIGHT = WIDTH/12*9-210;
	
	private JFrame frame;
	
	private Thread thread;
	//handles the ticking and rendering of all GameObjects
	private GameObjectHandler gameObjectHandler;
	
	private boolean running = false;
	
	//the player
	private final Player player;
	//the game (player's) money
	private int money = 0;
	//the menu handler. Includes shop, titlescreen, endscreen, pause, help, etc
	private final MenuHandler menuHandler;
	//Factories
	private final ZombieSpawner zombieSpawner;
	private final GroundItemSpawner groundItemSpawner;
	//player HUD (health grenade/bullet count)
	private final PlayerHUD playerHUD;
	//starts the game in the title screen
	private GameState state = GameState.TitleScreen;
	
	//defines the current state of the game
	public enum GameState 
	{
		Game,
		TitleScreen,
		Paused,
		Shop,
		EndScreen
	}

	//run on game start
	public Game()
	{
		//initializing handler
		gameObjectHandler = new GameObjectHandler();
		//initializing player. x,y,handler
		player = new Player(WIDTH/2, HEIGHT/2, this);
		//initializing menu and adding the shop as a keylistener
		addKeyListener((menuHandler = new MenuHandler(this)).getShop());
		//initializing the player's HUD
		playerHUD = new PlayerHUD(player);
		//initializing the zombie spawner
		zombieSpawner = new ZombieSpawner(this, gameObjectHandler, player);
		//initializing the groundItem spawner
		groundItemSpawner = new GroundItemSpawner(this, gameObjectHandler, player);
		//adding a key listener (which controls all movement) and a mouse motion listener (which is the player, its used for shooting)
		addKeyListener(new KeyInputHandler(this, gameObjectHandler));
		//adds the mouse listener
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);		
		//creating the window
		createWindow("Attack of the Zombies");
		//starts the thread
		start();
	}
	

	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop()
	{
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//handles the tick and render calls
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        
        while(running)
        {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;
	        while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running) render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
       stop();
	}
		
	//called every tick, checks the current game state, ticks things accordingly
	private void tick()
	{
		switch(state)
		{
			case Game:
				//ticks all GameObjects
				gameObjectHandler.tick();
				// tries to Spawn a zombie on any of the 4 sides
				zombieSpawner.trySpawnZombie();
				//tries spawns ground items anywhere
				groundItemSpawner.trySpawnGroundItems();
				break;
				
			//when the STATE is equal to a menu item, menu is ticked, which then ticks the corrosponding instances of the other gui classes
			case TitleScreen:
			case EndScreen:
			case Paused:
			case Shop:
				menuHandler.tick();
				break;
		}
	}
	
	//called every tick, checks the current game state, renders things accordingly
	private void render()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		//makes text appear better
		Map<?, ?> desktopHints = 
			    (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

			if (desktopHints != null) {
			    g.setRenderingHints(desktopHints);
			}		
			
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//clears the screen of everything. Except when paused, to create a real pause feature
		if(!(state == GameState.Paused)) g.clearRect(0, 0, WIDTH, HEIGHT);
		//renders things depending on game state
		switch(state)
		{
			//renders game objects and health bar
			case Game:
				//draws game backgrounds
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, 0, WIDTH, HEIGHT);
				//renders all GameObjects
				gameObjectHandler.render(g);
				//draws the dividing lines (temperary)
				g.setColor(Color.BLACK);
				//g.drawLine(Game.WIDTH/2, 0, Game.WIDTH/2, Game.HEIGHT);
				//g.drawLine(0, Game.HEIGHT/2, Game.WIDTH, Game.HEIGHT/2);
				//renders the player's health and greande/bullet count
				playerHUD.render(g);
				//displays player money in top right corner
				g.setColor(Color.BLACK);
				g.setFont(new Font("serif", 1, 20));
				Util.drawCenteredString("Money: $" + money, WIDTH-Util.getStringWidth("Money: $" + money, g)+38, 10, g);
				break;
				
			//when the STATE is equal to a menu item, menu is rendered, which then renders the corrosponding instances of the other gui classes
			case TitleScreen:
			case EndScreen:
			case Paused:
			case Shop:
				menuHandler.render(g);
				break;
		}
	
		g.dispose();
		bs.show();
		
		//used for fullscreen
		if(KeyInputHandler.changeFullScreen)
		{
			JFrame frame = getFrame();
			GraphicsDevice fullscreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			frame.dispose();

		    if (frame.isUndecorated()) {
		      fullscreenDevice.setFullScreenWindow(null);
		      frame.setUndecorated(false);
		    } else {
		      frame.setUndecorated(true);
		      fullscreenDevice.setFullScreenWindow(frame);
		    }

		    frame.setVisible(true);
		    frame.repaint();
		    KeyInputHandler.changeFullScreen = !KeyInputHandler.changeFullScreen;
			requestFocus();
		}
	}
	
	//MAIN METHOD
	public static void main(String[] args) 
	{
		new Game();
	}
	
	//used to create the frame the game is shown on
	private void createWindow(String title)
	{
		frame = new JFrame(title);
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		frame.setPreferredSize(dim);
		frame.setMaximumSize(dim);
		frame.setMinimumSize(dim);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
	}
	
	//called when game starts or resets bc of a Play Again or restart button
	public void startGame()
	{
		gameObjectHandler.gameObjects.clear();
		gameObjectHandler.addObject(player);
		money = 0;
		player.reset();
		menuHandler.reset();
		state = GameState.Game;
		zombieSpawner.reset();
	}
	
	//when the player dies
	public void endGame()
	{
		state = GameState.EndScreen;
	}

	//getter for state
	public GameState getState() {
		return state;
	}
	
	//setter for state
	public void setState(GameState state) {
		this.state = state;
	}
	//getter for player moeny
	public int getMoney()
	{
		return money;
	}
	//used to change the player money (used in shops and in collision handler (gets money for kills))
	public void changeMoney(int amount)
	{
		money+=amount;
	}
	//getter for player
	public Player getPlayer()
	{
		return player;
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public PlayerHUD getPlayerHUD()
	{
		return playerHUD;
	}
	
	public GameObjectHandler getObjectHandler()
	{ 
		return gameObjectHandler;
	}

	public GameObjectHandler getObjectHandler(String string) 
	{
		System.out.println("Player retreives the handler");
		return gameObjectHandler;
	}
}