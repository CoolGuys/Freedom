package com.freedom.view;


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

import com.freedom.utilities.SoundEngine;
import com.freedom.utilities.SoundEngine.SoundPlayer;
import com.freedom.utilities.StartScreenModel;

@SuppressWarnings("serial")
public class StartScreen extends JLayeredPane {
	private StartScreen()
	{
		logger.setLevel(Level.OFF);
		this.addMouseListener(new MouseHandler());

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

	private StartScreenModel startScreenModel = StartScreenModel.getInstance();
	
	private static final StartScreen INSTANCE = new StartScreen();

	Logger logger = Logger.getLogger("StartScreen");
	
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			startScreenModel.reactToClick(e.getPoint());
			
			
			
			/*if (reactToClick(e.getPoint()) == 1) {
				
					deactivate();
					SoundEngine.playClip(buttonClickedSound, -1, -20);
				
				ScreensHolder.swapDisplays(ScreensHolder.getInstance().getGameScreen(),
						ScreensHolder.getInstance().getStartScreen());
				
				
			} else if (reactToClick(e.getPoint()) == 2) {
				System.exit(0);
			}*/
		}
	}
}

