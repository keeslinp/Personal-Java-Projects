package main.states.game.actors;

import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;

import main.states.game.Stage;

public class LoopMonster extends Monster {

	public LoopMonster(Stage stage, LinkedList<Point> p) {
		super(stage, p);
		// TODO Auto-generated constructor stub
	}
	public void update(int delta)
	{
		animation.advance(delta);
		super.update(delta);
		if((getVx() == 1&&x>=target.x*32)||(getVx() == -1&&x<=target.x*32)||(getVy() == 1&&y>=target.y*32)||(getVy() == -1&&y<=target.y*32))
		{
			if(path.hasNext())
			{
				NextPoint();
			}else{
				path = pathList.listIterator();
			}
		}
	}
	public void NextPoint()
	{
		Point p;
		p = target;
		x = p.x*32;
		y = p.y*32;
		setVx(0);
		setVy(0);
		target = path.next();
		if(target.x*32>x){setVx(1);animation = animations[2];}
		if(target.x*32<x){setVx(-1);animation = animations[0];}
		if(target.y*32>y){setVy(1);animation = animations[3];}
		if(target.y*32<y){setVy(-1);animation = animations[1];}
	}
}
