package engine.GUI;


/**
 * This is the class that all fo the GUI exceptions extend
 * 
 * @author (h3ckboy aka Pearce Keesling) 
 */
public class GuiException extends Exception {
	private static final long serialVersionUID = 1L;
	public GuiException() {
		// Constructor without a message
		super();
	}
	public GuiException(String transferMsg){
		// Constructor with a message
		super(transferMsg);
	}
}