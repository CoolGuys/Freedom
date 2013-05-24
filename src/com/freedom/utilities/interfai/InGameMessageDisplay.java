package com.freedom.utilities.interfai;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Scanner;

import com.freedom.view.GameScreen;
import com.freedom.view.LoadingScreen;
import com.freedom.view.ScreensHolder;

public class InGameMessageDisplay {
	public void displayMessage(String message) {
		adapt(message);
		this.visible = true;
	}

	public void removeMessage() {
		this.visible = false;
		this.messageLines.clear();
	}

	public void adapt(String message) {
		FontRenderContext context = GameScreen.getInstance().getFontMetrics(messageFont)
				.getFontRenderContext();
		Rectangle2D bounds = messageFont.getStringBounds("A", context);
		// logger.info(message);
		int symbolsOnLine = (int) (this.width / bounds.getWidth());
		Scanner in = new Scanner(message);
		String buffer = "";

		while (in.hasNext()) {
			String nextWord = in.next();
			if (buffer.length() + nextWord.length() + 1 > symbolsOnLine) {
				messageLines.add(buffer);
				buffer = "";
			}
			if (buffer.length() + nextWord.length() + 1 <= symbolsOnLine) {
				buffer = buffer.concat(nextWord).concat(" ");
			}
		}
		this.messageLines.add(buffer);
		this.height = (int) (messageLines.size() * bounds.getHeight());
		this.y = ScreensHolder.getInstance().getHeight() - height;
		this.x = (int) (ScreensHolder.getInstance().getWidth() / 6.0);
		in.close();
	}

	public void draw(Graphics g) {
		if (!visible)
			return;
		String[] toDisp = messageLines.toArray(new String[1]);
		// logger.info(toDisp[0]);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		FontRenderContext context = g2.getFontRenderContext();
		g2.setFont(messageFont);
		g2.setColor(new Color(0, 0, 0, 0.5f));
		Rectangle2D rect = new Rectangle(
				x,
				(int) (y
						- messageFont.getStringBounds("A", context).getHeight() + messageFont
						.getLineMetrics("A", context).getDescent()), width,
				height);
		g2.fill(rect);

		g2.setColor(Color.WHITE);
		Rectangle2D bounds = messageFont.getStringBounds("A", context);
		int i = 0;
		for (String line : toDisp) {
			g2.drawString(line, this.x, (int) (this.y + bounds.getHeight() * i));
			i++;
		}
	}

	private int x, y;
	private int width = (int) (ScreensHolder.getInstance().getWidth() * 2.0 / 3.0);
	private int height;
	private Font messageFont = new Font("Monospaced", Font.PLAIN, LoadingScreen
			.getInstance().getHeight() / 30);
	private ArrayList<String> messageLines = new ArrayList<String>();
	private boolean visible;
}