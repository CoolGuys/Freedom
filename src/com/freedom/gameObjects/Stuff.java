package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;

import org.w3c.dom.Element;

public abstract class Stuff {

	double x;
	double y;
	Image texture;
	private boolean pickable;
	boolean passable;
	private static int size = GameField.getInstance().getCellSize();

	private int damage; // number of lives you loose
	private boolean ifDestroyable;
	int lives;
	private boolean ifAbsorb;
	private boolean ifReflect;
	
	// конструктор для совсем убогих объектов, которые
	// безвредны и которые не уничтожишь.
	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable)
	{
		this.pickable = pickable;
		this.passable = passable;
		this.ifReflect = reflectable;
		this.ifAbsorb = absorbable;
		this.damage = 0;
		this.ifDestroyable = false;
		this.lives = 1;
	}

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
	}

	public boolean obj() {
		return true;
	}

	// кастыли
	public boolean objc() {
		return false;
	}
	public void itsAlive(){
		
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
		obj.setAttribute("class", "com.freedom.gameObjects.Stuff");
	}

	public Stuff()
	{
		this.pickable = true;
		this.passable = false;
		this.damage = 0;
	}

	// if lives==0 , we cannot destroy this stuff
	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable, int damage, int lives)
	{
		this.pickable = pickable;
		this.passable = passable;
		this.damage = damage;
		this.ifReflect = reflectable;
		this.ifAbsorb = absorbable;

		if (lives == 0) {
			this.lives = 1;
			this.ifDestroyable = false;
		} else {
			this.lives = lives;
			this.ifDestroyable = true;
		}
	}

	boolean useOn() {
		return false;
	}

	boolean useOff() {
		return false;
	}

	void touch() {
		return;
	}

	void robotOn() {
		return;
	}

	void robotOff() {
		return;
	}

	boolean teleportate() {
		return false;
	}

	boolean getIfAbsorb() {
		return this.ifAbsorb;
	}

	public int getX() {
		return ((int) this.x);
	}

	public int getY() {
		return ((int) this.y);
	}
	
	public static int getSize() {
		return size;
	}

	void raiseDamage(int extraDamage) {
		this.damage = this.damage + extraDamage;
	}

	public boolean getIfTakeable() {
		return this.pickable;
	}

	public boolean getIfPassable() {
		return this.passable;
	}

	public int getDamage() {
		return this.damage;
	}

	public Image getTexture() {
		return this.texture;
	}

	public int getLives() {
		return this.lives;
	}

	public boolean ifCanDestroy() {
		return this.ifDestroyable;
	}

	boolean getIfReflect() {
		return this.ifReflect;
	}

	public void draw(Graphics g) {
		g.drawImage(texture, (int) (x * getSize()), (int) (y * getSize()),
				getSize(), getSize(), null);
	}

	public int getUseAmount() {
		return -1;
	}
	
	public void giveInfo() {
		return;
	}

	 public void removeInfo() {
		 
	 }
	
	public int[][] getUseList() {
		// TODO Автоматически созданная заглушка метода
		return null;
	}

}
