package main.states.game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import org.gamejolt.GameJoltAPI;

import main.states.MenuState;
import main.states.game.actors.Asteroid;
import main.states.game.actors.Player;
import main.states.game.actors.powerups.Shield;
import main.states.game.actors.powerups.SpeedUp;
import main.states.game.util.GJData;
import main.states.game.util.ImageCache;

import engine.Engine;
import engine.State;

public class GameState extends State implements Stage, MouseListener, MouseMotionListener, KeyListener {
	JApplet applet;
    Container container;
    Engine engine;
    public static int ID;
    boolean first = true;
    ArrayList<Actor> actors;
    Player player;
    double LastAsteroidTime;
    int AsteroidTime = 500;
    Random r = new Random();
    Background background;
/**Game Status**/
    boolean lost = false;
    boolean paused = false;
/**GameJolt**/
    final GJData gjdata = new GJData();
    final int GAME_ID = 8524;
    final String GAME_PRIVATE_KEY = "5593f3b330edd6e7d1e65cbe6691d21b";
    GameJoltAPI api;
/**Initiation**/
	public GameState (JApplet applet,Engine engine)
	{
		super();
        this.applet = applet;
        this.engine = engine;
        first = true;
        gjdata.name = applet.getParameter("gjapi_username");
        gjdata.userToken = applet.getParameter("gjapi_token");
        gjdata.api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY, gjdata.name, gjdata.userToken);
	}
	public void init()
	{
		background = new Background(this);
        actors = new ArrayList<Actor>();
		player = new Player(this);
		actors.add(player);
		try{
			applet.removeKeyListener(this);
			}catch(Exception e){}
			applet.addKeyListener(this);
		addAsteroid();
		lost = false;
		first = false;
	}
/**Main Loop**/
	/**Logic**/
	public void update(int delta)
	{
		if(first){}
		else if(lost){
		}else if(paused){
		}else{
			for(int i = 0;i<actors.size();i++)
			{
				Actor a = actors.get(i);
				if(a.MarkedForRemoval)
				{
					if(player.MarkedForRemoval)lose();
					if(!lost)actors.remove(i);
				}else
				{
					a.update(delta);
				}
			}
			collision();
			if(!lost)AsteroidGeneration();
			background.update(delta);
		}
	}
	public void collision()
	{
		for (int i = 0;i<actors.size();i++)
		{
			for(int j = i;j<actors.size();j++)
			{
				Actor a1 = actors.get(i);
				Actor a2 = actors.get(j);
				Rectangle r1 = new Rectangle((int)a1.x,(int)a1.y,a1.getWidth(),a1.getHeight());
				Rectangle r2 = new Rectangle((int)a2.x,(int)a2.y,a2.getWidth(),a2.getHeight());
				if (r1.intersects(r2))
				{
					if(ImageCache.isPixelCollide(a1.x, a1.y, a1.getImage(), a2.x, a2.y, a2.getImage()))
					{
						a1.collide(a2);
						a2.collide(a1);
					}
				}
			}
		}
	}
	/**Rendering**/
	public void render(Graphics g)
	{
		Font defaultFont = g.getFont();
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		background.render(g);
		for(int i = 0;i<actors.size();i++)
		{
			Actor a = actors.get(i);
			a.render(g);
		}
		if(lost)
		{
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
			g.setColor(Color.RED);
			//
			String text = "Your Ship is Destroyed";
			int length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2);
			//
			text = "You Travelled " +gjdata.score + " Meters";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2+25);
			//
			text = "Press Enter to Return to Menu";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2+150);
			//
			text = "Feel Free to Check the HighScores Below!";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2+50);
		}
			if(paused){
				g.setColor(new Color(0,0,0,100));
				g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
				g.setColor(Color.white);	
				String text = "Game Paused";
				int length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
				g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2);
			}
			g.setColor(Color.white);
			g.drawString("Distance Traveled: "+gjdata.score+" meters",10,20);
			
		g.setFont(defaultFont);
	}
/**Entity related code**/
	public void addAsteroid()
	{
		Asteroid asteroid = new Asteroid(this,r.nextInt(3));
		Asteroid.vy = 1+(500-AsteroidTime)/50f;
		asteroid.x = r.nextInt(applet.getWidth()-asteroid.getWidth());
		actors.add(asteroid);
	}
	public void AsteroidGeneration()
	{
		if(System.currentTimeMillis()-LastAsteroidTime >AsteroidTime)
		{
			addAsteroid();
			LastAsteroidTime = System.currentTimeMillis();
			AsteroidTime -=1;
			gjdata.score+=500;
			if(r.nextInt(50)==1)
			{
				Shield s = new Shield(this);
				s.vy = 1;
				s.x = r.nextInt(applet.getWidth()-s.image.getWidth());
				actors.add(s);
			}else if(r.nextInt(50)==1){
				SpeedUp su = new SpeedUp(this);
				su.vy = 1;
				su.x = r.nextInt(applet.getWidth()-su.image.getWidth());
				actors.add(su);
			}
		}
	}
/**Events**/
	public void lose()
	{
		if(!lost)
		{
			lost = true;
			Thread t = new Thread(new Runnable() {
				GJData data = gjdata;
				@Override
				public void run() {
					if(data.api.verifyUser(data.name, data.userToken))
					{
						data.api.addHighscore(""+data.score, data.score);
					}else{
						data.api.addHighscore(data.name,""+data.score,data.score);
					}
				}
			});
			t.run();
		}
	}
/**Implemented Methods**/
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(lost&&arg0.getKeyCode()==10)
		{
			engine.enterState(MenuState.ID);
			engine.restart("main.states.game.GameState", ID);
			lost = false;
		}else{
			if(arg0.getKeyChar()=='p')
			{
				paused = !paused;
			}else{
				player.keyPressed(arg0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		player.keyReleased(arg0);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setID(int i){ID = i;}
	@Override
	public JApplet getApplet() {
		return applet;
	}
	@Override
	public void boost()
	{
		AsteroidTime-=50;
		Asteroid.vy = 1+(500-AsteroidTime)/50f;
	}
	public void exitState()
    {
        first = true;
        applet.removeMouseListener(this);
        applet.removeMouseMotionListener(this);
        applet.removeKeyListener(this);
    }
}
