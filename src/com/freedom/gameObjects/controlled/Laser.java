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
		super(false, false, true, false);
		ifActive = false;
		textureRed=textureGreen=texture1;
		sender = new BeamSender();
	}
	
	//метод для призмы
	public Laser(boolean forReflector){
		super(false, false, false, true);
		ifActive = false;
		textureRed=textureGreen=textureBlue = texture1;
		sender = new BeamSender();
	}

	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		//this.setColour("Green");
		this.direction = obj.getAttribute("direction");
		System.out.println(""+getColour());
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("direction", this.direction);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.Laser");
	}

	public void rebuidBeam() {
		this.beamHead.deleteBeam();
		this.beamHead.buildBeam();
	}

	public boolean useOn() {
		if (this.ifActive)
			return false;
		else {
			this.ifActive = true;
			this.beamHead = new LaserBeam(this.direction, this.getX(),
					this.getY(), 8);
			this.beamHead.setSource(this);
			GameField.getInstance().getDeathTicker().addActionListener(sender);
			return true;
		}
	}

	@Override
	public boolean useOff() {
		if (!this.ifActive)
			return false;
		else {
			this.ifActive = false;
			GameField.getInstance().getDeathTicker()
					.removeActionListener(sender);
			this.beamHead.deleteBeam();
			return true;
		}
	}

	@Override
	// вращаем по часовой
	public void interact(Stuff interactor) {
		boolean condition = this.useOff();
		if(this.direction.equals("N"))
			this.direction = "NE";
		else if(this.direction.equals("S"))
			this.direction = "SW";
		else if(this.direction.equals("E"))
			this.direction = "SE";
		else if(this.direction.equals("W"))
			this.direction = "NW";

		else if(this.direction.equals("NW"))
			this.direction = "N";
		else if(this.direction.equals("NE"))
			this.direction = "E";

		else if(this.direction.equals("SW"))
			this.direction = "W";

		else if(this.direction.equals("SE"))
			this.direction = "S";
		
		if(condition)
			this.useOn();
	}

	private class BeamSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Laser.this.beamHead.deleteBeam();
			Laser.this.beamHead.buildBeam();
		}
	}

}
