package main.states.game;

import java.awt.Color;
import java.awt.Font;
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
import java.security.AccessControlException;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import org.gamejolt.DataStore;
import org.gamejolt.GameJoltAPI;
import org.gamejolt.Trophy;
import org.gamejolt.DataStore.DataStoreType;

import main.states.HighScoreState;
import main.states.MenuState;
import main.states.game.actors.Block;
import main.states.game.actors.Enemy;
import main.states.game.actors.Player;
import main.states.game.utils.SubmitThread;
import engine.Engine;
import engine.State;
import engine.GUI.Button;
import engine.GUI.Component;
import engine.GUI.Panel;
import engine.GUI.TextField;

public class GameState extends State implements Stage,KeyListener,ActionListener,MouseListener,MouseMotionListener {
	public static int ID;
	private JApplet applet;
	private Engine engine;
	public boolean first;
	Player player;
	ArrayList<Actor> actors = new ArrayList<Actor>();
	private boolean victory = false;
	private boolean lost = false;
	double victoryTime;
	private int level = 1;
	private Font defaultFont;
	int score = 0;
	private boolean paused;
	Panel[] panels = new Panel[0];
	String name = "UNKNOWN";
	private String userToken;
	private boolean firstClick = true;
	final int GAME_ID = 6664;
	int enemiesSlain;
    final String GAME_PRIVATE_KEY = "9fc01ca745379b972d2f83fff5df3007";
	GameJoltAPI api;// = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY);
	
/**Trophy Related variables**/
	ArrayList<String> trophies = new ArrayList<String>();
	boolean NoMiss = true;
	boolean NoHit = true;
	public GameState(JApplet applet, Engine engine)
    {
        super();
        this.applet = applet;
        this.engine = engine;
        //applet.getContentPane();
        first = true;
        try{
        	name = applet.getParameter("gjapi_username");
            userToken = applet.getParameter("gjapi_token");
        	api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY, name, userToken);
        }catch(Exception e){
        	System.out.println(":'(");
        	System.out.println(name);
        	System.out.println(userToken);
        }
    }
	public void init()
	{
		System.out.println("entering game state");
		if(level==1)
			{
			player = new Player(this);
			actors.add(player);
			}
		try{
		applet.removeKeyListener(this);
		}catch(Exception e){}
		Enemy.enemies = 0;
		applet.addKeyListener(this);
		initEnemies();
		if(level==1)initBlocks();
		victory = false;
		lost = false;
		paused = false;
		//trophy related
		NoMiss = true;
		NoHit = true;
		enemiesSlain = 0;
		//initGui();
		first = false;
	}
	public void initGui()
	{
		/**ArrayList<Component> components = new ArrayList<Component>();
        //submit component
        Component component = new Button(new Rectangle(275,250,100,50), "Submit", applet);
        component.addActionListener(this);
        components.add((Component)component);
        //text field for name
        component = (Component)new TextField(new Rectangle(275,150,100,25), applet, "");
        components.add(component);
        //text field for token
        component = (Component)new TextField(new Rectangle(275,200,100,25), applet);
        components.add(component);
        //making the panel to place them in
        try{
        panels[0] = new Panel(new Rectangle(0, 0, applet.getWidth(),applet.getHeight()),components);
        panels[0].setActive(false);
        panels[0].setBackground(Color.BLACK);
    }catch(Exception e){e.printStackTrace();}**/
        applet.addMouseListener(this);
        applet.addMouseMotionListener(this);
    }
	public void initEnemies()
	{
		Enemy enemy;
		//first enemy
			for(int i = 0;i<10;i++)
			{
				enemy = new Enemy(this,2);
				enemy.move(10+(enemy.getWidth()+20)*i, 10);
				actors.add(enemy);
			}
//		//second enemy
		for (int j = 0; j <2; j++)
			{
				for(int i = 0;i<10;i++)
				{
					enemy = new Enemy(this,1);
					enemy.move(10+(enemy.getWidth()+20)*i, 50+ (enemy.getHeight()+10)*j);
					actors.add(enemy);
				}
			}
		//third enemy
		for (int j = 0; j <2; j++)
		{
			for(int i = 0;i<10;i++)
			{
				enemy = new Enemy(this,0);
				enemy.move(10+(enemy.getWidth()+20)*i, 130+ (enemy.getHeight()+10)*j);
				actors.add(enemy);
			}
		}
		Enemy.vx = (float) (.2+level/10);
	}
	public void initBlocks()
	{
		Block block;
		int coefficient = applet.getWidth() /3;
		for(int i = 0;i<3;i++)
		{
			block = new Block(this);
			block.move(60+i*coefficient, 400);
			actors.add(block);
		}
	}
	public void render(Graphics g)
	{
		if (first){defaultFont = g.getFont();}else{
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		player.render(g);
		if(player.powerup)g.drawString("Time Remaining : "+((player.duration-(System.currentTimeMillis()-player.startPowerUp))/1000 + 1), 5, 80);
		for (int i = 0;i<actors.size();i++)
		{
			actors.get(i).render(g);
		}
		if(victory)
		{
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
			g.setColor(Color.red);
			String text = "VICTORIOUS!";
			int length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2);
			text = "Press any key to continue to the next level";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2+20);
			g.setColor(Color.yellow);
			for(int i = 0;i<trophies.size();i++)
			{
				text = trophies.get(i)+" trophy earned!";
				length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
				g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2+50 + 25*i);
			}
			g.setColor(Color.white);
		}
		if(paused)
		{
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
			g.setColor(Color.red);
			String text = "Paused...";
			int length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2);
			g.setColor(Color.white);
		}
		drawPanels(g);
		if(lost)
		{
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, applet.getWidth(), applet.getHeight());
			g.setColor(Color.RED);
			
			String text = "Sorry, you lost";
			int length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2-25);
			text = "Press Enter to Return to Menu";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2);
			text = "You can see the highscores below the game";
			//String text = "Sorry, you lost";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,applet.getWidth()/2-length/2,applet.getHeight()/2+25);
			/**text = "User Name";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,275 -length - 10, 150);
			text = "User Token";
			length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
			g.drawString(text,275 -length - 10, 200);
			**/
			g.setColor(Color.white);
		}
		g.drawString("Health : "+player.health, 5, 20);
		g.drawString("Level : "+level, 5, 40);
		g.drawString("Score : "+score,5,60);
		g.setFont(defaultFont);
		}
	}
	public void drawPanels(Graphics g)
	{
		for(int i = 0; i<panels.length;i++)
        {
            panels[i].render(g);
        }
	}
	public void update(int delta)
	{
		if (!first&!victory&!lost&!paused){//init();
			delta*=1;
			//player.update(delta);
			if(Enemy.moveDown)Enemy.vy = 20;
			for (int i = 0;i<actors.size();i++)
			{
				try{
				if(actors.get(i).MarkedForRemoval){actors.remove(i);}else{actors.get(i).update(delta);}
				if(actors.get(i).MarkedForRemoval)actors.remove(i);
				}catch(Exception e){e.printStackTrace();}
			}
			if(Enemy.moveDown&&Enemy.vy>0){Enemy.vy = 0;Enemy.moveDown= false;}
			collisionDetection();
		}else if(victory){
			//if(System.currentTimeMillis()-victoryTime>=1000){level ++;init();victory = false;}
		}else if(lost)
		{
			
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
				Rectangle r1 = new Rectangle((int)a1.x,(int)a1.y,a1.getWidth(),a1.getHeight());
				Rectangle r2 = new Rectangle((int)a2.x,(int)a2.y,a2.getWidth(),a2.getHeight());
				if (r1.intersects(r2))
				{
					a1.collide(a2);
					a2.collide(a1);
				}
			}
		}
	}
	public void setID(int i){ID = i;}
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return false;
	}
	public void keyPressed(KeyEvent c) {
		if(lost&&c.getKeyCode()==10){
			//engine.enterState(MenuState.ID);
			//engine.restart("main.states.game.GameState",ID);
			lost = false;
			submitScore();
		}else if(victory)
		{
			level ++;init();victory = false;
		}else{
			if (c.getKeyChar()=='p'){ paused = !paused;}
			player.keyPressed(c);
		}
	}
	public void keyReleased(KeyEvent c) {
		player.keyReleased(c);
	}
	public void keyTyped(KeyEvent c) {
		for(int i = 0; i<panels.length;i++)
        {
            panels[i].keyTyped(c);
        }
	}
	public void addActor(Actor a) {
		actors.add(a);
	}
	@Override
	public void victory() {
		update(0);
		victory = true;
		enemiesSlain ++;
		System.out.println("next level");
		if (api.verifyUser(name, userToken)) 
	    {
			ArrayList<Trophy> Trophies = api.getTrophies(Trophy.Achieved.FALSE);
			if(NoMiss)
				{
					if(Trophies.contains(api.getTrophy(422)))
					{
						api.achieveTrophy(422);
						SubmitThread st = new SubmitThread(name,userToken,422,api);
						new Thread(st).run();
						//trophies.add("Marksman");
					}
				}
			if(NoHit)
			{
				if(Trophies.contains(api.getTrophy(423)))
				{
					trophies.add("Survivor");
					SubmitThread st = new SubmitThread(name,userToken,423,api);
					new Thread(st).run();
					//api.achieveTrophy(423);
				}
			}
			if(NoMiss&&NoHit)
			{
				if(Trophies.contains(api.getTrophy(424)))
				{
					trophies.add("Flawless");
					//api.achieveTrophy(424);
					SubmitThread st = new SubmitThread(name,userToken,424,api);
					new Thread(st).run();
				}
			}
			/**DataStore DS = api.getDataStore(DataStoreType.USER, "Enemies Slain");
			if(DS!= null)enemiesSlain += Integer.parseInt(DS.getData());
			api.setDataStore(DataStoreType.USER, "Enemies Slain", ""+enemiesSlain);
			System.out.println("Enemies Slain : "+enemiesSlain);**/
	    }
		//player.MarkedForRemoval = true;
	}
	@Override
	public int getHeight() {
		return applet.getHeight();
	}
	public int getWidth()
	{
		return applet.getWidth();
	}
	@Override
	public void lose() {
		lost = true;
		//panels[0].setActive(true);
	}
	public void kill(int points)
	{
		score+=points;
		enemiesSlain++;
	}
	public int getLevel(){return level;}
	public void submitScore()
	{
		firstClick = false;
		SubmitThread r = new SubmitThread(name,userToken,api,score);
		new Thread(r).run();
		/*URL scoreboard = null;
		  BufferedReader in = null;
		try {
			scoreboard = new URL("http://keesling.dyndns.org/spaceinvaders/highscore.php?name="+name+"&score="+score);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in = new BufferedReader(
			        new InputStreamReader(
			        scoreboard.openStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  String inputLine;

		  try {
			while ((inputLine = in.readLine()) != null)
			      System.out.println(inputLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		engine.enterState(MenuState.ID);
		engine.restart("main.states.game.GameState",ID);
	}
	/**Mouse Listening Stuff*/
    public void mousePressed(MouseEvent e) {
       for(int i = 0; i<panels.length;i++)
        {
            panels[i].mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Submit"))
		{
			//System.out.println("cooolliiiiooo");
			name = panels[0].getComponent(1).getText();
			userToken = panels[0].getComponent(2).getText();
			if(firstClick)submitScore();
		}
	}
	@Override
	public ArrayList<Actor> getActors() {
		return actors;
	}
	@Override
	public void missed() {
		NoMiss = false;
		
	}
	@Override
	public void hit() {
		NoHit = false;
		
	}
}
