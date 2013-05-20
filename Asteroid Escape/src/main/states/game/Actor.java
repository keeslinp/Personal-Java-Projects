package main.states.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import engine.graphics.Animation;

public class Actor {
	protected float x;
	protected float y;
	protected float vx;
	protected float vy;
	protected BufferedImage image;
	protected Stage stage;
	public boolean MarkedForRemoval;
	private int width;
	private int height;
	protected Animation animation;
	protected boolean animated = false;
	public boolean visible = true;
	public Actor(Stage stage)
	{
		this.stage = stage;
	}
	public void render(Graphics g)
	{
		if(visible)
		{
			if(!animated)g.drawImage((Image)image, (int)x, (int)y, null);
			if(animated)animation.render(g,(int)x,(int)y);
		}
	}
	public void update(int delta)
	{
		if(delta>80){delta=10;}

		x += vx*delta/10;
		y += vy*delta/10;
		if(animated)animation.advance(delta);
	}
	public void move(int i, int j){x = i;y = j;}
	public float getX(){return x;}
	public float getY(){return y;}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	public void remove()
	{
		MarkedForRemoval = true;
	}
	public void collide(Actor a)
	{}
	/**
	 * @param vy the vy to set
	 */
	public void setVy(float vy) {
		this.vy = vy;
	}
	/**
	 * @return the vy
	 */
	public float getvy() {
		return vy;
	}
	public BufferedImage getImage() {
		if(animated)return animation.getCurrentFrame();
		return image;
	}
}
