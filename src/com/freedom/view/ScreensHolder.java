package com.freedom.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLayeredPane;

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
		logger.setLevel(Level.WARNING);
		logger.info("Entering the constructor...");
		setLayout(null);
		setDoubleBuffered(true);
	}

	public void createScreens() {
		GameScreen.getInstance().prepareModel();
		StartScreen.getInstance().prepareModel();
		PauseScreen.getInstance().prepareModel();
		LoadingScreen.getInstance().prepareModel();
		LoadScreen.getInstance().prepareModel();
		SaveScreen.getInstance().prepareModel();
		EditorSettingsScreen.getInstance().prepareModel();
		addScreen(StartScreen.getInstance());

	}

	public void addScreen(AbstractScreen toAdd) {
		lastScreen = currentScreen;
		if (lastScreen != null)
			lastScreen.deactivateModel();
		currentScreen = toAdd;
		INSTANCE.add(toAdd);
		toAdd.requestFocusInWindow();
		INSTANCE.moveToFront(toAdd);
		toAdd.activateModel();
		INSTANCE.revalidate();
		paintImmediately(getBounds());
	}

	
	public void removeScreen(AbstractScreen toRemove) {
		toRemove.deactivateModel();
		INSTANCE.remove(toRemove);
		lastScreen.requestFocusInWindow();
		INSTANCE.moveToFront(lastScreen);
		currentScreen = (AbstractScreen) getComponents()[getComponents().length-1];
		lastScreen = toRemove;
		currentScreen.activateModel();
		INSTANCE.revalidate();
		paintImmediately(getBounds());
	}

	public void swapScreens(AbstractScreen toAdd, AbstractScreen toRemove) {
		currentScreen = toAdd;
		INSTANCE.add(toAdd);
		toAdd.requestFocusInWindow();
		INSTANCE.moveToFront(toAdd);
		lastScreen = toRemove;
		INSTANCE.remove(toRemove);
		toRemove.deactivateModel();
		INSTANCE.revalidate();
		toAdd.activateModel();
		paintImmediately(getBounds());
	}

	public static ScreensHolder getInstance() {
		return INSTANCE;
	}

	public AbstractScreen getCurrentScreen() {
		return currentScreen;
	}

	public AbstractScreen getLastScreen() {
		return lastScreen;
	}

	public static void setDimensions(int dimensionX, int dimensionY) {
		INSTANCE.setSize(new Dimension(dimensionX, dimensionY));
	}

	private Logger logger = Logger.getLogger("ScreensHolder");

	private static final ScreensHolder INSTANCE = new ScreensHolder();
	private AbstractScreen lastScreen;
	private AbstractScreen currentScreen;

}
