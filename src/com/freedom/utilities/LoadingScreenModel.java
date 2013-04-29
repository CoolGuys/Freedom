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

	public static LoadingScreenModel getInstance() {

		if (INSTANCE == null)
			return INSTANCE = new LoadingScreenModel();
		else
			return INSTANCE;
	}

	public void addButtons() {
		labels[0] = new GLabel("", 1);
		labels[1] = new GLabel("", 2);
	}

	public void draw(Graphics g) {
		for (GLabel l : labels) {
			if (l != null)
				l.draw(g);
		}
	}

	public void setProgressPercent(int percent) {
		labels[1].setText(percent + "%");
		//logger.info(""+labels[1].positionX+"|"+ labels[1].positionY);
//		LoadingScreen.getInstance().paintImmediately(0,
//				labels[1].positionY-LoadingScreen.getInstance().getHeight() / 15,LoadingScreen.getInstance().getWidth(),
//				LoadingScreen.getInstance().getHeight() / 12);

	}

	public void setLoadingObjectName(String name) {
		labels[0].setText(name);
		//logger.info(""+labels[0].positionX+labels[0].positionY);
		LoadingScreen.getInstance().paintImmediately(0,
				labels[0].positionY-LoadingScreen.getInstance().getHeight() / 15,LoadingScreen.getInstance().getWidth(),
				LoadingScreen.getInstance().getHeight() / 5);
	}

	private GLabel[] labels = new GLabel[2];

	private static LoadingScreenModel INSTANCE;
	private static Logger logger = Logger.getLogger("LoadingScreenModel");

static {
	logger.setLevel(Level.OFF);
}
	
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

		public void setText(String text) {
			this.text = text;
		}

		public void draw(Graphics g) {
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
