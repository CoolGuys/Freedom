package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.*;
import javax.swing.*;
import com.freedom.gameObjects.GameField;
import com.freedom.utilities.AbstractScreen;
import com.freedom.utilities.GAction;
import com.freedom.utilities.StartScreenModel;

@SuppressWarnings("serial")
public class StartScreen extends AbstractScreen {
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

	public void activateModel() {
		startScreenModel.activate();
	}
	
	public void deactivateModel() {
		startScreenModel.deactivate();
	}
	
	public static StartScreen getInstance() {
		return INSTANCE;
	}

	private static StartScreenModel startScreenModel = StartScreenModel.getInstance();

	private static StartScreen INSTANCE = new StartScreen();
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
			StartScreen.getInstance().deactivateModel();
			GameField.getInstance().loadLevel("TEST", 1);
			ScreensHolder.swapScreens(GameScreen.getInstance(), StartScreen.getInstance());

		}
	}
	public static class ExitGameAction extends GAction {
		public void performAction() {
			System.exit(0);
		}
	}
	
	public static class SaveLevelAction extends GAction {
		public void performAction() {
			GameField.getInstance().saveLevel("Levels/LevelSaveTest.lvl", 1);	
		}
	}
}
