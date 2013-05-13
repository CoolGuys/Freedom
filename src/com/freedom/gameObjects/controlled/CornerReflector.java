package com.freedom.gameObjects.controlled;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.freedom.gameObjects.base.Stuff;

public class CornerReflector extends Laser {
	
	private static Image texture1;
	
	//УПЧК!!!
	private static ArrayList<String> list = new ArrayList<String>();
	static{
		list.add("N");
		list.add("NE");
		list.add("E");
		list.add("SE");	
		list.add("S");
		list.add("SW");
		list.add("W");
		list.add("NW");
	}
	
	private int revolve(int revolveFrom, int toRevolve){
		int buf;
		buf = revolveFrom + toRevolve;
		if(buf <0)
			return (7-buf);
		if(buf >7)
			return (buf-7);
		return buf;
	}
	
	private boolean getDirection(String dir){
		int bufEntry = list.indexOf(dir);
		int bufMine = list.indexOf(this.direction);
		
		if(bufEntry == this.revolve(bufMine, 4))
			return true;
		
		if(bufEntry == this.revolve(bufMine, 7)){
			this.direction = list.get(this.revolve(bufEntry, 1));
			return true;
		}
		
		if(bufEntry == this.revolve(bufMine, -7)){
			this.direction = list.get(this.revolve(bufEntry, -1));
			return true;
		}
		return false;
	}
	//

	private static Logger logger = Logger.getLogger("Laser");

	static {
		logger.setLevel(Level.WARNING);
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			logger.warning("Laser texture was corrupted or deleted");
		}
	}

	public CornerReflector() {
		super(true);	
		textureRed = texture1;
	}
	
	public boolean useOn(){
		return false;
	}
	public boolean useOff(){
		return false;
	}
	
	public void touch(Stuff element){
		LaserBeam buf = (LaserBeam) element;///dangerous!!!!
		if(!this.getDirection(buf.direction))
			return;
		
		super.useOn();
	}
	
	public void untouch(Stuff element){
		super.useOff();
	}
	
	public void interract() {
		boolean condition = super.useOff();
		switch (this.direction) {
		case "N":
			this.direction = "NE";
		case "S":
			this.direction = "SW";
		case "E":
			this.direction = "SE";
		case "W":
			this.direction = "NW";

		case "NW":
			this.direction = "N";
		case "NE":
			this.direction = "E";
		case "SW":
			this.direction = "W";
		case "SE":
			this.direction = "S";
		}
		if(condition)
			super.useOn();
	}
	
	

}