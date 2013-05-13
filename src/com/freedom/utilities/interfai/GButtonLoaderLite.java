package com.freedom.utilities.interfai;

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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import com.freedom.model.GameField;
import com.freedom.utilities.game.SoundEngine;
import com.freedom.view.ScreensHolder;

public class GButtonLoaderLite {
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

	public void checkIfPressed(Point p) {
		if ((p.getX() >= this.positionX
				&& p.getX() <= this.positionX + dimensionX
				&& p.getY() >= this.positionY && p.getY() <= this.positionY
				+ dimensionY)) {

			SoundEngine.playClip(clickedSound, -1, -10);
			try {
				loadLevel();
			} catch (IOException e) {
				logger.warning("Was unable to create temporary save file");
			}
		}
	}

	private void loadLevel() throws IOException{
		File src = new File(fileToLoad);
		File dst = new File("TmpSave");
		Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
		GameField.getInstance().loadLevel(dst.getAbsolutePath());
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		FontRenderContext context = g2.getFontRenderContext();
		if (textFont == null)
			textFont = new Font("Monospaced", Font.PLAIN, ScreensHolder
					.getInstance().getHeight() / 20);
		Rectangle2D bounds = textFont.getStringBounds(text, context);

		if (dimensionX == 0) {
			dimensionX = (int) bounds.getWidth();
			dimensionY = (int) bounds.getHeight();
			positionX = (int) (ScreensHolder.getInstance().getWidth() / 2 - bounds
					.getWidth() / 2);
			metrics = textFont.getLineMetrics(text, context);

		}
		g2.setFont(textFont);
		g2.setColor(textColor);
		g2.drawString(text, positionX,
				positionY + dimensionY - metrics.getDescent());
	}

	
	private Logger logger = Logger.getLogger("GButtonLoaderLite");
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
	private final int offsetY = ScreensHolder.getInstance().getHeight() / 3;
	private final int gap = ScreensHolder.getInstance().getHeight() / 12;

}