package main.states.game.actors;

import main.states.game.Actor;
import main.states.game.Stage;

public class Bullet extends Actor {
	boolean playerBullet;
	public Bullet(Stage stage,boolean playerBullet)
	{
		super(stage);
		this.playerBullet = playerBullet;
			if(playerBullet){image = Stage.imgCache.load("data/Bullet.png");setVy(-5);}
			if(!playerBullet){image = Stage.imgCache.load("data/Bullet2.png");setVy(2);}
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	public void update(int delta)
	{
		if (y <=0||y>=stage.getHeight()) 
			{
				remove();
				if(playerBullet)stage.missed();
			}
		super.update(delta);
	}
	public void collide(Actor a)
	{
		if(a instanceof Enemy&playerBullet)
		{
			remove();
		}
		if(a instanceof Player&!playerBullet)
		{
			if(!((Player)a).invulnerable)remove();
		}
		/*if(a instanceof Block)
		{
			remove();
		}*/
	}
}
