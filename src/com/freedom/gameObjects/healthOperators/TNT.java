package com.freedom.gameObjects.healthOperators;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import org.w3c.dom.Element;
import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.view.GameScreen;

public class TNT extends Stuff {

	private int expDamage = 15;
	private static Image texture1;

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/TNT.png")).getScaledInstance(getSize(), getSize(),
					BufferedImage.SCALE_SMOOTH);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*@SuppressWarnings("unused")
	private String direction;*/

	public TNT() {
		super(true, false, 0, 1);
		textureRed = texture1;
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class","com.freedom.gameObjects.healthOperators.TNT");
	}

	/*
	 * здесь мы его взрываем. считаем, что в начальной клетке создаем волну, она
	 * передает ее соседям, уменьшая дамаг на 1.
	 * "таймер" - здесь. взрыв инициируется interactом
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
		buf[this.getX()][this.getY()].delete(this);
		que.add(buf[this.getX()][this.getY()]);

		// распределяем урон
		int x = this.getX() - this.expDamage;
		int y = this.getY() - this.expDamage;
		

		while (!que.isEmpty()) {

			toWork = que.remove();
			if (toWork.expBuf < 1)
				continue;
			
			toWork.expBuf = toWork.expBuf - toWork.punchContent(toWork.expBuf); 
			ifExped[toWork.getX()][toWork.getY()] = true;

			if (!ifExped[toWork.getX() - x + 1][toWork.getY() - y]) {
				if (buf[toWork.getX() + 1][toWork.getY() ].getTop().expConductive()) {
					buf[toWork.getX() + 1][toWork.getY()].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()  + 1][toWork.getY()]);
				}
			}

			if (!ifExped[toWork.getX() -x- 1][toWork.getY()-y]) {
				if (buf[toWork.getX()- 1][toWork.getY()].getTop().expConductive()) {
					buf[toWork.getX()- 1][toWork.getY()].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX() - 1][toWork.getY()]);
				}
			}

			if (!ifExped[toWork.getX()-x][toWork.getY() -y + 1]) {
				if (buf[toWork.getX()][toWork.getY() + 1].getTop().expConductive()) {
					buf[toWork.getX()][toWork.getY() + 1].expBuf = toWork.expBuf - 1;
					que.add(buf[toWork.getX()][toWork.getY() + 1]);
				}
			}

			if (!ifExped[toWork.getX()-x][toWork.getY() -y- 1]) {
				if (buf[toWork.getX()][toWork.getY() - 1].getTop().expConductive()) {
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

	@Override
	public void interact(Stuff interactor) {
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

	
	@Override
	public int punch(int damage){
		this.activationProcess();
		return this.maxLives;
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
	public boolean reflects(Stuff element) {
		return false;
	}

}
