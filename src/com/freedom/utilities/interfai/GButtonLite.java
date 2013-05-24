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

import com.freedom.utilities.game.SoundEngine;
import com.freedom.view.PauseScreen;

public class GButtonLite {
	public GButtonLite(String text, int lineNumber, GAction action)
	{
		this.action = action;
		this.text = text;
		this.line = lineNumber;
		positionY = calculateYPosition();

		clickedSound = new File("Resource/Sound/ButtonClicked.wav");

	}

	public GButtonLite(String text, int lineNumber, GAction action, Font font)
	{
		this.action = action;
		this.text = text;
		this.line = lineNumber;
		this.textFont = font;
		positionY = calculateYPosition();

		clickedSound = new File("Resource/Sound/ButtonClicked.wav");

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
			action.performAction();
			return;
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		FontRenderContext context = g2.getFontRenderContext();
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
	private Color textColor = Color.LIGHT_GRAY;
	private LineMetrics metrics;
	private File clickedSound;
	private Font textFont = new Font("Monospaced", Font.PLAIN, PauseScreen
			.getInstance().getHeight() / 15);;
	public final GAction action;
	private final int offsetY = PauseScreen.getInstance().getHeight() / 3;
	private final int gap = PauseScreen.getInstance().getHeight() / 12;

}
