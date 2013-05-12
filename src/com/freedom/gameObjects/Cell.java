package com.freedom.gameObjects;




import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;



public class Cell {

	public volatile boolean locked;

	private int x;
	private int y;
	private int damage;

	private Stuff[] content;
	private Stuff metaLevel;
	private int contentAmount;
	public int buttonsNumber;
	public int counter;
	Logger logger = Logger.getLogger("Cell");

	private static Image highlighted;
	private boolean isHighlighted;

	public int expBuf; // буфер для взрыва - не трогать!

	public boolean isExamined;
	static {

		try {
			highlighted = ImageIO.read(new File(
					"Resource/Textures/Highlighter.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Cell(int a, int b) {
		this.x = a;
		this.y = b;
		this.contentAmount = 0;
		this.content = new Stuff[10];
		this.damage = 0;
		this.counter = 0;
		this.buttonsNumber = 0;
		this.expBuf = 0;
	}

	public synchronized void utilityAdd(Stuff toAdd) {
		// System.out.println("Utility add on "+
		// toAdd.toString()+" "+contentAmount+" "+x+" "+y);
		this.content[this.contentAmount] = toAdd;
		this.contentAmount++;

	}

	public boolean getIfReflect() {
		return this.content[this.contentAmount - 1].getIfReflect();
	}

	public boolean getIfAbsorb() {
		return this.content[this.contentAmount - 1].getIfAbsorb();
	}

	public synchronized Stuff utilityRemove(Stuff toRemove) {
		int i;
		for (i = 0; i < this.contentAmount; i++) {
			if (this.content[i].equals(toRemove))
				break;
			if (i == (this.contentAmount))
				return null;
		}

		// System.out.println("Utility remove on "+
		// toRemove.toString()+" "+x+" "+y);

		for (int j = i; j < this.contentAmount - 1; j++) {
			this.content[j] = this.content[j + 1];
		}
		this.contentAmount--;
		this.content[this.contentAmount] = null;
		return toRemove;
	}

	public synchronized boolean add(Stuff element) {
		// System.out.println("Add on "+ element.toString()+ ": " +
		// contentAmount+" "+x+" "+y);

		if (this.contentAmount == 10)
			return false;

		for (int i = 0; i < this.contentAmount; i++) { // с этим местом
		// аккуратнее при работе
			if (!this.content[i].getIfPassable())
				return false;
		}

		// теперь положить явно можем. кладем и изменяем состояние некот.
		// объектов
		this.damage = this.damage + element.getDamage();
		this.content[this.contentAmount] = element;
		this.contentAmount++;
		element.x = this.x;
		element.y = this.y;
		this.locked = false;
		this.touch(element);
		return true;
	}

	/*
	 * теперь уникален в удалении только лазерный луч
	 * 
	 * 
	 * @ivan
	 */

	public synchronized Stuff deleteStuff() {

		if (this.contentAmount == 0)
			return null;

		this.untouch(this.content[this.contentAmount - 1]);
		Stuff buf;
		this.contentAmount--;
		if (this.content[this.contentAmount] instanceof LaserBeam) {
			buf = this.content[this.contentAmount - 1];
			this.content[this.contentAmount - 1] = this.content[this.contentAmount];
			this.content[this.contentAmount] = null;
		} else {
			buf = this.content[this.contentAmount];
			this.content[this.contentAmount] = null;
		}
		this.damage = this.damage - buf.getDamage();
		buf.stopHarming();
		// System.out.println("Remove on "+ buf.toString()+ ": " +
		// contentAmount+" "+x+" "+y);

		return buf;
	}

	public synchronized boolean deleteStuff(Stuff element) {

		// System.out.println("Remove on "+ element.toString()+ ": " +
		// contentAmount+" "+x+" "+y);
		// добавить лог
		if (element == null)
			return false;

		if (this.contentAmount == 0)
			return false;
		this.untouch(element);
		int i;
		for (i = 0; i < this.contentAmount; i++) {
			if (this.content[i].equals(element))
				break;
		}

		if (i == (this.contentAmount))
			return false;

		this.damage = this.damage - element.getDamage();

		for (int j = i; j < this.contentAmount - 1; j++) {
			this.content[j] = this.content[j + 1];
		}
		this.contentAmount--;
		this.content[this.contentAmount] = null;

		return true;
	}

	public synchronized boolean replace(Stuff toReplace, Stuff replaceWith) {
		if (this.contentAmount == 0)
			return false;

		if (replaceWith.equals(null))
			this.deleteStuff(toReplace);

		int i;
		for (i = 0; i < this.contentAmount; i++) {
			if (this.content[i].equals(toReplace))
				break;
			if (i == (this.contentAmount - 1))
				return false;
		}
		this.content[i] = replaceWith;
		return true;

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

	public Stuff[] getContent() { // валидно ли без размера? валидно.
		return this.content;
	}

	public Stuff getTop() {
		return this.content[this.contentAmount - 1];
	}

	// /конец блока

	// Everything for robot:

	public void touch(Stuff toucher) {
		for (int i = 0; i < this.contentAmount - 1; i++) {
			this.content[i].touch(toucher);
		}
	}

	public void untouch(Stuff untoucher) {
		for (int i = 0; i < this.contentAmount - 1; i++) {
			this.content[i].untouch(untoucher);
		}
	}

	// выдаем роботу объект;
	// из-под лаз. луча его можно взять
	public Stuff takeObject() {

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

	public boolean useOn() {
		for (int i = 1; i < this.contentAmount; i++) {
			if (this.content[i].useOn()) {

				return true;

			}
		}
		return false;
	}

	public boolean useOff() {
		for (int i = 1; i < this.contentAmount; i++) {
			if (this.content[i].useOff()) {
				return true;
			}

		}
		return false;
	}

	// здесь наносим урон предметам
	public int dealDamageToContent(int damage) {
		if (this.contentAmount == 0)
			return 0;

		int buf = damage;
		for (int i = Cell.this.contentAmount - 1; i >= 0; i--) {
			buf = buf - Cell.this.content[i].punch(buf);
		}
		return (damage - buf);
	}

	void harmContent(int damage) {
		if (this.contentAmount == 0)
			return;

		int buf = damage;
		for (int i = Cell.this.contentAmount - 1; i >= 0; i--) {
			Cell.this.content[i].harm(buf);
		}
	}

	void stopHarmingContent() {
		if (this.contentAmount == 0)
			return;

		for (int i = Cell.this.contentAmount - 1; i >= 0; i--) {
			Cell.this.content[i].stopHarming();
		}
	}

	public void healContent(int heal) {

		for (int i = 0; i < Cell.this.contentAmount; i++) {
			Cell.this.content[i].heal(heal);
		}

	}

	public void activate() {
		for (int i = 0; i < this.contentAmount; i++) {
			this.content[i].activate();
		}
	}

	// ////////////////

	public boolean getIfPassable() {
		for (int i = 0; i < this.contentAmount; i++) {
			if (!this.content[i].passable)
				return false;
		}

		return true;
	}

	public boolean getIfConductsExp() {
		for (int i = 0; i < this.contentAmount; i++) {
			if (!this.content[i].isExpConductive())
				return false;
		}

		return true;
	}

	public boolean ifCanBePressed() {
		if (this.counter == this.buttonsNumber)
			return true;
		else
			return false;
	}

	public void draw(Graphics g) {
		if (isHighlighted)
			g.drawImage(highlighted, (int) (content[0].x * Stuff.getSize()),
					(int) (content[0].y * Stuff.getSize()), Stuff.getSize(),
					Stuff.getSize(), null);
		else
			return;

	}

	public void highlight() {
		this.isHighlighted = true;
	}

	public void unhighlight() {
		this.isHighlighted = false;
	}

	public void setMeta(Stuff toToggle) {
		this.metaLevel = toToggle;
	}

	public void clearMeta() {
		this.metaLevel = null;
	}

	public Stuff getMeta() {
		return this.metaLevel;
	}

}