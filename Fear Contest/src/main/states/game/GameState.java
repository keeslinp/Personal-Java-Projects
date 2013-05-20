package main.states.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;

import main.states.CreditsState;
import main.states.game.actors.Player;
import main.states.game.utils.GJData;
import engine.Engine;
import engine.State;

import org.gamejolt.DataStore.DataStoreType;
import org.gamejolt.GameJoltAPI;

public class GameState extends State implements Stage,KeyListener,ActionListener,MouseListener,MouseMotionListener {
//Engine
	public static int ID;
	private JApplet applet;
	private Engine engine;
	public boolean first;
//Game
	Player player;
	ArrayList<Actor> actors;
	Level level;
//Information
	final int TotalLevels = 8;
	boolean[][] visible;
//Game Status
	boolean victory = false;
	boolean lost = false;
	boolean startFade = false;
	float fade = 0;
/**Initiation**/
	boolean paused = false;
/**GameJolt**/
    final GJData gjdata = new GJData();
    public static final int GAME_ID = 9114;
    public static final String GAME_PRIVATE_KEY = "e5b10ace25b9a33716ff15d0e5e52b95";
    GameJoltAPI api;
	public GameState(JApplet applet, Engine engine)
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
		applet.addKeyListener(this);
		if(gjdata.api.verifyUser(gjdata.name, gjdata.userToken))
		{
			try{
			gjdata.level = Integer.parseInt(gjdata.api.getDataStore(DataStoreType.USER,"level").getData());
			gjdata.deaths = Integer.parseInt(gjdata.api.getDataStore(DataStoreType.USER,"deaths").getData());
			}catch(NullPointerException e){gjdata.level = 0;gjdata.deaths = 0;}
		}
		initLevel();
		visible = new boolean[level.width][level.height];
		victory = false;
		lost = false;
		paused = false;
		first = false;
		startFade = false;
		fade = 0;
	}
	public void initLevel()
	{
		actors = new ArrayList<Actor>();
		level = new Level(gjdata.level, this);
		level.GenerateImage(applet.createImage(applet.getWidth(), applet.getHeight()));
		player = new Player(this);
		actors.add(player);
		player.x = level.StartX*32;
		player.y = level.StartY*32;
		victory = false;
		lost = false;
		paused = false;
	}
	public void setID(int i){ID = i;}
	public void ExitState()
	{
		applet.removeKeyListener(this);
		engine.restart("main.states.game.GameState", ID);
	}
/**GameLoop**/
//rendering
	public void render(Graphics g)
	{
		g.drawImage(level.LevelImage,0,0, null);
		for(int i = 0;i<actors.size();i++)
		{
			actors.get(i).render(g);
		}
		renderFog(g);
		if(victory)
		{
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
			g.drawImage(Stage.imgCache.load("data/images/Victory.png"),50,150,null);
			try{
			g.setColor(new Color(0,0,0,fade));
			}catch(Exception e){engine.enterState(CreditsState.ID);g.setColor(new Color(0,0,0,255));}
			g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
			g.setColor(Color.white);
		}
		g.drawString("Deaths: "+gjdata.deaths,10,15);
		g.setColor(Color.white);
	}
	public void renderFog(Graphics g)
	{
		for(int i = 0;i<actors.size();i++)
		{
			actors.get(i).clearVision();
		}
		g.setColor(new Color(0,0,0,100));
		for(int x = 0;x<visible.length;x++)
		{
			for(int y = 0;y<visible[0].length;y++)
			{
				if(!visible[x][y])
				{
					g.fillRect(x*32, y*32, 32, 32);
				}
			}
		}
		g.setColor(Color.white);
	}
//Updating Logic
	public void update(int delta)
	{
		if (!first&!victory&!lost&!paused){
			visible = new boolean[level.width][level.height];
			for (int i = 0;i<actors.size();i++)
			{
				actors.get(i).update(delta);
				if(actors.size()<=i)break;
				if(actors.get(i).MarkedForRemoval)actors.remove(i);
			}
			collisionDetection();
		}
		if(victory&&startFade)
		{
			fade+=.005f;
		}
	}
	public void collisionDetection()
	{
		for (int i = 0;i<actors.size();i++)
		{
			for(int j = i;j<actors.size();j++)
			{
				Actor a1 = actors.get(i);
				Actor a2 = actors.get(j);
				//Rectangle r1 = new Rectangle((int)a1.x,(int)a1.y,a1.getWidth(),a1.getHeight());
				//Rectangle r2 = new Rectangle((int)a2.x,(int)a2.y,a2.getWidth(),a2.getHeight());
				if (a1.getBounds().intersects(a2.getBounds()))
				{
					a1.collide(a2);
					a2.collide(a1);
				}
			}
		}
	}
/**ALL OVERRIDES**/
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent c) {
		player.keyPressed(c);
	}
	@Override
	public void keyReleased(KeyEvent c) {
		player.keyReleased(c);
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addActor(Actor a) {
		actors.add(a);
	}
	@Override
	public void victory() {
		if(gjdata.level>=TotalLevels)
		{
			
			victory = true;
			Thread t = new Thread(new Runnable() {
				GJData data = gjdata;
				@Override
				public void run() {
					if(data.api.verifyUser(data.name, data.userToken))
					{
						data.api.addHighscore(""+data.deaths, data.deaths);
					}else{
						data.api.addHighscore(data.name,""+data.deaths,data.deaths);
					}
				}
			});
			t.run();
		}
		else {gjdata.level++;initLevel();}
		Thread t = new Thread(new Runnable() {
			GJData data = gjdata;
			@Override
			public void run() {
				if(data.api.verifyUser(data.name, data.userToken))
				{
					data.api.setDataStore(DataStoreType.USER,"level",""+ data.level);
				}
			}
		});
		t.run();
		Timer t1 = new Timer();
		TimerTask tt = new TimerTask(){

			@Override
			public void run() {
				startFade = true;
			}
		};
		t1.schedule(tt,5000);
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return applet.getHeight();
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return applet.getWidth();
	}
	@Override
	public void lose() {
		lost = true;
		Timer t = new Timer();
		TimerTask tt = new TimerTask(){

			@Override
			public void run() {
				initLevel();
				
			}
		};
		t.schedule(tt,2000);
		Thread t2 = new Thread(new Runnable() {
			GJData data = gjdata;
			@Override
			public void run() {
				if(data.api.verifyUser(data.name, data.userToken))
				{
					try{
					data.api.setDataStore(DataStoreType.USER,"deaths",(gjdata.deaths+=1)+"");
					}catch(Exception e){
					}
				}
			}
		});
		t2.run();
	}
	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return gjdata.level;
	}
	@Override
	public ArrayList<Actor> getActors() {
		return actors;
	}
	@Override
	public int getTile(int x, int y) {
		return level.level[x/32][y/32];
	}
	public void setVisible(int x, int y)
	{
		visible[x][y] = true;
	}
}
