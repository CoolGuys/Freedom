package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

/*
 * Телепорт ставим только на плитку!!!
 */
public class Teleport extends Stuff {

	static {
		try {
			textureOn = ImageIO.read(
					new File("Resource/Textures/TeleporterOn.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureOff = ImageIO.read(
					new File("Resource/Textures/TeleporterOff.png"))
					.getScaledInstance(getSize(), getSize(),
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

	private boolean on;

	protected int getXLeadTo() {
		return xLeadTo;
	}

	protected int getYLeadTo() {
		return yLeadTo;
	}

	// телепорт - хрупкая сущность.
	public Teleport() {
		super(false, true, false, false, 0, 1);
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

	void touch() {
		if (!on)
			return;
		if (!GameField.getInstance().getCells()[this.xLeadTo][this.yLeadTo]
				.getIfPassable())
			return;

		Stuff element = GameField.getInstance().getCells()[this.getX()][this
				.getY()].getTop();
		for (Stuff containedElement : element.container) {
			if (containedElement != null) {
				containedElement.x = xLeadTo;
				containedElement.y = yLeadTo;
			}
		}
		if (GameField.getInstance().getCells()[this.xLeadTo][this.yLeadTo]
				.add(element)) {
			GameField.getInstance().getCells()[this.getX()][this.getY()]
					.deleteStuff();
			return;
		}
	}

	protected boolean useOff() {
		if (this.on) {
			texture = textureOff;
			this.on = false;
			return true;
		}
		return false;

	}

	protected boolean useOn() {
		if (!this.on) {
			this.on = true;
			texture = textureOn;
			return true;
		} else
			return false;

	}

	public void draw(Graphics g) {
		g.drawImage(texture, (int) (getX() * getSize()),
				(int) (getY() * getSize()), getSize(), getSize(), null);
	}

	public void giveInfo() {
		GameField.getInstance().getCells()[(int) x][(int) y].highlight();
		GameField.getInstance().getCells()[xLeadTo][yLeadTo].highlight();

	}

	public void removeInfo() {
		GameField.getInstance().getCells()[(int) x][(int) y].unhighlight();
		GameField.getInstance().getCells()[xLeadTo][yLeadTo].unhighlight();
	}

}