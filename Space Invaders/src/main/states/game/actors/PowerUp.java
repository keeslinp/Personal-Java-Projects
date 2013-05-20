package main.states.game.actors;

import main.states.game.Actor;
import main.states.game.Stage;

public class PowerUp extends Actor {
	protected int duration;
	public PowerUp(Stage stage)
	{
		super(stage);
		setVy(1);
	}
	public void collide(Actor a)
	{
		if(a instanceof Player)
		{
			remove();
		}
	}
	public void apply(Player player)
	{
		
	}
	public void update(int delta)
	{
		if(y>=stage.getHeight())remove();
		super.update(delta);
	}
}
