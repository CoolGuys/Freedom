package com.freedom.utilities;

import java.awt.*;
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
			backgroundPicture = ImageIO.read(new File("Resource/UtilityPictures/startScreenBackground.png"));
			backgroundMusic = new File("Resource/Sound/BackgroundMusic.au");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addButtons() {
		buttons[0] = new GButton(null, 4, 4, "com.freedom.view.StartScreen$StartGameAction");
	}

	public void activate() {
		backgroundMusicPlayer = SoundEngine.playClip(backgroundMusic, 1679500,
				-20);
	}

	public void deactivate() {
		backgroundMusicPlayer.stopPlaying();
	}

	public static StartScreenModel getInstance() {
		return INSTANCE;
	}

	public String reactToClick(Point p) {
		//for (GButton b : buttons) {
			if (!buttons[0].checkIfPressed(p).equals("WasNotPressed"))
				return buttons[0].actionName;
		//}
		return "NothingHappened";
	}

	public void draw(Graphics g) {

		g.drawImage(backgroundPicture, this.calculateWallpaperPosition().x, this
				.calculateWallpaperPosition().y, StartScreen.getInstance()
				.getWidth(), StartScreen.getInstance().getWidth() * 761 / 1516,
				null);
		buttons[0].draw(g);
	}

	private double[] calculateWallpaperParameters() {
		double[] parameters = new double[2];

		double aspect = 761.0 / 1516.0;
		double width = (double) StartScreen.getInstance().getWidth();

		parameters[0] = (2.0 * width * (aspect - 0.5));
		parameters[1] = (1.0 / 12.0 * width * (14.0 - 26.0 * aspect));
		System.out.println(Arrays.toString(parameters));
		return parameters;
	}

	private Point calculateWallpaperPosition() {
		Point p = new Point();

		p.x = 0;
		p.y = -StartScreen.getInstance().getWidth() * 761 / 3032
				+ StartScreen.getInstance().getHeight() / 2;
		logger.info(p.y+"");
		return p;
	}

	private GButton[] buttons = new GButton[4];
	private Image backgroundPicture;
	private File backgroundMusic;
	private SoundPlayer backgroundMusicPlayer;
	private static final StartScreenModel INSTANCE = new StartScreenModel();

	Logger logger = Logger.getLogger("StartScreenModel");

	private class GButton {
		public GButton(String text, int cellX, int cellY, String actionName)
		{
			this.actionName = actionName;
			
			try {
				texture = ImageIO
						.read(new File("Resource/UtilityPictures/buttonGame.png"));
				buttonClickedSound = new File(
						"Resource/Sound/ButtonClicked.wav");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			calculateMyParameters(cellX, cellY);
		}

		public void calculateMyParameters(int cellX, int cellY) {
			double[] parameters = calculateWallpaperParameters();

			dimensionX = dimensionY = (int) parameters[1];
			positionX = (int) ((cellX)*parameters[0]+(cellX-1)*parameters[1]);
			positionY = (int) (calculateWallpaperPosition().y + (cellY)
					* parameters[0] + (cellY -1)* parameters[1]);
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

		public void draw(Graphics g) {
			g.drawImage(texture, positionX, positionY, dimensionX, dimensionY,
					null);
			// g.drawString(text, positionX + 40, positionY + 15);
		}

		private int positionX, positionY;
		private int dimensionX, dimensionY;
		private int textOffsetX, textOffsetY;
		private String text;
		private Image texture;
		private File buttonClickedSound;
		public final String actionName;

	}

}
