package com.freedom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import com.freedom.utilities.StartScreenModel;


/**
 * GraphicsController - контролирующий интерфейс класс. Здесь будет
 * осуществляться хранение и размещения всех частей графического интерфея
 * программы 
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
public class ScreensHolder extends JPanel {
	private ScreensHolder()
	{
		super();
		logger.setLevel(Level.OFF);
		logger.info("Entering the constructor...");
		setLayout(null);
		setBackground(Color.WHITE);
	}

	public void createScreens() {
		startScreen = StartScreen.getInstance();
		StartScreenModel.getInstance().addButtons();
		gameScreen = GameScreen.getInstance();
		addScreen(StartScreen.getInstance());
		StartScreen.activateModel();
	}
	
	public void addScreen(JLayeredPane toAdd) {
		INSTANCE.add(toAdd);
		toAdd.requestFocusInWindow();
		INSTANCE.revalidate();
		INSTANCE.repaint();
	}
	
	public void removeScreen(JLayeredPane toRemove) {
		INSTANCE.remove(toRemove);
		INSTANCE.revalidate();
		INSTANCE.repaint();
	}
	
	public static void swapScreens(JLayeredPane toAdd, JLayeredPane toRemove) {
		INSTANCE.removeScreen(toRemove);
		INSTANCE.addScreen(toAdd);
	}

	public static ScreensHolder getInstance() {
		return INSTANCE;
	}
	
	public static void setDimensions(int dimensionX, int dimensionY) {
		INSTANCE.dimensionX = dimensionX;
		INSTANCE.dimensionY = dimensionY;
		INSTANCE.setSize(new Dimension(dimensionX, dimensionY));
	}
	
	
	private StartScreen startScreen;
	private GameScreen gameScreen;
	
	private int dimensionX;
	private int dimensionY;
	private Logger logger = Logger.getLogger("ScreensHolder");
	
	private static final ScreensHolder INSTANCE = new ScreensHolder(); 
	

}