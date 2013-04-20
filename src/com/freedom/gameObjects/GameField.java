package com.freedom.gameObjects;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.Timer;

import com.freedom.utilities.Loader;
import com.freedom.view.GameScreen;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

/**
 * Класс GameField содержит все игровые объекты на уровне и осуществляет
 * операции с ними под контролем объекта класса GameScreen Поэтому сюда
 * должен быть добавлен процесс загрузки уровня, то есть метод, считывающий из
 * файла уровень, удаляющий его из памяти при прохождении, и еще что-нибудь.
 * 
 * @author gleb
 */

public class GameField {

	private int thislvl;
	private String pathToSave;
	
	public void setPath(String path){
		this.pathToSave=path;
	}
	
	public String getPath(){
		return this.pathToSave;
	}
	
	
	public void setlvl(int lvl){
		this.thislvl=lvl;
	}
	
	public int getlvl(){
		return this.thislvl;
	}
	
	public void activate() {
		ticker.start();
	}
	
	public void deactivate() {
		ticker.stop();
	}

	public void loadLevel(String pathToPackage, int levelID) {
		Loader.readLvl(levelID, pathToPackage);
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);
	}
	
	// это метод для перехода на
	// СЛЕДУЮЩИЙ УРОВНЬ
	public void nextlvl(int thislvl, int nextlvl) {
		resetTickerListeners();
		this.thislvl = nextlvl;
		Stuff buf = robot.getContent();
		robot.emptyContainer();
		Loader.lvlToSv(thislvl, this.pathToSave);
		Loader.readLvl(nextlvl, this.pathToSave);
		robot.setContainer(buf);
		Loader.lvlToSv(nextlvl, this.pathToSave);
		GameScreen.getInstance().setSize(cells.length * cellSize,
				cells[1].length * cellSize);
		GameScreen.getInstance().repaint();
	}

	public void resetTickerListeners() {
		ActionListener[] listeners = ticker.getActionListeners();
		for(ActionListener l : listeners)
			ticker.removeActionListener(l);
	}
	
	public void saveLevel(String pathToPackage, int levelID) {
		Loader.lvlToSv(this.thislvl,this.pathToSave);
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
	
	void damageRobot(int damage){
		this.robot.lives = this.robot.lives - damage;
	}

	public void draw(Graphics g) {
		for (int x = 1; x < cells.length-1; x++) {
			for (int y = 1; y < cells[1].length-1; y++) {
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
	
	public Timer getDeathTicker(){
		return this.deathTicker;
	}

	private Robot robot;
	public Cell[][] cells;
	private int xSize;
	private int ySize;
	private Logger logger = Logger.getLogger("Core.GameField");
	private int cellSize;
	public Timer ticker = new Timer(2, null);
	public Timer deathTicker = new Timer(2, null);
	private static GameField INSTANCE;

}
