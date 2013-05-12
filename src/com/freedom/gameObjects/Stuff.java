package com.freedom.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import org.w3c.dom.Element;

import com.freedom.view.GameScreen;

public class Stuff {

	double x;
	double y;
	Image texture;
	private boolean pickable;
	boolean passable;
	private static int size = GameField.getInstance().getCellSize();
	public Stuff[] container = new Stuff[1];
	int maxLives;

	protected int damage; // number of lives you loose
	private boolean ifDestroyable;
	private int lives;
	private boolean ifAbsorb;
	private boolean ifReflect;

	boolean expConductive;
	private DamageSender damager;
	private int toHarm; // буферное поле для передачи урона

	// конструктор для совсем убогих объектов, которые
	// безвредны и которые не уничтожишь.
	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable) {
		/*if ((!reflectable) && (!absorbable)) {
			this.ifAbsorb = true;
			this.ifReflect = false;
			System.out.println("bad reflection\\absorbation choice");
		} else {*/
			this.ifReflect = reflectable;
			this.ifAbsorb = absorbable;
			

		this.pickable = pickable;
		this.passable = passable;

		this.damage = 0;
		this.ifDestroyable = false;
		this.lives = 10;
		this.expConductive = true;
		damager = new DamageSender();
	}

	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
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

	public void itsAlive() {

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
	}

	public Stuff() {
		this.pickable = true;
		this.passable = false;
		this.damage = 0;
	}

	// if lives<=0 , we cannot destroy this stuff
	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable, int damage, int lives) {
		this.pickable = pickable;
		this.passable = passable;

		if (damage < 0)
			this.damage = 0;
		else
			this.damage = damage;

		this.ifReflect = reflectable;
		this.ifAbsorb = absorbable;
		this.expConductive = true;
		damager = new DamageSender();
		this.maxLives = this.lives;

		if (lives < 1) {
			this.lives = 1;
			this.ifDestroyable = false;
		} else {
			this.lives = lives;
			this.ifDestroyable = true;
		}
	}

	// Action methods

	boolean useOn() {
		return false;
	}

	boolean useOff() {
		return false;
	}

	void touch(Stuff toucher) {
		return;
	}

	void untouch(Stuff untouvher) {

	}

	public void activate() {
		return;
	}

	// //getters

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

	void reduceDamage(int toReduce) {
		this.damage = this.damage - toReduce;
		if (this.damage < 1)
			damage=0;
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

	// methods for damage

	/*
	 * здесь: вызывать метод harm нужно самостоятельно, stopHarming включается
	 * сам в Cell.deleteStuff() (должно быть прописано в Movement Animator)
	 */

	// "непрерывный" урон
	boolean harm(int damage) {
		if (damage == 0) {
			return false;
		}
		if (!this.ifDestroyable)
			return false;

		this.toHarm = damage;
		GameField.getInstance().getDeathTicker().addActionListener(damager);
		return true;
	}

	void stopHarming() {
		GameField.getInstance().getDeathTicker().removeActionListener(damager);
	}

	void die() {
		GameField.getInstance().cells[this.getX()][this.getY()]
				.deleteStuff(this);
	}

	// разовый урон
	int punch(int damage) {
		if (damage < 1) {
			return 0;
		}
		if (!this.ifDestroyable)
			return 0;

		Graphics2D g2 = (Graphics2D) GameScreen.getInstance().getGraphics();
		Rectangle2D r = new Rectangle((int) this.x * getSize(), (int) this.y
				* getSize(), getSize(), getSize());
		g2.setColor(Color.WHITE);
		g2.fill(r);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (this.lives < damage) {
			this.die();
			return this.lives;
		}

		this.lives = this.lives - damage;
		System.out.println(Stuff.this.lives + " Punched: "
				+ this.getClass().toString());
		return damage;
	}

	void heal(int lives) {
		this.lives = this.lives + lives;
		if (this.lives > this.maxLives)
			this.lives = this.maxLives;
	}

	// дописать обратботку смерти
	private class DamageSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Stuff.this.lives = Stuff.this.lives - Stuff.this.toHarm;
			System.out.println(Stuff.this.lives);
			if (Stuff.this.lives < 1) {
				Stuff.this.die();
			}
		}
	}

	public boolean isExpConductive() {
		return this.expConductive;
	}

}
