package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;


public class Wall extends Stuff {


	private static Map<String, Image> images = new HashMap<String, Image>();

	// здесь ключ: B stands for Boarder ; с ним - контакт с границей,
	// "N","W","NW" ясны и так

	public static void getImages() {
		try {
			images.put("Sole",ImageIO.read(new File("Resource/Textures/WallSole.png")));
			/*images.put("BN",ImageIO.read(new File("Resource/Textures/WallBN.png")));
			images.put("BE",ImageIO.read(new File("Resource/Textures/WallBE.png")));
			images.put("BS",ImageIO.read(new File("Resource/Textures/WallBS.png")));
			images.put("BW",ImageIO.read(new File("Resource/Textures/WallBW.png")));
			images.put("BNE",ImageIO.read(new File("Resource/Textures/WallBNE.png")));
			images.put("BNW",ImageIO.read(new File("Resource/Textures/WallBNW.png")));
			images.put("BSE",ImageIO.read(new File("Resource/Textures/WallBSE.png")));
			images.put("BSW",ImageIO.read(new File("Resource/Textures/WallBSW.png")));
			images.put("Hor",ImageIO.read(new File("Resource/Textures/WallHor.png")));
			images.put("Ver",ImageIO.read(new File("Resource/Textures/WallVer.png")));
			images.put("NE",ImageIO.read(new File("Resource/Textures/WallNE.png")));
			images.put("NW",ImageIO.read(new File("Resource/Textures/WallNW.png")));
			images.put("SE",ImageIO.read(new File("Resource/Textures/WallSE.png")));
			images.put("SW",ImageIO.read(new File("Resource/Textures/WallSW.png")));*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Wall() {
		super(false, false);
		getImages();
		texture = images.get("Sole");
		
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
		g.drawImage(texture, (int) (getX() * getSize()),
				(int) (getY() * getSize()), getSize(), getSize(), null);
	}
	/*static{
		getImages();
	}*/
}
