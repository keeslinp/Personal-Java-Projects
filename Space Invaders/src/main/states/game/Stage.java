package main.states.game;

import java.awt.image.ImageObserver;
import java.util.ArrayList;

import org.gamejolt.GameJoltAPI;

import main.states.game.utils.ImageCache;

public interface Stage extends ImageObserver {
/**variables*/
	public ImageCache imgCache = new ImageCache();
/**methods*/
	public void addActor(Actor a);
	public void victory();
	public int getHeight();
	public int getWidth();
	public void lose();
	public int getLevel();
	public void kill(int points);
	public ArrayList<Actor> getActors();
	public void initBlocks();
	public void missed();
	public void hit();
}
