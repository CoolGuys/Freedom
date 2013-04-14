package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Door extends Stuff {

	protected boolean ifOpen;

	public Door(int x, int y, boolean ifOpen) {
		super(false, false);
		super.x = x;
		super.y = y;
		this.ifOpen = ifOpen;
		try {
			texture = ImageIO
					.read(new File("Resource/Textures/DoorClosed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void buttonPressed() {
		if (this.ifOpen) {
			try {
				texture = ImageIO.read(new File(
						"Resource/Textures/DoorClosed.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.ifOpen = false;
		} else {
			try {
				texture = ImageIO.read(new File(
						"Resource/Textures/DoorOpened.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.ifOpen = true;
		}
	}

}
