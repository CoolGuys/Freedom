package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.utilities.SoundEngine;
import com.freedom.view.GameScreen;

public class Trap extends Stuff{
	
	
	private boolean used;
	private int damage;
	private static Image texture1;
	private static Image texture2;
	private static File f1;
	private static File f2;
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/TrapOpen.png"));
			texture2 = ImageIO.read(new File("Resource/Textures/TrapClosed.png"));
			f1 = new File("Resource/Sound/beep-5.wav");
			f2 = new File("Resource/Sound/ButtonClicked.wav");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Trap()
	{
		super(true, true,true, false, 0, 0);
		texture=texture1;
	}
	
	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * @param - Scanner файла
	 */
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
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("damage", String.valueOf((int) this.damage));
		obj.setAttribute("used", String.valueOf(this.used));
		obj.setAttribute("class", "com.freedom.gameObjects.Trap");
	}
	/*
	 * 			File f = new File("Resource/Sound/Alert.wav");
			if (f.exists()) {
				SoundEngine.playClip(f, -5, -10);
			} else {
				//System.out.println("File ne naiden");
			}
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.freedom.gameObjects.Stuff#touch()
	 */
	void touch(){
		if (!this.used) {
			GameField.getInstance().getCells()[this.getX()][this.getY()]
					.kickAllStuff(this.damage);
			used = true;
			this.texture = texture2;
			SoundEngine.playClip(f1, -1, -15);
			GameScreen.getInstance().repaint(
					(int) (this.getX() * Stuff.getSize()),
					(int) (this.getY() * Stuff.getSize()), Stuff.getSize(),
					Stuff.getSize());
		} else {
			used = false;
			this.texture = texture1;
			SoundEngine.playClip(f2, -1, -15);
			GameScreen.getInstance().repaint(
					(int) (this.getX() * Stuff.getSize()),
					(int) (this.getY() * Stuff.getSize()), Stuff.getSize(),
					Stuff.getSize());
		}
	}
}
