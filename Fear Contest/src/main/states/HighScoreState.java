/**
 * 
 */
package main.states;

/**
 * @author Pearce Keesling aka h3ckboy
 *
 */
import javax.swing.JApplet;

import org.gamejolt.GameJoltAPI;
import org.gamejolt.Highscore;

import main.states.game.Stage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import engine.Engine;
import engine.State;
import java.awt.event.*;
import java.util.ArrayList;
public class HighScoreState extends State implements KeyListener
{
	JApplet applet;
	Engine engine;
	public static int ID;
	boolean first = true;
	Font defaultFont;
	ArrayList<Highscore> strings = new ArrayList<Highscore>();
	int coefficient;
	final int GAME_ID = 9114;
    final String GAME_PRIVATE_KEY = "e5b10ace25b9a33716ff15d0e5e52b95";
	GameJoltAPI api;
	String name;
	String userToken;
/**Initiation Stuff*/
	public HighScoreState(JApplet applet, Engine engine)
	{
		super();
		this.applet = applet;
		this.engine = engine;
		coefficient = (applet.getHeight()-75)/10;
		name = applet.getParameter("gjapi_username");
        userToken = applet.getParameter("gjapi_token");
    	api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY, name, userToken);
	}
	public void init()
	{
		applet.addKeyListener(this);
		retrieveScores();
		first = false;
	}
	public void setID(int i){ID = i;}
/**MAIN LOOP*/
	public void render(Graphics g)
	{
		if(first){init();defaultFont = g.getFont();}
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString("Top 10:", 50, 50);
		int number = 10;
		if(number>strings.size())number = strings.size();
		for(int i = 0;i<number;i++)
		{
			g.drawString(i+1 + ". "+strings.get(i).getProperty("username") + ":",50,75+i*coefficient);
			g.drawString(""+strings.get(i).getScoreValue(),250,75+i*coefficient);
		}
		g.drawString("Press any key to return to main menu!", 200, 400);
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
    public void retrieveScores()
	{
    	strings = api.getHighscores();
		/**URL scoreboard = null;
		  BufferedReader in = null;
		try {
			scoreboard = new URL("http://keesling.dyndns.org/spaceinvaders/getScores.php?");
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
			while ((inputLine = in.readLine()) != null){
			      System.out.println(inputLine);
			strings.add(inputLine);}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/
    	
	}
}