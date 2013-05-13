package com.freedom.utilities.interfai;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.model.StartScreenModel;
import com.freedom.utilities.game.SoundEngine;

public class GButton {
	public GButton(String text, int cellX, int cellY, GAction action)
	{
		this.text = text;
		this.action = action;
		calculateMyParameters(cellX, cellY);
	}

	public void calculateMyParameters(int cellX, int cellY) {
		double[] parameters = StartScreenModel.getInstance()
				.calculateBackgroundParameters();

		dimensionX = dimensionY = (int) parameters[1];
		positionX = (int) ((cellX) * parameters[0] + (cellX - 1)
				* parameters[1]);
		positionY = (int) (StartScreenModel.getInstance()
				.calculateBackgroundPosition().y + (cellY) * parameters[0] + (cellY - 1)
				* parameters[1]);

	}

	public void checkIfPressed(Point p) {
		if ((p.getX() >= this.positionX
				&& p.getX() <= this.positionX + dimensionX
				&& p.getY() >= this.positionY && p.getY() <= this.positionY
				+ dimensionY)) {

			SoundEngine.playClip(buttonClickedSound, -1, -10);
			action.performAction();
		}
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

	public void reset() {
		this.textColor = Color.LIGHT_GRAY;
	}

	public void draw(Graphics g) {
		g.drawImage(texture, positionX, positionY, dimensionX, dimensionY, null);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds;
		if (buttonFont == null) {
			Font tF = new Font("Monospaced", Font.PLAIN, 100);
			bounds = tF.getStringBounds("A", context);
			buttonFont = new Font("Monospaced", Font.PLAIN, (int) (dimensionX
					* -bounds.getY() / bounds.getWidth() / 4));
		}

		bounds = buttonFont.getStringBounds(text, context);
		logger.info(bounds.toString());
		g2.setFont(buttonFont);
		g2.setColor(textColor);
		g2.drawString(
				text,
				(int) (positionX + 1.0 / 2.0 * (dimensionX - bounds.getWidth())),
				(int) (positionY + dimensionY * 1 / 2 - 0.5 * bounds.getY()));
	}

	private int positionX, positionY;
	private int dimensionX, dimensionY;
	private String text;
	private static Image texture;
	private Color textColor = Color.LIGHT_GRAY;
	private static File buttonClickedSound;
	private Font buttonFont;
	public final GAction action;
	private static Logger logger = Logger.getLogger("GButton");
	static {
		logger.setLevel(Level.WARNING);
		try {
			texture = ImageIO.read(new File(
					"Resource/UtilityPictures/GButton.png"));
			buttonClickedSound = new File("Resource/Sound/ButtonClicked.wav");
		} catch (IOException e) {
			logger.warning("Texture or sound were corrupted");
		}
	}
}
