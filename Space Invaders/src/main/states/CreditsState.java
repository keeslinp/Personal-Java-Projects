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
public class CreditsState extends State implements KeyListener
{
	JApplet applet;
	Engine engine;
	public static int ID;
	boolean first = true;
	Font defaultFont;
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
	}
	public void setID(int i){ID = i;}
/**MAIN LOOP*/
	public void render(Graphics g)
	{
		if(first){init();defaultFont = g.getFont();}
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString("Credits",applet.getWidth()/2-10,20);
		g.drawString("Graphics:",10,50);
		g.drawString("CarrionTrooper",30,70);
		g.drawString("Space Invaders Sprites (Atari Original)",30,90);
		g.drawString("http://www.clker.com/", 30, 110);
		g.drawString("Original Game - Atari", 10, 150);
		g.drawString("Programmer - h3ckboy aka Pearce Keesling",10,175);
		g.drawString("Press any key to return to menu",100,400);
		g.setFont(defaultFont);
	}
	public void update(int FPS){}
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
    }
}