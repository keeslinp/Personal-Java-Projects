package engine.GUI;


//import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/**
 * A panel to place custom GUI on
 * 
 * @author (h3ckboy aka Pearce Keesling) 
 */
public class Panel implements MouseListener, MouseMotionListener, KeyListener
{
    ArrayList<Component> components;
    Rectangle rectangle;
    public boolean active = true;
    boolean isBackground = false;
    Color background;
    @SuppressWarnings("unchecked")
	public Panel(Rectangle r, ArrayList components2) throws ButtonOutOfPanelException
    {
        this.rectangle = r;
        this.components = (ArrayList<Component>)components2;
        if(components2!=null)checkComponents();
        //applet.addMouseListener(this);
        //applet.addMouseMotionListener(this);
    }
    public void addComponent(Component component)
    {
        components.add(component);
    }
/**Rendering*/
    //general rendering
    public void render(Graphics g)
    {
    	if(isBackground&active)
    	{
    		g.setColor(background);
    		g.fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    	}
        renderComponents(g);
    }
    //rendering components
    public void renderComponents(Graphics g)
    {
        for(int i = 0;i<components.size();i++)
        {
            if(components.get(i).active)components.get(i).paint(g);
        }
    }
    public void setBackground(Color color)
    {
    	isBackground = true;
    	background = color;
    }
/**Checking setup for errors*/
    public void checkComponents()throws ButtonOutOfPanelException
    {
        for(int i = 0;i<components.size();i++)
        {
            if(!rectangle.contains(components.get(i).getRectangle()))throw new ButtonOutOfPanelException("Cannot place a button out of the Panel");
            components.get(i).activating(this);
        }
    }
/**Mouse Related Stuff*/
    public void mousePressed(MouseEvent e) {
       for(int i = 0; i<components.size();i++)
        {
    	   if(components.get(i).active)components.get(i).mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
       for(int i = 0; i<components.size();i++)
        {
    	   if(components.get(i).active)components.get(i).mouseReleased(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
       for(int i = 0; i<components.size();i++)
        {
    	   if(components.get(i).active)components.get(i).mouseEntered(e);
        }
    }

    public void mouseExited(MouseEvent e) {
       for(int i = 0; i<components.size();i++)
        {
    	   if(components.get(i).active)components.get(i).mouseEntered(e);
        }
    }

    public void mouseClicked(MouseEvent e) {
       for(int i = 0; i<components.size();i++)
        {
    	   if(components.get(i).active)components.get(i).mouseClicked(e);
        }
    }
    
    public void mouseMoved(MouseEvent e)
    {
        for(int i = 0; i<components.size();i++)
        {
        	if(components.get(i).active)components.get(i).mouseMoved(e);
        }
    }
    
    public void mouseDragged(MouseEvent e)
    {
        for(int i = 0; i<components.size();i++)
        {
        	if(components.get(i).active)components.get(i).mouseDragged(e);
        }
    }
/**Retrieving button*/
    public Component getComponent(int i)
    {
    	return components.get(i);
    }
    public Button getButton(int i)
    {
    	return (Button)getComponent(i);
    }
/**Setting components active/inactive*/
    public void setActive(boolean a)
    {
    	this.active = a;
    	for(int i = 0; i<components.size();i++)
        {
    	   components.get(i).setActive(a);
        }
    }
/**whenever a button is pressed in this panel for whatever reason*/
    public void clicked(Button button)
    {
    	System.out.println("BLAG");
    }
@Override
public void keyPressed(KeyEvent e) {
	for(int i = 0; i<components.size();i++)
    {
	   if(components.get(i).active)components.get(i).keyPressed(e);
    }
}
@Override
public void keyReleased(KeyEvent e) {
	for(int i = 0; i<components.size();i++)
    {
	   if(components.get(i).active)components.get(i).keyReleased(e);
    }
}
@Override
public void keyTyped(KeyEvent e) {
	for(int i = 0; i<components.size();i++)
    {
	   if(components.get(i).active)components.get(i).keyTyped(e);
    }
}
}