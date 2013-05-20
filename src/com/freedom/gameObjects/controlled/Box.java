package com.freedom.gameObjects.controlled;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;

public class Box extends Stuff {

	private static Image texture1;
	private static Image texture2;
	private static Image texture3;
	
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Box/1.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			texture2 = ImageIO.read(new File("Resource/Textures/Box/2.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			texture3 = ImageIO.read(new File("Resource/Textures/Box/3.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Box()
	{
		super(true, false, 0, 3);
		textureRed = texture1;
		textureGreen = texture2;
		textureBlue = texture3;
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.Box");
	}
}
