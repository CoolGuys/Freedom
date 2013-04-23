package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.freedom.view.*;
import com.freedom.utilities.*;

public class Robot extends Stuff implements Moveable {

	private String direction;
	private Stuff container;
	private Cell[][] environment;

	private boolean isMoving;
	private double step = 0.1;

	protected static int maxLives = 100;

	Image textureN;
	Image textureS;
	Image textureE;
	Image textureW;

	private static Logger logger = Logger.getLogger("Robot");

	public Robot()
	{
		super();
		try {
			// textureN = ImageIO.read(new
			// File("Resource/Textures/RobotN.png"));
			textureS = ImageIO
					.read(new File("Resource/Textures/RobotSLOL.png"));
			textureE = ImageIO.read(new File("Resource/Textures/RobotE.png"));
			textureW = ImageIO.read(new File("Resource/Textures/RobotW.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public Robot(int posX, int posY, String direction, Stuff c, Cell[][] tiles, int lives)
	{
		super(false, false, 0, lives);
		super.x = posX;
		super.y = posY;
		this.direction = direction;
		this.container = c;
		this.environment = tiles;

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
		
		logger.setLevel(Level.OFF);
	}

	public double getStep() {
		return step;
	}
	
	public void setContainer(Stuff buf){
		this.container = buf;
	}
	public void emptyContainer(){
		this.container = null;
	}

	public String getDirection() {
		return this.direction;
	}
	
	public void SetXY(int xr, int yr){
		this.x=xr;
		this.y=yr;
	}

	public Stuff getContent() {
		return (this.container);
	}
	
	public boolean getIfEmpty(){
		if(this.container == null)
			return true;
		return false;
	}


	public void recalibrate() {
		x = (int) Math.round(x);
		y = (int) Math.round(y);

	}

	
	//модифицирована. выдает null если пойти нельзя
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
			if (environment[x - 1][y].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("E")) {
			if (environment[x + 1][y].ifCanPassThrough())
				return true;
		}

		return false;

	}

	public void moveCoarse(String direction) {

		logger.info(direction);
		this.direction = direction;
		ScreensHolder.getInstance().repaint();

		if ((!isMoving) & (this.canGo())) {
			isMoving = true;
			this.environment[(int)this.x][(int)this.y].robotOff();
			this.environment[getTargetCellCoordinates(direction).x][getTargetCellCoordinates(direction).y].robotOn();
			Runnable r = new MovementAnimator<Robot>(this, this.direction);
			Thread t = new Thread(r);
			t.start();
		} else
			return;
	}

	public void moveFine(String direction) {
		if (!direction.equals(this.direction)) {
			this.direction = direction;
			ScreensHolder.getInstance().repaint();
			return;
		}
		if ((!isMoving) & (this.canGo())) {
			isMoving = true;
			this.environment[(int)this.x][(int)this.y].robotOff();
			this.environment[getTargetCellCoordinates(direction).x][getTargetCellCoordinates(direction).y].robotOn();
			Runnable r = new MovementAnimator<Robot>(this, this.direction);
			Thread t = new Thread(r);
			t.start();
		} else
			return;
	}
	/**
	 * что за хуйня? где комментарии?
	 * @param direction
	 * @return
	 */
	public Point getTargetCellCoordinates(String direction) {
		Point point = new Point();
		if (direction.equals("N")){
			point.x=(int)this.x;
			point.y =(int)this.y-1;
		}
		else if (direction.equals("S")) {
			point.x=(int)this.x;
			point.y =(int)this.y+1;
		}
		else if (direction.equals("E")) {
			point.x=(int)this.x+1;
			point.y =(int)this.y;
		}
		else {
			point.x=(int)this.x-1;
			point.y =(int)this.y;
		}
		return point;
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

		if (this.container != null)
			return;
		//

		if (this.direction.equals("N")) {
			this.container = environment[x][y - 1].takeObject();
			if (this.container == null)
				return;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("S")) {
			this.container = environment[x][y + 1].takeObject();
			if (this.container == null)
				return;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("W")) {
			this.container = environment[x - 1][y].takeObject();
			if (this.container == null)
				return;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("E")) {
			this.container = environment[x + 1][y].takeObject();
			if (this.container == null)
				return;

			GameField.getInstance().cells = environment;
			GameScreen.getInstance().repaint();
		}
	}

	public void put() {

		int x = (int) this.x;
		int y = (int) this.y;
		if (this.container == null)
			return;

		if (this.direction.equals("N")) {
			if (!environment[x][y - 1].add(this.container))
				return;
			this.container = null;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("S")) {
			if (!environment[x][y + 1].add(this.container))
				return;
			this.container = null;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("W")) {
			if (!environment[x - 1][y].add(this.container))
				return;
			this.container = null;
			ScreensHolder.getInstance().repaint();
			return;
		}

		if (this.direction.equals("E")) {
			if (!environment[x + 1][y].add(this.container))
				return;
			this.container = null;
			GameField.getInstance().cells = environment;
			ScreensHolder.getInstance().repaint();
		}

	}

	public void draw(Graphics g) {
		logger.info("Coords double:" + x + " " + y + "|| Coord int: "
				+ (int) (x * getSize()) + " " + (int) (y * getSize()));

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

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
			logger.info(container.toString());
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

}
