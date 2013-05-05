package com.freedom.gameObjects;

import java.awt.Image;

import java.util.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.view.GameScreen;

public class TNT extends Stuff implements Moveable {

	public static final int expDamage = 5;
	private static Image texture1;

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/TNT.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String direction;

	public TNT()
	{
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
		obj.setAttribute("class", "com.freedom.gameObjects.TNT");
	}

	/*
	 * здесь мы его взрываем. считаем, что в начальной клетке создаем волну, она
	 * передает ее соседям, уменьшая дамаг на 1. "таймер" - здесь. взрыв
	 * инициируется, когда робот кладет динамит на пол
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

		buf[this.getX()][this.getY()].expBuf = expDamage;
		que.add(buf[this.getX()][this.getY()]);
		

		this.punch(1);
		buf[this.getX()][this.getY()].deleteStuff(this);
		// распределяем урон

		while (!que.isEmpty()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toWork = que.remove();
			if (toWork.expBuf < 1)
				continue;
			toWork.dealDamageToContent(toWork.expBuf);
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
			GameScreen.getInstance().repaint();

			toWork.expBuf = 0;
		}

		for (int i = 0; i < expDamage; i++) {
			for (int j = 0; j < expDamage; j++) {
				if (buf[this.getX() + i][this.getY() + j] != null)
					buf[this.getX() + i][this.getY() + j].ifExped = false;
				if (buf[this.getX() - i][this.getY() + j] != null)
					buf[this.getX() - i][this.getY() + j].ifExped = false;
				if (buf[this.getX() + i][this.getY() - j] != null)
					buf[this.getX() + i][this.getY() - j].ifExped = false;
				if (buf[this.getX() - i][this.getY() - j] != null)
					buf[this.getX() - i][this.getY() - j].ifExped = false;
			}
		}

	}

	public void activate() {
		TNTExploder exploder = new TNTExploder();
		GameField.otherThreads.execute(exploder);
	}

	
	private class TNTExploder implements Runnable {
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			activationProcess();

		}
	}

	/*
	 * Поправить этот метод!!!!!!
	 */
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
	public double getStep() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canGo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getTargetCellCoordinates(String direction) {
		// TODO Auto-generated method stub
		return null;
	}

	public void recalibrate() {
		x = Math.round(x);
		y = Math.round(y);
		if (container[0] == null)
			return;
		container[0].x = Math.round(container[0].x);
		container[0].y = Math.round(container[0].y);
	}

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub

	}
}
