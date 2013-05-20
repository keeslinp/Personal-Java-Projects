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
		//g.drawString("Fire - Space",30,90);
		g.drawString("Pause - P", 30, 110);
		g.drawString("Dodge the asteroids as your brave vessel", 50, 200);
		g.drawString("accelarates away from Enemy ships!", 50, 225);
		g.drawString("Try to catch as many power-ups as you can!", 50, 260);
		g.drawString("Press any key to return to menu",150,400);
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
