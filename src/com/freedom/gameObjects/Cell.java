package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cell {

	private int x;
	private int y;
	private Image texture; // а оно нам здесь надо?? @ivan

	private Stuff[] content;
	private int contentAmount;

	public Cell(int a, int b) {
		this.x = a;
		this.y = b;
		this.contentAmount = 0;
		this.content = new Stuff[4];
	}

	public boolean add(Stuff element) {
		if (this.contentAmount == 4)
			return false;

		for (int i = 0; i < this.contentAmount; i++) { // с этим местом
														// аккуратнее при работе
			if (!this.content[i].getIfPassable())
				return false;
		}
		
		
		//теперь положить явно можем. кладем и изменяем состояние некот. объектов
		this.content[this.contentAmount] = element;
		this.contentAmount++;
		element.x = this.x;
		element.y = this.y;
		
		//акцент на кнопку - подумать потом, какие объекты ее нажимают.
		//пока не нажимает только лаз. луч
		if(element instanceof LaserBeam)
			return true;
		
			if(this.content[this.contentAmount - 2] instanceof Button){
				Button buf = (Button) this.content[this.contentAmount - 2];
				buf.touch();
			}
		
		return true;
	}

	/*
	 * теперь уникален в удалении только лазерный луч
	 * 
	 * @ivan
	 */
	private Stuff deleteStuff() {
		if (this.contentAmount == 1)
			return null;

		Stuff buf;
		this.contentAmount--;
		if (this.content[this.contentAmount] instanceof LaserBeam) {
			buf = this.content[this.contentAmount - 1];
			this.content[this.contentAmount - 1] = this.content[this.contentAmount];
			this.content[this.contentAmount] = null;
		} else {
			buf = this.content[this.contentAmount];
			this.content[this.contentAmount] = null;
			
			if(this.content[this.contentAmount - 2] instanceof Button){
				Button buttbuf = (Button) this.content[this.contentAmount - 2];
				buttbuf.touch();
				
			}
		}
		return buf;
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

	// выдаем роботу объект;
	// из-под лаз. луча его можно взять
	public Stuff takeObject() {
		if (this.contentAmount == 1)
			return null;

		if (this.content[this.contentAmount] instanceof LaserBeam) {
			if (!this.content[this.contentAmount - 2].getIfTakeable())
				return null;
		} else {
			if (!this.content[this.contentAmount - 1].getIfTakeable())
				return null;
		}
		return this.deleteStuff();
	}

	public boolean ifCanPassThrough() {
		for (int i = 0; i < this.contentAmount; i++) {
			if (!this.content[i].getIfPassable())
				return false;
		}
		return true;
	}

	// считаем, что если есть элемент, "экранирующий" урон, остальные не
	// действуют
	public int getDamage() {
		int buf = 0;
		for (int i = this.contentAmount - 1; i >= 0; i--) {
			if (this.content[i].getDamage() == 0)
				break;

			buf = buf + this.content[i].getDamage();
		}
		
		return buf;
	}

}
