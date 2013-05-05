package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class HealthKit extends Stuff{
	
	private int heals;
	private static Image texture1;
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/HealthKit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HealthKit()
	{
		super(true, true, true, false);
		texture = texture1;
	}
	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		this.heals = Integer.parseInt(obj.getAttribute("heals"));
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("heals", String.valueOf((int) this.heals));
		obj.setAttribute("class", "com.freedom.gameObjects.HealthKit");
	}
	
	void touch() {
		GameField.getInstance().getCells()[this.getX()][this.getY()]
				.kickAllStuff(-this.heals);
		heals=0;
		GameField.getInstance().getCells()[(int) this.x][(int) this.y].deleteStuff(this);
	}
}
