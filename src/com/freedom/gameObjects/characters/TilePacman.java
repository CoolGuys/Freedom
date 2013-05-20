package com.freedom.gameObjects.characters;

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
		super.loadToFile(obj);
		obj.setAttribute("rate", String.valueOf((int) super.getRate()));
		obj.setAttribute("alive", String.valueOf(super.getAlive()));
		obj.setAttribute("trekLenght", String.valueOf((int) super.trekLenght));
		obj.setAttribute("class", "com.freedom.gameObjects.characters.TilePacman");
		
	}
	public TilePacman()
	{
		super(false, true, 0, 3);
		textureRed = texturetile;
	}

	public void changeTexture() {
		textureRed = TilePacman.texturetile;
	}
}
