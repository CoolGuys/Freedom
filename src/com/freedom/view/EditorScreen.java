package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;

import com.freedom.gameObjects.base.Stuff.StuffColor;
import com.freedom.gameObjects.characters.Robot;
import com.freedom.gameObjects.characters.RobotEditor;
import com.freedom.gameObjects.controlled.Box;
import com.freedom.gameObjects.controlled.Door;
import com.freedom.gameObjects.controlled.Teleport;
import com.freedom.gameObjects.controllers.ButtonAnd;
import com.freedom.gameObjects.controllers.ButtonOr;
import com.freedom.gameObjects.healthOperators.TNT;
import com.freedom.gameObjects.uncontrolled.Pit;
import com.freedom.gameObjects.uncontrolled.Tile;
import com.freedom.gameObjects.uncontrolled.Wall;
import com.freedom.model.GameField;
import com.freedom.utilities.interfai.HitPointDisplay;

@SuppressWarnings("serial")
public class EditorScreen extends GameScreen {
	private EditorScreen()
	{
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setOpaque(true);
		this.createInputMap();
		this.createMovementController();
		setDoubleBuffered(true);
		logger.setLevel(Level.WARNING);
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
		imap.put(KeyStroke.getKeyStroke("O"), "assign");
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

		imap.put(KeyStroke.getKeyStroke("1"), "give.wall");
		imap.put(KeyStroke.getKeyStroke("2"), "give.tile");
		imap.put(KeyStroke.getKeyStroke("3"), "give.pit");
		imap.put(KeyStroke.getKeyStroke("4"), "give.tnt");
		imap.put(KeyStroke.getKeyStroke("5"), "give.teleport");
		imap.put(KeyStroke.getKeyStroke("6"), "give.buttonAnd");
		imap.put(KeyStroke.getKeyStroke("7"), "give.buttonOr");
		imap.put(KeyStroke.getKeyStroke("8"), "give.door");
		imap.put(KeyStroke.getKeyStroke("9"), "give.box");
		imap.put(KeyStroke.getKeyStroke("0"), "give.box");

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
		AssignAction assign = new AssignAction();
		BoxGiver boxGiver = new BoxGiver();
		DoorGiver doorGiver = new DoorGiver();
		WallGiver wallGiver = new WallGiver();
		ButtonOrGiver buttonOrGiver = new ButtonOrGiver();
		ButtonAndGiver buttonAndGiver = new ButtonAndGiver();
		TeleportGiver teleportGiver = new TeleportGiver();
		PitGiver pitGiver = new PitGiver();
		TNTGiver tntGiver = new TNTGiver();
		TileGiver tileGiver = new TileGiver();
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
		amap.put("assign", assign);
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
		amap.put("give.box", boxGiver);
		amap.put("give.tile", tileGiver);
		amap.put("give.wall", wallGiver);
		amap.put("give.door", doorGiver);
		amap.put("give.buttonOr", buttonOrGiver);
		amap.put("give.buttonAnd", buttonAndGiver);
		amap.put("give.tnt", tntGiver);
		amap.put("give.teleport", teleportGiver);
		amap.put("give.pit", pitGiver);
	}

	public void activateModel() {
		GameField.getInstance().activate();
		ScreensHolder.getInstance().add(guiPane);
		ScreensHolder.getInstance().moveToFront(guiPane);
	}

	public static EditorScreen getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new EditorScreen();
		else
			return INSTANCE;
	}

	private static EditorScreen INSTANCE;

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

	private class DoorGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new Door());
			repaint();

		}
	}

	private class TileGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new Tile());
			repaint();

		}
	}

	private class ButtonOrGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new ButtonOr());
			repaint();

		}
	}

	private class ButtonAndGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new ButtonAnd());
		}
	}

	private class TNTGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new TNT());
			repaint();

		}
	}

	private class TeleportGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new Teleport());
			repaint();

		}
	}

	private class WallGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new Wall());
			repaint();

		}
	}

	private class PitGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			Robot r = GameField.getInstance().getRobot();
			r.setContainer(new Pit());
			repaint();

		}
	}

	private class ExamineAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().getRobot().examineFrontCell();
		}
	}

	private class AssignAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().setRobotEditor( (RobotEditor)GameField.getInstance().getRobot());
			GameField.getInstance().getRobotEditor().assign();
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
