package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.Timer;

import com.freedom.utilities.Loader;
import com.freedom.view.ChoiceScreen;
import com.freedom.view.GameScreen;
import com.freedom.view.LoadingScreen;
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

	private int thislvl; 
	private String pathToSave;
	private int previousLevel;
	public Cell[][] previouscells;
	private Robot robot;
	public Cell[][] cells;
	private int xSize;
	private int ySize;
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger("Core.GameField");
	private int cellSize;
	public Timer ticker = new Timer(2, null);
	private static GameField INSTANCE;
	/**
	 *  
	 * @param path set path to the save file
	 */
	public void setPath(String path) {
		this.pathToSave = path;
	}
	/**
	 * 
	 * @return get a path to the save file
	 */
	public String getPath() {
		return this.pathToSave;
	}
	/**
	 * 
	 * @param lvl set This lvl param
	 */
	public void setlvl(int lvl) {
		this.thislvl = lvl;
	}
	/**
	 * 
	 * @param lvl set previous lvl param
	 */
	public void setPlvl(int lvl) {
		this.previousLevel = lvl;
	}

	public int getlvl() {
		return this.thislvl;
	}

	public void activate() {
		ticker.start();
	}

	public void deactivate() {
		ticker.stop();
	}

	/*
	 * public void loadSave(String pathToPackage){
	 * Loader.loadSave(pathToPackage);
	 * GameScreen.getInstance().setSize(cells.length * cellSize, cells[0].length
	 * * cellSize); }
	 */
	/**
	 * метод для старта игры. Подходит как для старта так и для загрузки.
	 * Обязательно нужно сделать так, чтобы при новой игре загружался один файл,
	 * а при загрузке другой
	 * 
	 * @param pathToPackage путь к файлу
	 * @param levelID апендикс, который сейчас не нужен
	 */
	public void loadLevel(String pathToPackage) {
		// ScreensHolder.swapScreens(LoadingScreen.getInstance(),
		// ChoiceScreen.getInstance());
		Loader.loadSave(pathToPackage);
		previouscells = cells;
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);

		// ScreensHolder.swapScreens(GameScreen.getInstance(),
		// LoadingScreen.getInstance());
	}

	/**
	 * метод для переключения уровней
	 * @param nextlvl слеудующий лвл.
	 */
	public void nextlvl(int nextlvl) {
		// ScreensHolder.swapScreens(LoadingScreen.getInstance(),
		// GameScreen.getInstance());
		resetTickerListeners();
		//System.out.println("This="+this.thislvl+" next="+nextlvl);
	//	if (previousLevel != nextlvl) {
			this.previousLevel = this.thislvl;
			this.thislvl = nextlvl;
			previouscells = cells;
			Stuff buf = robot.getContent();
			robot.emptyContainer();
			Loader.lvlToSv(this.previousLevel, this.pathToSave);
			Loader.readLvl(nextlvl, this.pathToSave);
			robot.setContainer(buf);
			Loader.lvlToSv(nextlvl, this.pathToSave);
			GameScreen.getInstance().setSize(cells.length * cellSize,
					cells[1].length * cellSize);
		/*}else{
			this.previousLevel = this.thislvl;
			this.thislvl = nextlvl;
			previouscells = cells;
			Stuff buf = robot.getContent();
			robot.emptyContainer();
			Loader.lvlToSv(this.previousLevel, this.pathToSave);
			//Loader.readLvl(nextlvl, this.pathToSave);
			Cell[][] bufCells;
			bufCells=previouscells;
			previouscells=cells;
			cells=bufCells;
			robot.setContainer(buf);
			Loader.lvlToSv(nextlvl, this.pathToSave);
			GameScreen.getInstance().setSize(cells.length * cellSize,
					cells[1].length * cellSize);
		}*/
		// ScreensHolder.swapScreens(GameScreen.getInstance(),
		// LoadingScreen.getInstance());

	}

	public void resetTickerListeners() {
		ActionListener[] listeners = ticker.getActionListeners();
		for (ActionListener l : listeners)
			ticker.removeActionListener(l);
	}

	public void saveLevel(String pathToPackage, int levelID) {
		Loader.lvlToSv(this.thislvl, this.pathToSave);
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

	public void draw(Graphics g) {
		for (int x = 1; x < cells.length - 1; x++) {
			for (int y = 1; y < cells[1].length - 1; y++) {
				for (int i = 0; i < cells[x][y].getContentAmount(); i++) {
					if (cells[x][y].getContent()[i] != null)
						cells[x][y].getContent()[i].draw(g);
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

	public void setRobot(Robot robo, Stuff con) {
		robot = robo;
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
}
