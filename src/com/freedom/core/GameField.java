package com.freedom.core;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

import javax.swing.*;

import com.freedom.gameObjects.Robot;
import com.freedom.gameObjects.Tile;

/**
 * Класс GameField содержит все игровые объекты на уровне и осуществляет
 * операции с ними под контролем объекта класса GraphicsController
 * Поэтому имеено сюда должен быть добавлен процесс загрузки уровня, то есть
 * метод, считывающий из файла уровень, удаляющий его из памяти при прохождении,
 * и еще что-нибудь. Сам знаешь, кто, тебе надо будет над этим поработать
 * *****отредактируй это описание после того, как добавишь***
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
class GameField extends JLayeredPane {

	// Иван, подумайте над конструктором, объекты ведь на вас, и напишите норм
	// комментарий
	// @gleb
	public GameField(GraphicsController aController)
	{
		logger.entering("GameField", "<init>", aController);
		logger.info("Creating GameField");
		setPreferredSize(new Dimension(1000, 600));
		setBackground(Color.DARK_GRAY);
		this.controller = aController;

		this.createInputMap();
		this.createMovementController();
		robot = new Robot(10, 10, "S", null, null, aController);
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

	public void loadLevel(String pathToPackage, int levelID) {

	}

	// Метод будет производить рисование всего, что лежит в массиве Tile (у
	// всего будет вызываться метод draw)
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		robot.draw(g);
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

	private Robot robot;
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
			robot.moveToNextTile((String) getValue(Action.NAME));
		}
	}

	private class PauseAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			logger.entering("PauseAction", "actionPerformed");
			controller.swapDisplays(controller.getStartScreen(), controller.getGameField());
			controller.getStartScreen().activate();
			logger.info("Paused");
			logger.exiting("PauseAction", "actionPerformed");
		}
	}
}
