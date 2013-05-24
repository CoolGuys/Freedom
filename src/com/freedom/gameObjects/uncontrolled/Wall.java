package com.freedom.gameObjects.uncontrolled;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class Wall extends Stuff {

	public Wall() {
		super(false, false);
		super.setExpConductive(false);
	}
	
	public Wall(int x,int y,StuffColor c){
		super(false, true);
		this.x = x;
		this.y = y;
		this.color=c;
	}

	private void setTexture() throws ArrayIndexOutOfBoundsException {
		finalTexture=new BufferedImage(getSize(), getSize(), BufferedImage.TYPE_INT_RGB);
		Graphics g = finalTexture.getGraphics();
		Cell[][] cells = GameField.getInstance().getCells();
		int x = (int) this.x;
		int y = (int) this.y;
		boolean[] neighbourWalls = new boolean[4];

		if (cells[x + 1][y] != null)
			if (cells[x + 1][y].getContentAmount() != 0)
				if (!(cells[x + 1][y].getContent()[0] instanceof Wall)) {
					g.drawImage(textureE, 0, 0, null);
				} else
					neighbourWalls[0] = true;

		if (cells[x][y + 1] != null)
			if (cells[x][y + 1].getContentAmount() != 0)
				if (!(cells[x][y + 1].getContent()[0] instanceof Wall)) {
					g.drawImage(textureS, 0, 0, null);
				} else
					neighbourWalls[1] = true;

		if (cells[x - 1][y] != null)
			if (cells[x - 1][y].getContentAmount() != 0)
				if (!(cells[x - 1][y].getContent()[0] instanceof Wall)) {
					g.drawImage(textureW, 0, 0, null);
				} else
					neighbourWalls[2] = true;

		if (cells[x][y - 1] != null)
			if (cells[x][y - 1].getContentAmount() != 0)
				if (!(cells[x][y - 1].getContent()[0] instanceof Wall)) {
					g.drawImage(textureN, 0, 0, null);
				} else
					neighbourWalls[3] = true;

		
		
		if (cells[x + 1][y + 1] != null)
			if (neighbourWalls[0] && neighbourWalls[1]
					&& cells[x + 1][y + 1].getContentAmount() != 0)
				if (!(cells[x + 1][y + 1].getTop() instanceof Wall))
					g.drawImage(textureSE, 0, 0, null);

		if (cells[x - 1][y + 1] != null)
			if (neighbourWalls[1] && neighbourWalls[2]
					&& cells[x - 1][y + 1].getContentAmount() != 0)

				if (!(cells[x - 1][y + 1].getTop() instanceof Wall))
					g.drawImage(textureSW, 0, 0, null);

		if (cells[x - 1][y - 1] != null)
			if (neighbourWalls[2] && neighbourWalls[3]
					&& cells[x - 1][y - 1].getContentAmount() != 0)
				if (!(cells[x - 1][y - 1].getTop() instanceof Wall))
					g.drawImage(textureNW, 0, 0, null);

		if (cells[x + 1][y - 1] != null)
			if (neighbourWalls[3] && neighbourWalls[0]
					&& cells[x + 1][y - 1].getContentAmount() != 0)
				if (!(cells[x + 1][y - 1].getTop() instanceof Wall))
					g.drawImage(textureNE, 0, 0, null);

		textureRed = finalTexture;
//		ready = true;
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.uncontrolled.Wall");
	}

	public void draw(Graphics g) {
		setTexture();
		g.drawImage(textureRed, (int) (x * getSize()), (int) (y * getSize()),
				null);
	}

	private static Image textureN;
	private static Image textureE;
	private static Image textureS;
	private static Image textureW;
	private static Image textureNE;
	private static Image textureSE;
	private static Image textureSW;
	private static Image textureNW;
	private BufferedImage finalTexture = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);

	static {
		getImages();
	}

	private static void getImages() {
		try {
			textureN = ImageIO.read(new File("Resource/Textures/WallN.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureE = ImageIO.read(new File("Resource/Textures/WallE.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureS = ImageIO.read(new File("Resource/Textures/WallS.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureW = ImageIO.read(new File("Resource/Textures/WallW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureNE = ImageIO.read(new File("Resource/Textures/WallNE.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureSE = ImageIO.read(new File("Resource/Textures/WallSE.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureSW = ImageIO.read(new File("Resource/Textures/WallSW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureNW = ImageIO.read(new File("Resource/Textures/WallNW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean absorbs(Stuff element){
		return false;
	}
	
	@Override
	public boolean reflects(Stuff element) {
		return true;
	}

}
