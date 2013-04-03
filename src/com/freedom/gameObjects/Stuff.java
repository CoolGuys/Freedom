package com.freedom.gameObjects;
//общее для все имеющихся объектов
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stuff {
	//ДОБАВЛЕНО	 поле "могу ли взять"
	
	protected int x;
	protected int y;
	private boolean pickable; //"поднимаем" ли объект?
	//private Image texture;
	
	public Stuff(int posX, int posY, boolean pickable){ //Глеб, допиши здесь создание рисунка
		this.x = posX;
		this.y = posY;
		this.pickable = pickable;
	}
	
	//////////////
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}
	
	public boolean getIfCanTake(){
		return this.pickable;
	}
	/////////////////////
	
}
