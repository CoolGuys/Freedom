package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.core.*;

public class Robot extends Stuff implements Moveable {

	private String direction;
	private Stuff container;
	private boolean isEmpty; // пуст ли контейнер
	private Cell[][] environment;
	private ScreensHolder painter;
	private boolean isMoving;
	private int size = 50;
	private int step = size / 5;

	Image textureN;
	Image textureS;
	Image textureE;
	Image textureW;

	// private static Logger logger = Logger.getLogger("Core.Robot");

	// /конструктор (я выпилил второй, буду ставить null в вызове) @gleb
	public Robot(int posX, int posY, String direction, Stuff c, Cell[][] tiles) {
		super(posX, posY, false, false);
		this.direction = direction;
		this.container = c;
		this.environment = tiles;
		isEmpty = false;

		try {
			textureN = ImageIO.read(new File("Resource/Textures/RobotN.png"));
			textureS = ImageIO.read(new File("Resource/Textures/RobotS.png"));
			textureE = ImageIO.read(new File("Resource/Textures/RobotE.png"));
			textureW = ImageIO.read(new File("Resource/Textures/RobotW.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// блок выдачи полей

	public int getX() {
		return (this.x);
	}

	public int getY() {
		return (this.y);
	}

	public String getDirection() {
		return this.direction;
	}

	public boolean getIfEmpty() {
		return (this.isEmpty);
	}

	public Stuff getContent() {
		return (this.container);
	}

	// конец блока выдачи

	public boolean canGo() {

		if (this.direction.equals("N")) {
			if (environment[x][y - 1].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("S")) {
			if (environment[x][y + 1].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("W")) {
			if (environment[x - 1][y].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("E")) {
			if (environment[x + 1][y].ifCanPassThrough())
				return true;
		}

		return false;

	}

	// Ваня, верни проверку canGo потом
	public void moveToNextTile(String direction) {

		if (!direction.equals(this.direction)) {
			this.direction = direction;
			ScreensHolder.getInstance().repaint();
		}

		else if ((!isMoving)&(this.canGo())) {
			isMoving = true;
			Runnable r = new MovementAnimator<Robot>(this, this.direction);
			Thread t = new Thread(r);
			t.start();
		} else
			return;
	}

	public void move(String direction) {

		if (direction.equals("N"))
			y -= step;
		else if (direction.equals("S"))
			y += step;
		else if (direction.equals("E"))
			x += step;
		else
			x -= step;
	}

	// Нужно доработать и брать с уловиями на направление
	// @gleb
	public void take() { 
		if (!this.isEmpty)
			return;

		if (this.container != null) {
			this.isEmpty = false;
			return;
		}
		
		
		if (this.direction.equals("N")) {
			this.container = environment[x][y - 1].takeObject();
			this.isEmpty = false;
			return;
		}
		
		if (this.direction.equals("S")) {
			this.container = environment[x][y + 1].takeObject();
			this.isEmpty = false;
			return;
		}

		if (this.direction.equals("W")) {
			this.container = environment[x - 1][y].takeObject();
			this.isEmpty = false;
			return;
		}

		if (this.direction.equals("E")) {
			this.container = environment[x + 1][y].takeObject();
			this.isEmpty = false;
		}

	}
	
	public void put(){
		if(this.isEmpty)
			return;
		
		if (this.direction.equals("N")) {
			environment[x][y - 1].add(this.container);
			this.container = null;
			this.isEmpty = true;
			return;
		}
		
		if (this.direction.equals("S")) {
			environment[x][y + 1].add(this.container);
			this.container = null;
			this.isEmpty = true;
			return;
		}

		if (this.direction.equals("W")) {
			environment[x - 1][y].add(this.container);
			this.container = null;
			this.isEmpty = true;
			return;
		}

		if (this.direction.equals("E")) {
			environment[x + 1][y].add(this.container);
			this.container = null;
			this.isEmpty = true;
		}
		
			
	}

	public void draw(Graphics g) {
		if (direction.equals("N"))
			g.drawImage(textureN, x, y, size, size, null);
		else if (direction.equals("S"))
			g.drawImage(textureS, x, y, size, size, null);
		else if (direction.equals("E"))
			g.drawImage(textureE, x, y, size, size, null);
		else
			g.drawImage(textureW, x, y, size, size, null);
	}

	@Override
	public boolean checkIfBeingMoved() {
		// TODO Auto-generated method stub
		return isMoving;
	}

	@Override
	public void tellIfBeingMoved(boolean isMoved) {
		// TODO Auto-generated method stub
		isMoving = isMoved;
	}

}
