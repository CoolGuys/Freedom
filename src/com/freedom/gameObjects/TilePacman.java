package com.freedom.gameObjects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class TilePacman extends PacmanBody{
	private static BufferedImage texturetile = new BufferedImage(getSize(),
			getSize(), BufferedImage.TYPE_INT_ARGB);
	static{
		try {
			Image texturetile = ImageIO
					.read(new File("Resource/Textures/Tile.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			TilePacman.texturetile.getGraphics().drawImage(texturetile, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("rate", String.valueOf((int) super.getRate()));
		obj.setAttribute("alive", String.valueOf(super.getAlive()));
		obj.setAttribute("trekLenght", String.valueOf((int) super.trekLenght));
		obj.setAttribute("class", "com.freedom.gameObjects.TilePacman");
		
	}
	public TilePacman()
	{
		super(false, true, false, false, 0, 3);
		texture = texturetile;
	}

	public void changeTexture() {
		texture = TilePacman.texturetile;
	}
}
