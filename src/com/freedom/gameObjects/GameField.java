package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.event.ActionListener;

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
	private Robot robot;
	public Cell[][] cells;
	private int xSize;
	private int ySize;
	// private Logger logger = Logger.getLogger("Core.GameField");
	private int cellSize;
	public Timer ticker = new Timer(2, null);
	private Timer deathTicker = new Timer(100, null);
	private static GameField INSTANCE;


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
		ticker.start();
		deathTicker.start();
	}

	public void deactivate() {
		ticker.stop();
		deathTicker.stop();
	}

	/**
	 * Метод как для старта так и для загрузки уровня.
	 * 
	 * @param pathToPackage
	 *            путь к файлу
	 * @param levelID
	 *            Апендикс, который сейчас не нужен
	 */
	public void loadNewLevel(String pathToPackage) {
		ScreensHolder.getInstance().swapScreens(LoadingScreen.getInstance(),
				SaveScreen.getInstance());
		Loader.loadSave(pathToPackage);
		previousCells = cells;
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);
		ScreensHolder.getInstance().swapScreens(GameScreen.getInstance(),
				LoadingScreen.getInstance());
	}

	public void loadSavedLevel(String pathToPackage) {
		ScreensHolder.getInstance().swapScreens(LoadingScreen.getInstance(),
			LoadScreen.getInstance());
		Loader.loadSave(pathToPackage);
		previousCells = cells;
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);

		ScreensHolder.getInstance().swapScreens(GameScreen.getInstance(),
				LoadingScreen.getInstance());
	}

	public void switchToNextLevel(int nextLevelId) {
		ScreensHolder.getInstance().swapScreens(LoadingScreen.getInstance(),
				GameScreen.getInstance());
		resetTickerListeners();
		
		previousLevelId = currentLevelId;
		currentLevelId = nextLevelId;

		Stuff buf = robot.getContent();
		robot.emptyContainer();
		Loader.lvlToSv(previousLevelId, this.pathToSave);
		Loader.readLvl(nextLevelId, this.pathToSave);
		robot.setContainer(buf);
		Loader.lvlToSv(nextLevelId, this.pathToSave);

		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);
		ScreensHolder.getInstance().swapScreens(GameScreen.getInstance(),
				LoadingScreen.getInstance());
	}

	public void saveCurrentLevelToPackage() {
		//this.pathToSave = "Saves/Save1.lvl";
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

	public Robot getRobot() {
		return robot;
	}

	void damageRobot(int damage) {
		this.robot.lives = this.robot.lives - damage;
	}

	public void draw(Graphics g) {
		for (int x = 1; x < cells.length - 1; x++) {
			for (int y = 1; y < cells[1].length - 1; y++) {
				for (int i = 0; i < cells[x][y].getContentAmount(); i++) {
					if (cells[x][y].getContent()[i] != null) 
						cells[x][y].getContent()[i].draw(g);
					cells[x][y].draw(g);
				}
			}
		}
		robot.draw(g);
		g.dispose();
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
