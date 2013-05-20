/**
 * 
 */
package engine.graphics;

/**
 * This class is the class that is used for animtions.
 * @author Pearce Keesling aka h3ckboy
 *
 */
import java.awt.*;

import java.awt.image.BufferedImage;
import java.awt.Container;
public class Animation implements Cloneable {
	//the frames for the animation
	public BufferedImage[] images;
	//the time between each frame
	int time;
	//time that has passed since last frame
	int elapsedTime = 0;
	int currentFrame;
	int direction;
	int currentDirection = 0;
	public Animation(BufferedImage[] images,int time)
	{
		this.images = images;
		this.time = time;
		currentFrame = 0;
	}
	//advance animation each frame
	public void advance(int time)
	{
		elapsedTime+=time;
		if(elapsedTime>=this.time)
		{
			elapsedTime -=this.time;
			currentFrame++;
			if(currentFrame>=images.length)
			{
				currentFrame = 0;
			}
		}
	}
	//render the animation's current frame
	public void render(Graphics g,int x, int y,Container container)
	{
		g.drawImage(images[currentFrame],x,y,container);
	}
	public void render(Graphics g,int x, int y)
	{
		g.drawImage(images[currentFrame],x,y,null);
	}
	public void render(Graphics g,int x, int y,int direction,Container container)
	{
		BufferedImage[] images = setDirection(direction,container);
		g.drawImage(images[currentFrame],x,y,container);
	}
	public BufferedImage[] setDirection(int direction, Container container)
	{
		int change = direction - currentDirection;
		//System.out.println("rotating sprite "+change*90+"from "+currentDirection);
		int d=0;
		BufferedImage[] images = this.images.clone();
		if(change>0)
		{
			for(int i = 0;i<images.length;i++)
			{
				for(d = 0;d<change;d++)
				{
					images[i] = GraphicsUtility.rotateImage(images[i], container);
				}
			}
		}else if(change<0)
		{
			for(int i = 0;i<images.length;i++)
			{
				for(d = 0;d<change+4;d++)
				{
					images[i] = GraphicsUtility.rotateImage(images[i], container);					
				}
			}
		}
		return images;
		//currentDirection = direction;
		//System.out.println(currentDirection+","+change+","+d);
	}
	public BufferedImage getCurrentFrame()
	{
		return images[currentFrame];
	}
	public int getCurrentFrameInt()
	{
		return currentFrame;
	}
	public void setCurrentFrame(int i)
	{
		currentFrame = i;
	}
}
