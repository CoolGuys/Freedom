package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cell {

	private int x;
	private int y;
	private Image texture;

	
	/*
	 * Нужно изменить, думаю, потребуется ArrayList
	 * @gleb
	 */
	private Stuff[] content; 
	private int contentAmount; 

	/*
	 * Это тоже надо будет изменить, вынести заполнение stuffIn в отдельный метод
	 * @gleb
	 */
	public Cell(int a, int b, int contentAmountin, Stuff stuffin[])
	{
		this.content = new Stuff[4]; // 4 - это по максимуму, чтоб не париться.
		this.x = a;
		this.y = b;
		this.contentAmount = contentAmountin;
		int i1;
		for (i1 = 0; i1 < this.contentAmount; i1++) {
			this.content[i1] = stuffin[i1];
		}
	}

	public Cell(int a, int b)
	{
		this.x = a;
		this.y = b;
		this.contentAmount = 1;
		this.content = new Stuff[4];
		this.content[0] = new Tile(a, b);
	}

	public boolean add(Stuff element) {
		if (this.contentAmount == 4)
			return false;

		this.content[this.contentAmount] = element;
		this.contentAmount++;
		element.x = this.x;
		element.y = this.y;
		return true;
	}

	/*
	 * Нужно изменить, должен удаляться первый попавшийся Stuff с pickable==true
	 * @gleb
	 */
	private void deleteStuff() { // удаляет "верхний" элемент
		if (this.contentAmount == 1)
			return;

		this.contentAmount--;
		this.content[this.contentAmount] = null;
	}

	// блок выдачи информации
	public int getContentAmount() {
		return (contentAmount);
	}

	public int getX() {
		return (this.x);
	}

	public int getY() {
		return (this.y);
	}

	public Stuff[] getContent() { // валидно ли без размера?
		return this.content;
	}

	// /конец блока
	
	
	
	//Everything for robot:
	
	public Stuff takeObject() { // метод, выдающий роботу объект
		if (this.contentAmount == 1) // на мне ничего ничего не лежит
			return null;

		if (!this.content[this.contentAmount - 1].getIfCanTake())
			return null;

		Stuff buf = this.content[this.contentAmount - 1];
		this.deleteStuff();
		return (buf);
	}
	
	public boolean ifCanPassThrough(){
		if(this.contentAmount==1)
			return true;
		for(int i = 1; i < this.contentAmount; i++){
			if (!this.content[i].ifCanPass())
				return false;
		}
		return true;
	}
	

}
