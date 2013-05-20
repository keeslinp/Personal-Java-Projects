package engine.GUI;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Graphics;

public class MultiPanel extends Panel {
	Rectangle rectangle;
	ArrayList<Panel> Panels;
	int value = 0;
	public MultiPanel(Rectangle r, ArrayList<Panel> Panels,ArrayList<Button> components) throws PanelOutOfBoundsException, ButtonOutOfPanelException 
	{
		super(r,components);
		rectangle = r;
		this.Panels = Panels;
		checkPanels();
		System.out.println(Panels.get(1).getButton(0).text);
	}
/**Rendering*/
	public void render(Graphics g)
	{
		super.render(g);
			Panels.get(value).render(g);
			//System.out.println(value);
	}
/**Checking setup for errors*/
    public void checkPanels()throws PanelOutOfBoundsException
    {
        for(int i = 0;i<Panels.size();i++)
        {
            if(!rectangle.contains(Panels.get(i).rectangle))throw new PanelOutOfBoundsException("Cannot have a panel outside of the MultiPanel!");
        }
    }
    /**Mouse Related Stuff*/
    public void mousePressed(MouseEvent e) {    	
    	super.mousePressed(e);
    	if(Panels.get(value).active)Panels.get(value).mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
    	super.mouseReleased(e);
         	   if(Panels.get(value).active)Panels.get(value).mouseReleased(e);
    }

    public void mouseEntered(MouseEvent e) {
    	super.mouseEntered(e);
    	   if(Panels.get(value).active)Panels.get(value).mouseEntered(e);
    }

    public void mouseExited(MouseEvent e) {
    	super.mouseExited(e);
    	   if(Panels.get(value).active)Panels.get(value).mouseEntered(e);
    }

    public void mouseClicked(MouseEvent e) {
    	super.mouseClicked(e);
    	   if(Panels.get(value).active)Panels.get(value).mouseClicked(e);
    }
    
    public void mouseMoved(MouseEvent e)
    {
    	super.mouseMoved(e);
        	if(Panels.get(value).active)Panels.get(value).mouseMoved(e);
    }
    public void mouseDragged(MouseEvent e)
    {
    	super.mouseDragged(e);
        	if(Panels.get(value).active)Panels.get(value).mouseDragged(e);
    }
/** retrieving Panels*/
    public Panel getPanel(int i)
    {
    	return Panels.get(i);
    }
    public Component getComponent(int i)
    {
    	return Panels.get(value).components.get(i);
    }
    public Button getButton(int i)
    {
    	return (Button)getComponent(i);
    }
    public void clicked(Button button)
    {
    	int number = 0;
    	for(int x = 0;x<components.size();x++)
    	{
    		if(components.get(x)==button) number = x;
    	}
    	System.out.println("number : "+number);
    	value = number;
    }
}
