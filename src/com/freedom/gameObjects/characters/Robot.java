package com.freedom.gameObjects.characters;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Moveable;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.utilities.game.Mover;
import com.freedom.view.GameScreen;
import com.freedom.view.ScreensHolder;
import com.freedom.view.StartScreen;

public class Robot extends Stuff implements Moveable {

	protected String direction;
	private double step = 0.1;

	public static int maxLives = 1000;

	private static Image textureN;
	private static Image textureS;
	private static Image textureE;
	private static Image textureW;

	private static Logger logger = Logger.getLogger("Robot");

	static {
		try {
			textureN = ImageIO.read(new File("Resource/Textures/RobotN.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);

			textureS = ImageIO.read(new File("Resource/Textures/RobotS.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);

			textureE = ImageIO.read(new File("Resource/Textures/RobotE.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);

			textureW = ImageIO.read(new File("Resource/Textures/RobotW.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);
		} catch (IOException e) {
			logger.warning("Robot texture was corrupted or deleted");
		}
	}
	
	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
	}
	
	public Robot(int posX, int posY, String direction, Stuff c, int lives) {
		super(false, true, 0, maxLives);
		super.type=LoadingType.DNW;
		super.x = posX;
		super.y = posY;
		this.setColour("Blue");
		this.direction = direction;
		this.container[0] = c;
		GameField.getInstance().cells[(int) this.x][(int) this.y].add(this);
		logger.setLevel(Level.ALL);
	}

	@Override
	public int getX() {
		return (int) Math.round(x);
	}

	@Override
	public int getY() {
		return (int) Math.round(y);
	}

	@Override
	public double getStep() {
		return step;
	}

	public void setContainer(Stuff buf) {
		if (buf != null) {
			this.container[0] = buf;
			container[0].x = this.x;
			container[0].y = this.y;
		}
	}

	public void emptyContainer() {
		this.container[0] = null;
	}

	public String getDirection() {
		return this.direction;
	}

	@Override
	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void SetXY(int xr, int yr) {
		this.x = xr;
		this.y = yr;
	}

	public Stuff getContent() {
		return (this.container[0]);
	}

	public boolean getIfEmpty() {
		if (this.container[0] == null)
			return true;
		return false;
	}

	@Override
	public void recalibrate() {
		x = Math.round(x);
		y = Math.round(y);
		if (container[0] == null)
			return;
		container[0].x = Math.round(container[0].x);
		container[0].y = Math.round(container[0].y);
	}

	@Override
	public boolean canGo() {
		if (GameField.getInstance().cells[this
				.getTargetCellCoordinates(getDirection()).x][this
				.getTargetCellCoordinates(getDirection()).y].passable())
			return true;

		return false;

	}

	public void moveCoarse(String direction) {
		if (Math.abs(GameScreen.getInstance()
				.calculateDistanceFromRobotToScreenCenter(this).x) > ScreensHolder
				.getInstance().getWidth() / 2 - 4 * getSize())
			GameScreen.getInstance().centerByRobotHorisontally(this);

		if (Math.abs(GameScreen.getInstance()
				.calculateDistanceFromRobotToScreenCenter(this).y) > ScreensHolder
				.getInstance().getHeight() / 2 - 4 * getSize())
			GameScreen.getInstance().centerByRobotVertically(this);

		Runnable r = new Mover<Robot>(this, direction, 1, 10);

		GameField.otherThreads.execute(r);
	}

	public void moveFine(String direction) {
		if (!direction.equals(this.direction)) {
			this.direction = direction;
			ScreensHolder.getInstance().getCurrentScreen().repaint();
			return;
		}
		Runnable r = new Mover<Robot>(this, direction, 1, 10);

		GameField.otherThreads.execute(r);
	}

	/**
	 * Штука, которая выдает пару чисел - координаты целла в массиве, лежащего
	 * роботом в некотором направлении от робота
	 * 
	 * @param direction
	 *            направление, в котором берется целл
	 * 
	 * @return пара координат нужного целла
	 */
	@Override
	public Point getTargetCellCoordinates(String direction) {
		Point point = new Point();
		if (direction.equals("N")) {
			point.x = (int) Math.round(this.x);
			point.y = (int) Math.round(this.y - 1);
			return point;
		} else if (direction.equals("S")) {
			point.x = (int) Math.round(this.x);
			point.y = (int) Math.round(this.y + 1);
			return point;
		} else if (direction.equals("E")) {
			point.x = (int) Math.round(this.x + 1);
			point.y = (int) Math.round(this.y);
			return point;
		} else {
			point.x = (int) Math.round(this.x - 1);
			point.y = (int) Math.round(this.y);
			return point;
		}
	}

	@Override
	public void move(String direction) {

		if (direction.equals("N")) {
			y -= step;
			if (this.container[0] != null) {
				this.container[0].y -= step;
			}

		} else if (direction.equals("S")) {
			y += step;
			if (this.container[0] != null) {
				this.container[0].y += step;
			}
		} else if (direction.equals("E")) {
			x += step;
			if (this.container[0] != null) {
				this.container[0].x += step;
			}
		} else {
			x -= step;
			if (this.container[0] != null) {
				this.container[0].x -= step;
			}
		}
	}

	public void take() {
		if (this.container[0] != null)
			return;
		this.container[0] = GameField.getInstance().cells[this
				.getTargetCellCoordinates(getDirection()).x][this
				.getTargetCellCoordinates(getDirection()).y].takeObject();
		if (this.container[0] == null)
			return;
		container[0].x = x;
		container[0].y = y;
		repaintNeighbourhood();
	}

	public void put() {
		if (isMoving
				|| GameField.getInstance().cells[this
						.getTargetCellCoordinates(getDirection()).x][this
						.getTargetCellCoordinates(getDirection()).y].locked)
			return;

		int targetX = this.getTargetCellCoordinates(getDirection()).x;
		int targetY = this.getTargetCellCoordinates(getDirection()).y;
		if (this.container[0] == null)
			return;

		if (!GameField.getInstance().cells[targetX][targetY]
				.add(this.container[0]))
			return;

		this.container[0] = null;
		repaintNeighbourhood();

		return;

	}

	public void interact() {
		if (this.container[0] != null)
			this.container[0].interact(this);
		else {
			GameField.getInstance().cells[this
					.getTargetCellCoordinates(getDirection()).x][this
					.getTargetCellCoordinates(getDirection()).y].interact(this);

		}
	}

	public void examineFrontCell() {
		Cell cell = GameField.getInstance().cells[this
				.getTargetCellCoordinates(getDirection()).x][this
				.getTargetCellCoordinates(getDirection()).y];
		if (!cell.isExamined) {
			for (Stuff s : cell.getContent())
				if (s != null)
					s.giveInfo();
			cell.isExamined = true;
		} else {
			for (Stuff s : cell.getContent())
				if (s != null)
					s.removeInfo();
			cell.isExamined = false;
		}
		ScreensHolder.getInstance().getCurrentScreen().repaint();

	}

	public void heal(int lives) {
		this.lives = this.lives + lives;
		if (this.lives > Robot.maxLives)
			this.lives = Robot.maxLives;
	}

	@Override
	public void draw(Graphics g) {
		// logger.info("Coords double:" + x + " " + y + "|| Coord int: "
		// + (int) (x * getSize()) + " " + (int) (y * getSize()));

		Graphics2D g2 = (Graphics2D) g;
		
		if (direction.equals("N")) {
			g2.drawImage(textureN, (int) (x * getSize()),
					(int) (y * getSize()), null);
		} else if (direction.equals("S")) {
			g2.drawImage(textureS, (int) (x * getSize()),
					(int) (y * getSize()), null);
		} else if (direction.equals("E")) {
			g2.drawImage(textureE, (int) (x * getSize()),
					(int) (y * getSize()), null);
		} else {
			g2.drawImage(textureW, (int) (x * getSize()),
					(int) (y * getSize()), null);
		}

		if (container[0] != null) {
			container[0].draw(g);
		}
		g.drawImage(harmTexture, (int) (x * getSize()),
				(int) (y * getSize()), null);
		harmTexture = null;
	}

	@Override
	public boolean checkIfBeingMoved() {
		return isMoving;
	}

	@Override
	public void tellIfBeingMoved(boolean isMoved) {
		isMoving = isMoved;
	}

	@Override
	public void die() {
		System.out.println("You are dead!");
		super.die();
		ScreensHolder.getInstance().swapScreens(StartScreen.getInstance(),GameScreen.getInstance());
	}

}
