package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.view.GameScreen;

/*
 * Телепорт ставим только на плитку!!!
 */
public class Teleport extends Stuff {

	static {
		try {
			textureOn = ImageIO.read(new File(
					"Resource/Textures/TeleporterOn.png")).getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureOff = ImageIO.read(new File(
					"Resource/Textures/TeleporterOn.png")).getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static Image textureOn;
	private static Image textureOff;

	private int xLeadTo;
	private int yLeadTo;

	protected int getXLeadTo() {
		return xLeadTo;
	}

	protected int getYLeadTo() {
		return yLeadTo;
	}

	// телепорт - хрупкая сущность.
	public Teleport()
	{
		super(false, true, 0, 1);
		texture = textureOff;
	}

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.xLeadTo = Integer.parseInt(obj.getAttribute("xLeadTo"));
		this.yLeadTo = Integer.parseInt(obj.getAttribute("yLeadTo"));
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
		obj.setAttribute("xLeadTo", String.valueOf((int) this.xLeadTo));
		obj.setAttribute("yLeadTo", String.valueOf((int) this.yLeadTo));
		obj.setAttribute("class", "com.freedom.gameObjects.Teleport");
	}

	boolean teleportate() {
		if (!GameField.getInstance().getCells()[this.xLeadTo][this.yLeadTo]
				.getIfPassable())
			return false;

		Stuff element = GameField.getInstance().getCells()[this.getX()][this
				.getY()].getTop();
		if (GameField.getInstance().getCells()[this.xLeadTo][this.yLeadTo]
				.add(element)) {
			GameField.getInstance().getCells()[this.getX()][this.getY()]
					.deleteStuff();
			return true;
		}

		return false;
	}

	protected void robotOn() {
		texture = textureOn;
		GameScreen.getInstance().paintImmediately(getX() * getSize(),
				getY() * getSize(), getSize(), getSize());
		Robot buf = GameField.getInstance().getRobot();
		buf.x = this.xLeadTo;
		buf.y = this.yLeadTo;
		if (!GameField.getInstance().getRobot().canGo())
			GameField.getInstance().getRobot().isMoving = false;

		texture = textureOff;
		GameScreen.getInstance().paintImmediately(getX() * getSize(),
				getY() * getSize(), getSize(), getSize());
	}

	public void draw(Graphics g) {
		g.drawImage(texture, (int) (getX() * getSize()),
				(int) (getY() * getSize()), getSize(), getSize(), null);
	}

}