package com.freedom.gameObjects.controlled;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class Laser extends Stuff {
	LaserBeam beamHead;
	boolean ifActive;
	String direction;
	private BeamSender sender;
	private static Image texture1;
	
	private static Logger logger = Logger.getLogger("Laser");
	
	
	static {
		logger.setLevel(Level.WARNING);
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			logger.warning("Laser texture was corrupted or deleted");
		}
	}
	
	public Laser() {
		super(false, false,true, false);
		ifActive = false;
		texture=texture1;
		sender = new BeamSender();
	}
	
	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		this.direction = obj.getAttribute("direction");
	}
	
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("direction",this.direction);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.Laser");
	}
	
	
	public void rebuidBeam(){
		this.beamHead.deleteBeam();
		this.beamHead.buildBeam();
	}
	
	public boolean useOn(){
		if(this.ifActive)
			return false;
		else{
			this.ifActive = true;
			this.beamHead = new LaserBeam(this.direction, this.getX(), this.getY(), 8);
			this.beamHead.setSource(this);
			GameField.getInstance().getDeathTicker().addActionListener(sender);
			return true;	
		}
	}
	
	@Override
	public boolean useOff(){
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