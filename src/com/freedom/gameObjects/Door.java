package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Door extends Stuff{
	
	protected boolean ifOpen;
	private Button controller;
	
	
	public Door(int x, int y, boolean ifOpen){
		super(false,false);
		super.x = x;
		super.y = y;
		this.ifOpen = ifOpen;
		try {
			texture = ImageIO.read(new File("Resource/Textures/DoorClosed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tryOpen(){
		if (!controller.ifPressed){
			return;
		}
		
		ifOpen = true;
		try {
			texture = ImageIO.read(new File("Resource/Textures/DoorClosed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

}
