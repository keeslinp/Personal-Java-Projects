package main.states.game.actors.powerups;

import java.util.Timer;
import java.util.TimerTask;

import main.states.game.Stage;
import main.states.game.actors.Player;
import main.states.game.actors.PowerUp;

public class Shield extends PowerUp {
	public static boolean active = false;
	public static boolean second = false;
	private boolean thisSecond = false;
	final Timer t = new Timer();
	public Shield(Stage stage)
	{
		super(stage);
		image = Stage.imgCache.load("data/PowerUps/shield.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		duration = 10000;
	}
	public void apply(final Player player)
	{
		if(active)
			{
				second = true;
				thisSecond = true;
			}
		active = true;
		player.invincible = true;
		TimerTask tt = new TimerTask(){
			Player p = player;
			@Override
			public void run() {
				if(second)
					{
						t.cancel();
						second = false;
					}else{
						p.invincible = false;
						active = false;
					}
			}
		};

		t.schedule(tt, 5000);
		//blinking
		tt = new TimerTask(){
			Player p = player;
			@Override
			public void run() {
				if(second && !thisSecond)
				{
					cancel();
				}
				player.visible = !player.visible;
				if(!player.invincible)
				{
					player.visible = true;
					cancel();
				}
			}
		};
		t.schedule(tt,200,200);
	}
}
