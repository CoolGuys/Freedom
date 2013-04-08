package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * 
 * @author ush
 * 
 */

public class Tile extends Stuff {

	public Tile()
	{
		super(false, true);
		try {
			texture = ImageIO.read(new File("Resource/Textures/Tile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readLvlFile(Scanner sc){
		this.x = sc.nextInt(); 
		this.y = sc.nextInt();		
	}

	public void draw(Graphics g) {
		g.drawImage(texture, getX(), getY(), getSize(),
				getSize(), null);
	}

	private static Image texture;

}
