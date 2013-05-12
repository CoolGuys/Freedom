package com.freedom.gameObjects.controllers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.characters.Robot;
import com.freedom.model.GameField;

public class LevelExit extends Stuff {

	private int nextLevelID;
	private static Image texture1;
	private int robotx;
	private int roboty;
	private boolean buf;
	
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

	public LevelExit() {
		super(false, true, true, false);
		texture = texture1;
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * @param - Scanner файла
	 */
	
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.nextLevelID = Integer.parseInt(obj.getAttribute("next"));
		this.robotx = Integer.parseInt(obj.getAttribute("xr"));
		this.roboty = Integer.parseInt(obj.getAttribute("yr"));
		String salive= obj.getAttribute("buf");
		System.out.println(salive+"lolo");
		if(!salive.equals("")){
			this.buf=Boolean.parseBoolean(salive);
		}else {
			this.buf=true;
		}
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
		obj.setAttribute("class", "com.freedom.gameObjects.controllers.LevelExit");
		obj.setAttribute("next", String.valueOf((int) this.nextLevelID));
		obj.setAttribute("buf", String.valueOf(this.buf));
	}

	
	
	
	public void touch(Stuff element) {

		if (element instanceof Robot) {
			GameField.getInstance().getRobot().setXY(this.x-1, this.y);
			GameField.getInstance().switchToNextLevel(nextLevelID, robotx,
					roboty,this.buf);
		}
	}
}
