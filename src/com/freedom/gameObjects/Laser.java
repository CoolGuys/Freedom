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
		super(false, true, false, true,0, 0);
		ifActive = false;
	}
	
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.xShoot = Double.parseDouble(obj.getAttribute("xShoot"));
		this.xShoot = Double.parseDouble(obj.getAttribute("yShoot"));
		this.colour = obj.getAttribute("colour");
		beamHead.x = this.x+0.5;
		beamHead.y = this.y+0.5;
		beamHead.buildBeam(xShoot, yShoot);
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("xShoot", String.valueOf( this.xShoot));
		obj.setAttribute("yShoot", String.valueOf( this.yShoot));
		obj.setAttribute("class", "com.freedom.gameObjects.Door");
	}
	
	protected boolean useOn(){
		beamHead = new LaserBeam(this.x + 0.5*GameField.getInstance().getCellSize(), this.y + 0.5*GameField.getInstance().getCellSize());
		return true;
	}
	
}