package com.freedom.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * признак конца - this.next.next = this.next;
 * Найдите кто-нибудь метод для рисования прямой, пожалуйста
 */

public class LaserBeam extends Stuff {

	double xStart;
	double yStart;
	LaserBeam next;

	public LaserBeam(double x, double y) {
		super(false, true,false, false, 1, 0);
		super.x = x;
		super.y = y;
		next = null;
	}

	/*
	 * Метод построения луча. Вероятность мелкого косяка огромна.
	 * Здесь x,y - координаты "конца" 1ого куска; должны лежать на границе
	 * целла!!!
	 */
	void buildBeam(double xNext, double yNext) {
		this.next = new LaserBeam(x, y);
		double yBuf;
		double xBuf;

		this.next.x = xNext;
		this.next.y = yNext;

		if (x == this.x) {

			// получаем коорд. "нашего" целла
			if (yNext > this.y) {
				yBuf = Math.floor(this.y);
			} else {
				yBuf = Math.floor(yNext);
			}

			// проверка на поглощение
			if (GameField.getInstance().cells[(int) xNext][(int) yBuf].getTop()
					.getIfAbsorb()) {
				this.next.next = this.next;
				return;
			}
			GameField.getInstance().cells[(int) xNext][(int) yBuf].add(this);

			// ну и отражается или нет
			if (GameField.getInstance().cells[(int) xNext][(int) yBuf].getTop()
					.getIfReflect()) {
				if (yNext > this.y)
					this.next.buildBeam(this.x, yNext - 1);
				else
					this.next.buildBeam(this.x, yNext + 1);
			}

			if (yNext > this.y)
				this.next.buildBeam(x, y + 1);
			else
				this.next.buildBeam(x, y - 1);

		}

		if (y == this.y) {

			if (yNext > this.y) {
				xBuf = Math.floor(this.x);
			} else {
				xBuf = Math.floor(xNext);
			}

			if (GameField.getInstance().cells[(int) xBuf][(int) yNext].getTop()
					.getIfAbsorb()) {
				this.next.next = this.next;
				return;
			}
			GameField.getInstance().cells[(int) xBuf][(int) yNext].add(this);

			if (GameField.getInstance().cells[(int) xBuf][(int) yNext].getTop()
					.getIfReflect()) {
				if (xNext > this.x)
					this.next.buildBeam(xNext - 1, yNext);
				else
					this.next.buildBeam(xNext + 1, yNext);
			} else {
				if (xNext > this.x)
					this.next.buildBeam(xNext + 1, yNext);
				else
					this.next.buildBeam(xNext - 1, yNext);
			}
		}

		if ((xNext - this.x) == (yNext - this.y)) {

			if (yNext > this.y) {
				yBuf = Math.floor(this.y);
				xBuf = Math.floor(this.x);
			} else {
				yBuf = Math.floor(yNext);
				xBuf = Math.floor(xNext);
			}

			if (GameField.getInstance().cells[(int) xBuf][(int) yBuf].getTop()
					.getIfAbsorb()) {
				this.next.next = this.next;
				return;
			}
			GameField.getInstance().cells[(int) xBuf][(int) yBuf].add(this);

			if (GameField.getInstance().cells[(int) xBuf][(int) yBuf].getTop()
					.getIfReflect()) {
				if (yNext > this.y)
					this.next.buildBeam(xNext - 1, yNext - 1);
				else
					this.next.buildBeam(xNext + 1, yNext + 1);
			} else {

				if (yNext > this.y)
					this.next.buildBeam(xNext - 1, yNext - 1);
				else
					this.next.buildBeam(xNext + 1, yNext + 1);
			}

		}
		
		
		if ((this.x - xNext) == (yNext - this.y)) {

			if (yNext > this.y) {
				yBuf = Math.floor(this.y);
				xBuf = Math.floor(xNext);
			} else {
				yBuf = Math.floor(yNext);
				xBuf = Math.floor(this.x);
			}

			if (GameField.getInstance().cells[(int) xBuf][(int) yBuf].getTop()
					.getIfAbsorb()) {
				this.next.next = this.next;
				return;
			}
			
			GameField.getInstance().cells[(int) xBuf][(int) yBuf].add(this);

			if (GameField.getInstance().cells[(int) xBuf][(int) yBuf].getTop()
					.getIfReflect()) {
				if (yNext > this.y)
					this.next.buildBeam(xNext - 1, yNext + 1);
				else
					this.next.buildBeam(xNext - 1, yNext + 1);
			} else {

				if (yNext > this.y)
					this.next.buildBeam(xNext - 1, yNext + 1);
				else
					this.next.buildBeam(xNext + 1, yNext - 1);
			}

		}
		

	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
		g2.drawLine((int)(this.x*getSize()), (int)(this.y*getSize()), (int)(this.next.x*getSize()), (int)(this.next.y)*getSize());
	}
	
	
}
