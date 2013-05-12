package com.freedom.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.utilities.interfai.GButtonLite;
import com.freedom.view.PauseScreen;

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
				"com.freedom.view.PauseScreen$QuitAction");
		buttons[1] = new GButtonLite("SAVE", 1,
				"com.freedom.view.PauseScreen$SaveLevelAction");
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

	public String reactToClick(Point p) {
		logger.info(p.toString());
		for (GButtonLite b : buttons) {
			if (b != null)
				if (!b.checkIfPressed(p).equals("WasNotPressed"))
					return b.actionName;
		}
		return "NothingHappened";
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

	
}
