package engine.GUI;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JApplet;

public class Component implements KeyListener, MouseListener, MouseMotionListener {
	Panel panel;
	String ActionName;
	boolean active = true;
  Container applet; 
  protected String text = "";
  protected Rectangle rectangle;
  protected ActionListener actionListener;
	public void setActive(boolean i){active = i;}
    public void activating(Panel p)
    {
  	  this.panel = p;
    }
    public String getText()
    {
    	return text;
    }
	public void mouseClicked(MouseEvent e) {
	  }

	  public void mousePressed(MouseEvent e) {
	  }

	  public void mouseReleased(MouseEvent e) {
	  }

	  public void mouseExited(MouseEvent e) {
	  }

	  public void mouseEntered(MouseEvent e) {
	  }
	  public void paint(Graphics g)
	  {}
	  public Rectangle getRectangle()
	  {
	      return rectangle;
	    }
	  public void keyTyped(KeyEvent c) {
		}
	  public void mouseDragged(MouseEvent e) {
	  }
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void addActionListener(ActionListener a)
	  {
	      this.actionListener = a;
	    }
}
