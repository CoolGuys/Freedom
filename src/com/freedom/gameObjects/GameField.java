package com.freedom.gameObjects;

import java.awt.*;
import java.util.logging.Logger;


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
	}

	public void loadLevel(String pathToPackage, int levelID) {

		robot = new Robot(10, 10, "S", null, null);
	}

	public static void unloadLevel() {

	}

	public static int getXsize() {
		return (xSize);
	}

	public static int getYsize() {
		return (ySize);
	}

	public static Tile[][] getTiles() {
		return tiles;
	}

	public static Robot getRobot() {
		return robot;
	}

	public static void draw(Graphics g) {
		robot.draw(g);
	}
	
	public static GameField getInstance() {
		return INSTANCE;
	}

	private static Robot robot;
	private static Tile[][] tiles;
	private static int xSize; 
	private static int ySize;
	private static Logger logger = Logger.getLogger("Core.GameField");

	private static final GameField INSTANCE = new GameField();

}
