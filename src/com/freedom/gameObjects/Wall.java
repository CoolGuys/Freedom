package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Wall extends Stuff {

	private Image textureN;
	private Image textureE;
	private Image textureS;
	private Image textureW;

	private void getImages() {
		try {
			textureN = ImageIO.read(new File("Resource/Textures/WallN.png"));
			textureE = ImageIO.read(new File("Resource/Textures/WallE.png"));
			textureS = ImageIO.read(new File("Resource/Textures/WallS.png"));
			textureW = ImageIO.read(new File("Resource/Textures/WallW.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Wall()
	{
		super(false, false);
		getImages();

	}

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
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
		obj.setAttribute("class", "com.freedom.gameObjects.Wall");
	}

	public void draw(Graphics g) {
		Cell[][] cells = GameField.getInstance().getCells();
		int x = (int) this.x;
		int y = (int) this.y;
		if (cells[x + 1][y] != null)
			if (!(cells[x + 1][y].getContent()[0] instanceof Wall))
				g.drawImage(textureE, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);

		if (cells[x][y + 1] != null)
			if (!(cells[x][y + 1].getContent()[0] instanceof Wall))
				g.drawImage(textureS, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);

		if (cells[x - 1][y] != null)
			if (!(cells[x - 1][y].getContent()[0] instanceof Wall))
				g.drawImage(textureW, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);
		if (cells[x][y - 1] != null)
			if (!(cells[x][y - 1].getContent()[0] instanceof Wall))
				g.drawImage(textureN, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);
	}

}
