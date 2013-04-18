package com.freedom.gameObjects;

import java.awt.*;
import java.util.logging.Logger;

import com.freedom.utilities.Loader;
import com.freedom.utilities.PathFinder;

/**
 * Класс GameField содержит все игровые объекты на уровне и осуществляет
 * операции с ними под контролем объекта класса GameScreen Поэтому сюда
 * должен быть добавлен процесс загрузки уровня, то есть метод, считывающий из
 * файла уровень, удаляющий его из памяти при прохождении, и еще что-нибудь.
 * 
 * @author gleb
 */

public class GameField {

	public void loadLevel(String pathToPackage, int levelID) {
		cells = Loader.readLvl(2, "Save1.lvl");
		System.out.println(PathFinder.find(3, 5, 12, 10 ,50));
	}
	
	public void nextlvl(int thislvl, int nextlvl){//это метод для перехода на СЛЕДУЮЩИЙ УРОВНЬ
		Loader.lvlToSv(thislvl,"Save1.lvl",cells);
		cells = Loader.readLvl(nextlvl, "Save1.lvl");
		Loader.lvlToSv(nextlvl,"Save1.lvl",cells);
	}
	
	public  void unloadLevel() {
		
	}
	
	public void saveLevel(String pathToPackage, int levelID) {
		Loader.lvlToSv(2,"Save1.lvl",cells);
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
		for (int x = 1; x < cells.length; x++) {
			for (int y = 1; y < cells[1].length; y++) {
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

	public void setRobot(Robot robo) {
		robot = robo;
	}

	public void setCellSize(int scale) {
		cellSize = scale;
	}

	private Robot robot;
	private Cell[][] cells;
	private int xSize;
	private int ySize;
	private Logger logger = Logger.getLogger("Core.GameField");
	private int cellSize;

	private static GameField INSTANCE;

}
