package main.states.game.actors;

import java.awt.event.KeyEvent;

import main.states.game.Actor;
import main.states.game.Stage;

public class Player extends Actor {

	public boolean invincible = false;
	public Player(Stage stage) {
		super(stage);
		image = Stage.imgCache.load("data/ship.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		x = 350;
		y = 450;
	}
	public void update(int delta)
	{
		if(x+getWidth()+vx>stage.getApplet().getWidth()||x+vx<0)vx = 0;
		if(y+getHeight()+vy>stage.getApplet().getHeight()||y+vy<0)vy = 0;
		super.update(delta);
	}
	public void keyPressed(KeyEvent c) {
		//System.out.println(c.getKeyCode());
		switch (c.getKeyCode()){
			case 39 : vx = 3f; break;
			case 37 : vx = -3f;break;
			case 38 : vy = -2f;break;
			case 40 : vy = 3f; break;
		}
	}
	public void keyReleased(KeyEvent c) {
		switch (c.getKeyCode()){
			case 39 : if(vx >0)vx = 0; break;
			case 37 : if(vx <0)vx = 0;break;
			case 38 : if(vy <0)vy = 1;break;
			case 40 : if(vy >0)vy = 1;break;
		}
	}
	public void collide(Actor a)
	{
		if(a instanceof Asteroid)
		{
			if(!invincible)remove();
		}
		if(a instanceof PowerUp)
		{
			((PowerUp) a).apply(this);
		}
	}
}
