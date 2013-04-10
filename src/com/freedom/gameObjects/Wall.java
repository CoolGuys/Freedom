package com.freedom.gameObjects;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Wall extends Stuff{
	
	public Wall(){
		super(false,false,0);
		try {
			texture = ImageIO.read(new File("Resource/Textures/Wall.png"));
		} catch (IOException e) {
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
	
}
