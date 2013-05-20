package main.states.game.actors.powerups;

import main.states.game.Stage;
import main.states.game.actors.Asteroid;
import main.states.game.actors.Player;
import main.states.game.actors.PowerUp;

public class SpeedUp extends PowerUp {

	public SpeedUp(Stage stage) {
		super(stage);
		image = Stage.imgCache.load("data/PowerUps/SpeedUp.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	public void apply(final Player player)
	{
		stage.boost();
	}

}
