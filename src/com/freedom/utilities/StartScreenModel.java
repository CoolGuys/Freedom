package com.freedom.utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.freedom.utilities.SoundEngine.SoundPlayer;

public class StartScreenModel {

	private StartScreenModel() {
		logger.setLevel(Level.OFF);

		buttons = new GButton[3];
		buttons[1] = new GButton("Start", 100, 100);
		buttons[2] = new GButton("Exit", 100, 300);
		
		try {
			wallpaper = ImageIO.read(new File(
					"Resource/Textures/StartScreenWallpaper.png"));
			buttonClickedSound = new File("Resource/Sound/ButtonClicked.wav");
			backgroundMusic = new File("Resource/Sound/BackgroundMusic.au");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void activate() {
		backgroundMusicPlayer = SoundEngine.playClip(backgroundMusic, 1679500, -20);
	}

	public void deactivate() {
		backgroundMusicPlayer.stopPlaying();
	}
	
	public static StartScreenModel getInstance(){
		return INSTANCE;
	}
	
	
	public void reactToClick(Point p) {
		for (GButton b : buttons) {
			if (b.checkIfPressed(p))
				b.performAction();
		}
	}
	
	private GButton[] buttons = new GButton[4];
	private Image wallpaper;
	private File buttonClickedSound;
	private File backgroundMusic;
	private SoundPlayer backgroundMusicPlayer; 
	private static final StartScreenModel INSTANCE = new StartScreenModel();

	Logger logger = Logger.getLogger("StartScreenModel");

	
	
	
}
