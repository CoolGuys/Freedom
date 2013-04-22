package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

/**
 * 
 * @author IvTakm
 * 
 */

public class Tile extends Stuff {

	private static Image texture1;
	static {

		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// if you want tile to be pit, just put damage = maxDamage
	// we also don't need coordinates - it'll get them while pulling to cell

	public Tile()
	{
		super(false, true);
		texture = texture1;
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
		obj.setAttribute("class", "com.freedom.gameObjects.Tile");
	}

}
