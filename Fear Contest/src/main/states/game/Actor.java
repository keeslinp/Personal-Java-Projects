package main.states.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.graphics.Animation;

public class Actor {
	protected float x;
	protected float y;
	private float vx;
	private float vy;
	protected BufferedImage image;
	protected Stage stage;
	public boolean MarkedForRemoval;
	private int width;
	private int height;
	protected Animation animation;
	protected boolean animated = false;
	protected boolean visible = true;
	protected static boolean debug = false;
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
			if(debug)
				{
				g.setColor(Color.YELLOW);
				g.drawRect(getBounds().x,getBounds().y,getBounds().width,getBounds().height);
				g.setColor(Color.white);
				}
		}
	}
	public void update(int delta)
	{
		if(delta>80){delta=10;}
		x += getVx()*delta/10;
		y += getVy()*delta/10;
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
	public Rectangle getBounds()
	{
		return new Rectangle((int)x,(int)y,getWidth(),getHeight());
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
		return getVy();
	}
	public float getVy() {
		return vy;
	}
	public float getVx() {
		return vx;
	}
	public void setVx(float vx) {
		this.vx = vx;
	}
	public void clearVision() {
	}
}
