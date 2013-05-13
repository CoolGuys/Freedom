package com.freedom.gameObjects.base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import org.w3c.dom.Element;

import com.freedom.model.GameField;
import com.freedom.view.GameScreen;

public class Stuff {

	public double x;
	public double y;
	protected Image texture;
	protected static int size = GameField.getInstance().getCellSize();
	public Stuff[] container = new Stuff[1];
	protected int maxLives;
	
	private String colour;

	protected boolean pickable;
	protected boolean passable;
	
	//punching
	protected int damage;
	protected boolean ifDestroyable;
	protected int lives;
	
	//for laser
	private boolean ifAbsorb;
	protected boolean ifReflect;
	
	//for TNT
	private boolean expConductive;

	//for harming
	private DamageSender damager;
	private int toHarm; // буферное поле для передачи урона
	private Logger logger = Logger.getLogger("Stuff");

	public Stuff()
	{
		this.pickable = true;
		this.passable = false;
		this.damage = 0;
	}

	// if lives<=0 , we cannot destroy this stuff
	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable, int damage, int lives)
	{
		this.pickable = pickable;
		this.passable = passable;

		if (damage < 0)
			this.damage = 0;
		else
			this.damage = damage;

		this.ifReflect = reflectable;
		this.ifAbsorb = absorbable;
		this.setExpConductive(true);
		damager = new DamageSender();
		this.maxLives = this.lives;

		if (lives < 1) {
			this.lives = 1;
			this.ifDestroyable = false;
		} else {
			this.lives = lives;
			this.ifDestroyable = true;
		}
		//TODO убрать и заменить нормальным чтением из файла
				this.colour = "Red";////////////////Заглушка!
	}

	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable)
	{
		this.ifReflect = reflectable;
		this.ifAbsorb = absorbable;

		this.pickable = pickable;
		this.passable = passable;

		this.damage = 0;
		this.ifDestroyable = false;
		this.lives = 10;
		this.setExpConductive(true);
		damager = new DamageSender();
		//TODO убрать и заменить нормальным чтением из файла
		this.colour = "Red";////////////////Заглушка!
	}

	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}

//TODO пока в случае отсутствия цвета делает красным
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.colour = obj.getAttribute("color");
		
		if (this.colour.equals(""))
			this.colour = "Red";
	}
	
	

	public boolean obj() {
		return true;
	}

	public boolean objc() {
		return false;
	}

	public void itsAlive() {
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("color", String.valueOf(this.colour));
	}

	// Action methods

	public boolean useOn() {
		return false;
	}

	public boolean useOff() {
		return false;
	}

	public void touch(Stuff toucher) {
		return;
	}

	public void untouch(Stuff untouvher) {

	}

	public void activate() {
		return;
	}

	// //getters

	public boolean getIfAbsorb() {
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

	public void raiseDamage(int extraDamage) {
		this.damage = this.damage + extraDamage;
	}

	public void reduceDamage(int toReduce) {
		this.damage = this.damage - toReduce;
		if (this.damage < 1)
			damage = 0;
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

	public boolean getIfReflect() {
		return this.ifReflect;
	}

	public boolean isExpConductive() {
		return expConductive;
	}

	public void setExpConductive(boolean expConductive) {
		this.expConductive = expConductive;
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
		return null;
	}

	
	//TODO Remove this method
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

	public void die() {
		GameField.getInstance().cells[this.getX()][this.getY()]
				.deleteStuff(this);
	}

	// разовый урон
	public int punch(int damage) {
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
			logger.info("Interrupted while punched");
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

	public void heal(int lives) {
		this.lives = this.lives + lives;
		if (this.lives > this.maxLives)
			this.lives = this.maxLives;
	}

	//TODO Finish death handling
	private class DamageSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Stuff.this.lives = Stuff.this.lives - Stuff.this.toHarm;
			System.out.println(Stuff.this.lives);
			if (Stuff.this.lives < 1) {
				Stuff.this.die();
			}
		}
	}
	
	//фишки с рангом предметов
	
	public String getColour(){
		return this.colour;
	}
	public void setColour(String s){
		this.colour = s;

	}

}
