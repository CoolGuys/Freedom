package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class LevelExit extends Stuff{
	
	private int nextLevelID;
	private static Image texture1;
	
	static {

		try {
			texture1 = ImageIO.read(new File("Resource/Textures/NextLevel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public LevelExit()
	{
		super(false, true,0,0);
		texture = texture1;
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
		this.nextLevelID=Integer.parseInt(obj.getAttribute("next"));
	}
	
	/**
	 * Метод, который добавляет инфу в файл
	 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("class","com.freedom.gameObjects.LevelExit");
		obj.setAttribute("next", String.valueOf((int)this.nextLevelID));
	} 
	
	public void robotOn(){
		GameField.getInstance().getRobot().SetXY(super.getX()-1, super.getY());
		GameField.getInstance().nextlvl(GameField.getInstance().getlvl(), nextLevelID);
	}
}
