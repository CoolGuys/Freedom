package com.freedom.gameObjects;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Laser extends Stuff {
	LaserBeam beamHead;
	boolean ifActive;
	String colour;
	double xShoot;
	double yShoot;
	

	public Laser() {
		super(false, true, 0, 0);
		ifActive = false;
	}
	
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.xShoot = Integer.parseInt(obj.getAttribute("xShoot"));
		this.xShoot = Integer.parseInt(obj.getAttribute("yShoot"));
		this.colour = obj.getAttribute("colour");
	}
	
	protected boolean useOn(){
		beamHead = new LaserBeam(this.x + 0.5*GameField.getInstance().getCellSize(), this.y + 0.5*GameField.getInstance().getCellSize());
		return true;
	}
	
}