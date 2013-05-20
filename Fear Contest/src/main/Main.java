	package main;

/**
 * Main class that starts is (Applet)
 * 
 * @author (h3ckboy aka Pearce Keesling)
 */
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;

import main.states.CreditsState;
import main.states.HighScoreState;
import main.states.InstructionState;
import main.states.IntroState;
import main.states.MenuState;
import main.states.game.GameState;
import engine.Engine;
public class Main extends JApplet implements Runnable
{
	private static final long serialVersionUID = 1L;
	boolean run;
    Thread th;
    Graphics g;
    Engine engine;
    public void init()
    {
    	resize(700,500);
    	requestFocus();
    	super.setBackground(Color.BLACK);
        try{
        engine = new Engine(this);
        engine.addState(new HighScoreState(this,engine));
        engine.addState(new IntroState(this,engine));
        engine.addState(new MenuState(this,engine));
        engine.addState(new GameState(this,engine));
        engine.addState(new CreditsState(this,engine));
        engine.addState(new InstructionState(this,engine));
    }catch(Exception e){e.printStackTrace();}
        System.out.println("username: "+getParameter("gjapi_username"));
    	//engine.ShowFPS(true);
    	engine.enterState(IntroState.ID);
    }
    public void start()
    {
        run = true;
        if(th==null)
        {
            th = new Thread(this);
            th.start();
        }
    }
    public void run()
    {        
    	requestFocus();
        while(run){
            repaint();
            update();
            try{
            Thread.sleep(10);
        }catch(Exception e){}
        }
    }
    public void paint(Graphics g)
    {
        engine.render(g);
    }
    public void update()
    {
       engine.update();
    }
    public void stop()
    {
        if(th!=null)
        {
            th = null;
        }
        run = false;
        System.out.println(1);
    }
    public void destroy()
    {
        if(th!=null)
        {
            th = null;
        }
        run = false;
    }
}
