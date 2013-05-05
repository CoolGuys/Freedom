package com.freedom.gameObjects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class LevelExit extends Stuff {

	private int nextLevelID;
	private static Image texture1;
	private int robotx;
	private int roboty;

	static {

		try {
			texture1 = ImageIO
					.read(new File("Resource/Textures/NextLevel.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LevelExit()
	{
		super(false, true, true, false);
		texture = texture1;
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.nextLevelID = Integer.parseInt(obj.getAttribute("next"));
		this.robotx = Integer.parseInt(obj.getAttribute("xr"));
		this.roboty = Integer.parseInt(obj.getAttribute("yr"));
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
		obj.setAttribute("xr", String.valueOf((int) this.robotx));
		obj.setAttribute("yr", String.valueOf((int) this.roboty));
		obj.setAttribute("class", "com.freedom.gameObjects.LevelExit");
		obj.setAttribute("next", String.valueOf((int) this.nextLevelID));
	}

	public void touch(Stuff element) {
		GameField.getInstance().getRobot()
				.SetXY(super.getX() - 1, super.getY());

		GameField.getInstance().switchToNextLevel(nextLevelID,robotx,roboty);
	}
}
