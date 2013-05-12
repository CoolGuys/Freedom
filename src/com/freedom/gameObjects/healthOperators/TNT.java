package com.freedom.gameObjects.healthOperators;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Moveable;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.view.GameScreen;

public class TNT extends Stuff implements Moveable {

	private int expDamage = 10;
	private static Image texture1;

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/TNT.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String direction;

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
		obj.setAttribute("class","com.freedom.gameObjects.healthOperators.TNT");
	}

	/*
	 * здесь мы его взрываем. считаем, что в начальной клетке создаем волну, она
	 * передает ее соседям, уменьшая дамаг на 1.
	 * "таймер" - здесь. взрыв инициируется, когда робот кладет динамит на
	 * пол
	 */

	/*
	 * убрать поле ifexped, заменить на ручками сделанный буфер
	 */
	private void activationProcess() {
		Cell[][] buf = GameField.getInstance().getCells();
		Queue<Cell> que = new LinkedList<Cell>();
		Cell toWork;
		
		boolean[][] ifExped = new boolean [2*this.expDamage+1][2*this.expDamage+1];
		for (int i = 0; i < 2*this.expDamage+1; i++) {
			for (int j = 0; j < 2*this.expDamage+1; j++) {
				ifExped[i][j] = false;
			}
		}
		
		
		

		buf[this.getX()][this.getY()].expBuf = this.expDamage;
		buf[this.getX()][this.getY()].deleteStuff(this);
		que.add(buf[this.getX()][this.getY()]);

		// распределяем урон
		int x = this.getX() - this.expDamage;
		int y = this.getY() - this.expDamage;
		

		while (!que.isEmpty()) {

			toWork = que.remove();
			if (toWork.expBuf < 1)
				continue;
			
			toWork.expBuf = toWork.expBuf - toWork.dealDamageToContent(toWork.expBuf); 
			ifExped[toWork.getX()][toWork.getY()] = true;

			if (!ifExped[toWork.getX() - x + 1][toWork.getY() - y]) {
				if (buf[toWork.getX() + 1][toWork.getY() ].getTop().isExpConductive()) {
					buf[toWork.getX() + 1][toWork.getY()].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()  + 1][toWork.getY()]);
				}
			}

			if (!ifExped[toWork.getX() -x- 1][toWork.getY()-y]) {
				if (buf[toWork.getX()- 1][toWork.getY()].getTop().isExpConductive()) {
					buf[toWork.getX()- 1][toWork.getY()].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX() - 1][toWork.getY()]);
				}
			}

			if (!ifExped[toWork.getX()-x][toWork.getY() -y + 1]) {
				if (buf[toWork.getX()][toWork.getY() + 1].getTop().isExpConductive()) {
					buf[toWork.getX()][toWork.getY() + 1].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()][toWork.getY() + 1]);
				}
			}

			if (!ifExped[toWork.getX()-x][toWork.getY() -y- 1]) {
				if (buf[toWork.getX()][toWork.getY() - 1].getTop().isExpConductive()) {
					buf[toWork.getX()][toWork.getY() - 1].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()][toWork.getY() - 1]);
				}
			}

			toWork.expBuf = 0;
			GameScreen.getInstance().repaint();
		}
		
		if ((GameField.getInstance().getRobot().getX() == this.getX())&&(GameField.getInstance().getRobot().getY() == this.getY())){
			GameField.getInstance().getRobot().container[0] = null;
		}

	}


	public void activate() {
		TNTExploder t = new TNTExploder();
		GameField.otherThreads.execute(t);
	}

	private class TNTExploder implements Runnable {
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TNT.this.activationProcess();

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
	public int punch(int damage){
		this.activationProcess();
		return this.maxLives;
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
		if(container[0]==null)
			return;
		container[0].x=Math.round(container[0].x);
		container[0].y=Math.round(container[0].y);
	}

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub
		
	}
}
