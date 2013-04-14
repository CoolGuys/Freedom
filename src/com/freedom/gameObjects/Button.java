package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Button extends Stuff{
	
	protected boolean ifPressed;
	
	
	//при инициализации не нажата, что нормально
	public Button(int x, int y){
		super(false,true);
		super.x = x;
		super.y = y;
		try {
			texture = ImageIO.read(new File("Resource/Textures/Button.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
	}

}
