package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

/**
 * 
 * Ты зачем ко мне в код пришёл? Дверь мне быстро сделал! Дверь мне запилил!
 * 
 * @author ИнтереснаяЛичность
 * 
 */

public class Door extends Stuff {

	protected boolean ifOpen;
	
	private Image textureOpen;
	private Image textureClosed;
	
	public Door(){
		super(false, false,0,0);
		try {
			textureClosed = ImageIO.read(new File("Resource/Textures/DoorClosed.png"));
			textureOpen = ImageIO.read(new File("Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ifOpen=false;
		this.texture = textureClosed;
	}
	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
		System.out.print("Двеееерьь мне запили!!!");
	}
	
	/**
	 * Метод, который добавляет инфу в файл
	 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("class","com.freedom.gameObjects.Door");
	} 
	
	protected void use() {
		if (this.ifOpen) {
			texture = textureClosed;
			this.ifOpen = false;
			super.passable = false;
		} else {
			texture  = textureOpen;
			this.ifOpen = true;
			super.passable = true;
		}
	}
	
	
	
	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}
	
	

}
