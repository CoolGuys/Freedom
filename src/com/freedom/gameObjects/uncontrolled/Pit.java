package com.freedom.gameObjects.uncontrolled;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.characters.Robot;

public class Pit extends Stuff {

	public Pit() {
		super(false, true);
		textureRed = texture1;
	}

	public Pit(double x, double y) {
		super(false, true);
		textureRed = texture1;
		this.x = x;
		this.y = y;
		this.raiseDamage(10000);
		this.setColour("Red");
	}

	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		super.raiseDamage(Robot.maxLives);
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.uncontrolled.Pit");
	}

	private static Image texture1;
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Pit.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void touch(Stuff element) {
		element.punch(this.getDamage());
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
