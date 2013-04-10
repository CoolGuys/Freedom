package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

/**
 * 
 * @author IvTakm
 * 
 */

public class Tile extends Stuff {

	// if you want tile to be pit, just put damage = maxDamage
	// we also don't need coordinates - it'll get them while pulling to cell

	public Tile() { 
		super(false, true,0);
			try {
				texture = ImageIO.read(new File("Resource/Textures/Tile.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public Tile(boolean ifPit){ 
		super(false, true, Robot.maxLives);
		try {
			texture = ImageIO.read(new File("Resource/Textures/Pit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
	}

	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}

	private static Image texture;

}
