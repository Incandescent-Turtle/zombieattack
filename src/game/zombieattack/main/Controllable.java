package game.zombieattack.main;

//Calls these methods every game tick from the KeyHandler class. Implement on game objects 
public interface Controllable {
	//called from the KeyHandler class every game tick
	public void keyPressed(int key);
	//called from the KeyHandler class every game tick
	public void keyReleased(int key);
}
