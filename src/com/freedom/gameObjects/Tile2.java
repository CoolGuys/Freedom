package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

/**
 * 
 * Эта плитка может быть уничтожена
 * После уничтожения - провал
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
		super(false, true,false, false,0,5);
		texture = texture1;
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
	
	void die(){
		Pit buf = new Pit(this.x,this.y);
		GameField.getInstance().cells[this.getX()][this.getY()].replace(this, buf);
		
	}
	
}
