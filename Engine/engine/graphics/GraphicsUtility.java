/**
 * 
 */
package engine.graphics;

/**
 * @author Pearce Keesling aka h3ckboy
 *
 */
import java.awt.image.*;
import java.awt.*;

import javax.swing.ImageIcon;
import java.awt.Container;
public class GraphicsUtility {
	public static BufferedImage rotateImage(BufferedImage image, Container container)
	{
		RotateImage rotate = new RotateImage(image,container);
		return rotate.rotImage;
	}
/**Code taken from http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html*/
	// This method returns a buffered image with the contents of an image
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
    
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
    
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = applet.hasAlpha(image);
    
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            //int transparency = Transparency.OPAQUE;
            //if (hasAlpha) {
            int transparency = Transparency.BITMASK;
            //}
    
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
    
        if (bimage == null) {
            // Create a buffered image using the default color model
            //int type = BufferedImage.TYPE_INT_RGB;
            //if (hasAlpha) {
            int type = BufferedImage.TYPE_INT_ARGB;
            //}
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
    
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
    
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
    
        return bimage;
    }
}
