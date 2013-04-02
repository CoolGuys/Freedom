package com.freedom.core;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.freedom.core.SoundEngine.SoundPlayer;

@SuppressWarnings("serial")
public class StartScreen extends JLayeredPane {
	private StartScreen()
	{
		logger.setLevel(Level.OFF);
		this.addMouseListener(new MouseHandler());

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
	public int reactToClick(Point2D p) {
		if ((p.getX() >= buttons[1].positionX
				&& p.getX() <= buttons[1].positionX + 100
				&& p.getY() >= buttons[1].positionY && p.getY() <= buttons[1].positionY + 35))
			return 1;
		else if ((p.getX() >= buttons[2].positionX
				&& p.getX() <= buttons[2].positionX + 100
				&& p.getY() >= buttons[2].positionY && p.getY() <= buttons[2].positionY + 35))
			return 2;

		return 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		logger.info("Painting");
		g.drawImage(wallpaper, 0, 0, this.getWidth(), this.getHeight(), null);
		buttons[1].draw(g);
		buttons[2].draw(g);
	}
	
	public static StartScreen getInstance() {
		return INSTANCE;
	}

	private GButton[] buttons;
	private Image wallpaper;
	private File buttonClickedSound;
	private File backgroundMusic;
	private SoundPlayer backgroundMusicPlayer; 
	
	private static final StartScreen INSTANCE = new StartScreen();

	Logger logger = Logger.getLogger("StartScreen");
	private class MouseHandler extends MouseAdapter {

		// 1==start
		// 2==exit
		@Override
		public void mouseClicked(MouseEvent e) {
			if (reactToClick(e.getPoint()) == 1) {
				
					deactivate();
					SoundEngine.playClip(buttonClickedSound, -1, -20);
				
				ScreensHolder.swapDisplays(ScreensHolder.getInstance().getGameScreen(),
						ScreensHolder.getInstance().getStartScreen());
				
				
			} else if (reactToClick(e.getPoint()) == 2) {
				System.exit(0);
			}
		}
	}
}

class GButton {
	public GButton(String aText, int posX, int posY)
	{
		positionX = posX;
		positionY = posY;
		text = aText;
		try {
			texture = ImageIO.read(new File("Resource/Textures/GButton.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		g.drawImage(texture, positionX, positionY, 100, 35, null);
		g.drawString(text, positionX + 40, positionY + 15);
	}

	int positionX, positionY;
	String text;
	Image texture;

}