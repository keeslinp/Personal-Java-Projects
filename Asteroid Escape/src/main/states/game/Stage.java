package main.states.game;

import java.awt.image.BufferedImage;

import javax.swing.JApplet;

import main.states.game.util.ImageCache;

public interface Stage {

	ImageCache imgCache = new ImageCache();

	JApplet getApplet();

	public void boost();
}
