package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Button extends Stuff {

	protected boolean ifPressed;

	ArrayList<Cell> leadTo = new ArrayList<Cell>();

	// при инициализации не нажата, что нормально
	// принимаются заявки на изменение входных данных)
	
	public Button()
	{
		super(false, true);
		super.x = x;
		super.y = y;

		try {
			texture = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
	}
	
	/**
	 * Метод, который добавляет инфу в файл
	 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("class","com.freedom.gameObjects.Tile2.png");
	} 
	
	public Button(int x, int y, Cell[] objects) {
		super(false, true);
		super.x = x;
		super.y = y;

		for (int i = 0; i < objects.length; i++) {
			this.leadTo.add(objects[i]);
		}

		try {
			texture = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
	}

	// конструктор для кнопки, которая будет ссылаться на 1 элем
	// , неудобно ведь толкать в массив
	public Button(int x, int y, Cell thing) {
		super(false, true);
		super.x = x;
		super.y = y;

		this.leadTo.add(thing);

		try {
			texture = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
	}


	protected void touch() {
		Cell buf;
		for (int i = 0; i < this.leadTo.size() - 1; i++) {
			buf = this.leadTo.get(i);
			buf.buttonPressed();
		}
		this.ifPressed = !this.ifPressed;
	}

}
