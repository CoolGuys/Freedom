package com.freedom.gameObjects.healthOperators;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.utilities.game.SoundEngine;
import com.freedom.view.GameScreen;

public class Trap extends Stuff {
	public Trap()
	{
		super(true, true, true, false, 0, 0);
		texture = textureOpen;
	}

	@Override
	public void touch(Stuff element) {
		element.punch(this.damage);
		this.texture = textureClosed;
		SoundEngine.playClip(f1, -1, -15);
		GameScreen.getInstance().repaint((int) (this.getX() * Stuff.getSize()),
				(int) (this.getY() * Stuff.getSize()), Stuff.getSize(),
				Stuff.getSize());
	}

	@Override
	public void untouch(Stuff element) {
		this.texture = textureOpen;
		SoundEngine.playClip(f2, -1, -15);
		GameScreen.getInstance().repaint((int) (this.getX() * Stuff.getSize()),
				(int) (this.getY() * Stuff.getSize()), Stuff.getSize(),
				Stuff.getSize());
	
	}
	
	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		try {
			this.used = Boolean.parseBoolean(obj.getAttribute("used"));
		} catch (Exception e) {
			this.used = false;
		}
		try {
			this.damage = Integer.parseInt(obj.getAttribute("damage"));
		} catch (Exception e) {
			this.damage = 10;
		}

	}

	/**
	 * Метод, который добавляет инфу в файл если вы хотите чтоб всё работало
	 * пихайте такие методы везде где стафф!
	 */
	public void loadToFile(Element obj) {

		super.loadToFile(obj);
		obj.setAttribute("damage", String.valueOf((int) this.damage));
		obj.setAttribute("used", String.valueOf(this.used));
		obj.setAttribute("class", "com.freedom.gameObjects.healthOperators.Trap");
	}


	private boolean used;
	private int damage;
	private static Image textureOpen;
	private static Image textureClosed;
	private static File f1;
	private static File f2;
	static {
		try {
			textureOpen = ImageIO.read(new File(
					"Resource/Textures/TrapOpen.png"));
			textureClosed = ImageIO.read(new File(
					"Resource/Textures/TrapClosed.png"));
			f1 = new File("Resource/Sound/beep-5.wav");
			f2 = new File("Resource/Sound/ButtonClicked.wav");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
