/**
 * 
 */
package engine.graphics;

/**
 * @author Pearce Keesling aka h3ckboy
 *
 */
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import javax.swing.JApplet;
public class RotateImage {
	BufferedImage image;
	BufferedImage rotImage;
	int buffer[] = new int[32 * 32];
	int rotate[] = new int[32 * 32];
	public RotateImage(BufferedImage image,Container container)
	{
		try {
		this.image = image;
		MediaTracker tracker = new MediaTracker(container);
		tracker.addImage (image, 0);
	      tracker.waitForAll();
	      PixelGrabber grabber =
	        new PixelGrabber(image, 0, 0, 32, 32, buffer, 0, 32);
	        try {
	        grabber.grabPixels();
	          }
	      catch(InterruptedException e) {
	         e.printStackTrace();
	         }
	      for(int y = 0; y < 32; y++) {
	        for(int x = 0; x < 32; x++) {
	          rotate[((32-x-1)*32)+y] = buffer[(y*32)+x];
	          }
	        }
	      rotImage =  GraphicsUtility.toBufferedImage(container.createImage(new MemoryImageSource(32, 32, rotate, 0, 32)));
	      }
	   catch (Exception e) {
	      e.printStackTrace();
	      }
	}
	
}
