package com.freedom.gameObjects;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.utilities.Mover;

public class PacmanBody extends Stuff implements Moveable {

	private int rate;
	boolean isMoving;
	private double step = 0.1;
	private int picID;
	private String direction;
	private int direc;
	private int trekLenght = 10;
	private PacmanSoul p;
	private static Logger logger = Logger.getLogger("");

	private static BufferedImage texture1 = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture2 = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture3 = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture4 = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture5 = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);

	static {
		try {

			Image texture1 = ImageIO
					.read(new File("Resource/Textures/p1s.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			Image texture2 = ImageIO
					.read(new File("Resource/Textures/p2s.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			Image texture3 = ImageIO
					.read(new File("Resource/Textures/p3s.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			Image texture4 = ImageIO
					.read(new File("Resource/Textures/p4s.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			Image texture5 = ImageIO
					.read(new File("Resource/Textures/p5s.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			PacmanBody.texture1.getGraphics().drawImage(texture1, 0, 0, null);
			PacmanBody.texture2.getGraphics().drawImage(texture2, 0, 0, null);
			PacmanBody.texture3.getGraphics().drawImage(texture3, 0, 0, null);
			PacmanBody.texture4.getGraphics().drawImage(texture4, 0, 0, null);
			PacmanBody.texture5.getGraphics().drawImage(texture5, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void itsAlive() {
		this.p = new PacmanSoul(this.rate, this, this.trekLenght);
		GameField.getInstance().getThreads().execute(this.p);
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

	public boolean checkIfBeingMoved() {
		return isMoving;
	}

	public void tellIfBeingMoved(boolean isMoved) {
		this.isMoving = isMoved;
	}

	public void recalibrate() {
		x = (int) Math.round(x);
		y = (int) Math.round(y);
	}

	public int getX() {
		return super.getX();
	}

	public int getY() {
		return super.getY();
	}

	public double getStep() {
		return step;
	}

	public PacmanBody()
	{
		super(true, false, false, false);
		texture = texture1;
		this.picID = 1;
		this.direction = "N";
		this.direc = -1;
		// this.widthF=7;
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.rate = Integer.parseInt(obj.getAttribute("rate"));
		// System.out.println("ololo");
		itsAlive();
		// p.InHell();
	}

	/**
	 * Метод, который добавляет инфу в файл если вы хотите чтоб всё работало
	 * пихайте такие методы везде где стафф!
	 * 
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("rate", String.valueOf((int) this.rate));
		obj.setAttribute("class", "com.freedom.gameObjects.PacmanBody");
	}

	public void move1(String direction) {

		logger.info("Coords double:" + x + " " + y + "|| Coord int: "
				+ (int) (x * getSize()) + " " + (int) (y * getSize()));
		this.direction = direction;

		Runnable r = new Mover<PacmanBody>(this, this.direction, 1, 10);
		Thread t = new Thread(r);
		t.start();
	}

	public boolean canGo() {
		if (GameField.getInstance().cells[this
				.getTargetCellCoordinates(direction).x][this
				.getTargetCellCoordinates(direction).y].ifCanPassThrough())
			return true;

		return false;

	}

	/**
	 * Штука, которая выдает пару чисел - координаты целла в массиве, лежащего
	 * роботом в некотором направлении от робота
	 * 
	 * @param direction
	 *            направление, в котором берется целл
	 * 
	 * @return пара координат нужного целла
	 * */
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

	public void changeTexture() {
		double rotationRequired = Math.toRadians(0);
		double locationX;
		double locationY;
		AffineTransform tx;
		AffineTransformOp op;
		switch (this.picID) {
		case 1:
			this.direc *= -1;
			if (this.direction.equals("S")) {
				rotationRequired = Math.toRadians(0);
			}
			if (this.direction.equals("N")) {
				rotationRequired = Math.toRadians(180);
			}
			if (this.direction.equals("W")) {
				rotationRequired = Math.toRadians(90);
			}
			if (this.direction.equals("E")) {
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture2.getWidth() / 2;
			locationY = texture2.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture = op.filter(texture2, null);
			this.picID += this.direc;
			break;
		case 2:
			if (this.direction.equals("S")) {
				rotationRequired = Math.toRadians(0);
			}
			if (this.direction.equals("N")) {
				rotationRequired = Math.toRadians(180);
			}
			if (this.direction.equals("W")) {
				rotationRequired = Math.toRadians(90);
			}
			if (this.direction.equals("E")) {
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture3.getWidth() / 2;
			locationY = texture3.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture = op.filter(texture3, null);
			this.picID += this.direc;
			break;
		case 3:
			if (this.direction.equals("S")) {
				rotationRequired = Math.toRadians(0);
			}
			if (this.direction.equals("N")) {
				rotationRequired = Math.toRadians(180);
			}
			if (this.direction.equals("W")) {
				rotationRequired = Math.toRadians(90);
			}
			if (this.direction.equals("E")) {
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture4.getWidth() / 2;
			locationY = texture4.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture = op.filter(texture4, null);
			this.picID += this.direc;
			break;
		case 4:
			if (this.direction.equals("S")) {
				rotationRequired = Math.toRadians(0);
			}
			if (this.direction.equals("N")) {
				rotationRequired = Math.toRadians(180);
			}
			if (this.direction.equals("W")) {
				rotationRequired = Math.toRadians(90);
			}
			if (this.direction.equals("E")) {
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture5.getWidth() / 2;
			locationY = texture5.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture = op.filter(texture5, null);
			this.picID += this.direc;
			break;
		case 5:
			this.direc *= -1;
			if (this.direction.equals("S")) {
				rotationRequired = Math.toRadians(0);
			}
			if (this.direction.equals("N")) {
				rotationRequired = Math.toRadians(180);
			}
			if (this.direction.equals("W")) {
				rotationRequired = Math.toRadians(90);
			}
			if (this.direction.equals("E")) {
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture5.getWidth() / 2;
			locationY = texture5.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture = op.filter(texture5, null);
			this.picID += this.direc;
			break;
		default:
		}
	}
}
