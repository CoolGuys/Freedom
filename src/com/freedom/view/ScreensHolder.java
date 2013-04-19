package com.freedom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLayeredPane;

import com.freedom.utilities.AbstractScreen;


/**
 * GraphicsController - контролирующий интерфейс класс. Здесь будет
 * осуществляться хранение и размещение всех частей графического интерфея
 * программы 
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
public class ScreensHolder extends JLayeredPane {
	private ScreensHolder()
	{
		super();

		this.setBackground(Color.BLACK);
		this.setOpaque(true);
		logger.setLevel(Level.OFF);
		logger.info("Entering the constructor...");
		setLayout(null);
	}

	public void createScreens() {
		GameScreen.getInstance().prepareModel(); 
		StartScreen.getInstance().prepareModel();
		PauseScreen.getInstance().prepareModel();
		LoadingScreen.getInstance().prepareModel();
		
		addScreen(StartScreen.getInstance());
	}
	
	public void addScreen(AbstractScreen toAdd) {
		INSTANCE.add(toAdd);
		toAdd.requestFocusInWindow();
		toAdd.activateModel();
		INSTANCE.moveToFront(toAdd);
		INSTANCE.revalidate();
		INSTANCE.repaint();
	}
	
	public void removeScreen(AbstractScreen toRemove) {
		INSTANCE.remove(toRemove);
		toRemove.deactivateModel();
		INSTANCE.revalidate();
		INSTANCE.repaint();
	}
	
	public static void swapScreens(AbstractScreen toAdd, AbstractScreen toRemove) {
		INSTANCE.removeScreen(toRemove);
		INSTANCE.addScreen(toAdd);
	}

	public static ScreensHolder getInstance() {
		return INSTANCE;
	}
	
	public static void setDimensions(int dimensionX, int dimensionY) {
		INSTANCE.setSize(new Dimension(dimensionX, dimensionY));
	}
	
	private Logger logger = Logger.getLogger("ScreensHolder");
	
	private static final ScreensHolder INSTANCE = new ScreensHolder(); 
	

}
