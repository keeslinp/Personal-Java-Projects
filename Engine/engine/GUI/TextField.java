package engine.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class TextField extends Component implements KeyListener {
	Color TextColor = Color.black;
	private boolean selected;
	double cursorTimer;
	double cursorTimerOn;
	boolean compute;
	private int length;
	public TextField(Rectangle r,Container applet, String n)
	{
		this.rectangle = r;
		this.applet = applet;
		setActionName(n);
	}
	public void activating(Panel p)
    {
  	  this.panel = p;
    }
	public void setActionName(String a){ActionName = a;}
    public String getActionName(){return ActionName;}
    public String getText() {
        return text;
      }
    public void setText(String t)
    {
    	text = t;
    }
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Button #"+e.getButton()+"was pressed"); public void mouseClicked(MouseEvent e) {
        if(rectangle.contains(e.getX(),e.getY())&&e.getButton()==1)
        {
        	selected = true;
        	cursorTimer = System.currentTimeMillis();
        }else selected = false;
    }
	@Override
	public void keyPressed(KeyEvent e) {
		if(selected)actionListener.actionPerformed(new ActionEvent(this,0,ActionName+e.getKeyCode()));
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent c) {
		if(selected)
		{
			if(c.getKeyChar()==''){
				text = text.substring(0,text.length()-1);
			}else{
			text += c.getKeyChar();
			}
			compute = true;
		}
	}
	public void paint(Graphics g)
	{
		if(compute)length = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );  
		g.setColor(Color.white);
		g.fillRect((int)rectangle.getX(),(int)rectangle.getY(),(int)rectangle.getWidth(),(int)rectangle.getHeight());
		g.setColor(Color.black);
		g.drawRect((int)rectangle.getX(),(int)rectangle.getY(),(int)rectangle.getWidth(),(int)rectangle.getHeight());
		g.drawString(text,(int)rectangle.getX()+2,(int)rectangle.getY()+(int)rectangle.getHeight()/2+10);
		if(selected)
		{
			if(System.currentTimeMillis()-cursorTimerOn<=500)
			{
				g.drawLine((int)rectangle.getX()+length+2, (int)rectangle.getY()+4,(int)rectangle.getX()+length+2 , (int) ((int)rectangle.getY()+rectangle.getHeight())-4);
			}else
			if(System.currentTimeMillis()-cursorTimer>=1000)
			{
				g.drawLine((int)rectangle.getX()+length+2, (int)rectangle.getY()+4,(int)rectangle.getX()+length , (int) ((int)rectangle.getY()+rectangle.getHeight())-4);
				cursorTimer = System.currentTimeMillis();
				cursorTimerOn = System.currentTimeMillis();
			}
		}
	}
}
