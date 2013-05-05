package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Pit extends Stuff{
	
	public Pit() { 
		super(false, true, false, false);
		texture=texture1;
	}
	
	public Pit(double x, double y) { 
		super(false, true, false, false);
		texture=texture1;
		this.x = x;
		this.y = y;
	}
	
	
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
		super.raiseDamage(Robot.maxLives);
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("class","com.freedom.gameObjects.Pit");
	} 
	
	
	private static Image texture1;
	static {
		try {
			texture1= ImageIO.read(new File("Resource/Textures/p2s.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
