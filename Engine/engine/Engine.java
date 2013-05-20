package engine;

/**
 * The class that handles different classes
 * 
 * @author (h3ckboy aka Pearce Keesling) 
 */
import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import engine.State;
public class Engine
{
    ArrayList<State> states;
    public int currentState;
    Container container;
    Graphics bufferGraphics;
    Image offscreen; 
    Dimension dim; 
    double current_frame_time, previous_frame_time;
    boolean ShowFPS = false;
    public Engine(Container container)
    {
        this.container = container;
        states = new ArrayList<State>();
        //double buffer initiation
        dim = container.getSize();
        //container.setBackground(Color.white);
        System.out.println(dim.width+","+dim.height);
        /*try {
			offscreen = ImageIO.read(getClass().getClassLoader().getResource("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        offscreen = container.createImage(dim.width,dim.height);
        System.out.println(GraphicsEnvironment.isHeadless());
        bufferGraphics = offscreen.getGraphics();
        current_frame_time = 0;
    }
    public void addState(State state)
    {
        state.setID(states.size());
        System.out.println("initiating state #"+states.size());
        states.add(state);
    }
    public State getState(int i)
    {
        return states.get(i);
    }
    public void enterState(int i)
    {
        states.get(currentState).exitState();
        currentState=i;
        states.get(currentState).init();
    }
    public void render(Graphics g)
    {
        bufferGraphics.clearRect(0,0,container.getWidth(),container.getHeight());
        states.get(currentState).render((Graphics)bufferGraphics);
        if(ShowFPS)bufferGraphics.drawString("FPS: "+getFPS(), 5, 490);
        g.drawImage(offscreen,0,0,container);
    }
    public void update()
    {
    	previous_frame_time = current_frame_time;
    	current_frame_time = System.currentTimeMillis();
        states.get(currentState).update((int) (current_frame_time-previous_frame_time));
    }
    public int getFPS()
    {
    	return (int)(1000/(current_frame_time-previous_frame_time));
    }
    public void ShowFPS(boolean fps){ShowFPS = fps;}
    public void restart(String classname, int ID)
    {
    	//State[] states2 = (State[])states.toArray();
    	states.remove(ID);
    	try {
			states.add(ID,(State)Class.forName(classname).getConstructor(JApplet.class,Engine.class).newInstance(new Object[]{(JApplet)container,this}));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//states = (ArrayList<State>)java.util.Arrays.asList(states2);
    	//states.get(ID).init((JApplet)container,this);
    }
}
