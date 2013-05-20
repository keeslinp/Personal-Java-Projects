/**
 * 
 */
package engine.GUI;

import java.applet.Applet;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author Pearce Keesling aka h3ckboy
 *
 */
import java.awt.Graphics;
public class BorderedPanel extends Panel {
	public BorderedPanel(Rectangle r, ArrayList<Button> buttons) throws ButtonOutOfPanelException
	{
		super(r,buttons);
	}
	public void render(Graphics g)
	{
		if(active)g.drawRect((int)rectangle.getX(),(int)rectangle.getY(),(int)rectangle.getWidth(),(int)rectangle.getHeight());
		super.render(g);
	}

}
