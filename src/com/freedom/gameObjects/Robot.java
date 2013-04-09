package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.freedom.view.*;
import com.freedom.utilities.*;

public class Robot extends Stuff implements Moveable {

	private String direction;
	private Stuff container;
	private boolean isEmpty; // пуст ли контейнер
	private Cell[][] environment;

	private boolean isMoving;
	private double step = 0.2;

	private int lives;
	protected static int maxLives = 1;

	Image textureN;
	Image textureS;
	Image textureE;
	Image textureW;

	private static Logger logger = Logger.getLogger("Robot");

	public Robot(int posX, int posY, String direction, Stuff c, Cell[][] tiles)
	{
		super(false, false, 0);
		super.x = posX;
		super.y = posY;
		this.direction = direction;
		this.container = c;
		this.environment = tiles;
		this.isEmpty = true;

		try {
			textureN = ImageIO.read(new File("Resource/Textures/RobotN.png"));
			textureS = ImageIO.read(new File("Resource/Textures/RobotS.png"));
			textureE = ImageIO.read(new File("Resource/Textures/RobotE.png"));
			textureW = ImageIO.read(new File("Resource/Textures/RobotW.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		lives = 1;
	}

	public double getStep() {
		return step;
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

	public int getLives() {
		return this.lives;
	}

	public void recalibrate() {
		x = (int) Math.round(x);
		y = (int) Math.round(y);

	}

	public boolean canGo() {
		int x = (int) this.x;
		int y = (int) this.y;
		if (this.direction.equals("N")) {
			if (environment[x][y - 1].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("S")) {
			// logger.info("Checking S direction");
			if (environment[x][y + 1].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("W")) {
			System.out.print(1);
			if (environment[x - 1][y].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("E")) {
			if (environment[x + 1][y].ifCanPassThrough())
				return true;
		}

		return false;

	}

	public void moveToNextTile(String direction) {

		logger.info(direction);
		//if (!direction.equals(this.direction)) {
			this.direction = direction;
			ScreensHolder.getInstance().repaint();
		//}

		 if ((!isMoving) & (this.canGo())) {
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

	public void take() {

		int x = (int) this.x;
		int y = (int) this.y;

		// TODO Спросить у Вани, что тут происходит
		if (!this.isEmpty)
			return;

		if (this.container != null) {
			this.isEmpty = false;
			return;
		}
		//
		
		if (this.direction.equals("N")) {
			this.container = environment[x][y - 1].takeObject();
			if(this.container == null) 
				return;
			this.isEmpty = false;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("S")) {
			this.container = environment[x][y + 1].takeObject();
			if(this.container == null) 
				return;
			this.isEmpty = false;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("W")) {
			this.container = environment[x - 1][y].takeObject();
			if(this.container == null) 
				return;
			this.isEmpty = false;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("E")) {
			this.container = environment[x + 1][y].takeObject();
			if(this.container == null) 
				return;
			this.isEmpty = false;
			ScreensHolder.getInstance().repaint();
		}
	}

	public void put() {

		int x = (int) this.x;
		int y = (int) this.y;
		if (this.isEmpty)
			return;

		if (this.direction.equals("N")) {
			if(!environment[x][y - 1].add(this.container))
				return;
			this.container = null;
			this.isEmpty = true;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("S")) {
			if(!environment[x][y + 1].add(this.container))
				return;
			this.container = null;
			this.isEmpty = true;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("W")) {
			if(!environment[x - 1][y].add(this.container))
				return;
			this.container = null;
			this.isEmpty = true;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("E")) {
			if(!environment[x + 1][y].add(this.container))
				return;
			this.container = null;
			this.isEmpty = true;
			ScreensHolder.getInstance().repaint();
		}

	}

	public void draw(Graphics g) {
		logger.info("Coords double:" + x + " " + y + "|| Coord int: "
				+ (int) (x * getSize()) +" " + (int) (y * getSize()));
		if (direction.equals("N"))
			g.drawImage(textureN, (int) (x * getSize()), (int) (y * getSize()),
					getSize(), getSize(), null);
		else if (direction.equals("S"))
			g.drawImage(textureS, (int) (x * getSize()), (int) (y * getSize()),
					getSize(), getSize(), null);
		else if (direction.equals("E"))
			g.drawImage(textureE, (int) (x * getSize()), (int) (y * getSize()),
					getSize(), getSize(), null);
		else
			g.drawImage(textureW, (int) (x * getSize()), (int) (y * getSize()),
					getSize(), getSize(), null);
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
