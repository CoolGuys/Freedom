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
import com.freedom.view.ScreensHolder;

public class Stuff {

	public enum StuffColor {
		RED, GREEN, BLUE
	}

	public enum LoadingType {
		OBJ, OBJC, DNW; // simple object, object with cells, do not write;
	}

	protected LoadingType type = LoadingType.OBJ;
	public double x;
	public double y;
	protected Image textureRed;
	protected Image textureGreen;
	protected Image textureBlue;
	protected static int size = GameField.getInstance().getCellSize();
	public Stuff[] container = new Stuff[1];
	protected int maxLives;
	private int basicMaxLives;

	private StuffColor color;

	protected boolean pickable;
	protected boolean passable;

	// punching
	protected int damage;
	protected boolean ifDestroyable;
	protected int lives;

	// for laser
	private boolean ifAbsorb;
	protected boolean ifReflect;

	// for TNT
	private boolean expConductive;

	// for harming
	private DamageSender damager;
	private int toHarm; // буферное поле для передачи урона
	private Logger logger = Logger.getLogger("Stuff");
	public volatile boolean isMoving;

	public Stuff() {
		this.pickable = true;
		this.passable = false;
		this.damage = 0;
	}

	// if lives<=0 , we cannot destroy this stuff
	public Stuff(boolean pickable, boolean passable, boolean reflects,
			boolean absorbs, int damage, int lives) {
		this.pickable = pickable;
		this.passable = passable;

		if (damage < 0)
			this.damage = 0;
		else
			this.damage = damage;

		this.ifReflect = reflects;
		this.ifAbsorb = absorbs;
		this.setExpConductive(true);
		damager = new DamageSender();
		

		if (lives < 1) {
			this.lives = 1;
			this.ifDestroyable = false;
		} else {
			this.lives = lives;
			this.ifDestroyable = true;
		}
		this.basicMaxLives = this.lives;
	}

	public Stuff(boolean pickable, boolean passable, boolean reflectable,
			boolean absorbable) {
		this.ifReflect = reflectable;
		this.ifAbsorb = absorbable;

		this.pickable = pickable;
		this.passable = passable;

		this.damage = 0;
		this.ifDestroyable = false;
		this.lives = 10;
		this.setExpConductive(true);
		damager = new DamageSender();
		this.basicMaxLives = this.lives;
	}

	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// TODO пока в случае отсутствия цвета делает красным
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		setColour(obj.getAttribute("color"));
		this.lives = this.maxLives;
	}

	public boolean ifCoolEnough(Stuff element) {
		return GameField.ifPowerfulEnough(element, this);
	}

	public LoadingType getLoadingType() {
		return this.type;
	}

	public void itsAlive() {
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("color", String.valueOf(this.color));
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

	public void untouch(Stuff untoucher) {
		return;
	}

	public void interact(Stuff interactor) {
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
		return this.textureRed;
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

	public StuffColor getColor() {
		return this.color;
	}

	public void draw(Graphics g) {

		switch (getColor()) {
		case RED: {
			g.drawImage(textureRed, (int) (x * getSize()),
					(int) (y * getSize()), null);
			return;
		}
		case GREEN: {
			g.drawImage(textureGreen, (int) (x * getSize()),
					(int) (y * getSize()), null);
			return;
		}
		case BLUE:
			g.drawImage(textureBlue, (int) (x * getSize()),
					(int) (y * getSize()), null);

		}

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

	// TODO Remove this method
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
		if (!isMoving)
			isMoving=true;
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
		ScreensHolder.getInstance().repaint();
		return damage;
	}

	public void heal(int lives) {
		this.lives = this.lives + lives;
		if (this.lives > this.maxLives)
			this.lives = this.maxLives;
	}

	// TODO Finish death handling
	private class DamageSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Stuff.this.lives = Stuff.this.lives - Stuff.this.toHarm;
			System.out.println(Stuff.this.lives);
			if (Stuff.this.lives < 1) {
				Stuff.this.die();
			}
		}
	}

	// фишки с рангом предметов

	public String getColour() {
		if (this.color == null)
			System.gc();
		switch (color) {
		case RED:
			return "Red";
		case GREEN:
			return "Green";
		case BLUE:
			return "Blue";
		}
		return "Red";
	}

	public void setColour(String color) {

		if (color.equalsIgnoreCase("Red") || color.equalsIgnoreCase(""))
			this.color = StuffColor.RED;
		if (color.equalsIgnoreCase("Green"))
			this.color = StuffColor.GREEN;
		if (color.equalsIgnoreCase("Blue"))
			this.color = StuffColor.BLUE;
		
		GameField.getInstance();
		this.maxLives = this.basicMaxLives * GameField.power.get(this.getColour());
	}
	

}
