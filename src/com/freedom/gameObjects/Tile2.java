package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

/**
 * захотелось создать ещо объектик
 * @author ushale
 *
 */

public class Tile2 extends Stuff {

	private static Image texture1;
	
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Tile2() { 
		super(false, true);
		texture = texture1;
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
		obj.setAttribute("class","com.freedom.gameObjects.Tile2");
	} 

	public Image getTexture() { 
		return texture;
	}
	
	

}
