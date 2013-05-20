package main.states.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.states.game.actors.Asteroid;

public class Background {
	BufferedImage image;
	int difference;
	Stage stage;
	float yOffSet;
	public Background(Stage stage)
	{
		this.stage = stage;
		image = Stage.imgCache.load("data/Background.png");
		difference = image.getHeight()-stage.getApplet().getHeight();
		yOffSet = -difference;
	}
	public void render(Graphics g)
	{
		g.drawImage(image,0,(int)yOffSet,null);
		g.drawImage(image,0,(int)(-image.getHeight()+yOffSet),null);
	}
	public void update(int delta)
	{
		if(delta>80){delta=10;}
		yOffSet+=Asteroid.vy*delta/10;
		if(yOffSet>=stage.getApplet().getHeight())yOffSet = -difference;
	}
}
