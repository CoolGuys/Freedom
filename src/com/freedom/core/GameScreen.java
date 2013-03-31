package com.freedom.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.*;
import com.freedom.gameObjects.*;

@SuppressWarnings("serial")
public class GameScreen extends JLayeredPane {
	
		private GameScreen()
		{
			setBackground(Color.DARK_GRAY);
			this.createInputMap();
			this.createMovementController();
			GameField.getInstance().loadLevel("TEST", 1);
			
		}
		
		public void setDimensions(int width, int height) {
			this.setSize(width, height);
		}
		
		public void createInputMap() {
			InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
			imap.put(KeyStroke.getKeyStroke("W"), "move.up");
			imap.put(KeyStroke.getKeyStroke("A"), "move.right");
			imap.put(KeyStroke.getKeyStroke("D"), "move.left");
			imap.put(KeyStroke.getKeyStroke("S"), "move.down");
			imap.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
		}
		
		private void createMovementController() {
			MovementAction moveUp = new MovementAction("N");
			MovementAction moveDown = new MovementAction("S");
			MovementAction moveLeft = new MovementAction("E");
			MovementAction moveRight = new MovementAction("W");
			PauseAction pause = new PauseAction();
			ActionMap amap = this.getActionMap();
			amap.put("move.up", moveUp);
			amap.put("move.down", moveDown);
			amap.put("move.left", moveLeft);
			amap.put("move.right", moveRight);
			amap.put("pause", pause);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			GameField.draw(g);
		}
		
		public void activate() {
		}
		
		public static GameScreen getInstance() {
			return INSTANCE;
		}
		
		
		Logger logger = Logger.getLogger("GameScreen");
		private static final GameScreen INSTANCE = new GameScreen();
		
		
		private class MovementAction extends AbstractAction {
			public MovementAction(String name)
			{
				putValue(Action.NAME, name);
			}

			public void actionPerformed(ActionEvent e) {
				GameField.getRobot().moveToNextTile((String) getValue(Action.NAME));
			}
		}

		private class PauseAction extends AbstractAction {
			public void actionPerformed(ActionEvent e) {
				logger.entering("PauseAction", "actionPerformed");
				ScreensHolder.swapDisplays(ScreensHolder.getInstance().getStartScreen(), ScreensHolder.getInstance().getGameScreen());
				ScreensHolder.getInstance().getStartScreen().activate();
				logger.info("Paused");
				logger.exiting("PauseAction", "actionPerformed");
			}
		}
		

}
