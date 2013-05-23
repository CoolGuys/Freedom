package com.freedom.utilities.interfai;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import com.freedom.gameObjects.characters.Robot;
import com.freedom.model.GameField;
import com.freedom.view.LoadingScreen;

public class HitPointDisplay {

	public void draw(Graphics g) {

		String message = GameField.getInstance().getRobot().getLives() + " Robot colour: " + GameField.getInstance().getRobot().getColour();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		FontRenderContext context = g2.getFontRenderContext();
		g2.setFont(messageFont);

		Rectangle2D bounds = messageFont.getStringBounds(message, context);
		g2.setColor(new Color(0, 0, 0, 0.5f));
		Rectangle2D rect = new Rectangle(
				x,
				(int) (y
						- messageFont.getStringBounds("A", context).getHeight() + messageFont
						.getLineMetrics("A", context).getDescent()),(int) bounds.getWidth(),
				(int) bounds.getHeight());
		g2.fill(rect);


		if(GameField.getInstance().getRobot().getLives()<Robot.maxLives/10)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Durability: "+message, this.x, this.y);
	}

	private int x = 50, y = 50;
	private Font messageFont = new Font("Courier", Font.PLAIN, LoadingScreen
			.getInstance().getHeight() / 30);
}
