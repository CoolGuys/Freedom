package com.freedom.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

/*
 * признак конца - this.next.next = this.next;
 */

public class LaserBeam extends Stuff {

	LaserBeam next;
	String direction;
	private static Image textureVertical;
	private static Image textureNW;
	private static Image textureHorisontal;
	private static Image textureNE;

	private void setPicture(String direct) {
		if (direct.equals("N"))
			this.texture = textureVertical;
		if (direct.equals("S"))
			this.texture = textureVertical;
		if (direct.equals("W"))
			this.texture = textureHorisontal;
		if (direct.equals("E"))
			this.texture = textureHorisontal;

		if (direct.equals("NW"))
			this.texture = textureNW;
		if (direct.equals("SW"))
			this.texture = textureNE;
		if (direct.equals("NE"))
			this.texture = textureNE;
		if (direct.equals("SE"))
			this.texture = textureNW;

		return;
	}
	
	private String reflect(){
		if (this.direction.equals("N"))
			return("S");
		else if (this.direction.equals("S"))
			return("N");
		else if (this.direction.equals("W"))
			return("E");
		else if (this.direction.equals("E"))
			return("W");
		
		else if (this.direction.equals("NW"))
			return("SE");
		else if (this.direction.equals("NE"))
			return("SW");
		else if (this.direction.equals("SE"))
			return("NW");
		else
			return("NE");
	}

	private Point getTargetCellCoordinates() {
		Point point = new Point();
		if (direction.equals("N")) {
			point.x = (int) this.x;
			point.y = (int) this.y - 1;
		} else if (direction.equals("S")) {
			point.x = (int) this.x;
			point.y = (int) this.y + 1;
		} else if (direction.equals("E")) {
			point.x = (int) this.x + 1;
			point.y = (int) this.y;
		} else if (this.direction.equals("W")) {
			point.x = (int) this.x - 1;
			point.y = (int) this.y;
		} else if (direction.equals("SW")) {
			point.x = (int) this.x - 1;
			point.y = (int) this.y + 1;
		} else if (direction.equals("SE")) {
			point.x = (int) this.x + 1;
			point.y = (int) this.y + 1;
		} else if (this.direction.equals("NW")) {
			point.x = (int) this.x - 1;
			point.y = (int) this.y - 1;
		} else {
			point.x = (int) this.x + 1;
			point.y = (int) this.y - 1;
		}
		return point;
	}

	public LaserBeam(String direction, int x, int y) {
		super(false, false, true, false);
		super.x = x;
		super.y = y;
		this.direction = direction;
		next = null;
	}

	void buildBeam() {
		Cell[][] buf = GameField.getInstance().getCells();
		
		//если поглощает
		if(buf[this.getTargetCellCoordinates().x][this.getTargetCellCoordinates().y].getTop().getIfAbsorb()){
			this.next = null;
			buf[this.getTargetCellCoordinates().x][this.getTargetCellCoordinates().y].touch();
		}
		//если отражает
		else if(buf[this.getTargetCellCoordinates().x][this.getTargetCellCoordinates().y].getTop().getIfReflect()){
			String stbuf = this.direction;
			this.direction = this.reflect();
			this.next = new LaserBeam(this.direction, this.getTargetCellCoordinates().x,this.getTargetCellCoordinates().y);
			this.next.buildBeam();
			return;
		}
		else{
			this.next = new LaserBeam(this.direction, this.getTargetCellCoordinates().x,this.getTargetCellCoordinates().y);
		}

	}

}
