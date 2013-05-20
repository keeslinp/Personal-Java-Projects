package main.states.game.actors.powerups;

import main.states.game.Stage;
import main.states.game.actors.Player;
import main.states.game.actors.PowerUp;

public class SpeedShot extends PowerUp {
	public SpeedShot(Stage stage)
	{
		super(stage);
		image = Stage.imgCache.load("data/SpeedShot.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		duration = 10000;
	}
	public void apply(Player player)
	{
		player.delay=player.startDelay/2;
		player.powerup = true;
		player.duration = duration;
		player.startPowerUp = System.currentTimeMillis();
	}
}
