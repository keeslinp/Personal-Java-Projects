package main.states.game.actors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import engine.graphics.Animation;

import main.states.game.Actor;
import main.states.game.Stage;

public class Player extends Actor {
	Animation[] animations = new Animation[4];
	boolean caught = false;
	public Player(Stage stage) {
		super(stage);
		animated = true;
		BufferedImage[] images;
		for(int i = 0; i<4;i++)
		{
			images = new BufferedImage[2];
			images[0] = Stage.imgCache.load("data/images/actors/1_"+i+"f0.png");
			images[1] = Stage.imgCache.load("data/images/actors/1_"+i+"f1.png");
			animations[i] = new Animation(images,200);
		}
		animation = animations[1];
	}
	public void render(Graphics g)
	{
		if(caught)g.drawImage(Stage.imgCache.load("data/images/ExclamationMark.png"),(int)x+10,(int)y-30,null);
		super.render(g);
	}
	public void keyPressed(KeyEvent c) {
		//System.out.println(c.getKeyCode());
		if(!caught)
		{
		switch (c.getKeyCode()){
			case 39 : setVx(2f); setVy(0);animation = animations[2];break;
			case 37 : setVx(-2f);setVy(0);animation = animations[0];break;
			case 40 : setVy(2f); setVx(0);animation = animations[3];break;
			case 38 : setVy(-2f);setVx(0);animation = animations[1];break;
			//case 68 : debug = !debug;break;
		}
		}
	}
	public void keyReleased(KeyEvent c) {
		switch (c.getKeyCode()){
			case 39 : if(getVx() >0)setVx(0); break;
			case 37 : if(getVx() <0)setVx(0);break;
			case 40 : if(getVy() >0)setVy(0); break;
			case 38 : if(getVy() <0)setVy(0);break;
		}
	}
	public void update(int delta)
	{
		if(getVx()!=0||getVy() !=0)
		{
			animation.advance(delta);
			if(delta>80)delta = 80;
			setVx(getVx() * (delta/10));
			setVy(getVy() * (delta/10));
			if(validMove())
			{
				x+=getVx()*delta/10;
				y+=getVy()*delta/10;
			}
			setVx(getVx() / (delta/10));
			setVy(getVy() / (delta/10));
		}
	}
	public boolean validMove()
	{
		if(getVx()>0){
			if(stage.getTile((int)(x+getWidth()+getVx()),(int)(y+5))==1){
				if(stage.getTile((int)(x+getWidth()+getVx()),(int)(y+10))==0){y+=1;return validMove();}
				return false;
			}
			if(stage.getTile((int)(x+getWidth()+getVx()),(int)(y-5+getHeight()))==1){
			if(stage.getTile((int)(x+getWidth()+getVx()),(int)(y-10+getHeight()))==0){y-=1;return validMove();}
			return false;
			}
			if(stage.getTile((int)(x+getWidth()+getVx()),(int)(y+5))==3||stage.getTile((int)(x+getWidth()+getVx()),(int)(y-5+getHeight()))==3)stage.victory();
		}else if(getVx() <0)
		{
			if(stage.getTile((int)(x+getVx()),(int)(y+5))==1){
			if(stage.getTile((int)(x+getVx()),(int)(y+10))==0){y+=1;return validMove();}
			return false;}
			if(stage.getTile((int)(x+getVx()),(int)(y-5+getHeight()))==1){
			if(stage.getTile((int)(x+getVx()),(int)(y-10+getHeight()))==0){y-=1;return validMove();}
			return false;}
			
			if(stage.getTile((int)(x+getVx()),(int)(y+5))==3||stage.getTile((int)(x+getVx()),(int)(y-5+getHeight()))==3)stage.victory();
		}else if(getVy() <0)
		{
			if(stage.getTile((int)(x+5),(int)(y+getVy()))==1){
			if(stage.getTile((int)(x+10),(int)(y+getVy()))==0){x+=1;return validMove();}
			return false;}
			if(stage.getTile((int)(x-5+getWidth()),(int)(y+getVy()))==1){
			if(stage.getTile((int)(x-10+getWidth()),(int)(y+getVy()))==0){x-=1;return validMove();}
			return false;}
			
			if(stage.getTile((int)(x+5),(int)(y+getVy()))==3||stage.getTile((int)(x-5+getWidth()),(int)(y+getVy()))==3)stage.victory();
		}else if(getVy()>0)
		{
			if(stage.getTile((int)(x+5),(int)(y+getHeight()+getVy()))==1){
			if(stage.getTile((int)(x+10),(int)(y+getHeight()+getVy()))==0){x+=1;return validMove();}
			return false;}
			if(stage.getTile((int)(x-5+getWidth()),(int)(y+getHeight()+getVy()))==1){
			if(stage.getTile((int)(x-10+getWidth()),(int)(y+getHeight()+getVy()))==1){x-=1;return validMove();}
			return false;}
			
			if(stage.getTile((int)(x+5),(int)(y+getHeight()+getVy()))==3||stage.getTile((int)(x-5+getWidth()),(int)(y+getHeight()+getVy()))==3)stage.victory();
		}
		return true;
	}
	public int getWidth()
	{
		return animation.getCurrentFrame().getWidth();
	}
	public int getHeight()
	{
		return animation.getCurrentFrame().getHeight();
	}
	public void collide(Actor a)
	{
		if(a instanceof Monster)
		{
			if(direct(a))
				{
				stage.lose();
				caught = true;
				}
		}
	}
	public Rectangle getBounds()
	{
		return new Rectangle((int)x+5,(int)y+5,getWidth()-5,getHeight()-5);
	}
	public boolean direct(Actor a)
	{
		if(a.getVx()!=0)
			{
//			System.out.println(((a.getX()-x)*32)/Math.abs(a.getX()-x));
				for(int i = 0;Math.abs(i)<Math.abs(a.getX()-x);i+=((a.getX()-x)*32)/Math.abs(a.getX()-x))
				{
					if(stage.getTile((int)x+16+i, (int)(a.getY()+16))==1)return false;
				}
			}else
		if(a.getVy()!=0)
		{
				for(int i = 0;Math.abs(i)<Math.abs(a.getY()-y);i+=((a.getY()-y)*32)/Math.abs(a.getY()-y))
			{	
				if(stage.getTile((int)a.getX()+16, (int)(y+16+i))==1)return false;
			}
		}
		return true;
	}
}
