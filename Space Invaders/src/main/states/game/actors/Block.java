package main.states.game.actors;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.states.game.Actor;
import main.states.game.Stage;

public class Block extends Actor {
	int health = 5;
	//graphics for the image
	Graphics IG;
	static final int color = -1048576;
	static final int color2 = -16777216;
	public Block (Stage stage)
	{
		super(stage);
			try {
				image = ImageIO.read(getClass().getClassLoader().getResource("data/Block.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		//getting image graphics
		IG = image.getGraphics();
		//IG.clearRect(0,0,100,100);
	}
	public void update(int delta)
	{
	}
	public void render(Graphics g)
	{
		super.render(g);
		//g.drawString(health+"",(int)x+getWidth()/2,(int)y+getHeight()/2);
	}
	public void collide(Actor a)
	{
		if(a instanceof Bullet)
		{
			//health--;
			//if (health <= 0)remove();
			//a.remove();
			int coefficient = (int)((a.getvy()/Math.abs(a.getvy())));
			int c = 0;
			try{
				if(coefficient==1)c = image.getRGB((int) (a.getX()-getX()),(int) (a.getY()+a.getHeight()-getY()));
				if(coefficient==-1)c = image.getRGB((int) (a.getX()-getX()),(int) (a.getY()-getY()));
			}catch(ArrayIndexOutOfBoundsException e){}
			if(c==color){
				a.remove();
				IG.setColor(Color.BLACK);
				IG.fillOval((int) ((a.getX()-a.getWidth()/2)-getX()), (int) ((a.getY()+coefficient*(a.getHeight()-1))-getY())-(5*coefficient), a.getWidth()*3, a.getHeight());//coefficient*a.getHeight());
			}
		}
	}
}
