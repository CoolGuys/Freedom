package com.freedom.gameObjects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Laser extends Stuff {
	LaserBeam beamHead;
	boolean ifActive;
	String color;
	String direction;
	private BeamSender sender;
	

	public Laser() {
		super(false, false,true, false);
		ifActive = false;
		
		
		try {
			this.texture = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sender = new BeamSender();
	}
	
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.direction = obj.getAttribute("direction");
		this.color = obj.getAttribute("color");
		
		
		
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("direction",this.direction);
		obj.setAttribute("color",this.color);
		obj.setAttribute("class", "com.freedom.gameObjects.Laser");
	}
	
	
	void rebuidBeam(){
		this.beamHead.deleteBeam();
		this.beamHead.buildBeam();
	}
	
	boolean useOn(){
		if(this.ifActive)
			return false;
		else{
			this.ifActive = true;
			this.beamHead = new LaserBeam(this.direction, this.getX(), this.getY(), 50);
			this.beamHead.setSource(this);
			GameField.getInstance().getDeathTicker().addActionListener(sender);
			return true;	
		}
	}
	
	@Override
	boolean useOff(){
		if(!this.ifActive)
			return false;
		else{
			this.ifActive = false;
			GameField.getInstance().getDeathTicker().removeActionListener(sender);
			this.beamHead.deleteBeam();
			return true;
		}
	}
	
	private class BeamSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Laser.this.beamHead.deleteBeam();
			Laser.this.beamHead.buildBeam();
		}
	}
	
}