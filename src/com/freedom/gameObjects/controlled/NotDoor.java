package com.freedom.gameObjects.controlled;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.uncontrolled.Wall;
import com.freedom.model.GameField;

public class NotDoor extends Stuff {

	// открытость двери проверяем по passable

	private static Image textureOpen;
	private static Image textureClosed;
	private static Image textureClosedHorisontal;
	private static Image textureClosedVertical;
	private boolean textureSet;

	static {
		try {
			textureClosedVertical = ImageIO.read(
					new File("Resource/Textures/DoorClosedVertical.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);
			textureClosedHorisontal = ImageIO.read(
					new File("Resource/Textures/DoorClosedHorisontal.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);
			;
			textureOpen = ImageIO.read(new File(
					"Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block\
		}
	}

	public NotDoor()
	{
		super(false, true);
		super.setExpConductive(false);
	}

	/**
	 * Метод, который добавляет инфу в файл если вы хотите чтоб всё работало
	 * пихайте такие методы везде где стафф!
	 * 
	 * @author UshAle
	 */

	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.NotDoor");
	}

	@Override
	public boolean useOn() {
		textureRed = textureClosed;
		if (super.passable) {
			super.passable = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean useOff() {

		textureRed = textureOpen;
		if (!super.passable) {
			super.passable = true;
			return true;
		}
		return false;
	}

	@Override
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
			textureRed = textureOpen;
		}
		g.drawImage(textureRed, getX() * getSize(),
				getY() * getSize(), getSize(), getSize(), null);
	}
	@Override
	public boolean absorbs(Stuff element) {
			return false;
	}

	@Override
	public boolean reflects(Stuff element) {
		return false;
	}

}
