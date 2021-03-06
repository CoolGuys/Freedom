package com.freedom.gameObjects.healthOperators;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.utilities.game.SoundEngine;

//TODO поправить, чтобы становился неактивным!

public class Trap extends Stuff {
	private boolean open;

	public Trap() {
		super(true, true, 0, 0);
		textureRed = textureOpen;
		this.open = true;
	}

	@Override
	public void touch(Stuff element) {
		if (open) {
			element.punch(this.damage);
			this.textureRed = textureClosed;
			SoundEngine.playClip(f1, -1, -15);
			repaintSelf();
			this.open = false;
		} else {
			return;
		}
	}

	@Override
	public void interact(Stuff element) {
		if (!open) {
			this.textureRed = textureOpen;
			this.open = true;
			SoundEngine.playClip(f2, -1, -15);
			repaintSelf();
		}
	}

	@Override
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
	@Override
	public void loadToFile(Element obj) {

		super.loadToFile(obj);
		obj.setAttribute("damage", String.valueOf(this.damage));
		obj.setAttribute("used", String.valueOf(this.used));
		obj.setAttribute("class",
				"com.freedom.gameObjects.healthOperators.Trap");
	}

	private boolean used;
	private int damage;
	private static Image textureOpen;
	private static Image textureClosed;
	private static File f1;
	private static File f2;
	static {
		try {
			textureOpen = ImageIO.read(
					new File("Resource/Textures/TrapOpen.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);
			textureClosed = ImageIO.read(
					new File("Resource/Textures/TrapClosed.png"))
					.getScaledInstance(getSize(), getSize(),
							Image.SCALE_SMOOTH);
			f1 = new File("Resource/Sound/beep-5.wav");
			f2 = new File("Resource/Sound/ButtonClicked.wav");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean absorbs(Stuff element){
		return false;
	}
	
	@Override
	public boolean reflects(Stuff element) {
		return false;
	}
}
