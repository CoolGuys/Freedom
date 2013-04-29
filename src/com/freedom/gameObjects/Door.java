package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

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
					"Resource/Textures/DoorClosedVertical.png"));
			textureClosedHorisontal = ImageIO.read(new File(
					"Resource/Textures/DoorClosedHorisontal.png"));
			textureOpen = ImageIO.read(new File(
					"Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Door()
	{
<<<<<<< HEAD
		super(false, false,true, false);
		super.expConductive = false;
=======
		super(false, false, true, false);
>>>>>>> eea82d5996ffd291d973fef291cd68f23e18472a
	}

	/**
	 * Метод, который добавляет инфу в файл если вы хотите чтоб всё работало
	 * пихайте такие методы везде где стафф!
	 * 
	 * @author UshAle
	 */

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.Door");
	}

	boolean useOff() {

		texture = textureClosed;
		if (super.passable) {
			super.passable = false;
			return true;
		}
		return false;

	}

	boolean useOn() {

		texture = textureOpen;
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
			texture = textureClosed;
		}
		g.drawImage(texture, (int) (getX() * getSize()),
				(int) (getY() * getSize()), getSize(), getSize(), null);
	}

}
