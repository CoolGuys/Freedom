package com.freedom.utilities.interfai;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import com.freedom.view.LoadingScreen;
import com.freedom.view.ScreensHolder;

public class GLabel {
	/**
	 * Класс неинтерактивных элементов экрана, несущих на себе текст и больше
	 * ничего
	 * 
	 * @param text
	 *            определяет отобоажаемую на метке информацию
	 * @param line
	 *            определяет уровень
	 * @param disposition
	 *            определяет стобец местоположения -центр "center" -левый нижний
	 *            угол "left bottom corner"
	 * @param textFont
	 *            шрифт
	 * @param textSize
	 *            размер шрифта
	 */
	public GLabel(String text, int line, Alignment disposition, Font textFont)
	{
		this.disposition = disposition;
		this.textFont = textFont;
		this.text = text;
		this.line = line;
		switch (this.disposition) {
		
		case LEFT_BOTTOM_CORNER:
			this.positionX = textFont.getSize();
			this.positionY = ScreensHolder.getInstance().getHeight()
					* (12 + line) / 15;
			break;
			
		case CENTER:
			setText(text);
			break;
		}

	}

	public void center() {

		FontRenderContext context = ScreensHolder.getInstance().getFontMetrics(textFont)
				.getFontRenderContext();
		Rectangle2D bounds = textFont.getStringBounds(text, context);
		this.positionX = (int) (LoadingScreen.getInstance().getWidth() / 2 - bounds
				.getWidth() / 2);
		this.positionY = (int) (LoadingScreen.getInstance().getHeight() * (1f / 3f + 1f / 14f * line));
	}

	public void setText(String text) {
		this.text = text;
		switch (disposition) {
		case CENTER: 
			center();
			break;
		case LEFT_BOTTOM_CORNER:
			break;
		default:
			break;
		}
		//ScreensHolder.getInstance().repaint();
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.setFont(textFont);
		g2.setColor(Color.WHITE);
		g2.drawString(text, positionX, positionY);
	}

	public int getY() {
		return positionY;
	}

	public enum Alignment {
		CENTER, LEFT_BOTTOM_CORNER
	}

	private String text;
	private int positionX, positionY;
	private int line;
	private Font textFont;
	private Alignment disposition;

}
