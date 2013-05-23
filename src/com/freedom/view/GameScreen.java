package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DebugGraphics;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;

import com.freedom.gameObjects.base.Stuff.StuffColor;
import com.freedom.gameObjects.characters.Robot;
import com.freedom.gameObjects.controlled.Box;
import com.freedom.gameObjects.healthOperators.TNT;
import com.freedom.model.GameField;
import com.freedom.utilities.interfai.HitPointDisplay;
import com.freedom.utilities.interfai.InGameMessageDisplay;

@SuppressWarnings("serial")
public class GameScreen extends AbstractScreen {

	protected GameScreen()
	{
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setOpaque(true);
		this.createInputMap();
		this.createMovementController();
		//setDoubleBuffered(true);
		setDebugGraphicsOptions(DebugGraphics.LOG_OPTION);

		logger.setLevel(Level.WARNING);
	}

	public void prepareModel() {
		GameField.getInstance().setCellSize(scale);
	}

	public void createInputMap() {
		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("W"), "move.up");
		imap.put(KeyStroke.getKeyStroke("D"), "move.right");
		imap.put(KeyStroke.getKeyStroke("A"), "move.left");
		imap.put(KeyStroke.getKeyStroke("S"), "move.down");
		imap.put(KeyStroke.getKeyStroke("U"), "take");
		imap.put(KeyStroke.getKeyStroke("Q"), "interact");
		imap.put(KeyStroke.getKeyStroke("E"), "examine");
		imap.put(KeyStroke.getKeyStroke("Q"), "interact");
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

		imap.put(KeyStroke.getKeyStroke("B"), "give.box");
		imap.put(KeyStroke.getKeyStroke("T"), "give.tnt");

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
		TakeAction take = new TakeAction();
		InteractAction interact = new InteractAction();
		ExamineAction examine = new ExamineAction();
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
		amap.put("take", take);
		amap.put("interact", interact);
		amap.put("examine", examine);
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
		ScreensHolder.getInstance().add(guiPane);
		ScreensHolder.getInstance().moveToFront(guiPane);
	}

	public static GameScreen getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameScreen();
		else
			return INSTANCE;
	}

	public GameScreen instance() {
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

	public Point calculateDistanceFromRobotToScreenCenter(Robot robot) {
		int deltaX = -this.getX() + ScreensHolder.getInstance().getWidth() / 2
				- robot.getTargetCellCoordinates(robot.getDirection()).x
				* Robot.getSize();
		int deltaY = -this.getY() + ScreensHolder.getInstance().getHeight() / 2
				- robot.getTargetCellCoordinates(robot.getDirection()).y
				* Robot.getSize();
		return new Point(deltaX, deltaY);
	}

	public void centerByRobotVertically(Robot robot) {

		setLocation(getX(), getY()
				+ calculateDistanceFromRobotToScreenCenter(robot).y);
	}

	public void centerByRobotHorisontally(Robot robot) {

		setLocation(getX() + calculateDistanceFromRobotToScreenCenter(robot).x,
				getY());
	}

	public void centerByRobot(Robot robot) {

		setLocation(getX() + calculateDistanceFromRobotToScreenCenter(robot).x,
				getY() + calculateDistanceFromRobotToScreenCenter(robot).y);
		ScreensHolder.getInstance().repaint();
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
		ScreensHolder.getInstance().remove(guiPane);
		msgDisplay.removeMessage();
		GameField.getInstance().deactivate();
	}

	public void displayMessage(String message) {
		msgDisplay.displayMessage(message);
		ScreensHolder.getInstance().moveToFront(guiPane);
		ScreensHolder.getInstance().repaint();
	}

	public void removeMessage() {
		msgDisplay.removeMessage();
		ScreensHolder.getInstance().repaint();
	}

	protected InGameMessageDisplay msgDisplay = new InGameMessageDisplay();
	protected InGameGUIPane guiPane = new InGameGUIPane();
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
			ScreensHolder.getInstance().addScreen(PauseScreen.getInstance());
			logger.info("Paused");
		}
	}

	private class InteractAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().getRobot().interact();
		}
	}

	private class TakeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (GameField.getInstance().getRobot().getIfEmpty())
				GameField.getInstance().getRobot().take();
			else
				GameField.getInstance().getRobot().put();
		}
	}

	@SuppressWarnings("unused")
	private class TNTGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new TNT());
			repaint();

		}
	}

	@SuppressWarnings("unused")
	private class BoxGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			if (r.container[0] != null) {
				if (r.container[0].getColor() == StuffColor.BLUE) {
					r.container[0].setColour("Red");
				} else if (r.container[0].getColor() == StuffColor.RED) {
					r.container[0].setColour("Green");
				} else if (r.container[0].getColor() == StuffColor.GREEN) {
					r.container[0].setColour("Blue");
				}
			} else {
				r.setContainer(new Box());
				r.container[0].setColour("Blue");
			}
			repaint();

		}
	}

	private class ExamineAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().getRobot().examineFrontCell();
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

	public class InGameGUIPane extends JLayeredPane {
		public InGameGUIPane()
		{
			this.setBounds(ScreensHolder.getInstance().getBounds());
			this.setLocation(0, 0);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			msgDisplay.draw(g);
			hpDisp.draw(g);
		}

		private HitPointDisplay hpDisp = new HitPointDisplay();
	}
}
