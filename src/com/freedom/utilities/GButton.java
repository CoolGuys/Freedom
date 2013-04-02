package com.freedom.utilities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GButton {
	public GButton(String aText, int posX, int posY)
	{
		positionX = posX;
		positionY = posY;
		text = aText;
		try {
			texture = ImageIO.read(new File("Resource/Textures/GButton.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void performAction() {
		
	}
	
	public boolean checkIfPressed(Point p) {
		if ((p.getX() >= this.positionX
				&& p.getX() <= this.positionX + dimensionX
				&& p.getY() >= this.positionY && p.getY() <= this.positionY + dimensionY))
			return true;
		else
			return false;
	}

	public void draw(Graphics g) {
		g.drawImage(texture, positionX, positionY, dimensionX, dimensionY, null);
		g.drawString(text, positionX + 40, positionY + 15);
	}

	private int positionX, positionY;
	private int dimensionX = 100, dimensionY = 30;
	private int textOffsetX, textOffsetY;
	private String text;
	private Image texture;
	
	private class GAction {
		
	}
}
