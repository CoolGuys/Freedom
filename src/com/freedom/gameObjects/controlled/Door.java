package com.freedom.gameObjects.controlled;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.uncontrolled.Wall;
import com.freedom.model.GameField;

public class Door extends Stuff {

	// открытость двери проверяем по passable

	private static Image textureOpen;
	private static Image textureClosedVertical;
	private static Image textureClosedHorisontal;
	private static Image textureClosed;
	private boolean textureSet;

	static {
		try {
			textureClosedVertical = ImageIO.read(new File(
					"Resource/Textures/DoorClosedVertical.png")).getScaledInstance(getSize(), getSize(), BufferedImage.SCALE_SMOOTH);
			textureClosedHorisontal = ImageIO.read(new File(
					"Resource/Textures/DoorClosedHorisontal.png")).getScaledInstance(getSize(), getSize(), BufferedImage.SCALE_SMOOTH);
			textureOpen = ImageIO.read(new File(
					"Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Door()
	{
		super(false, false,true, false);
		super.setExpConductive(false);
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.Door");
	}

	public boolean useOff() {

		textureRed = textureClosed;
		if (super.passable) {
			super.passable = false;
			return true;
		}
		return false;

	}

	public boolean useOn() {

		textureRed = textureOpen;
		if (!super.passable) {
			super.passable = true;
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		if (!textureSet) {
			Cell[][] cells = GameField.getInstance().cells;
			int x = (int) this.x;
			int y = (int) this.y;
			boolean[] neighbourWalls = new boolean[4];
			if (cells[x + 1][y] != null)
				if ((cells[x + 1][y].getContent()[0] instanceof Wall))
					neighbourWalls[0] = true;

			if (cells[x][y + 1] != null)
				if ((cells[x][y + 1].getContent()[0] instanceof Wall))
					neighbourWalls[1] = true;

			if (cells[x - 1][y] != null)
				if ((cells[x - 1][y].getContent()[0] instanceof Wall))
					neighbourWalls[2] = true;

			if (cells[x][y - 1] != null)
				if ((cells[x][y - 1].getContent()[0] instanceof Wall))
					neighbourWalls[3] = true;
			if (neighbourWalls[1] && neighbourWalls[3])
				textureClosed = textureClosedVertical;
			else
				textureClosed = textureClosedHorisontal;
			textureSet = true;
			textureRed = textureClosed;
		}
		g.drawImage(textureRed, (int) (getX() * getSize()),
				(int) (getY() * getSize()), getSize(), getSize(), null);
	}

}
