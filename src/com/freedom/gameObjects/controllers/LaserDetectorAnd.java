package com.freedom.gameObjects.controllers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class LaserDetectorAnd extends ButtonAnd {

	public LaserDetectorAnd()
	{
		super(true);
	}

	@Override
	public void touch(Stuff toucher) {
		sender = new SignalOnSender();
		for (int i = 0; i < controlledCellsAmount; i++)
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]].counter++;

		GameField.getInstance().getTicker().addActionListener(sender);
		switch (toucher.getColor()) {
		case RED:
			this.textureRed = this.textureGreen = this.textureBlue = texturesOn[1];
		case GREEN:
			this.textureRed = this.textureGreen = this.textureBlue = texturesOn[2];
		case BLUE:
			this.textureRed = this.textureGreen = this.textureBlue = texturesOn[3];
		}
	}

	public void untouch(Stuff toucher) {
		super.untouch(toucher);
		this.textureRed = this.textureGreen = this.textureBlue = textureOff;
	}

	private static Image textureOff;
	private static Image[] texturesOn = new Image[4];

	static {
		try {
			textureOff = ImageIO.read(
					new File("Resource/Textures/LaserDetector/0.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			for (int i = 1; i <= 3; i++) {
				texturesOn[i] = ImageIO.read(
						new File("Resource/Textures/LaserDetector/" + i
								+ ".png")).getScaledInstance(getSize(),
						getSize(), BufferedImage.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
