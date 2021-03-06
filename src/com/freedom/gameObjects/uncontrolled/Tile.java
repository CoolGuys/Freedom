package com.freedom.gameObjects.uncontrolled;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;

/**
 * 
 * @author IvTakm
 * 
 */

public class Tile extends Stuff {

	private static Image texture1;
	static {

		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tile()
	{
		super(false, true);
		textureRed = texture1;
	}
	
	public Tile(int x,int y,StuffColor c){
		super(false, true);
		this.x = x;
		this.y = y;
		this.color=c;
	}

	/**
	 * Метод, который добавляет инфу в файл если вы хотите чтоб всё работало
	 * пихайте такие методы везде где стафф!
	 * 
	 * @author UshAle
	 */
	@Override
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.uncontrolled.Tile");
	}
	
	@Override
	public boolean absorbs(Stuff element){
		return false;
	}
	
	@Override
	public boolean reflects(Stuff element) {
		return false;
	}
}
