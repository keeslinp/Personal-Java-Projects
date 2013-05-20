package main.states;

/**
 * The state with the menu
 * 
 * @author (h3ckboy Pearce Keesling) 
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.gamejolt.GameJoltAPI;
import org.gamejolt.DataStore.DataStoreType;

import java.util.ArrayList;
/**My Game library imports*/
import engine.Engine;
import engine.State;
import engine.GUI.Button;
import engine.GUI.ImageButton;
import engine.GUI.Panel;
import engine.GUI.Component;
import main.states.game.GameState;
import main.states.game.Stage;
public class MenuState extends State implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{
    JApplet applet;
    Container container;
    Engine engine;
    public static int ID;
    boolean first = true;
    boolean firstAction = true;
    final Panel[] panels = new Panel[2];
	private BufferedImage title;
	boolean IsContinueAvailable = true;
	final int i = 1;
    public MenuState(final JApplet applet, Engine engine)
    {
        super();
        this.applet = applet;
        this.engine = engine;
        container = applet.getContentPane();
			title = Stage.imgCache.load("data/images/title.png");
			IsContinueAvailable = true;
		Thread t = new Thread(new Runnable() {
			Panel[] ps = panels;
			JApplet a = applet;
				@Override
				public void run() {
					String name = applet.getParameter("gjapi_username");
			        String userToken = applet.getParameter("gjapi_token");
			        GameJoltAPI api = new GameJoltAPI(GameState.GAME_ID, GameState.GAME_PRIVATE_KEY, name, userToken);
			        if(api.verifyUser(name, userToken))
					{
						try{
						api.getDataStore(DataStoreType.USER,"level").getData();
						}catch(NullPointerException e){IsContinueAvailable = false;}
					}
				}
				});
    }
    /**
	 * @param applet2
	 * @param engine2
	 */
    public BufferedImage resizeImage(BufferedImage img, int width,int height)
    {
    	BufferedImage resizedImage = new BufferedImage(width, height, img.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(img, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), null);
        g.dispose();
        return resizedImage;
    }
	public void init()
    {
        System.out.println("entering menu state");
//panel 0
        ArrayList<Component> components = new ArrayList<Component>();
        //play component
        Component component = new ImageButton(new Point(250,200), "Play", applet, Stage.imgCache.load("data/images/play.png"), resizeImage(Stage.imgCache.load("data/images/play.png"),150,50));
        component.addActionListener(this);
        components.add((Component)component);
        //instructions component
        component = new ImageButton(new Point(250,260),"Instructions", applet,Stage.imgCache.load("data/images/instructions.png"), resizeImage(Stage.imgCache.load("data/images/instructions.png"),390,50));
        component.addActionListener(this);
        components.add(component);
        //Credits component
        component = new ImageButton(new Point(250,320),"Credits", applet,Stage.imgCache.load("data/images/credits.png"), resizeImage(Stage.imgCache.load("data/images/credits.png"),225,50));
        component.addActionListener(this);
        components.add(component);
        /*//HighScore
        component = new Button(new Rectangle(275,380,100,50),"High-Scores", applet);
        component.addActionListener(this);
        components.add(component);*/
        //making the panel to place them in
        try{
        panels[1] = new Panel(new Rectangle(0, 0, applet.getWidth(),applet.getHeight()),components);
    }catch(Exception e){e.printStackTrace();}
//panel 1
        components = new ArrayList<Component>();
        //new game
        component = new ImageButton(new Point(250,200), "New Game", applet, Stage.imgCache.load("data/images/New Game.png"), resizeImage(Stage.imgCache.load("data/images/New Game.png"),310,50));
        component.addActionListener(this);
        components.add((Component)component);
        //continue
        if(IsContinueAvailable)
        {
        component = new ImageButton(new Point(250,260), "Continue", applet, Stage.imgCache.load("data/images/Continue.png"), resizeImage(Stage.imgCache.load("data/images/Continue.png"),250,50));
        component.addActionListener(this);
        components.add((Component)component);}
        try{
            panels[0] = new Panel(new Rectangle(0, 0, applet.getWidth(),applet.getHeight()),components);
        }catch(Exception e){e.printStackTrace();}
        panels[0].setActive(false);
        applet.addMouseListener(this);
        applet.addMouseMotionListener(this);
        applet.addKeyListener(this);
        first = false;
    }
    public void setID(int i){ID = i;}
/**MAIN LOOP STUFF*/
//rendering stuff
    public void render(Graphics g){
        g.setFont(new Font(Font.DIALOG, 0, 15));
        g.setColor(Color.white);
        renderPanels(g);
        g.drawImage(title, applet.getWidth()/2-title.getWidth()/2, 10, null);
        //System.out.println(container.getComponentAt(200,150).getName());
    }
    public void renderPanels(Graphics g)
    {
        for(int i = 0; i<panels.length;i++)
        {
            panels[i].render(g);
        }
    }
    public void update(int FPS)
    {
    }
    public void exitState()
    {
        first = true;
        applet.removeMouseListener(this);
        applet.removeMouseMotionListener(this);
        applet.removeKeyListener(this);
        //engine.restart("main.states.MenuState", ID);
    }
/**GUI ACTION PERFORMED*/
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("there was an action performed : "+e.getActionCommand());
        if(e.getActionCommand().equals("Play"))
        {
        	panels[1].setActive(false);
        	panels[0].setActive(true);
        }
        if(e.getActionCommand().equals("Instructions"))
        {
        	engine.enterState(InstructionState.ID);
        }
        if(e.getActionCommand().equals("Credits"))
        {
        	engine.enterState(CreditsState.ID);
        }
        if(e.getActionCommand().equals("High-Scores"))
       	{ 	
        	//engine.enterState(HighScoreState.ID);
  		}
        if(e.getActionCommand().equals("New Game"))
        {
			String name = applet.getParameter("gjapi_username");
	        String userToken = applet.getParameter("gjapi_token");
	        GameJoltAPI api = new GameJoltAPI(GameState.GAME_ID, GameState.GAME_PRIVATE_KEY, name, userToken);
	        if(api.verifyUser(name, userToken))
	        {
				api.removeDataStore(DataStoreType.USER,"level");
				api.removeDataStore(DataStoreType.USER,"deaths");
			}
			engine.enterState(GameState.ID);
        }
        if(e.getActionCommand().equals("Continue"))
        {
        	engine.enterState(GameState.ID);
        }
    }
    /**Mouse Listening Stuff*/
    public void mousePressed(MouseEvent e) {
    	applet.requestFocus();
    	if(firstAction)
    	{
       for(int i = 0; i<panels.length;i++)
        {
            panels[i].mousePressed(e);
        }
    	}
    	firstAction = false;
    }

    public void mouseReleased(MouseEvent e) {
    	firstAction = true;
       for(int i = 0; i<panels.length;i++)
        {
            panels[i].mouseReleased(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
       for(int i = 0; i<panels.length;i++)
        {
            panels[i].mouseEntered(e);
        }
    }

    public void mouseExited(MouseEvent e) {
       for(int i = 0; i<panels.length;i++)
        {
            panels[i].mouseEntered(e);
        }
    }

    public void mouseClicked(MouseEvent e) {
       for(int i = 0; i<panels.length;i++)
        {
            panels[i].mouseClicked(e);
        }
    }
    
    public void mouseMoved(MouseEvent e)
    {
        for(int i = 0; i<panels.length;i++)
        {
            panels[i].mouseMoved(e);
        }
    }
    
    public void mouseDragged(MouseEvent e)
    {
        for(int i = 0; i<panels.length;i++)
        {
            panels[i].mouseDragged(e);
        }
    }
    void saySomething(String eventDescription, MouseEvent e) {
        //System.out.println(eventDescription + " detected on "
        //                + e.getComponent().getClass().getName());
    }
	@Override
	public void keyPressed(KeyEvent e) {
		for(int i = 0; i<panels.length;i++)
        {
            panels[i].keyPressed(e);
        }
	}
	@Override
	public void keyReleased(KeyEvent e) {
		for(int i = 0; i<panels.length;i++)
        {
            panels[i].keyReleased(e);
        }
	}
	@Override
	public void keyTyped(KeyEvent e) {
		for(int i = 0; i<panels.length;i++)
        {
            panels[i].keyTyped(e);
        }
	}
}