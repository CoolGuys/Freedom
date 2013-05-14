package com.freedom.gameObjects.uncontrolled;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

/**
 * 
<<<<<<< HEAD
 * Эта плитка может быть уничтожена. После уничтожения - провал
=======
 * Эта плитка может быть уничтожена После уничтожения - провал
>>>>>>> branch 'master' of https://github.com/CoolGuys/Freedom.git
 * 
 */

public class Tile2 extends Stuff {

	private static Image texture1;

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile2.png")).getScaledInstance(getSize(), getSize(),
					BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tile2()
	{
		super(false, true, false, false, 0, 5);
		textureRed = texture1;
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
		obj.setAttribute("class", "com.freedom.gameObjects.uncontrolled.Tile2");
	}

	@Override
	public void die() {
		Pit buf = new Pit(this.x, this.y);
		GameField.getInstance().cells[this.getX()][this.getY()].replace(this,
				buf);

	}

}
