/**
 * 
 */
package main.states;

/**
 * @author Pearce Keesling aka h3ckboy
 *
 */
import javax.swing.JApplet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import engine.Engine;
import engine.State;
import java.awt.event.*;
import java.util.ArrayList;
public class CreditsState extends State implements KeyListener
{
	JApplet applet;
	Engine engine;
	public static int ID;
	boolean first = true;
	Font defaultFont;
	ArrayList<Message> credits = new ArrayList<Message>();
/**Initiation Stuff*/
	public CreditsState(JApplet applet, Engine engine)
	{
		super();
		this.applet = applet;
		this.engine = engine;
	}
	public void init()
	{
		first = false;
		applet.addKeyListener(this);
		Message cr = new Message("Credits");
		cr.x = 300;
		credits.add(cr);
		cr = new Message("Graphics");
		cr.x = 100;
		credits.add(cr);
		cr = new Message("Philipp Lenssen");
		cr.x = 175;
		credits.add(cr);
		cr = new Message("David Gurrea");
		cr.x = 175;
		credits.add(cr);
		cr = new Message("Everything Else");
		cr.x = 100;
		credits.add(cr);
		cr = new Message("Pearce Keesling aka h3ckboy");
		cr.x = 175;
		credits.add(cr);
		for(int i =0;i<credits.size();i++)
		{
			Message c = credits.get(i);
			c.y = applet.getHeight()+i*100;
			c.vy = -1;
		}
	}
	public void setID(int i){ID = i;}
/**MAIN LOOP*/
	public void render(Graphics g)
	{
		if(first){init();defaultFont = g.getFont();}
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		/*g.setColor(Color.white);
		g.drawString("Credits",applet.getWidth()/2-10,20);
		g.drawString("Graphics:",10,50);
		g.drawString("Philipp Lenssen",30,70);
		g.drawString("Programmer - Pearce Keesling aka h3ckboy",10,175);
		g.drawString("Press any key to return to menu",100,400);
		*/
		for(int i = 0;i<credits.size();i++)
		{
			credits.get(i).render(g);
		}
		g.setFont(defaultFont);
	}
	public void update(int delta){
		for(int i = 0;i<credits.size();i++)
		{
			credits.get(i).update(delta);
			if(credits.get(i).MarkedForRemoval)credits.remove(i);
		}
		if(credits.size()<=0)engine.enterState(MenuState.ID);
	}
/**Key Listening Stuff*/
	public void keyPressed(KeyEvent e)
    {
		engine.enterState(MenuState.ID);
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e){}
/**Exiting State*/
    public void exitState()
    {
    	first = true;
    	applet.removeKeyListener(this);
    	credits.clear();
    }
}