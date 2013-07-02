package com.freedom.model;

import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.utilities.interfai.GLabel;
import com.freedom.utilities.interfai.GLabel.Alignment;
import com.freedom.view.LoadingScreen;
import com.freedom.view.ScreensHolder;

public class LoadingScreenModel {

	public static LoadingScreenModel getInstance() {

		if (INSTANCE == null)
			return INSTANCE = new LoadingScreenModel();
		else
			return INSTANCE;
	}

	public void addButtons() {
		labels[0] = new GLabel("", 1, Alignment.LEFT_BOTTOM_CORNER, textFont);
		labels[1] = new GLabel("", 2, Alignment.LEFT_BOTTOM_CORNER, textFont);
	}

	public void draw(Graphics g) {
		for (GLabel l : labels) {
			if (l != null)
				l.draw(g);
		}
	}

	public void setProgressPercent(int percent) {
		labels[1].setText(percent + "%");

	}

	public void setLoadingObjectName(String name) {
		labels[0].setText(name);
		LoadingScreen.getInstance()
				.repaint(
						0,
						labels[0].getY()
								-ScreensHolder.getInstance().getHeight() / 15,
						ScreensHolder.getInstance().getWidth(),
						ScreensHolder.getInstance().getHeight() / 5);
	}

	private GLabel[] labels = new GLabel[2];
	private Font textFont = new Font("Monospaced", Font.PLAIN, ScreensHolder
			.getInstance().getHeight() / 15);

	private static LoadingScreenModel INSTANCE;
	private static Logger logger = Logger.getLogger("LoadingScreenModel");

	static {
		logger.setLevel(Level.OFF);
	}
}
