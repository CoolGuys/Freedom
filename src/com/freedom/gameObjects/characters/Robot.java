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

	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
	}

	public Robot(int posX, int posY, String direction, Stuff c, int lives) {
		super(false, true, 0, maxLives);
		super.type = LoadingType.DNW;
		super.x = posX;
		super.y = posY;
		this.setColour("Blue");
		this.direction = direction;
		this.container[0] = c;
		GameField.getInstance().cells[(int) this.x][(int) this.y].add(this);
		logger.setLevel(Level.WARNING);
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
			repaintSelf();
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
		// logger.warning("Coords double:" + x + " " + y + "|| Coord int: "
		// + (int) (x * getSize()) + " " + (int) (y * getSize()));

		Graphics2D g2 = (Graphics2D) g;

		if (direction.equals("N")) {
			switch (color) {

			case RED:
				g2.drawImage(texturesN[1], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case GREEN:
				g2.drawImage(texturesN[2], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case BLUE:
				g2.drawImage(texturesN[3], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			}
		} else if (direction.equals("S")) {
			switch (color) {

			case RED:
				g2.drawImage(texturesS[1], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case GREEN:
				g2.drawImage(texturesS[2], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case BLUE:
				g2.drawImage(texturesS[3], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			}
		} else if (direction.equals("E")) {
			switch (color) {

			case RED:
				g2.drawImage(texturesE[1], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case GREEN:
				g2.drawImage(texturesE[2], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case BLUE:
				g2.drawImage(texturesE[3], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			}
		} else {
			switch (color) {

			case RED:
				g2.drawImage(texturesW[1], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case GREEN:
				g2.drawImage(texturesW[2], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			case BLUE:
				g2.drawImage(texturesW[3], (int) (x * getSize()),
						(int) (y * getSize()), null);
				break;
			}
		}

		if (container[0] != null) {
			container[0].draw(g);
		}
		g.drawImage(harmTexture, (int) (x * getSize()), (int) (y * getSize()),
				null);
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
	public int punch(int damage) {
		int ret = super.punch(damage);
		GameScreen.getInstance().updateGUIPane();
		return ret;
	}

	@Override
	public void die() {
		System.out.println("You are dead!");
		super.die();
		GameField.getInstance().resetTickerListeners();
		GameField.getInstance();
		GameField.otherThreads.shutdownNow();
		// TODO: Более оригинальная и элегантная обработка смерти
		ScreensHolder.getInstance().swapScreens(StartScreen.getInstance(),
				GameScreen.getInstance());
	}

	private static Image[] texturesN = new Image[4];
	private static Image[] texturesS = new Image[4];
	private static Image[] texturesW = new Image[4];
	private static Image[] texturesE = new Image[4];

	private static Logger logger = Logger.getLogger("Robot");

	static {
		try {
			for (int i = 1; i < 4; i++) {
				texturesN[i] = ImageIO.read(
						new File("Resource/Textures/Robot/" + i + "N.png"))
						.getScaledInstance(getSize(), getSize(),
								Image.SCALE_SMOOTH);

				texturesS[i] = ImageIO.read(
						new File("Resource/Textures/Robot/" + i + "S.png"))
						.getScaledInstance(getSize(), getSize(),
								Image.SCALE_SMOOTH);

				texturesE[i] = ImageIO.read(
						new File("Resource/Textures/Robot/" + i + "E.png"))
						.getScaledInstance(getSize(), getSize(),
								Image.SCALE_SMOOTH);

				texturesW[i] = ImageIO.read(
						new File("Resource/Textures/Robot/" + i + "W.png"))
						.getScaledInstance(getSize(), getSize(),
								Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			logger.warning("Robot texture was corrupted or deleted");
		}
	}

}
