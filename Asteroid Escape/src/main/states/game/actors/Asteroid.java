package main.states.game.actors;

import java.awt.image.BufferedImage;

import engine.graphics.Animation;
import main.states.game.Actor;
import main.states.game.Stage;

public class Asteroid extends Actor {
	public static float vy;
	public Asteroid(Stage stage, int image) {
		super(stage);
		BufferedImage[] images = new BufferedImage[4];
		for(int i =0;i<4;i++)
		{
			images[i] = Stage.imgCache.load("data/Asteroids/"+image+"f"+i+".png");
		}
		animation = new Animation(images,250);
		animated = true;
	}
	public void collide(Actor a)
	{
		if(a instanceof Player)
		{
			remove();
		}
	}
	public void update(int delta)
	{
		if(y>stage.getApplet().getHeight())remove();
		super.vy = Asteroid.vy;
		super.update(delta);
	}
	public int getWidth()
	{
		return animation.getCurrentFrame().getWidth();
	}
	public int getHeight()
	{
		return animation.getCurrentFrame().getHeight();
	}
}
