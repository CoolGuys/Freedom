package com.freedom.gameObjects.controlled;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;

public class Box extends Stuff {

	private static Image texture1;
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/BoxBlack.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Box()
	{
		super(true, false,true, false, 0, 3);
		texture=texture1;
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.Box");
	}
}




