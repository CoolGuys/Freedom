package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import com.freedom.gameObjects.*;
import com.freedom.utilities.AbstractScreen;
import com.freedom.utilities.StartScreenModel;

@SuppressWarnings("serial")
public class GameScreen extends AbstractScreen {

	private GameScreen()
	{
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setOpaque(true);
		this.createInputMap();
		this.createMovementController();

		logger.setLevel(Level.OFF);

	}
	
	public void prepareModel () {
		GameField.getInstance().setCellSize(scale);
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

		imap.put(KeyStroke.getKeyStroke("shift S"), "offset.up");
		imap.put(KeyStroke.getKeyStroke("shift A"), "offset.right");
		imap.put(KeyStroke.getKeyStroke("shift D"), "offset.left");
		imap.put(KeyStroke.getKeyStroke("shift W"), "offset.down");
		imap.put(KeyStroke.getKeyStroke("shift K"), "fineOffset.up");
		imap.put(KeyStroke.getKeyStroke("shift J"), "fineOffset.right");
		imap.put(KeyStroke.getKeyStroke("shift L"), "fineOffset.left");
		imap.put(KeyStroke.getKeyStroke("shift I"), "fineOffset.down");

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
		FieldCoarseOffsetAction offsetUp = new FieldCoarseOffsetAction("N");
		FieldCoarseOffsetAction offsetDown = new FieldCoarseOffsetAction("S");
		FieldCoarseOffsetAction offsetLeft = new FieldCoarseOffsetAction("W");
		FieldCoarseOffsetAction offsetRight = new FieldCoarseOffsetAction("E");
		FieldFineOffsetAction fineOffsetUp = new FieldFineOffsetAction("N");
		FieldFineOffsetAction fineOffsetDown = new FieldFineOffsetAction("S");
		FieldFineOffsetAction fineOffsetLeft = new FieldFineOffsetAction("W");
		FieldFineOffsetAction fineOffsetRight = new FieldFineOffsetAction("E");

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

		amap.put("offset.up", offsetUp);
		amap.put("offset.left", offsetLeft);
		amap.put("offset.right", offsetRight);
		amap.put("offset.down", offsetDown);
		amap.put("fineOffset.up", fineOffsetUp);
		amap.put("fineOffset.left", fineOffsetLeft);
		amap.put("fineOffset.right", fineOffsetRight);
		amap.put("fineOffset.down", fineOffsetDown);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GameField.getInstance().draw(g);
	}

	public void activateModel() {
		GameField.getInstance().activate();

	}

	public static GameScreen getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameScreen();
		else
			return INSTANCE;
	}

	public void changeOffsetCoarse(String direction) {
		logger.info("Offsettig");
		if (direction.equals("N"))
			setLocation(this.getLocation().x, this.getLocation().y
					- coarseOffset);
		if (direction.equals("W"))
			setLocation(this.getLocation().x - coarseOffset,
					this.getLocation().y);
		if (direction.equals("E"))
			setLocation(this.getLocation().x + coarseOffset,
					this.getLocation().y);
		if (direction.equals("S"))
			setLocation(this.getLocation().x, this.getLocation().y + scale
					+ coarseOffset);
		revalidate();
		repaint();
	}

	public void changeOffsetFine(String direction) {
		logger.info("Offsettig");
		if (direction.equals("N"))
			setLocation(this.getLocation().x, this.getLocation().y - fineOffset);
		if (direction.equals("W"))
			setLocation(this.getLocation().x - fineOffset, this.getLocation().y);
		if (direction.equals("E"))
			setLocation(this.getLocation().x + fineOffset, this.getLocation().y);
		if (direction.equals("S"))
			setLocation(this.getLocation().x, this.getLocation().y + fineOffset);
		revalidate();
		repaint();
	}

	public void deactivateModel() {
		GameField.getInstance().deactivate();
	}
	
	private int scale = 50;
	private final int fineOffset = scale / 2;
	private final int coarseOffset = (scale * 3) / 2;

	Logger logger = Logger.getLogger("GameScreen");
	private static GameScreen INSTANCE;

	private class CoarseMovementAction extends AbstractAction {
		public CoarseMovementAction(String name)
		{
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().getRobot()
					.moveCoarse((String) getValue(Action.NAME));
		}
	}

	private class PauseAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			deactivateModel();
			ScreensHolder.getInstance().addScreen(PauseScreen.getInstance());
			PauseScreen.getInstance().activateModel();
			
			logger.info("Paused");
		}
	}

	private class InteractAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (GameField.getInstance().getRobot().getIfEmpty())
				GameField.getInstance().getRobot().take();
			else
				GameField.getInstance().getRobot().put();
		}
	}

	private class FineMovementAction extends AbstractAction {
		public FineMovementAction(String name)
		{
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().getRobot()
					.moveFine((String) getValue(Action.NAME));
		}
	}

	private class FieldCoarseOffsetAction extends AbstractAction {
		public FieldCoarseOffsetAction(String name)
		{
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("Offset requested");
			changeOffsetCoarse((String) getValue(Action.NAME));
		}
	}

	private class FieldFineOffsetAction extends AbstractAction {
		public FieldFineOffsetAction(String name)
		{
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("Offset requested");
			changeOffsetFine((String) getValue(Action.NAME));
		}
	}
}
