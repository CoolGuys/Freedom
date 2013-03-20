package core;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

import javax.swing.*;

/**
 * Класс GameField содержит все игровые объекты на уровне и осуществляет
 * операции с ними под контролем объекта класса GraphicsHolderAndController
 * Поэтому имеено сюда должен быть добавлен процесс создания уровня, то есть
 * метод, считывающий из файла уровень, удаляющий его из памяти при прохождении,
 * и еще что-нибудь. Сам знаешь, кто, тебе надо будет над этим поработать
 * *****Ушаков, отредактируй это описание после того, как добавишь***
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
class GameField extends JPanel {

	// Иван, подумайте над конструктором, объекты ведь на вас, и напишите норм
	// комментарий
	// @gleb
	public GameField(GraphicsController aController)
	{
		logger.entering("GameField", "<init>", aController);
		logger.info("Creating GameField");
		setPreferredSize(new Dimension(1000, 600));
		setBackground(Color.BLACK);
		this.controller = aController;

		this.createInputMap();
		this.createMovementController();
		player = new Player(10, 10);
		logger.exiting("GraphicsController", "<init>");

	}

	// Метод, создающий таблицу ввода нашей JPanel
	public void createInputMap() {
		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("W"), "move.up");
		imap.put(KeyStroke.getKeyStroke("A"), "move.right");
		imap.put(KeyStroke.getKeyStroke("D"), "move.left");
		imap.put(KeyStroke.getKeyStroke("S"), "move.down");
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
	}

	// Метод, который сопоставляет соответствующие движению поля таблицы ввода
	// полям таблицы действий, которые будут вызывать методы движения робота
	private void createMovementController() {
		MovementAction moveUp = new MovementAction("up");
		MovementAction moveDown = new MovementAction("down");
		MovementAction moveLeft = new MovementAction("left");
		MovementAction moveRight = new MovementAction("right");
		PauseAction pause = new PauseAction();
		ActionMap amap = this.getActionMap();
		amap.put("move.up", moveUp);
		amap.put("move.down", moveDown);
		amap.put("move.left", moveLeft);
		amap.put("move.right", moveRight);
		amap.put("pause", pause);
	}

	public void loadLevel(String pathToPackage, int levelID) {

	}

	// Метод будет производить рисование всего, что лежит в массиве Tile (у
	// всего будет вызываться метод drawSelf)
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		player.draw(g);
	}

	public void unloadLevel() {

	}

	public int getXsize() {
		return (xSize);
	}

	public int getYsize() {
		return (ySize);
	}

	public Tile[][] getTiles() {
		return this.tiles;
	}

	private Player player;
	private Tile[][] tiles;
	private int xSize; // размеры поля
	private int ySize;
	private GraphicsController controller;
	private static Logger logger = Logger.getLogger("Core.GameField");

	// Это вложенный класс обработчика событий (команд на движение), такая форма
	// записи распространена для них
	// Профит в том, что он имеет доступ к плееру, который, вроде бы, приватное
	// поле другого класса.
	private class MovementAction extends AbstractAction {
		public MovementAction(String name)
		{
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent e) {

			logger.entering("GameField.MovementAction", "actionPerformed", e);
			if (isActionStillPerformed == false)
			{

				duty = (String) getValue(Action.NAME);
				ActionListener smoother = new MovementSmoother();
				t = new Timer(10, smoother);
				t.start();
				isActionStillPerformed = true;
				// logger.info("Created Timer"+t.toString()+
				// "and started it."+"Action source:" + e.toString());
				logger.exiting("GameField.MovementAction", "actionPerformed");
			}
		}

		Timer t;
		String duty;

		boolean isActionStillPerformed = false;

		private class MovementSmoother implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.entering("GameField.MovementAction.MovementSmoother",
						"actionPerformed");
				// logger.info(e.toString()+"\n");
				if (counter <= 5)
				{
					player.move(duty);
					controller.repaint();
					// logger.warning("Moved player and repainted controller");
					counter++;
				} else
				{
					counter = 0;
					t.stop();
					isActionStillPerformed = false;
					// logger.warning("Stopped the timer"+t.toString());
				}
				logger.exiting("GameField.MovementAction.MovementSmoother",
						"actionPerformed");

			}

			int counter = 0;
		}

	}

	private class PauseAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			logger.entering("PauseAction", "actionPerformed");
			controller.swapDisplays(controller.getStartScreen(), controller.getGameField());
			logger.info("Paused");
			logger.exiting("PauseAction", "actionPerformed");
		}
	}
}
