package com.freedom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


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

	public StartScreen getStartScreen() {
		return startScreen;
	}

	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}
	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.gameScreen.setDimensions(dimensionX, dimensionY);
		this.gameScreen.setOpaque(true);
	}
	
	public void createScreens() {
		setStartScreen(StartScreen.getInstance());
		setGameScreen(GameScreen.getInstance());
		add(getStartScreen());
		startScreen.setBounds(0, 0, dimensionX, dimensionY);
		startScreen.activate();
	}
	
	public static void swapDisplays(JLayeredPane toAdd, JLayeredPane toRemove) {
		INSTANCE.remove(toRemove);
		INSTANCE.add(toAdd);
		toAdd.requestFocusInWindow();
		toAdd.setBounds(0, 0, INSTANCE.dimensionX, INSTANCE.dimensionY);
		INSTANCE.revalidate();
		INSTANCE.repaint();
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
