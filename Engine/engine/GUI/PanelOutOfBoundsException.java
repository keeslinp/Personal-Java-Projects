package engine.GUI;

public class PanelOutOfBoundsException extends GuiException {
	public PanelOutOfBoundsException()
	{
		super();
		System.out.println("There is a panel outside of the MultiPanel");
	}
	public PanelOutOfBoundsException(String msg)
	{
		super(msg);
	}
}