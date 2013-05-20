package main.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;

import engine.Engine;
import engine.State;

public class InstructionState extends State implements KeyListener {
	JApplet applet;
	Engine engine;
	public static int ID;
	boolean first = true;
	Font defaultFont;
	/**Initiation Stuff*/
	public InstructionState(JApplet applet, Engine engine)
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
		g.drawString("Instructions :",applet.getWidth()/2-100,20);
		g.drawString("Controls:",10,50);
		g.drawString("Movement - Arrow Keys",30,70);
		g.drawString("Avoid the monsters as you move around your darkest nightmares!", 10, 200);
		g.drawString("Reach the door to move on to the next area!", 10, 225);
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
