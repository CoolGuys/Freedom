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

	private boolean alive;
	private int rate;
	boolean isMoving;
	private double step = 0.1;
	private int picID;
	private String direction;
	private int direc;
	private int trekLenght = 5;
	private PacmanSoul p;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("");

	private static BufferedImage texture1S = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture2S = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture3S = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture4S = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture5S = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture1N = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture2N = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture3N = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture4N = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture5N = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture1W = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture2W = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture3W = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture4W = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture5W = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture1E = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture2E = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture3E = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture4E = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	private static BufferedImage texture5E = new BufferedImage(getSize(),
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

			PacmanBody.texture1S.getGraphics().drawImage(texture1, 0, 0, null);
			PacmanBody.texture2S.getGraphics().drawImage(texture2, 0, 0, null);
			PacmanBody.texture3S.getGraphics().drawImage(texture3, 0, 0, null);
			PacmanBody.texture4S.getGraphics().drawImage(texture4, 0, 0, null);
			PacmanBody.texture5S.getGraphics().drawImage(texture5, 0, 0, null);

			double rotationRequired = Math.toRadians(270);
			int locationX = getSize() / 2;
			int locationY = getSize() / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			texture1E = op.filter(texture1S, null);
			texture2E = op.filter(texture2S, null);
			texture3E = op.filter(texture3S, null);
			texture4E = op.filter(texture4S, null);
			texture5E = op.filter(texture5S, null);
			rotationRequired = Math.toRadians(180);
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			texture1N = op.filter(texture1S, null);
			texture2N = op.filter(texture2S, null);
			texture3N = op.filter(texture3S, null);
			texture4N = op.filter(texture4S, null);
			texture5N = op.filter(texture5S, null);
			rotationRequired = Math.toRadians(90);
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX,
					locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			texture1W = op.filter(texture1S, null);
			texture2W = op.filter(texture2S, null);
			texture3W = op.filter(texture3S, null);
			texture4W = op.filter(texture4S, null);
			texture5W = op.filter(texture5S, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void itsAlive() {
		if (this.alive) {
			this.p = new PacmanSoul(this.rate, this, this.trekLenght);
			GameField.getInstance().getThreads().execute(this.p);
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

	public void setDirection(String direction) {
		this.direction=direction;
	}

	public PacmanBody()
	{
		super(true, false, false, false,0,1);
		texture = texture1N;
		this.picID = 1;
		this.direction = "N";
		this.direc = -1;
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
		String salive= obj.getAttribute("alive");
		System.out.println(salive+"lolo");
		if(!salive.equals("")){
			this.alive=Boolean.parseBoolean(salive);
		}else {
			this.alive=true;
		}
		itsAlive();
		// p.InHell();
	}
	
	void die(){
		this.alive=false;
		this.p.alive=false;
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
		obj.setAttribute("alive", String.valueOf(this.alive));
		obj.setAttribute("class", "com.freedom.gameObjects.PacmanBody");
		
	}

	public void move1(String direction) {

//		logger.info("Coords double:" + x + " " + y + "|| Coord int: "
//				+ (int) (x * getSize()) + " " + (int) (y * getSize()));
//	
	//	System.out.println(direction);
		Runnable r = new Mover<PacmanBody>(this, direction, 1, 10);
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
		switch (this.picID) {
		case 1:
			this.direc *= -1;
			if (this.direction.equals("S")) {
				texture = texture1S;
			}
			if (this.direction.equals("N")) {
				texture = texture1N;
			}
			if (this.direction.equals("W")) {
				texture = texture1W;
			}
			if (this.direction.equals("E")) {
				texture = texture1E;
			}
			this.picID += this.direc;
			break;
		case 2:
			if (this.direction.equals("S")) {
				texture = texture2S;
			}
			if (this.direction.equals("N")) {
				texture = texture2N;
			}
			if (this.direction.equals("W")) {
				texture = texture2W;
			}
			if (this.direction.equals("E")) {
				texture = texture2E;
			}
			this.picID += this.direc;
			break;
		case 3:
			if (this.direction.equals("S")) {
				texture = texture3S;
			}
			if (this.direction.equals("N")) {
				texture = texture3N;
			}
			if (this.direction.equals("W")) {
				texture = texture3W;
			}
			if (this.direction.equals("E")) {
				texture = texture3E;
			}
			this.picID += this.direc;
			break;
		case 4:
			if (this.direction.equals("S")) {
				texture = texture4S;
			}
			if (this.direction.equals("N")) {
				texture = texture4N;
			}
			if (this.direction.equals("W")) {
				texture = texture4W;
			}
			if (this.direction.equals("E")) {
				texture = texture4E;
			}
			this.picID += this.direc;
			break;
		case 5:
			this.direc *= -1;
			if (this.direction.equals("S")) {
				texture = texture5S;
			}
			if (this.direction.equals("N")) {
				texture = texture5N;
			}
			if (this.direction.equals("W")) {
				texture = texture5W;
			}
			if (this.direction.equals("E")) {
				texture = texture5E;
			}
			this.picID += this.direc;
			break;
		default:
		}
	}
}
