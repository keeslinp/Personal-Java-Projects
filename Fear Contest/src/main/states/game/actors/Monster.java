package main.states.game.actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;

import engine.graphics.Animation;
import main.states.game.Actor;
import main.states.game.Stage;

public class Monster extends Actor {
	LinkedList<Point> pathList;
	ListIterator<Point> path;
	Animation[] animations = new Animation[4];
	Point target;
	boolean forward = true;
	public Monster(Stage stage, ListIterator<Point> p) {
		super(stage);
		path = p;
		init();
	}
	public Monster(Stage stage, LinkedList<Point> list)
	{
		super(stage);
		pathList = list;
		path = pathList.listIterator();
		init();
	}
	public void init()
	{
		animated = true;
		BufferedImage[] images;
		for(int i = 0; i<4;i++)
		{
			images = new BufferedImage[2];
			images[0] = Stage.imgCache.load("data/images/actors/2_"+i+"f0.png");
			images[1] = Stage.imgCache.load("data/images/actors/2_"+i+"f1.png");
			animations[i] = new Animation(images,200);
		}
		animation = animations[1];
		target = path.next();
		NextPoint();
	}
	
	public void NextPoint(){}
	public Rectangle getBounds()
	{
		if(getVx()==1)return new Rectangle((int)x+5,(int)y+5,stage.getWidth(),getHeight()-5);
		else if(getVx()==-1)return new Rectangle(0,(int)y+5,(int)(x+getWidth()-5),getHeight()-5);
		else if(getVy()==1)return new Rectangle((int)x+6,(int)y+5,getWidth()-6,stage.getHeight());
		else if(getVy()==-1)return new Rectangle((int)x+6,0,getWidth()-5,(int)y+getHeight()-5);
		return super.getBounds();
	}
	public int getWidth()
	{
		return animation.getCurrentFrame().getWidth();
	}
	public int getHeight()
	{
		return animation.getCurrentFrame().getHeight();
	}
	public void clearVision()
	{
		if(getVx() == -1)
		{
			stage.setVisible((int)(x+getWidth())/32,(int)(y+getHeight()/2)/32);
		}
		if(getVy()==-1)
		{
			stage.setVisible((int)(x+getWidth()/2)/32,(int)(y+getHeight())/32);
		}
		if(getVx() !=0)
		{
			for(int i = 0;i<getBounds().width;i+=32)
			{
				if(stage.getTile((int) (x+i*getVx()), (int)y+getHeight()/2)==0)
				{
					stage.setVisible((int) (x+i*getVx())/32, (int)(y+getHeight()/2)/32);
				}else {break;}
			}
		}
		if(getVy() !=0)
		{
			for(int i = 0;i<getBounds().height;i+=32)
			{
				if(stage.getTile((int)x+getWidth()/2, (int) (y+i*getVy()))==0)
				{
					stage.setVisible((int)(x+getWidth()/2)/32, (int) (y+i*getVy())/32);
				}else {break;}
			}
		}
	}
	public void render(Graphics g)
	{
		super.render(g);
		g.setColor(new Color(0,0,0,100));
		if(getVx()>0)
		{
			g.fillRect((int)(x/32)*32,(int)y,(int)(x-((int)(x/32)*32))+5,getHeight());
		}
		if(getVx()<0)
		{
			g.fillRect((int)x+getWidth()-5,(int)y,(int)(((int)((x)/32)*32)-(x-getWidth()))+6,getHeight());
		}
		if(getVy()>0)
		{
			g.fillRect((int)x,(int)(y/32)*32,getWidth(),(int)(y-((int)(y/32)*32)));
		}
		if(getVy()<0)
		{
			g.fillRect((int)x,(int)y+getHeight(),getWidth(),(int)(((int)((y)/32)*32)-(y-getHeight()))+1);
		}
		
	}
}
