package com.freedom.gameObjects;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

import com.freedom.utilities.Loader;

/**
 * Класс GameField содержит все игровые объекты на уровне и осуществляет
 * операции с ними под контролем объекта класса GameScreen Поэтому имеено сюда
 * должен быть добавлен процесс загрузки уровня, то есть метод, считывающий из
 * файла уровень, удаляющий его из памяти при прохождении, и еще что-нибудь. Сам
 * знаешь, кто, тебе надо будет над этим поработать *****отредактируй это
 * описание после того, как добавишь***
 * 
 * @author gleb
 * 
 */

public class GameField {

	private GameField()
	{
		cellSize = 50;
	}

	public void loadLevel(String pathToPackage, int levelID) {
		//Cell[][] cells = new C
		cells = Loader.readLvl(2);
		
	}

	public static void unloadLevel() {

	}

	public static int getXsize() {
		return (xSize);
	}

	public static int getYsize() {
		return (ySize);
	}

	public static Cell[][] getCells() {
		return cells;
	}

	public static Robot getRobot() {
		return robot;
	}

	public static void draw(Graphics g) {
		for (int x = 1; x < cells.length; x++) {
			for (int y = 1; y < cells[1].length; y++) {
				for (int i = 0; i < cells[x][y].getContentAmount(); i++) {
					if (cells[x][y].getContent()[i] != null)
						cells[x][y].getContent()[i].draw(g);
				}
			}
		}
		robot.draw(g);
	}

	public static GameField getInstance() {
		return INSTANCE;
	}

	public static int getcellSize() {
		return cellSize;
	}
	
	public void setRobot(Robot robo){
		robot=robo;
	}

	private static Robot robot;
	private static Cell[][] cells;
	private static int xSize;
	private static int ySize;
	private static Logger logger = Logger.getLogger("Core.GameField");
	private static int cellSize;

	private static final GameField INSTANCE = new GameField();

}
