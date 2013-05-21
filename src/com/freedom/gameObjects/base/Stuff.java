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
import com.freedom.view.ScreensHolder;

public class Stuff {

	public enum StuffColor {
		RED, GREEN, BLUE
	}

	public enum LoadingType {
		OBJ, OBJC, DNW; // simple object, object with cells, do not write;
	}

	public LoadingType type = LoadingType.OBJ;
	public double x;
	public double y;
	protected Image textureRed;
	protected Image textureGreen;
	protected Image textureBlue;
	protected static int size = GameField.getInstance().getCellSize();
	public Stuff[] container = new Stuff[1];
	protected int maxLives;
	private int basicMaxLives;

	public StuffColor color;

	protected boolean pickable;
	protected boolean passable;
	protected boolean god;

	// punching
	protected int damage;
	protected boolean destroyable;
	protected int lives;


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
		this.color = StuffColor.RED;
	}

	// if lives<=0 , we cannot destroy this stuff
	public Stuff(boolean pickable, boolean passable, int damage, int lives) {
		this.pickable = pickable;
		this.passable = passable;
		this.color = StuffColor.RED;

		if (damage < 0)
			this.damage = 0;
		else
			this.damage = damage;

		this.setExpConductive(true);
		damager = new DamageSender();
		
		if (lives < 1) {
			this.lives = 1;
			this.destroyable = false;
		} else {
			this.lives = lives;
			this.destroyable = true;
		}
		this.basicMaxLives = this.lives;
	}


	public Stuff(boolean pickable, boolean passable) {

		this.color = StuffColor.RED;
		
		this.pickable = pickable;
		this.passable = passable;

		this.damage = 0;
		this.destroyable = false;
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
		String str = obj.getAttribute("lives");
		if (!str.equals("")) {
			this.lives = Integer.parseInt(str);
		} else {
			this.lives = this.maxLives;
		}
	}

	public void setLives(int liv){
		this.lives=liv;
	}
	
	public boolean isCoolEnough(Stuff element) {
		return GameField.isCoolEnough(element, this);
	}

	public LoadingType getLoadingType() {
		return this.type;
	}

	public void itsAlive() {
		return;
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("color", String.valueOf(this.color));
		obj.setAttribute("lives", String.valueOf(this.lives));
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

	public boolean absorbs(Stuff element) {
		if(element.getColour() != this.getColour())
			return true;
		else return false;
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

	public boolean takeable() {
		return this.pickable;
	}

	public boolean passable() {
		return this.passable;
	}

	public int getDamage() {
		return this.damage;
	}

	public int getLives() {
		return this.lives;
	}

	public boolean destroyable() {
		return this.destroyable;
	}

	public boolean reflects(Stuff element) {
		return !this.absorbs(element);
	}

	public boolean expConductive() {
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
		if (!this.destroyable)
			return false;

		this.toHarm = damage;
		GameField.getInstance().getDeathTicker().addActionListener(damager);
		return true;
	}

	void stopHarming() {
		GameField.getInstance().getDeathTicker().removeActionListener(damager);
	}

	public void die() {
		this.lives=0;
		if (!isMoving)
			isMoving=true;
			GameField.getInstance().cells[this.getX()][this.getY()]
					.delete(this);
	}

	// разовый урон
	public int punch(int damage) {
		if (damage < 1) {
			return 0;
		}
		if (!this.destroyable)
			return 0;

		Graphics2D g2 = (Graphics2D) ScreensHolder.getInstance().getCurrentScreen().getGraphics();
		Rectangle2D r = new Rectangle((int) this.x * getSize(), (int) this.y
				* getSize(), getSize(), getSize());
		g2.setColor(Color.WHITE);
		g2.fill(r);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			logger.warning("Interrupted while punched");
		}

		if (this.lives < damage) {
			int lastLives = this.lives;
			this.die();
			return lastLives;
		}

		this.lives = this.lives - damage;
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

	public void setControlled(Cell element) {
		return;
	}
	

}
