package main.states.game.actors.powerups;

import java.util.ArrayList;

import main.states.game.Actor;
import main.states.game.Stage;
import main.states.game.actors.Block;
import main.states.game.actors.Player;
import main.states.game.actors.PowerUp;

public class Repair extends PowerUp {
	public Repair (Stage stage)
	{
		super(stage);
		image = Stage.imgCache.load("data/RepairPowerUp.png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	public void apply(Player player)
	{
		ArrayList<Actor> actors = stage.getActors();
		for(int i = 0;i<actors.size();i++)
		{
			Actor a = actors.get(i);
			if(a instanceof Block)
			{
				a.MarkedForRemoval = true;
				
			}
		}
		stage.initBlocks();
	}
}
