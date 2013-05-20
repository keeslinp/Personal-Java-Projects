package main.states;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;

import main.states.game.GameState;

import engine.Engine;
import engine.State;

public class IntroState extends State implements KeyListener, MouseListener {
	JApplet applet;
	Engine engine;
	public static int ID;
	public void setID(int i){ID = i;}
	ArrayList<Message> messages = new ArrayList<Message>();
	Timer t = new Timer();
	public IntroState(JApplet applet, Engine engine)
	{
		super();
		this.applet = applet;
		this.engine = engine;
	}
	public void init()
	{
		applet.addKeyListener(this);
		Message m = new Message("\"monsters don't exist! It will all be okay,");
		messages.add(m);
		m.y = 100;
		m.x = 35;
		m.fade = 255;
		m.fade(0,2000);
		TimerTask tt = new TimerTask(){

			@Override
			public void run() {
				messages.get(0).fade(255,2000);
				messages.get(1).fade(255,2000);
			}
		};
		t.schedule(tt,4000);
		m = new Message("your father and I are in the other room.\"");
		m.y = 125;
		m.x = 35;
		m.fade = 255;
		m.fade(0,2000);
		messages.add(m);
		m = new Message("Only, no one can protect you from the monsters in your dreams.");
		m.y = 150;
		m.x = 35;
		m.fade = 255;
		tt = new TimerTask(){
			@Override
			public void run() {
				messages.get(2).fade(0,2000);
				Timer t = new Timer();
				TimerTask tt = new TimerTask(){
					@Override
					public void run() {
						messages.get(2).fade(255,2000);
					}
				};
				t.schedule(tt, 3000);
			}
		};
		t.schedule(tt,4000);
		messages.add(m);
		m = new Message("Except you.");
		m.y = 175;
		m.x = 35;
		m.fade = 255;
		tt = new TimerTask(){
			@Override
			public void run() {
				messages.get(3).fade(0,2000);
				Timer t = new Timer();
				TimerTask tt = new TimerTask(){
					@Override
					public void run() {
						messages.get(3).fade(255,3000);
					}
				};
				t.schedule(tt, 2000);
			}
		};
		t.schedule(tt,7000);
		messages.add(m);
		tt = new TimerTask(){
			@Override
			public void run() {
				engine.enterState(MenuState.ID);
			}
		};
		t.schedule(tt,12000);
	}
	public void update(int delta)
	{
		for(int i = 0;i<messages.size();i++){
			messages.get(i).update(delta);
		}
	}
	public void render(Graphics g)
	{
		g.setFont(new Font("SansSerif",Font.BOLD,20));
		for(int i = 0;i<messages.size();i++){
			messages.get(i).render(g);
		}
	}
	public void exitState()
    {
    	applet.removeKeyListener(this);
    	if(messages.size()>0)t.cancel();
    }
	@Override
	public void keyPressed(KeyEvent arg0) {
		engine.enterState(MenuState.ID);
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		applet.requestFocus();
		
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
		applet.requestFocus();
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
