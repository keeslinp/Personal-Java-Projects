package engine.GUI;

/**
 * Exception thrown when buttons ar not inside their designated panels
 * 
 * @author (h3ckboy aka Pearce Keesling) 
 */
public class ButtonOutOfPanelException extends GuiException {
	private static final long serialVersionUID = 1L;
	public ButtonOutOfPanelException() {
		super();
		System.out.println("There is a button out of the Panel");
	}
	public ButtonOutOfPanelException(String OutOfPanelMsg){
		super(OutOfPanelMsg);
	}
}
