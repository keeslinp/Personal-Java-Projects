package main.states.game;


import java.util.ArrayList;

//import org.gamejolt.GameJoltAPI;

import main.states.game.utils.ImageCache;

public interface Stage {
/**variables*/
	public ImageCache imgCache = new ImageCache();
/**methods*/
	public void addActor(Actor a);
	public void victory();
	public int getHeight();
	public int getWidth();
	public void lose();
	public int getLevel();
	public ArrayList<Actor> getActors();
	public int getTile(int x, int y);
	public void setVisible(int x,int y);
}
