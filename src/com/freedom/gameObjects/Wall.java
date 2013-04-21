package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Wall extends Stuff {

	private static Image textureN;
	private static Image textureE;
	private static Image textureS;
	private static Image textureW;
	private static Image textureNE;
	private static Image textureSE;
	private static Image textureSW;
	private static Image textureNW;
	
	static {
		getImages();
	}
	
	private static void getImages() {
		try {
			textureN = ImageIO.read(new File("Resource/Textures/WallN.png"));
			textureE = ImageIO.read(new File("Resource/Textures/WallE.png"));
			textureS = ImageIO.read(new File("Resource/Textures/WallS.png"));
			textureW = ImageIO.read(new File("Resource/Textures/WallW.png"));
			textureNE = ImageIO.read(new File("Resource/Textures/WallNE.png"));
			textureSE = ImageIO.read(new File("Resource/Textures/WallSE.png"));
			textureSW = ImageIO.read(new File("Resource/Textures/WallSW.png"));			textureNW = ImageIO.read(new File("Resource/Textures/WallNW.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Wall()
	{
		super(false, false);
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
		boolean[] neighbourWalls = new boolean[4];
		if (cells[x + 1][y] != null)
			if (!(cells[x + 1][y].getContent()[0] instanceof Wall)) {
				g.drawImage(textureE, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);
			} else
				neighbourWalls[0] = true;
		

		if (cells[x][y + 1] != null)
			if (!(cells[x][y + 1].getContent()[0] instanceof Wall)) {
				g.drawImage(textureS, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);
			} else
				neighbourWalls[1] = true;

		if (cells[x - 1][y] != null)
			if (!(cells[x - 1][y].getContent()[0] instanceof Wall)) {
				g.drawImage(textureW, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);
			} else 
				neighbourWalls[2] = true;
		
		if (cells[x][y - 1] != null)
			if (!(cells[x][y - 1].getContent()[0] instanceof Wall)) {
				g.drawImage(textureN, (int) (getX() * getSize()),
						(int) (getY() * getSize()), getSize(), getSize(), null);
			} else
				neighbourWalls[3] = true;
		
		if (neighbourWalls[0] && neighbourWalls[1])
			g.drawImage(textureSE, (int) (getX() * getSize()),
					(int) (getY() * getSize()), getSize(), getSize(), null);
		if (neighbourWalls[1] && neighbourWalls[2])
			g.drawImage(textureSW, (int) (getX() * getSize()),
					(int) (getY() * getSize()), getSize(), getSize(), null);
		if (neighbourWalls[2] && neighbourWalls[3])
			g.drawImage(textureNW, (int) (getX() * getSize()),
					(int) (getY() * getSize()), getSize(), getSize(), null);
		if (neighbourWalls[3] && neighbourWalls[0])
			g.drawImage(textureNE, (int) (getX() * getSize()),
					(int) (getY() * getSize()), getSize(), getSize(), null);

	}

}
