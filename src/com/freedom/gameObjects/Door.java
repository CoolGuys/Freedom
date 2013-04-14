package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Door extends Stuff {

	protected boolean ifOpen;
	private Button controller;
	
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
	
	

}
