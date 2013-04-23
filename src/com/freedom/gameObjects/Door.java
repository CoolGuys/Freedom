package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Door extends Stuff {

	// открытость двери проверяем по passable

	private static Image textureOpen;
	private static Image textureClosed;
	
	static {
		try {
			textureClosed = ImageIO.read(new File(
					"Resource/Textures/DoorClosed.png"));
			textureOpen = ImageIO.read(new File(
					"Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Door() {
		super(false, false, true, false,0, 0);
		texture = textureClosed;

	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
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
		obj.setAttribute("class", "com.freedom.gameObjects.Door");
	}

	 boolean useOff() {
		if (super.passable) {
			texture = textureClosed;
			super.passable = false;
			return true;
		}
		return false;

	}

	 boolean useOn() {
		if (!super.passable) {
			texture = textureOpen;
			super.passable = true;
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		g.drawImage(texture, (int) (getX() * getSize()),
				(int) (getY() * getSize()), getSize(), getSize(), null);
	}

	public Image getTexture() {
		return this.texture;
	}

}
