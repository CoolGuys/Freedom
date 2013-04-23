package com.freedom.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * признак конца - this.next.next = this.next;
 */

public class LaserBeam extends Stuff {

	double xStart;
	double yStart;
	LaserBeam next;

	public LaserBeam(double x, double y) {
		super(false, false,true, false);
		xStart = x;
		yStart = y;
		next = null;
	}

	/*
	 * Здесь x,y - координаты "конца" 1ого куска; должны лежать на границе
	 * целла!!!
	 */
	void buildBeam(double x, double y) {
		this.next = new LaserBeam(x, y);
		double k;
		double m;
		double yBuf;
		double xBuf;

		this.next.xStart = x;
		this.next.yStart = y;

		if (x == this.x) {

			GameField.getInstance().cells[(int) Math.floor(this.xStart)][(int) Math
					.floor(this.yStart)].add(this);

			if (GameField.getInstance().cells[(int) Math.floor(this.xStart)][(int) Math
					.floor(this.yStart)].getTop().getIfReflect()) {
				if (y > this.yStart)
					this.next.buildBeam(x, y - 1);
				else
					this.next.buildBeam(x, y + 1);
			}
			if (GameField.getInstance().cells[(int) Math.floor(x)][(int) Math
					.floor(y)].getTop().getIfAbsorb()) {
				this.next.next = this.next;
				return;
			}

			if (y > this.yStart)
				this.next.buildBeam(x, y + 1);
			else
				this.next.buildBeam(x, y - 1);

		}

		if (y == this.y) {
			GameField.getInstance().cells[(int) Math.floor(this.xStart)][(int) Math
					.floor(this.yStart)].add(this);

			if (GameField.getInstance().cells[(int) Math.floor(this.xStart)][(int) Math
					.floor(this.yStart)].getTop().getIfReflect()) {
				if (x > this.xStart)
					this.next.buildBeam(x - 1, y);
				else
					this.next.buildBeam(x + 1, y);
			}
			if (GameField.getInstance().cells[(int) Math.floor(x)][(int) Math
					.floor(y)].getTop().getIfAbsorb()) {
				this.next.next = this.next;
				return;
			}

			if (x > this.xStart)
				this.next.buildBeam(x + 1, y);
			else
				this.next.buildBeam(x - 1, y);

		}

		k = (y - this.yStart) / (x - this.xStart);

		if (GameField.getInstance().cells[(int) Math.floor(this.xStart)][(int) Math
				.floor(this.yStart)].getTop().getIfAbsorb()) {
			this.next = this.next.next;
			return;
		}

		if (k>0) {
			if (x == Math.ceil(x)) {
				if ((y - Math.floor(y)) >= (1 - k)) { // след. точка - на у
					yBuf = Math.ceil(y);
					xBuf = x + (yBuf - y) / k;
				}else{
					yBuf = y + k;
					xBuf = x + 1;
				}
			}else{
				if ((x - Math.floor(y)) >= (1 - k)) { // след. точка - на у
					yBuf = Math.ceil(y);
					xBuf = x + (yBuf - y) / k;
				}else{
					yBuf = y + k;
					xBuf = x + 1;
				}
				
			}
			
		}

	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
		g2.drawLine((int)(this.x*getSize()), (int)(this.y*getSize()), (int)(this.next.x*getSize()), (int)(this.next.y)*getSize());
	}
	

}
