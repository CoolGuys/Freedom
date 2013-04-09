package com.freedom.view;

import java.awt.Color;
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

import com.freedom.utilities.GAction;
import com.freedom.utilities.SoundEngine;
import com.freedom.utilities.SoundEngine.SoundPlayer;
import com.freedom.utilities.StartScreenModel;

@SuppressWarnings("serial")
public class StartScreen extends JLayeredPane {
	private StartScreen()
	{
		logger.setLevel(Level.OFF);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setBackground(Color.BLACK);
		this.setOpaque(true);
		this.addMouseListener(new MouseHandler());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		startScreenModel.draw(g);
	}

	public static void activateModel() {
		startScreenModel.activate();
	}
	
	public static void deactivateModel() {
		startScreenModel.deactivate();
	}
	
	public static StartScreen getInstance() {
		return INSTANCE;
	}

	private static StartScreenModel startScreenModel = StartScreenModel.getInstance();

	private static final StartScreen INSTANCE = new StartScreen();
	Logger logger = Logger.getLogger("StartScreen");

	
	
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			String s = startScreenModel.reactToClick(e.getPoint());
			if (!s.equals("NothingHappened")) {
				GAction m;
				try {
					m = (GAction) Class.forName(s).newInstance();
					m.performAction();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	public static class StartGameAction extends GAction {
		public void performAction() {
			deactivateModel();
			ScreensHolder.swapScreens(GameScreen.getInstance(), StartScreen.getInstance());
		}
	}
	public static class ExitGameAction extends GAction {
		public void performAction() {
			System.exit(0);
		}
	}
}
