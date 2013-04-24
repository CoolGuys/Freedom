package com.freedom.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.gameObjects.GameField;
import com.freedom.view.ChoiceScreen;
import com.freedom.view.PauseScreen;

public class ChoiceScreenModel {

	private ChoiceScreenModel()
	{
		logger.setLevel(Level.ALL);
	}

	public void addEntries() {
		logger.info("Adding Entries");
		int counter = 0;
		Path dir = Paths.get(listedDirectory);
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(dir);
			for (Path file : stream) {
				buttons[counter] = new GButtonLoaderLite(file.getFileName()
						.toString(), counter, file.toString());
				counter++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ChoiceScreenModel getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new ChoiceScreenModel();
		else
			return INSTANCE;
	}

	public void activate() {
		addEntries();
	}

	public void deactivate() {
		buttons=new GButtonLoaderLite[5];
		for (GButtonLoaderLite b : buttons) {
			if (b != null)
				b.reset();
		}
	}

	public void draw(Graphics g) {

		for (GButtonLoaderLite b : buttons)
			if (b != null)
				b.draw(g);
	}

	public String reactToClick(Point p) {
		logger.info(p.toString());
		for (GButtonLoaderLite b : buttons) {
			if (b != null)
				b.checkIfPressed(p);
		}
		return "NothingHappened";
	}

	public void setListedDirectory(String dir) {
		listedDirectory = dir;
		addEntries();
	}

	public void reactToRollOver(Point point) {
		for (GButtonLoaderLite b : buttons) {
			if (b != null)
				if (b.checkRollOver(point))
					ChoiceScreen.getInstance().repaint();
		}
	}

	private GButtonLoaderLite[] buttons = new GButtonLoaderLite[5];
	private String listedDirectory;
	private static ChoiceScreenModel INSTANCE;

	private Logger logger = Logger.getLogger("PauseScreenModel");

	private class GButtonLoaderLite {
		public GButtonLoaderLite(String text, int lineNumber, String file)
		{
			this.text = text;
			this.line = lineNumber;
			this.positionY = calculateYPosition();
			this.fileToLoad = file;

			this.clickedSound = new File("Resource/Sound/ButtonClicked.wav");

		}

		public boolean checkRollOver(Point p) {
			if ((p.getX() >= this.positionX
					&& p.getX() <= this.positionX + dimensionX
					&& p.getY() >= this.positionY && p.getY() <= this.positionY
					+ dimensionY)) {
				textColor = Color.WHITE;
				return true;
			} else if (textColor.equals(Color.WHITE)) {
				textColor = Color.LIGHT_GRAY;
				return true;
			}
			return false;
		}

		private int calculateYPosition() {
			return line * (dimensionY + gap) + offsetY;
		}

		public void reset() {
			this.textColor = Color.LIGHT_GRAY;
		}

		public void checkIfPressed(Point p) {
			if ((p.getX() >= this.positionX
					&& p.getX() <= this.positionX + dimensionX
					&& p.getY() >= this.positionY && p.getY() <= this.positionY
					+ dimensionY)) {

				SoundEngine.playClip(clickedSound, -1, -10);
				loadLevel();
			}
		}

		private void loadLevel() {
			GameField.getInstance().setCurrentLevel(1);
			GameField.getInstance().loadLevel(fileToLoad);
		}

		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			FontRenderContext context = g2.getFontRenderContext();
			if (textFont == null)
				textFont = new Font("Monospaced", Font.PLAIN, PauseScreen
						.getInstance().getHeight() / 20);
			Rectangle2D bounds = textFont.getStringBounds(text, context);

			if (dimensionX == 0) {
				dimensionX = (int) bounds.getWidth();
				dimensionY = (int) bounds.getHeight();
				positionX = (int) (PauseScreen.getInstance().getWidth() / 2 - bounds
						.getWidth() / 2);
				metrics = textFont.getLineMetrics(text, context);

			}
			g2.setFont(textFont);
			g2.setColor(textColor);
			g2.drawString(text, positionX,
					positionY + dimensionY - metrics.getDescent());
		}

		private int line;
		private int dimensionX = 0;
		private int dimensionY;
		private int positionX, positionY;
		private String text;
		private String fileToLoad;
		private Color textColor = Color.LIGHT_GRAY;
		private LineMetrics metrics;
		private File clickedSound;
		private Font textFont;
		private final int offsetY = PauseScreen.getInstance().getHeight() / 3;
		private final int gap = PauseScreen.getInstance().getHeight() / 12;

	}
}
