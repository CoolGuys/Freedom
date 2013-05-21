package com.freedom.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.utilities.interfai.GAction;
import com.freedom.utilities.interfai.GButtonLite;
import com.freedom.view.AbstractScreen;
import com.freedom.view.GameScreen;
import com.freedom.view.PauseScreen;
import com.freedom.view.SaveScreen;
import com.freedom.view.ScreensHolder;
import com.freedom.view.StartScreen;

public class PauseScreenModel {

	private PauseScreenModel()
	{
		logger.setLevel(Level.ALL);

		try {
			background = ImageIO.read(new File(
					"Resource/UtilityPictures/pauseScreenBackground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addButtons() {
		buttons[0] = new GButtonLite("QUIT", 2,
				new QuitAction());
		buttons[1] = new GButtonLite("SAVE", 1,
				new SaveLevelAction());
	}

	public static PauseScreenModel getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new PauseScreenModel();
		else
			return INSTANCE;
	}

	public void activate() {

	}

	public void deactivate() {
		for (GButtonLite b : buttons)
			if (b != null)
				b.reset();
	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, PauseScreen.getInstance().getWidth(),
				PauseScreen.getInstance().getHeight(), null);

		for (GButtonLite b : buttons)
			if (b != null)
				b.draw(g);
	}

	public void reactToClick(Point p) {
		logger.info(p.toString());
		for (GButtonLite b : buttons) {
			if (b != null)
				b.checkIfPressed(p);
		}
	}

	public void reactToRollOver(Point point) {
		for (GButtonLite b : buttons) {
			if (b != null)
				if (b.checkRollOver(point))
					PauseScreen.getInstance().repaint();
		}
	}

	private GButtonLite[] buttons = new GButtonLite[5];
	private Image background;

	private static PauseScreenModel INSTANCE;

	private Logger logger = Logger.getLogger("PauseScreenModel");

	
	public static class QuitAction extends GAction {
		public void performAction() {
			GameField.getInstance().resetTickerListeners();
			GameField.otherThreads.shutdownNow();
			ScreensHolder.getInstance().removeScreen((AbstractScreen) ScreensHolder.getInstance().getComponents()[1]);
			ScreensHolder.getInstance().swapScreens(StartScreen.getInstance(),
					PauseScreen.getInstance());
		}
	}
	
	

	public static class SaveLevelAction extends GAction {
		public void performAction() {
			SaveScreenModel.getInstance().setSourcePack(GameField.getInstance().getPathToSave());
			SaveScreenModel.getInstance().setDescriptor("Enter Save Name");
			SaveScreenModel.getInstance().addEntries();
			ScreensHolder.getInstance().swapScreens(SaveScreen.getInstance(), PauseScreen.getInstance());
		}
	}
	
}
