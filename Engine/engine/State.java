package engine;

/**
 * The class all states will extend
 * 
 * @author (h3ckboy aka Pearce Keesling) 
 */
import java.awt.Graphics;

import javax.swing.JApplet;

import engine.Engine;
public class State
{
    public State()
    {
    }
    public void setID(int i){}
    public void render(Graphics g){}
    public void update(int FPS){}
    public void exitState(){}
	public void init() {}
	public void init(JApplet container, Engine engine) {}
}