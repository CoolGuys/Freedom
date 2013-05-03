package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.utilities.Mover;
import com.freedom.view.GameScreen;
import com.freedom.view.ScreensHolder;

public class Robot extends Stuff implements Moveable {

	private String direction;
	private Stuff container;

	volatile boolean isMoving;
	private double step = 0.1;

	static int maxLives = 10000;

	private static Image textureN;
	private static Image textureS;
	private static Image textureE;
	private static Image textureW;

	private static Logger logger = Logger.getLogger("Robot");

	static {
		try {
			textureN = ImageIO.read(new File("Resource/Textures/RobotN.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureS = ImageIO.read(new File("Resource/Textures/RobotS.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureE = ImageIO.read(new File("Resource/Textures/RobotE.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureW = ImageIO.read(new File("Resource/Textures/RobotW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Robot(int posX, int posY, String direction, Stuff c, int lives)
	{
		super(false, true, false, true, 0, 10000);
		super.x = posX;
		super.y = posY;
		this.direction = direction;
		this.container = c;
		GameField.getInstance().cells[(int) this.x][(int) this.y].add(this);
		logger.setLevel(Level.OFF);
	}
	public boolean obj() {
		return false;
	}

	// кастыли
	public boolean objc() {
		return false;
	}
	public int getX() {
		return (int)Math.round(x);
	}

	public int getY() {
		return (int)Math.round(y);
	}
	
	public double getStep() {
		return step;
	}

	public void setContainer(Stuff buf) {
		this.container = buf;
	}

	public void emptyContainer() {
		this.container = null;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction=direction;
	}
	
	public void SetXY(int xr, int yr) {
		this.x = xr;
		this.y = yr;
	}

	public Stuff getContent() {
		return (this.container);
	}

	public boolean getIfEmpty() {
		if (this.container == null)
			return true;
		return false;
	}

	public void recalibrate() {
		x = Math.round(x);
		y = Math.round(y);

	}

	public boolean canGo() {
		if (GameField.getInstance().cells[this
				.getTargetCellCoordinates(getDirection()).x][this
				.getTargetCellCoordinates(getDirection()).y].ifCanPassThrough())
			return true;

		return false;

	}

	public void moveCoarse(String direction) {
		if (Math.abs(GameScreen.getInstance()
				.рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(this).x) > ScreensHolder
				.getInstance().getWidth() / 2 - 4 * getSize())
			GameScreen.getInstance().центрироватьПоРоботуПоГоризонтали(this);

		if (Math.abs(GameScreen.getInstance()
				.рассчитатьРасстояниеОтРоботаДоЦентраЭкрана(this).y) > ScreensHolder
				.getInstance().getHeight() / 2 - 4 * getSize())
			GameScreen.getInstance().центрироватьПоРоботуПоВертикали(this);


		Runnable r = new Mover<Robot>(this, direction, 1, 10);
		Thread t = new Thread(r);
		t.start();

	}

	public void moveFine(String direction) {
		if (!direction.equals(this.direction)) {
			this.direction = direction;
			ScreensHolder.getInstance().repaint();
			return;
		}
		Runnable r = new Mover<Robot>(this, direction, 1, 10);

		Thread t = new Thread(r);
		t.start();
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
	public Point getTargetCellCoordinates(String direction) {
		Point point = new Point();
		if (direction.equals("N")) {
			point.x = (int) Math.round(this.x);
			point.y = (int)Math.round(this.y-1);
			return point;
		} else if (direction.equals("S")) {
			point.x = (int)Math.round(this.x);
			point.y = (int)Math.round(this.y+1);
			return point;
		} else if (direction.equals("E")) {
			point.x = (int)Math.round(this.x+1);
			point.y = (int) Math.round(this.y);
			return point;
		} else {
			point.x = (int) Math.round(this.x-1);
			point.y = (int) Math.round(this.y);
			return point;
		}
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
		if (this.container != null)
			return;
		this.container = GameField.getInstance().cells[this
				.getTargetCellCoordinates(getDirection()).x][this
				.getTargetCellCoordinates(getDirection()).y].takeObject();
		if (this.container == null)
			return;
		GameScreen.getInstance().repaint();
	}

	public void put() {
		if (isMoving)
			return;

		if (this.container instanceof TNT) {
			TNT buf = (TNT) this.container;
			buf.x = this.getTargetCellCoordinates(direction).x;
			buf.y = this.getTargetCellCoordinates(direction).y;
			buf.activate();
			this.container = null;
		}

		if (this.container == null)
			return;

		int targetX = this.getTargetCellCoordinates(getDirection()).x;
		int targetY = this.getTargetCellCoordinates(getDirection()).y;
		if (this.container == null)
			return;

		if (!GameField.getInstance().cells[targetX][targetY]
				.add(this.container))
			return;
		this.container = null;
		GameScreen.getInstance().repaint();
		return;

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
		GameScreen.getInstance().repaint();

	}

	public void draw(Graphics g) {
//		logger.info("Coords double:" + x + " " + y + "|| Coord int: "
//				+ (int) (x * getSize()) + " " + (int) (y * getSize()));

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

		if (container != null) {
			g.drawImage(container.getTexture(), (int) (x * getSize()),
					(int) (y * getSize()), getSize(), getSize(), null);
			// logger.info(container.toString());
		}
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

	void die() {
		System.out.println("You are dead, idiot!");
		System.exit(10);
	}

}
