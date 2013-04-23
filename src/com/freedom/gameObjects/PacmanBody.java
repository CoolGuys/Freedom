package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.utilities.MovementAnimator;
import com.freedom.view.ScreensHolder;

public class PacmanBody extends Stuff implements Moveable {

	private int rate;
	boolean isMoving;
	private double step = 0.1;
	private int pic; 
	private String dire;
	private int direc;

	private static BufferedImage texture1;
	private static BufferedImage texture2;
	private static BufferedImage texture3;
	private static BufferedImage texture4;
	private static BufferedImage texture5;
	
	
/*	private static Image textureN;
	private static Image textureS;
	private static Image textureE;
	private static Image textureW;*/

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/p1s.png"));
			texture2 = ImageIO.read(new File("Resource/Textures/p2s.png"));
			texture3 = ImageIO.read(new File("Resource/Textures/p3s.png"));
			texture4 = ImageIO.read(new File("Resource/Textures/p4s.png"));
			texture5 = ImageIO.read(new File("Resource/Textures/p5s.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void changeTexture(){			
		double rotationRequired = Math.toRadians(0);
		double locationX;
		double locationY;
		AffineTransform tx;
		AffineTransformOp op;
		switch ( this.pic)
		{
		case 1:		
			this.direc*=-1;
			if(this.dire.equals("S")){
				rotationRequired = Math.toRadians(0);
			}
			if(this.dire.equals("N")){
				rotationRequired = Math.toRadians(180);
			}
			if(this.dire.equals("W")){
				rotationRequired = Math.toRadians(90);
			}
			if(this.dire.equals("E")){
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture2.getWidth() / 2;
			locationY = texture2.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture=op.filter(texture2, null);
			this.pic+=this.direc;
			break;
		case 2: 
			if(this.dire.equals("S")){
				rotationRequired = Math.toRadians(0);
			}
			if(this.dire.equals("N")){
				rotationRequired = Math.toRadians(180);
			}
			if(this.dire.equals("W")){
				rotationRequired = Math.toRadians(90);
			}
			if(this.dire.equals("E")){
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture3.getWidth() / 2;
			locationY = texture3.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture=op.filter(texture3, null);
			this.pic+=this.direc;
			break;
		case 3: 
			if(this.dire.equals("S")){
				rotationRequired = Math.toRadians(0);
			}
			if(this.dire.equals("N")){
				rotationRequired = Math.toRadians(180);
			}
			if(this.dire.equals("W")){
				rotationRequired = Math.toRadians(90);
			}
			if(this.dire.equals("E")){
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture4.getWidth() / 2;
			locationY = texture4.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture=op.filter(texture4, null);
			this.pic+=this.direc;
			break;
		case 4: 
			if(this.dire.equals("S")){
				rotationRequired = Math.toRadians(0);
			}
			if(this.dire.equals("N")){
				rotationRequired = Math.toRadians(180);
			}
			if(this.dire.equals("W")){
				rotationRequired = Math.toRadians(90);
			}
			if(this.dire.equals("E")){
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture5.getWidth() / 2;
			locationY = texture5.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture=op.filter(texture5, null);
			this.pic+=this.direc;
			break;
		case 5: 
			this.direc*=-1;
			if(this.dire.equals("S")){
				rotationRequired = Math.toRadians(0);
			}
			if(this.dire.equals("N")){
				rotationRequired = Math.toRadians(180);
			}
			if(this.dire.equals("W")){
				rotationRequired = Math.toRadians(90);
			}
			if(this.dire.equals("E")){
				rotationRequired = Math.toRadians(270);
			}
			locationX = texture5.getWidth() / 2;
			locationY = texture5.getHeight() / 2;
			tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			this.texture=op.filter(texture5, null);
			this.pic+=this.direc;
			break;
		default:
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
		// TODO Auto-generated method stub
		return isMoving;
	}
	
	public void tellIfBeingMoved(boolean isMoved) {
		// TODO Auto-generated method stub
		this.isMoving = isMoved;
	}

	public void recalibrate() {
		x = (int) Math.round(x);
		y = (int) Math.round(y);
	}

	public int getX(){
		return super.getX();
	}
	
	public int getY(){
		return super.getY();
	}
	
	public double getStep() {
		return step;
	}

	public PacmanBody() {
		super(true, false, 0, 10);
		this.isMoving = false;
		texture = texture1;
		this.pic = 1;
	}

	public PacmanBody(int x1, int y1, int rate1,int wid) {
		super(false, false, 0, 10);
		this.isMoving = false;
		texture = this.texture1;
		this.pic = 1;
		this.x = x1;
		this.y = y1;
		this.rate=rate1;
		this.dire="N";
		this.direc=-1;
		PacmanSoul p = new PacmanSoul(rate1, this, wid);
		new Thread(p).start();// IT'S ALVE!!!! MYXAXA
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
		this.rate=Integer.parseInt(obj.getAttribute("rate"));
		
	}
	
	/**
	 * Метод, который добавляет инфу в файл
	 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("rate", String.valueOf((int)this.rate));
		obj.setAttribute("class","com.freedom.gameObjects.PacmanBody");
	} 
	
	public void move1(String dir) {
		char direction=dir.charAt(0);
		int x = (int) this.x;
		int y = (int) this.y;
		if (direction=='N') {
			if (GameField.getInstance().getCells()[x][y - 1].ifCanPassThrough()){
				GameField.getInstance().getCells()[x][y].deleteStuff();
				this.y--;
				this.dire="N";
				GameField.getInstance().getCells()[x][y-1].add(this);
				
			}
		}

		if (direction==('S')) {
			// logger.info("Checking S direction");
			if (GameField.getInstance().getCells()[x][y + 1].ifCanPassThrough()){
				GameField.getInstance().getCells()[x][y].deleteStuff();
				this.y++;
				this.dire="S";
				GameField.getInstance().getCells()[x][y+1].add(this);
			}
		}

		if (direction==('W')) {
			if (GameField.getInstance().getCells()[x - 1][y].ifCanPassThrough()){
				GameField.getInstance().getCells()[x][y].deleteStuff();
				this.x--;
				this.dire="W";
				GameField.getInstance().getCells()[x-1][y].add(this);
			}
		}

		if (direction==('E')) {
			if (GameField.getInstance().getCells()[x + 1][y].ifCanPassThrough()){
				GameField.getInstance().getCells()[x][y].deleteStuff();
				this.x++;
				this.dire="E";
				GameField.getInstance().getCells()[x+1][y].add(this);
			}
		}

	}
	
	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}
}
