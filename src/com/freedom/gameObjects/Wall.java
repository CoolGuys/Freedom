package com.freedom.gameObjects;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Wall extends Stuff{
	
	public Wall(){
		super(false,false,0);
		try {
			texture = ImageIO.read(new File("Resource/Textures/Wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		obj.setAttribute("class","com.freedom.gameObjects.Wall");
	} 
	
	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}
	
}
