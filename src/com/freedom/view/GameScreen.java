package com.freedom.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;

import com.freedom.gameObjects.characters.Robot;
import com.freedom.gameObjects.controlled.Box;
import com.freedom.model.GameField;

@SuppressWarnings("serial")
public class GameScreen extends AbstractScreen {

	private GameScreen() {
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setOpaque(true);
		this.createInputMap();
		this.createMovementController();
		setDoubleBuffered(true);

		//setDebugGraphicsOptions(DebugGraphics.);
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
		imap.put(KeyStroke.getKeyStroke("U"), "interact");
		imap.put(KeyStroke.getKeyStroke("E"), "examine");
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
		BoxGiver boxGiver = new BoxGiver();
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
		amap.put("give.box", boxGiver);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GameField.getInstance().draw(g);
	}

	public void activateModel() {
		GameField.getInstance().activate();
		ScreensHolder.getInstance().add(guiPane);
		// ScreensHolder.getInstance().moveToFront(guiPane);
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

	public Point рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(Robot robot) {
		int deltaX = -this.getX() + ScreensHolder.getInstance().getWidth() / 2
				- robot.getTargetCellCoordinates(robot.getDirection()).x
				* Robot.getSize();
		int deltaY = -this.getY() + ScreensHolder.getInstance().getHeight() / 2
				- robot.getTargetCellCoordinates(robot.getDirection()).y
				* Robot.getSize();
		return new Point(deltaX, deltaY);
	}

	public void центрироватьПоРоботуПоВертикали(Robot robot) {

		setLocation(getX(), getY()
				+ рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(robot).y);
	}

	public void центрироватьПоРоботуПоГоризонтали(Robot robot) {

		setLocation(getX()
				+ рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(robot).x, getY());
	}

	public void центрироватьПоРоботу(Robot robot) {

		setLocation(getX()
				+ рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(robot).x, getY()
				+ рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(robot).y);
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

	private InGameMessageDisplay msgDisplay = new InGameMessageDisplay();
	private InGameGUIPane guiPane = new InGameGUIPane();
	private int scale = 50;
	private final int fineOffset = scale / 2;
	private final int coarseOffset = (scale * 3) / 2;

	Logger logger = Logger.getLogger("GameScreen");
	private static GameScreen INSTANCE;

	private class CoarseMovementAction extends AbstractAction {
		public CoarseMovementAction(String name) {
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
	

	private class BoxGiver extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			
				Robot r = GameField.getInstance().getRobot();
				r.setContainer(new Box());
				r.container[0].setColour("Blue");
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
		public FineMovementAction(String name) {
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			GameField.getInstance().getRobot()
					.moveFine((String) getValue(Action.NAME));
		}
	}

	private class FieldCoarseOffsetAction extends AbstractAction {
		public FieldCoarseOffsetAction(String name) {
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("Offset requested");
			changeOffsetCoarse((String) getValue(Action.NAME));
		}
	}

	private class FieldFineOffsetAction extends AbstractAction {
		public FieldFineOffsetAction(String name) {
			putValue(Action.NAME, name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.info("Offset requested");
			changeOffsetFine((String) getValue(Action.NAME));
		}
	}

	private class InGameMessageDisplay {
		public void displayMessage(String message) {
			adapt(message);
			this.visible = true;
		}

		public void removeMessage() {
			this.visible = false;
			this.messageLines.clear();
		}

		public void adapt(String message) {
			FontRenderContext context = getFontMetrics(messageFont)
					.getFontRenderContext();
			Rectangle2D bounds = messageFont.getStringBounds("A", context);
			// logger.info(message);
			int symbolsOnLine = (int) (this.width / bounds.getWidth());
			Scanner in = new Scanner(message);
			String buffer = "";

			while (in.hasNext()) {
				String nextWord = in.next();
				if (buffer.length() + nextWord.length() + 1 > symbolsOnLine) {
					messageLines.add(buffer);
					buffer = "";
				}
				if (buffer.length() + nextWord.length() + 1 <= symbolsOnLine) {
					buffer = buffer.concat(nextWord).concat(" ");
				}
			}
			this.messageLines.add(buffer);
			this.height = (int) (messageLines.size() * bounds.getHeight());
			this.y = (int) (ScreensHolder.getInstance().getHeight() - height);
			this.x = (int) (ScreensHolder.getInstance().getWidth() / 6.0);
		}

		public void draw(Graphics g) {
			if (!visible)
				return;
			String[] toDisp = messageLines.toArray(new String[1]);
			// logger.info(toDisp[0]);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			FontRenderContext context = g2.getFontRenderContext();
			g2.setFont(messageFont);
			g2.setColor(new Color(0, 0, 0, 0.5f));
			Rectangle2D rect = new Rectangle(x,
					(int) (y
							- messageFont.getStringBounds("A", context)
									.getHeight() + messageFont.getLineMetrics(
							"A", context).getDescent()), width, height);
			g2.fill(rect);

			g2.setColor(Color.WHITE);
			Rectangle2D bounds = messageFont.getStringBounds("A", context);
			int i = 0;
			for (String line : toDisp) {
				g2.drawString(line, this.x, (int) (this.y + bounds.getHeight()
						* i));
				i++;
			}
		}

		private int x, y;
		private int width = (int) (ScreensHolder.getInstance().getWidth() * 2.0 / 3.0);
		private int height;
		private Font messageFont = new Font("Monospaced", Font.PLAIN,
				LoadingScreen.getInstance().getHeight() / 30);
		private ArrayList<String> messageLines = new ArrayList<String>();
		private boolean visible;
	}

	public class InGameGUIPane extends JLayeredPane {
		public InGameGUIPane() {
			this.setBounds(ScreensHolder.getInstance().getBounds());
			this.setLocation(0, 0);
			this.setVisible(true);
		}

		public void paintComponent(Graphics g) {
			msgDisplay.draw(g);
		}

	}
}
