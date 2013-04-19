package com.freedom.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.view.LoadingScreen;

public class LoadingScreenModel {

	private LoadingScreenModel() {
		logger.setLevel(Level.OFF);
	}
	
	public static LoadingScreenModel getInstance() {

		if (INSTANCE == null)
			return INSTANCE = new LoadingScreenModel();
		else
			return INSTANCE;
	}

	public void addButtons() {
		labels[0] = new GLabel("Loading", 1);
		labels[1] = new GLabel("Percentage", 2);
	}

	public void draw(Graphics g) {
		logger.info("LOL");
		for (GLabel l : labels) {
			if (l != null)
				l.draw(g);
		}
	}

	private GLabel[] labels = new GLabel[2];

	private static LoadingScreenModel INSTANCE;
	private Logger logger = Logger.getLogger("LoadingScreenModel");

	private class GLabel {
		/**
		 * Класс неинтерактивных элементов экрана, несущих на себе текст и
		 * больше ничего
		 * 
		 * @param text
		 *            определяет отобоажаемую на метке информацию
		 * @param line
		 *            определяет позицию (пока тупо в углу экрана)
		 */
		public GLabel(String text, int line)
		{
			this.text = text;
			this.positionX = LoadingScreen.getInstance().getWidth() / 15;
			this.positionY = LoadingScreen.getInstance().getHeight()
					* (12 + line) / 15;
		}

		public void draw(Graphics g) {

			logger.info("LOLinner");
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g2.setFont(textFont);
			g2.setColor(Color.WHITE);
			g2.drawString(text, positionX, positionY);
		}

		private String text;
		private Font textFont = new Font("Monospaced", Font.PLAIN,
				LoadingScreen.getInstance().getHeight() / 15);
		// private int dimensionX;
		// private int dimensionY;
		private int positionX, positionY;
	}

}
