package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Button extends Stuff {

	private boolean ifPressed;
	private Image texturePressed;
	private Image textureDepressed;
	private int[][] useList;//массив с координатами селлов на которые действует батон
	private int useAmount;  //количество целлов на которые действует батон

	public int getUseAmount(){
		return useAmount;
	}

	public int[][] getUseList() {
		return useList;
	}

	public boolean obj() {
		return false;
	}
	//кастыли
	public boolean objc(){
		return true;
	}

	public Button() {
		super(false, true);
		super.x = x;
		super.y = y;
		useList = new int[10][2];
		try {
			texturePressed = ImageIO.read(new File("Resource/Textures/ButtonPressed.png"));
			textureDepressed = ImageIO.read(new File("Resource/Textures/ButttonDepressed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
		texture=textureDepressed;
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
		this.ifPressed=Boolean.parseBoolean(obj.getAttribute("Press"));
		if(this.ifPressed){
			texture=texturePressed;
		}else {
			texture=textureDepressed;
		}
		int length=list.getLength();
		for(int i=0;i<length;i++){
			Element buf = (Element)list.item(i);
			useList[i][0]=Integer.parseInt(buf.getAttribute("x"));
			useList[i][1]=Integer.parseInt(buf.getAttribute("y"));
		}
		this.useAmount=length;
	}
	
	/**
	 * неа, не то. надо еще сохранить её состояние как-то. иначе
	 * при запуске всегда будет закрыта
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.Button");
		obj.setAttribute("Press", String.valueOf(this.ifPressed));
	}

	protected void touch() {
		for (int i = 0; i < useAmount; i++) {
			GameField.getInstance().getCells()[useList[i][0]][useList[i][1]].use();
		}
		this.ifPressed = !this.ifPressed;
		if(this.ifPressed){
			texture=texturePressed;
		}else {
			texture=textureDepressed;
		}
	}

}
