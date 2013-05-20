package main.states.game.actors.powerups;

import main.states.game.Stage;
import main.states.game.actors.Player;
import main.states.game.actors.PowerUp;

public class ExtraLife extends PowerUp {
	public ExtraLife(Stage stage)
	{
		super(stage);
			image = Stage.imgCache.load("data/ExtraLife.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	public void apply(Player player)
	{
		player.health++;
	}
}
