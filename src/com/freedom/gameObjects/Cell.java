package com.freedom.gameObjects;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


//объект класса Плитка.
public class Cell {
	
	private int x;
	private int y;
	private Image texture;
	
	private Stuff [] content; // объекты, лежащие на клетке
	private int contentAmount; // кол-во объектов на клетке, может стать багом в случае лазера; 
	public Cell (int a, int b, int contentAmountin, Stuff stuffin[]){//зполнение массива content
		this.content = new Stuff[3]; //3 - это по максимуму, чтоб не париться. 
		this.x = a;
		this.y = b;
		this.contentAmount = contentAmountin;
		int i1;
		for(i1=0;i1<this.contentAmount;i1++)
		{
			this.content[i1]=stuffin[i1];
		}
	}
	
	
	//блок выдачи информации
	public int getContentAmount(){
		return(contentAmount);
	}
	
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}
	
	public Stuff[] getContent(){  // валидно ли без размера?
		return this.content;
	}
	
	///конец блока
	
	

	public Stuff takeObject(){ // метод, выдающий роботу объект
		if(this.contentAmount == 0)  //на мне ничего ничего не лежит
			return null;
		
		if (!this.content[this.contentAmount-1].getIfCanTake()) //зая, прости, но поднять нельзя
			return null;
		
		Stuff buf = this.content[this.contentAmount-1];
		this.content[this.contentAmount-1] = null;
		this.contentAmount--;
		return (buf);
	}
	
}