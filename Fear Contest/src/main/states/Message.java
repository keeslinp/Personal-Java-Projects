package main.states;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

public class Message {
	public String value;
	public int x,y;
	public int vx,vy;
	boolean MarkedForRemoval = false;
	public float fade = 0;
	public int target;
	public int time;
	float increment;
	public Message(String text)
	{
		value = text;
	}
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.drawString(value,x,y);
		try{
			g.setColor(new Color(0,0,0,(int)fade));
		}catch(Exception e){e.printStackTrace(); fade = target;g.setColor(new Color(0,0,0,(int)fade));}
		g.fillRect(x, y-20, SwingUtilities.computeStringWidth( g.getFontMetrics(),value), 25);
		g.setColor(Color.white);
	}
	public void update(int delta)
	{
		if(delta>80)delta=80;
		y+= vy*delta/10;
		if(y<=0)MarkedForRemoval = true;
		if((increment<0&&fade>target)||(increment>0&&fade<target))
		{
			if(time ==0)fade = target;
			else fade+=(increment)*delta;
		}
	}
	public void fade(int target, int time)
	{
		this.target = target;
		this.time = time;
		increment = (target-fade)/time;
	}
}
