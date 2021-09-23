package game.zombieattack.main.handlers;

import java.awt.Point; 
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import game.zombieattack.main.Controllable;
import game.zombieattack.main.Game;
import game.zombieattack.main.Game.GameState;
import game.zombieattack.main.GameObject;
import game.zombieattack.main.objects.zombies.BossZombie;

//used to detect key strokes
public class KeyInputHandler extends KeyAdapter {
	
	private GameObjectHandler handler;
	private Game game;
	
	public static boolean changeFullScreen = false;
	
	public KeyInputHandler(Game game, GameObjectHandler handler)
	{
		this.handler = handler;
		this.game  = game;
	}
		
	//extends from KeyAdapter
	@Override
	public void keyPressed(KeyEvent e)
	{
		//iterates through all game objects and checks if they implement Controllable, if so it sends the key that is pressed to said object
		//used in the player class for WASD
		for(GameObject object : handler.gameObjects)
		{
			if(object instanceof Controllable)((Controllable)object).keyPressed(e.getKeyCode());
		}
		//quick exit, pause, and shop
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE:
				System.exit(1);
				break;
				
			case KeyEvent.VK_P:
				if(game.getState() == GameState.Game || game.getState() == GameState.Paused) game.setState(game.getState() == GameState.Game ? GameState.Paused : GameState.Game);
				break;
				
			case KeyEvent.VK_M:
				if(game.getState() == GameState.Game || game.getState() == GameState.Shop) game.setState(game.getState() == GameState.Game ? GameState.Shop : GameState.Game);
				break;
				
			case KeyEvent.VK_N:
				Random rand = new Random();
				handler.addObject(new BossZombie(new Point(rand.nextInt(Game.WIDTH), rand.nextInt(Game.HEIGHT)), handler.getPlayer(), game, handler));
				break;
				
			case KeyEvent.VK_F:
				game.changeMoney(14140);
				break;
			case KeyEvent.VK_F11:
				changeFullScreen = !changeFullScreen;
				
		}
	}
	
	//extends from KeyAdapter
	@Override
	public void keyReleased(KeyEvent e) 
	{
		//iterates through all game objects and checks if they implement Controllable, if so it sends the key that is released to said object
		//used in the player class for WASD
		for(GameObject object : handler.gameObjects)
		{
			if(object instanceof Controllable)((Controllable)object).keyReleased(e.getKeyCode());
		}
	}
}
