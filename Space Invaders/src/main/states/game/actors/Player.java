package main.states.game.actors;

import java.awt.event.KeyEvent;

import main.states.game.Actor;
import main.states.game.Stage;

public class Player extends Actor {
	double lastShot;
	public int delay = 1000;
	public final int startDelay = 1000;
	public int health = 3;
	public boolean powerup;
	public int duration;
	public long startPowerUp;
	public boolean invulnerable = false;
	double invulnerableStartTime;
	double invisStartTime;
	public Player(Stage stage)
	{
		super(stage);
		x = 300;
		y = 450;
		image = Stage.imgCache.load("data/Player.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	public void keyPressed(KeyEvent c) {
		//System.out.println(c.getKeyCode());
		switch (c.getKeyCode()){
			case 39 : vx = 1.5f; break;
			case 37 : vx = -1.5f;break;
			case ' ' : if(System.currentTimeMillis()-lastShot>=delay)shoot();break;
		}
	}
	public void update(int delta)
	{
		if(x>=650){vx = 0; x -= 5;}
		if(x <=0){vx = 0; x +=5;}
		if(powerup)
		{
			if(System.currentTimeMillis()-startPowerUp>=duration)
			{
				powerup = false;
				delay = startDelay;
			}
		}
		if(invulnerable)
		{
			if(System.currentTimeMillis()-invulnerableStartTime<3000)
			{
				if(System.currentTimeMillis()-invisStartTime>250)
				{
					visible = !visible;
					invisStartTime=System.currentTimeMillis();
				}
			}else{
				invulnerable = false;
				visible = true;
			}
		}
		super.update(delta);
	}
	public void keyReleased(KeyEvent c) {
		switch (c.getKeyCode()){
			case 39 : if(vx >0)vx = 0; break;
			case 37 : if(vx <0)vx = 0;break;
		}
	}
	public void shoot()
	{
		Bullet bullet = new Bullet(stage,true);
		bullet.move((int)x+getWidth()/2,(int)y);
		stage.addActor(bullet);
		lastShot = System.currentTimeMillis();
	}
	public void collide(Actor a)
	{
		if(a instanceof Bullet)
		{
			if(!((Bullet) a).playerBullet)
				{
					damage();
					stage.hit();
				}
		}
		if(a instanceof PowerUp)
		{
			((PowerUp) a).apply(this);
		}
	}

	private void damage() {
		if(!invulnerable)
		{
			health -=1;
			if(health <=0)stage.lose();
			else{
				invulnerable  = true;
				invulnerableStartTime = System.currentTimeMillis();
			}
		}
	}
}
