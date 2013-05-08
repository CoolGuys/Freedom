package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import com.freedom.utilities.Loader;
import com.freedom.view.GameScreen;
import com.freedom.view.LoadScreen;
import com.freedom.view.LoadingScreen;
import com.freedom.view.SaveScreen;
import com.freedom.view.ScreensHolder;

/**
 * Класс GameField содержит все игровые объекты на уровне и осуществляет
 * операции с ними под контролем объекта класса GameScreen Поэтому сюда должен
 * быть добавлен процесс загрузки уровня, то есть метод, считывающий из файла
 * уровень, удаляющий его из памяти при прохождении, и еще что-нибудь.
 * 
 * @author gleb
 */

public class GameField {

	private int currentLevelId;
	private String pathToSave;
	private int previousLevelId;
	public Cell[][] previousCells;
	private volatile Robot robot;
	public volatile Cell[][] cells;
	private int xSize;
	private int ySize;
	//private Logger logger = Logger.getLogger("Core.GameField");
	private int cellSize;
	public Timer ticker = new Timer(2, null);
	private Timer deathTicker = new Timer(100, null);
	private static GameField INSTANCE;
	public static ExecutorService otherThreads;
	public boolean active;
	private Logger gleblo = Logger.getLogger("gleblo");
	public void setPathToSave(String pathToSaveFile) {
		this.pathToSave = pathToSaveFile;
	}

	public String getPathToSave() {
		return this.pathToSave;
	}

	public void setCurrentLevel(int currentLevelIdToSet) {
		this.currentLevelId = currentLevelIdToSet;
	}

	public void setPreviousLevel(int prevoiusLevelIdToSet) {
		this.previousLevelId = prevoiusLevelIdToSet;
	}

	public int getLevelId() {
		return this.currentLevelId;
	}

	public void activate() {
		active = true;
		ticker.start();
		deathTicker.start();
	}

	public void deactivate() {
		ticker.stop();
		deathTicker.stop();
		active = false;
	}

	/**
	 * Метод как для старта так и для загрузки уровня.
	 * 
	 * @param pathToPackage
	 *            путь к файлу
	 * @param levelID
	 *            Апендикс, который сейчас не нужен
	 */
	public void loadLevel(String pathToPackage, boolean isNew) {
		if (isNew)
			ScreensHolder.getInstance().swapScreens(
					LoadingScreen.getInstance(), SaveScreen.getInstance());
		else
			ScreensHolder.getInstance().swapScreens(
					LoadingScreen.getInstance(), LoadScreen.getInstance());
		otherThreads = Executors.newCachedThreadPool();
		Loader.loadSave(pathToPackage);
		previousCells = cells;
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);

		GameScreen.getInstance().центрироватьПоРоботу(getRobot());
		ScreensHolder.getInstance().swapScreens(GameScreen.getInstance(),
				LoadingScreen.getInstance());
	}

	public ExecutorService getThreads() {
		return GameField.otherThreads;
	}

	public void switchToNextLevel(int nextLevelId, int robotx, int roboty) {

		ScreensHolder.getInstance().swapScreens(LoadingScreen.getInstance(),
				GameScreen.getInstance());
		resetTickerListeners();
		otherThreads.shutdownNow();
		otherThreads = Executors.newCachedThreadPool();
		previousLevelId = currentLevelId;
		currentLevelId = nextLevelId;
		Stuff buf = robot.getContent();
		robot.emptyContainer();
		Loader.lvlToSv(previousLevelId, this.pathToSave);
		Loader.readLvl(nextLevelId, this.pathToSave);
		robot.setContainer(buf);
		if ((robotx != -1) && (roboty != -1)) {
			gleblo.setLevel(Level.ALL);
			gleblo.info("robotXYsetting x=" + robotx + " y=" + roboty);

			int previousx = robot.getX();
			int previousy = robot.getY();
			Stuff element = GameField.getInstance().getCells()[previousx][previousy]
					.getTop();
			for (Stuff containedElement : element.container) {
				if (containedElement != null) {
					containedElement.x = robotx;
					containedElement.y = roboty;
				}
			}
			if (GameField.getInstance().getCells()[robotx][roboty].add(element)) {
				GameField.getInstance().getCells()[previousx][previousy]
						.deleteStuff();
			}

			gleblo.setLevel(Level.OFF);
		}
		
		try {
			buf.itsAlive();
		} catch (Exception E) {

		}
		Loader.lvlToSv(nextLevelId, this.pathToSave);
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);
		GameScreen.getInstance().центрироватьПоРоботу(getRobot());
		ScreensHolder.getInstance().swapScreens(GameScreen.getInstance(),
				LoadingScreen.getInstance());
		ScreensHolder.getInstance().repaint();
	}

	/*
	 * public void saveLevelToPackage(int levelID) { //this.pathToSave =
	 * "Saves/Save1.lvl"; Loader.lvlToSv(this.currentLevelId, this.pathToSave);
	 * try { buf.itsAlive(); } catch (Exception E) {
	 * 
	 * } Loader.lvlToSv(nextLevelId, this.pathToSave);
	 * GameScreen.getInstance().setSize(cells.length * cellSize, cells[1].length
	 * * cellSize);
	 * ScreensHolder.getInstance().swapScreens(GameScreen.getInstance(),
	 * LoadingScreen.getInstance()); }
	 */
	public void saveCurrentLevelToPackage() {
		// this.pathToSave = "Saves/Save1.lvl";
		Loader.lvlToSv(this.currentLevelId, this.pathToSave);
	}

	public void resetTickerListeners() {
		ActionListener[] listeners = ticker.getActionListeners();
		for (ActionListener l : listeners)
			ticker.removeActionListener(l);
	}

	public int getXsize() {
		return (xSize);
	}

	public int getYsize() {
		return (ySize);
	}

	public Cell[][] getCells() {
		return cells;
	}


	public synchronized Robot getRobot() {

		return robot;
	}

	public void draw(Graphics g) {
		for (int i = 0; i < 6; i++) {
			for (int x = 1; x < cells.length - 1; x++) {
				for (int y = 1; y < cells[1].length - 1; y++) {
					if (cells[x][y].getContent()[i] != null)
						cells[x][y].getContent()[i].draw(g);
					if (cells[x][y].getMeta() != null)
						cells[x][y].getMeta().draw(g);
					cells[x][y].draw(g);
				}
			}
		}
	}

	public static GameField getInstance() {
		if (INSTANCE != null)
			return INSTANCE;
		else {
			INSTANCE = new GameField();
			return INSTANCE;
		}
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setRobot(Robot robo) {
		robot = robo;
	}

	public void setCellSize(int scale) {
		cellSize = scale;
	}

	public Timer getTicker() {
		return ticker;
	}

	public Timer getDeathTicker() {
		return deathTicker;
	}
}
