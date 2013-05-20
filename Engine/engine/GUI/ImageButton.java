/**
 * 
 */
package engine.GUI;

/**
 * @author Pearce Keesling aka h3ckboy
 *
 */
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import java.awt.image.BufferedImage;
import java.awt.Image;
public class ImageButton extends Button {
	BufferedImage image,ActiveImage;
	public ImageButton(Rectangle rectangle, String text, JApplet applet, BufferedImage image)
	{
		super(rectangle,text,applet);
		this.image = image;
	}
	public ImageButton(Point p, String text, JApplet applet, BufferedImage image,BufferedImage ActiveImage)
	{
		super(new Rectangle(p.x,p.y,image.getWidth(),image.getHeight()),text,applet);
		this.image = image;
		this.ActiveImage = ActiveImage;
	}
	public void paint(Graphics g)
	{
		if(!isActive)g.drawImage((Image)image,rectangle.x,rectangle.y, applet);
		if(isActive){
			if(ActiveImage!=null)
				{
					g.drawImage((Image)ActiveImage,rectangle.x,rectangle.y, applet);
				}else{
					g.drawImage((Image)image,rectangle.x,rectangle.y, applet);
				}
	    	int topLine = (int)rectangle.getY()-12*toolTip.length;
	    	for(int i=0;i<toolTip.length;i++){
	    		int length = SwingUtilities.computeStringWidth( g.getFontMetrics(), toolTip[i] );
	    		g.drawString(toolTip[i],(int)(rectangle.getX()+rectangle.getWidth()/2)-length/2,topLine+i*12);
	    	}
	    }
	}
	public BufferedImage getImage()
	{
		return image;
	}
}
