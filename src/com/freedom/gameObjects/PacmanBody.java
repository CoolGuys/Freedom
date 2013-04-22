package com.freedom.gameObjects;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.utilities.MovementAnimator;
import com.freedom.view.ScreensHolder;

public class PacmanBody extends Stuff{
	
	private int rate; 
	boolean isMoving;
	//this.environment;

	public PacmanBody()
	{
		super(false, false ,0 ,10 );
		this.isMoving=false;
		try {
			texture = ImageIO.read(new File("Resource/Textures/BoxBlack.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
		this.rate=Integer.parseInt(obj.getAttribute("rate"));;
	}
	
	/**
	 * Метод, который добавляет инфу в файл
	 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("rate", String.valueOf((int)this.rate));
		obj.setAttribute("class","com.freedom.gameObjects.PacmanBody");
	} 
	
	public boolean canGo(char a;) {
		int x = (int) this.x;
		int y = (int) this.y;
		if (direction) {
			if (GameField.getInstance().getCells()[x][y - 1].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("S")) {
			// logger.info("Checking S direction");
			if (environment[x][y + 1].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("W")) {
			if (environment[x - 1][y].ifCanPassThrough())
				return true;
		}

		if (this.direction.equals("E")) {
			if (environment[x + 1][y].ifCanPassThrough())
				return true;
		}

		return false;

	}

	public void moveCoarse(String direction) {
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
	}
}
