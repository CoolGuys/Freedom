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
	 * 
	 * @gleb
	 */
	private Stuff[] content;
	private int contentAmount;

	public Cell(int a, int b)
	{
		this.x = a;
		this.y = b;
		this.contentAmount = 1;
		this.content = new Stuff[4];

		/*
		 * Это нужно изменить. В случае, когда есть провал, не нужно иметь тайл
		 * под ним, согласуйтесь с Ушем, чтобы все объекты считывались из файла
		 * на равных в случае, если там есть строка для нужного целла, или в
		 * противном случае, заполнялась одним только тайлом по-дефолту
		 * 
		 * @gleb
		 */
		this.content[0] = new Tile();
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
	 * 
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

	// Everything for robot:

	public Stuff takeObject() { // метод, выдающий роботу объект
		if (this.contentAmount == 1) // на мне ничего ничего не лежит
			return null;

		if (!this.content[this.contentAmount - 1].getIfTakeable())
			return null;

		Stuff buf = this.content[this.contentAmount - 1];
		this.deleteStuff();
		return (buf);
	}

	public boolean ifCanPassThrough() {
		if (this.contentAmount == 1)
			return true;
		for (int i = 1; i < this.contentAmount; i++) {
			if (!this.content[i].getIfPassable())
				return false;
		}
		return true;
	}

}
