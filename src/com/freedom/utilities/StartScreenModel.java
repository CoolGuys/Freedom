package com.freedom.utilities;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.freedom.utilities.SoundEngine.SoundPlayer;
import com.freedom.view.StartScreen;

public class StartScreenModel {

	private StartScreenModel()
	{
		logger.setLevel(Level.OFF);

		try {
			backgroundPicture = ImageIO.read(new File(
					"Resource/UtilityPictures/startScreenBackground.png"));
			backgroundMusic = new File("Resource/Sound/BackgroundMusic.au");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addButtons() {
		buttons[0] = new GButton("GAME", 2, 4,
				"com.freedom.view.StartScreen$StartGameAction");
		buttons[1] = new GButton("EXIT", 12, 6,
				"com.freedom.view.StartScreen$ExitGameAction");
		buttons[3] = new GButton("LOAD", 4, 5,
				"com.freedom.view.StartScreen$LoadGameAction");

		buttons[4] = new GButton("NEW", 4, 3,
				"com.freedom.view.StartScreen$NewGameAction");
	}

	public void activate() {
		backgroundMusicPlayer = SoundEngine.playClip(backgroundMusic, 1679500,
				-20);
	}

	public void deactivate() {
		backgroundMusicPlayer.stopPlaying();

		for (GButton b : buttons) 
			if (b != null)
				b.reset();
	}

	public static StartScreenModel getInstance() {
		return INSTANCE;
	}

	public String reactToClick(Point p) {
		for (GButton b : buttons) {
			if (b != null)
				if (!b.checkIfPressed(p).equals("WasNotPressed"))
					return b.actionName;
		}
		return "NothingHappened";
	}

	public void draw(Graphics g) {

		g.drawImage(backgroundPicture, this.calculateBackgroundPosition().x,
				this.calculateBackgroundPosition().y, StartScreen.getInstance()
						.getWidth(),
				StartScreen.getInstance().getWidth() * 761 / 1516, null);

		for (GButton b : buttons)
			if (b != null)
				b.draw(g);
	}

	private double[] calculateBackgroundParameters() {
		double[] parameters = new double[2];

		double aspect = 761.0 / 1516.0;
		double width = (double) StartScreen.getInstance().getWidth();

		parameters[0] = (2.0 * width * (aspect - 0.5));
		parameters[1] = (1.0 / 12.0 * width * (14.0 - 26.0 * aspect));
		logger.info(Arrays.toString(parameters));
		return parameters;
	}

	private Point calculateBackgroundPosition() {
		Point p = new Point();

		p.x = 0;
		p.y = -StartScreen.getInstance().getWidth() * 761 / 3032
				+ StartScreen.getInstance().getHeight() / 2;
		return p;
	}

	public void reactToRollOver(Point point) {
		for (GButton b : buttons) {
			if (b != null)
				if(b.checkRollOver(point)) 
					StartScreen.getInstance().repaint();
					
		}
	}
	
	private GButton[] buttons = new GButton[5];
	private Image backgroundPicture;
	private File backgroundMusic;
	private SoundPlayer backgroundMusicPlayer;
	private static final StartScreenModel INSTANCE = new StartScreenModel();

	Logger logger = Logger.getLogger("StartScreenModel");

	private class GButton {
		public GButton(String text, int cellX, int cellY, String actionName)
		{
			this.actionName = actionName;
			this.text = text;

			try {
				texture = ImageIO.read(new File(
						"Resource/UtilityPictures/GButton.png"));
				buttonClickedSound = new File(
						"Resource/Sound/ButtonClicked.wav");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			calculateMyParameters(cellX, cellY);
		}


		public void calculateMyParameters(int cellX, int cellY) {
			double[] parameters = calculateBackgroundParameters();

			dimensionX = dimensionY = (int) parameters[1];
			positionX = (int) ((cellX) * parameters[0] + (cellX - 1)
					* parameters[1]);
			positionY = (int) (calculateBackgroundPosition().y + (cellY)
					* parameters[0] + (cellY - 1) * parameters[1]);

		}

		public String checkIfPressed(Point p) {
			if ((p.getX() >= this.positionX
					&& p.getX() <= this.positionX + dimensionX
					&& p.getY() >= this.positionY && p.getY() <= this.positionY
					+ dimensionY)) {

				SoundEngine.playClip(buttonClickedSound, -1, -10);
				return actionName;
			}
			return "WasNotPressed";
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
			g.drawImage(texture, positionX, positionY, dimensionX, dimensionY,
					null);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds;
			if (buttonFont == null) {
				Font tF = new Font("Monospaced", Font.PLAIN, 100);
				bounds = tF.getStringBounds("A", context);
				buttonFont = new Font(
						"Monospaced",
						Font.PLAIN,
						(int) (dimensionX * -bounds.getY() / bounds.getWidth() / 4));
			}

			bounds = buttonFont.getStringBounds(text, context);
			logger.info(bounds.toString());
			g2.setFont(buttonFont);
			g2.setColor(textColor);
			g2.drawString(text,
					(int) (positionX + 1.0 / 2.0 * (dimensionX - bounds
							.getWidth())), (int) (positionY + dimensionY * 1
							/ 2 - 0.5 * bounds.getY()));
		}

		private int positionX, positionY;
		private int dimensionX, dimensionY;
		private String text;
		private Image texture;
		private Color textColor=Color.LIGHT_GRAY;
		private File buttonClickedSound;
		private Font buttonFont;
		public final String actionName;

	}

	

}
