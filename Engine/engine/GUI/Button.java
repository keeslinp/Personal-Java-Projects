package engine.GUI;

/**
 * A button that can be placed anywhere and can be any shape.
 * 
 * @author (h3ckboy aka Pearce Keesling) 
 * @version (a version number or a date)
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import java.util.StringTokenizer;
public class Button extends Component
{
 
  
  Color ACTIVE_COLOR = Color.red;

  Color INACTIVE_COLOR = Color.lightGray;
  
  protected String[] toolTip= new String[0];
  int textLength;
  

  protected boolean isActive;
  
  Color TextColor = Color.black;
  //Color TextColorActive = Color.black;
  
  String tooltip = "";

private boolean compute;

  protected static Button button;
    public Button(Rectangle r, String text, Container applet)
    {
            this.rectangle = r;
            this.applet = applet;
            setText(text);
    }
    public void setCaption(String t)
    {
    	text = t;
    }
      public void setText(String t) {
    text = t;
    ActionName = t;
    compute = true;
  }
      //how to make the ActionName the string that defines the button different than the string on the button.
      public void setActionName(String a){ActionName = a;}
      public String getActionName(){return ActionName;}
    public void setToolTip(String t)
    {
    	tooltip = t;
    	toolTip = tooltip.split("#/#");
    }
  public String getText() {
    return text;
  }

  public void mouseMoved(MouseEvent e) {
      //System.out.println("well the mouse movemnet is being registered by the button named "+text);
    if (!rectangle.contains(e.getX(), e.getY()) || e.isConsumed()) {
      if (isActive) {
        isActive = false;
        setState(false);
      }
      return; // quickly return, if outside our rectangle
    }
    //System.out.println("mouse is over the button");
    int x = e.getX();
    int y = e.getY();
    boolean active = rectangle.contains(x, y);

    if (isActive != active)
      setState(active);
    if (active)
      e.consume();
  }

  public void mouseDragged(MouseEvent e) {
	  mouseClicked(e);
  }

  protected void setState(boolean active) {
    isActive = active;
    if (active) {
      if (button != null)
        button.setState(false);
      applet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    } else {
      button = null;
      applet.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  public void mouseClicked(MouseEvent e) {
      if(rectangle.contains(e.getX(),e.getY())&&e.getButton()==1)
      {
    	  System.out.println("cool");
//          ActionEvent event = new ActionEvent(this,0,text);
    	  panel.clicked(this);
          actionListener.actionPerformed(new ActionEvent(this,0,ActionName));
          setState(false);
        }
      //System.out.println("Button #"+e.getButton()+"was pressed");
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
    mouseMoved(e);
  }

  public void mouseEntered(MouseEvent e) {
    mouseMoved(e);
  }

  public void paint(Graphics g) {
	  if (compute)textLength = SwingUtilities.computeStringWidth( g.getFontMetrics(), text );
    g.setColor(isActive ? ACTIVE_COLOR : INACTIVE_COLOR);
    //g.drawPolygon(polygon);
    //Rectangle r = polygon.getBounds();
    g.fillRect((int)rectangle.getX(),(int)rectangle.getY(),(int)rectangle.getWidth(),(int)rectangle.getHeight());
    
    g.setColor(TextColor);
    g.drawString(text,(int)(rectangle.getX()+rectangle.getWidth()/2)-textLength/2,(int)(rectangle.getY()+rectangle.getHeight()/2));
    if(isActive){
    	int length;
    	int topLine = (int)rectangle.getY()-12*toolTip.length;
    	for(int i=0;i<toolTip.length;i++){
    		length = SwingUtilities.computeStringWidth( g.getFontMetrics(), toolTip[i] );
    		g.drawString(toolTip[i],(int)(rectangle.getX()+rectangle.getWidth()/2)-length/2,topLine+i*12);
    	}
    }
    //System.out.println("rendering button named : "+text);
  }
  public void setColor(Color Active,Color Inactive)
  {
      this.ACTIVE_COLOR = Active;
      this.INACTIVE_COLOR = Inactive;
    }
  public void setTextColor(Color TextColor)
  {
      this.TextColor = TextColor;
  }
  public BufferedImage getImage()
  {
	  return null;
  }
  public void setActive(boolean i){active = i;}
}