package com.freedom.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.utilities.game.SoundEngine;
import com.freedom.utilities.game.SoundEngine.SoundPlayer;
import com.freedom.utilities.interfai.GAction;
import com.freedom.utilities.interfai.GButton;
import com.freedom.view.EditorSettingsScreen;
import com.freedom.view.LoadScreen;
import com.freedom.view.ScreensHolder;
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
				new StartGameAction());
		buttons[1] = new GButton("EXIT", 12, 6,
				new ExitGameAction());

		buttons[3] = new GButton("LOAD", 4, 5,
				new LoadGameAction());

		buttons[4] = new GButton("NEW", 4, 3,
				new NewGameAction());

		buttons[5] = new GButton("EDIT", 8, 4,
				new LoadEditorAction());
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

	public void reactToClick(Point p) {
		for (GButton b : buttons) {
			if (b != null)
				b.checkIfPressed(p);
		}
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

	public double[] calculateBackgroundParameters() {
		double[] parameters = new double[2];

		double aspect = 761.0 / 1516.0;
		double width = (double) StartScreen.getInstance().getWidth();

		parameters[0] = (2.0 * width * (aspect - 0.5));
		parameters[1] = (1.0 / 12.0 * width * (14.0 - 26.0 * aspect));
		logger.info(Arrays.toString(parameters));
		return parameters;
	}

	public Point calculateBackgroundPosition() {
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
	
	private GButton[] buttons = new GButton[10];
	private Image backgroundPicture;
	private File backgroundMusic;
	private SoundPlayer backgroundMusicPlayer;
	private static final StartScreenModel INSTANCE = new StartScreenModel();

	Logger logger = Logger.getLogger("StartScreenModel");

	public static class StartGameAction extends GAction {
		public void performAction() {	

		}
	}
	
	public static class NewGameAction extends GAction {
		public void performAction() {
			LoadScreenModel.getInstance().setListedDirectory("Levels");
			ScreensHolder.getInstance().swapScreens(LoadScreen.getInstance(),
					StartScreen.getInstance());

		}
	}
	
	public static class LoadGameAction extends GAction {
		public void performAction() {
			LoadScreenModel.getInstance().setListedDirectory("Saves");
			ScreensHolder.getInstance().swapScreens(LoadScreen.getInstance(),
					StartScreen.getInstance());

		}
	}
	
	public static class LoadEditorAction extends GAction {
		public void performAction() {
			ScreensHolder.getInstance().swapScreens(EditorSettingsScreen.getInstance(),
					StartScreen.getInstance());

		}
	}

	public static class ExitGameAction extends GAction {
		public void performAction() {
			System.exit(0);
		}
	}
	

	

}
