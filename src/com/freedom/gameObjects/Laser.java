package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Laser extends Stuff {
	LaserBeam beamHead;
	boolean ifActive;
	String colour;
	String direction;
	

	public Laser() {
		super(false, false,true, false);
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
		this.direction = obj.getAttribute("direction");
		this.colour = obj.getAttribute("colour");
		
		
		
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("direction",this.direction);
		obj.setAttribute("colour",this.colour);
		obj.setAttribute("class", "com.freedom.gameObjects.Laser");
	}
	
	boolean useOn(){
		if(this.ifActive)
			return false;
		else{
			this.ifActive = true;
			this.beamHead = new LaserBeam(this.direction, this.getX(), this.getY());
			this.beamHead.buildBeam();
			return true;	
		}
	}
	
	boolean useOf(){
		if(!this.ifActive)
			return false;
		else{
			this.ifActive = false;
			this.beamHead.deleteBeam();
			return true;
		}
	}
	
	
}