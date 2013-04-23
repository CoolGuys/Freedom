package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Laser extends Stuff {
	LaserBeam beamHead;
	boolean ifActive;
	String colour;
	double xShoot;
	double yShoot;
	

	public Laser() {
		super(false, true, false, true,0, 0);
		ifActive = false;
		
		try {
			this.texture = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.xShoot = Double.parseDouble(obj.getAttribute("xShoot"));
		this.xShoot = Double.parseDouble(obj.getAttribute("yShoot"));
		this.colour = obj.getAttribute("colour");
		
		
		
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("xShoot", String.valueOf( this.xShoot));
		obj.setAttribute("yShoot", String.valueOf( this.yShoot));
		obj.setAttribute("class", "com.freedom.gameObjects.Laser");
	}
	
	void robotOn(){
		beamHead = new LaserBeam(this.x + 0.5*GameField.getInstance().getCellSize(), this.y + 0.5*GameField.getInstance().getCellSize());
		beamHead.buildBeam(xShoot, yShoot);
	}
	
	
}