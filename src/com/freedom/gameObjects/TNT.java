package com.freedom.gameObjects;

import java.awt.Image;
import java.util.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class TNT extends Stuff implements Moveable {

	public static final int expDamage = 10;
	private static Image texture1;

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TNT() {
		super(true, false, false, false, 0, 1);
		texture = texture1;
	}

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
	}

	/*
	 * здесь мы его взрываем. считаем, что в начальной клетке создаем волну, она
	 * передает ее соседям, уменьшая дамаг на 1.
	 * "таймер" - здесь. взрыв инициируется, когда робот кладет динамит на
	 * пол
	 */

	private void activationProcess() {
		Cell[][] buf = GameField.getInstance().getCells();
		Queue<Cell> que = new LinkedList<Cell>();
		Cell toWork;

		if (buf[this.getX()][this.getY()].deleteStuff(this))
			buf[this.getX()][this.getY()].add(this);
		else {
			this.x = GameField.getInstance().getRobot().getX();
			this.y = GameField.getInstance().getRobot().getY();
			GameField.getInstance().getRobot().setContainer(null);
		}

		buf[this.getX()][this.getY()].expBuf = this.expDamage;
		que.add(buf[this.getX()][this.getY()]);

		// распределяем урон

		while (!que.isEmpty()) {

			toWork = que.remove();
			if (toWork.expBuf < 1)
				continue;
			toWork.tryToDestroy(toWork.expBuf);
			toWork.ifExped = true;

			if (!buf[toWork.getX() + 1][toWork.getY()].ifExped) {
				if (buf[toWork.getX() + 1][toWork.getY()].getTop().expConductive) {
					buf[toWork.getX() + 1][toWork.getY()].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX() + 1][toWork.getY()]);
				}
			}

			if (!buf[toWork.getX() - 1][toWork.getY()].ifExped) {
				if (buf[toWork.getX() - 1][toWork.getY()].getTop().expConductive) {
					buf[toWork.getX() - 1][toWork.getY()].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX() - 1][toWork.getY()]);
				}
			}

			if (!buf[toWork.getX()][toWork.getY() + 1].ifExped) {
				if (buf[toWork.getX()][toWork.getY() + 1].getTop().expConductive) {
					buf[toWork.getX()][toWork.getY() + 1].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()][toWork.getY() + 1]);
				}
			}

			if (!buf[toWork.getX()][toWork.getY() - 1].ifExped) {
				if (buf[toWork.getX()][toWork.getY() - 1].getTop().expConductive) {
					buf[toWork.getX()][toWork.getY() - 1].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()][toWork.getY() - 1]);
				}
			}

			toWork.expBuf = 0;
		}

		buf[this.getX()][this.getY()].deleteStuff(this);
		for (int i = 0; i < this.expDamage; i++) {
			for (int j = 0; j < this.expDamage; j++) {
				buf[this.getX() + i][this.getY() + j].ifExped = false;
				buf[this.getX() - i][this.getY() + j].ifExped = false;
				buf[this.getX() + i][this.getY() - j].ifExped = false;
				buf[this.getX() - i][this.getY() - j].ifExped = false;
			}
		}

	}

	public void activate() {
		MyThread t = new MyThread();
		t.start();
	}

	private class MyThread extends Thread {
		public void run() {
			try {
				this.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TNT.this.activationProcess();

		}
	}

	public void move(String direction) {
		double step = this.expDamage
				* GameField.getInstance().getRobot().getStep();
		if (direction.equals("N"))
			y -= step;
		else if (direction.equals("S"))
			y += step;
		else if (direction.equals("E"))
			x += step;
		else
			x -= step;
	}

	@Override
	public boolean checkIfBeingMoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void tellIfBeingMoved(boolean isMoved) {
		// TODO Auto-generated method stub

	}

	@Override
	public void recalibrate() {
		// TODO Auto-generated method stub

	}

	@Override
	public double getStep() {
		// TODO Auto-generated method stub
		return 0;
	}
}
