package com.freedom.utilities;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.freedom.view.PauseScreen;

public class PauseScreenModel {

	private PauseScreenModel()
	{
		logger.setLevel(Level.ALL);

		try {
			background = ImageIO.read(new File(
					"Resource/UtilityPictures/pauseScreenBackground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addButtons() {
		buttons[0] = new GButtonLite("QUIT", 2,
				"com.freedom.view.PauseScreen$QuitAction");
		buttons[1] = new GButtonLite("SAVE", 1,
				"com.freedom.view.PauseScreen$SaveLevelAction");
	}

	public static PauseScreenModel getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new PauseScreenModel();
		else
			return INSTANCE;
	}

	public void activate() {

	}

	public void deactivate() {
		for (GButtonLite b : buttons)
			if (b != null)
				b.reset();
	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, PauseScreen.getInstance().getWidth(),
				PauseScreen.getInstance().getHeight(), null);

		for (GButtonLite b : buttons)
			if (b != null)
				b.draw(g);
	}

	public String reactToClick(Point p) {
		logger.info(p.toString());
		for (GButtonLite b : buttons) {
			if (b != null)
				if (!b.checkIfPressed(p).equals("WasNotPressed"))
					return b.actionName;
		}
		return "NothingHappened";
	}

	public void reactToRollOver(Point point) {
		for (GButtonLite b : buttons) {
			if (b != null)
				if (b.checkRollOver(point))
					PauseScreen.getInstance().repaint();
		}
	}

	private GButtonLite[] buttons = new GButtonLite[5];
	private Image background;

	private static PauseScreenModel INSTANCE;

	private Logger logger = Logger.getLogger("PauseScreenModel");

	private class GButtonLite {
		public GButtonLite(String text, int lineNumber, String actionName)
		{
			this.actionName = actionName;
			this.text = text;
			this.line = lineNumber;
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

		public String checkIfPressed(Point p) {
			if ((p.getX() >= this.positionX
					&& p.getX() <= this.positionX + dimensionX
					&& p.getY() >= this.positionY && p.getY() <= this.positionY
					+ dimensionY)) {

				SoundEngine.playClip(clickedSound, -1, -10);
				return actionName;
			}
			return "WasNotPressed";
		}

		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			FontRenderContext context = g2.getFontRenderContext();
			if(buttonFont==null)
				buttonFont = new Font("Monospaced", Font.PLAIN, PauseScreen
					.getInstance().getHeight() / 15);
			Rectangle2D bounds = buttonFont.getStringBounds(text, context);

			if (dimensionX == 0) {
				dimensionX = (int) bounds.getWidth();
				dimensionY = (int) bounds.getHeight();
				positionX = (int) (PauseScreen.getInstance().getWidth() / 2 - bounds
						.getWidth() / 2);
				metrics = buttonFont.getLineMetrics(text, context);

			}
			g2.setFont(buttonFont);
			g2.setColor(textColor);
			g2.drawString(text, positionX,
					positionY + dimensionY - metrics.getDescent());
		}

		private int line;
		private int dimensionX=0;
		private int dimensionY;
		private int positionX, positionY;
		private String text;
		private Color textColor = Color.LIGHT_GRAY;
		private LineMetrics metrics;
		private File clickedSound;
		private Font buttonFont;
		public final String actionName;
		private final int offsetY = PauseScreen.getInstance().getHeight() / 3;
		private final int gap = PauseScreen.getInstance().getHeight() / 12;

	}

}
