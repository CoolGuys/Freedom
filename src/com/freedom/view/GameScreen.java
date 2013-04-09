package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.*;
import com.freedom.gameObjects.*;
import com.freedom.utilities.StartScreenModel;

@SuppressWarnings("serial")
public class GameScreen extends JLayeredPane {

	private GameScreen()
	{
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setOpaque(true);
		this.createInputMap();
		this.createMovementController();
		GameField.getInstance().loadLevel("TEST", 1);

	}

	public void createInputMap() {
		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("W"), "move.up");
		imap.put(KeyStroke.getKeyStroke("D"), "move.right");
		imap.put(KeyStroke.getKeyStroke("A"), "move.left");
		imap.put(KeyStroke.getKeyStroke("S"), "move.down");
		imap.put(KeyStroke.getKeyStroke("U"), "interact");
		imap.put(KeyStroke.getKeyStroke("I"), "turn.up");
		imap.put(KeyStroke.getKeyStroke("L"), "turn.right");
		imap.put(KeyStroke.getKeyStroke("J"), "turn.left");
		imap.put(KeyStroke.getKeyStroke("K"), "turn.down");
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");

	}

	private void createMovementController() {
		CoarseMovementAction moveUp = new CoarseMovementAction("N");
		CoarseMovementAction moveDown = new CoarseMovementAction("S");
		CoarseMovementAction moveLeft = new CoarseMovementAction("W");
		CoarseMovementAction moveRight = new CoarseMovementAction("E");
		FineMovementAction turnUp = new FineMovementAction("N");
		FineMovementAction turnDown = new FineMovementAction("S");
		FineMovementAction turnLeft = new FineMovementAction("W");
		FineMovementAction turnRight = new FineMovementAction("E");
		PauseAction pause = new PauseAction();
		InteractAction interact = new InteractAction();
		ActionMap amap = this.getActionMap();
		amap.put("move.up", moveUp);
		amap.put("move.down", moveDown);
		amap.put("move.left", moveLeft);
		amap.put("move.right", moveRight);
		amap.put("pause", pause);
		amap.put("interact", interact);
		amap.put("turn.up", turnUp);
		amap.put("turn.left", turnLeft);
		amap.put("turn.right", turnRight);
		amap.put("turn.down", turnDown);
		
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

	private class CoarseMovementAction extends AbstractAction {
		public CoarseMovementAction(String name)
		{
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent e) {
			GameField.getRobot().moveCoarse((String) getValue(Action.NAME));
		}
	}

	private class PauseAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			logger.entering("PauseAction", "actionPerformed");
			ScreensHolder.swapScreens(StartScreen.getInstance(),
					GameScreen.getInstance());
			StartScreenModel.getInstance().activate();
			logger.info("Paused");
			logger.exiting("PauseAction", "actionPerformed");
		}
	}

	private class InteractAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (GameField.getRobot().getIfEmpty())
				GameField.getRobot().take();
			else
				GameField.getRobot().put();
		}
	}

	private class FineMovementAction extends AbstractAction {
		public FineMovementAction(String name)
		{
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent e) {
			GameField.getRobot().moveFine((String) getValue(Action.NAME));
			logger.info("LOL");
		}
	}
}
