package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Button extends Stuff {

	private boolean ifPressed;

	private int[][] useList;//массив с координатами селлов на которые действует батон
	private int useAmount;//количество целлов на которые действует батон



	public Button() {
		super(false, true);
		super.x = x;
		super.y = y;
		useList = new int[10][2];
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
		NodeList list=obj.getElementsByTagName("cels");
		int length=list.getLength();
		for(int i=0;i<length;i++){
			Element buf = (Element)list.item(i);
			useList[i][0]=Integer.parseInt(buf.getAttribute("x"));
			useList[i][1]=Integer.parseInt(buf.getAttribute("y"));
		}
		this.useAmount=length;
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

	protected void touch() {
		for (int i = 0; i < useAmount; i++) {
			GameField.getInstance().getCells()[useList[i][0]][useList[i][1]].use();
		}
		this.ifPressed = !this.ifPressed;
	}

}
