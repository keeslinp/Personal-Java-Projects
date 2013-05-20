package main.states.game.actors;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;

import engine.graphics.Animation;

import main.states.game.Actor;
import main.states.game.Stage;

public class RepeatMonster extends Monster {
	public RepeatMonster(Stage stage, ListIterator<Point> p) {
		super(stage, p);
	}
	public void update(int delta)
	{
		animation.advance(delta);
		super.update(delta);
		if((getVx() == 1&&x>=target.x*32)||(getVx() == -1&&x<=target.x*32)||(getVy() == 1&&y>=target.y*32)||(getVy() == -1&&y<=target.y*32))
		{
			if((forward&&path.hasNext())||(!forward&&path.hasPrevious()))NextPoint();
			else{
				setVx(0);
				setVy(0);
				if(forward){forward = false;target = path.previous();}
				else{ forward = true;target = path.next();}
				NextPoint();
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
		if(forward)target = path.next();
		else target = path.previous();
		if(target.x*32>x){setVx(1);animation = animations[2];}
		if(target.x*32<x){setVx(-1);animation = animations[0];}
		if(target.y*32>y){setVy(1);animation = animations[3];}
		if(target.y*32<y){setVy(-1);animation = animations[1];}
	}
}
