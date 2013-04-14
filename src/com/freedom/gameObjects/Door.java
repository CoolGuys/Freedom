package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Door extends Stuff {

	protected boolean ifOpen;
	
	private Image textureOpen;
	private Image textureClosed;
	
	public Door(int x, int y, boolean ifOpen){
		super(false,false);
		super.x = x;
		super.y = y;
		this.ifOpen = ifOpen;
		try {
			textureClosed = ImageIO.read(new File("Resource/Textures/DoorClosed.png"));
			textureOpen = ImageIO.read(new File("Resource/Textures/DoorOpen.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void buttonPressed() {
		if (this.ifOpen) {
				texture = textureClosed;
			this.ifOpen = false;
			super.passable = false;
		} else {
			texture = textureOpen;
			this.ifOpen = true;
			super.passable = true;
		}
	}
	
	
	
	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}
	
	

}
