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


	public Tile() { 
		super(false, true);
	}
	
	
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
	}
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("class","com.freedom.gameObjects.Tile");
	} 

	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}


	public Image getTexture() { 
		return this.texture;
	}
	
	private static Image texture;
	static {
		try {
			texture = ImageIO.read(new File("Resource/Textures/Tile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
