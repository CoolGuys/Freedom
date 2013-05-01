package com.freedom.gameObjects;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
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

	private String direction;

	public TNT() {
		super(true, false, false, false, 0, 1);
		texture=texture1;
	}

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class","com.freedom.gameObjects.TNT");
	
	}

	/*
	 * здесь мы его взрываем. считаем, что в начальной клетке создаем волну, она
	 * передает ее соседям, уменьшая дамаг на 1. обработка события - в целле,
	 * "таймер" - там же(tryToDestroy) взрыв пока инициируется, когда робот
	 * кладет динамит на пол
	 */
	public void activate() {
		Cell[][] buf = GameField.getInstance().getCells();
		int[][] toGive = new int[2 * expDamage + 2][2 * expDamage + 2];
		toGive[expDamage][expDamage] = this.expDamage;
		int xSize = GameField.getInstance().getXsize();
		int ySize = GameField.getInstance().getYsize();

		// распределяем урон
		for (int r = 0; r < expDamage; r++) {
			for (int i = 0; i < expDamage; i++) {
				for (int j = 0; j < expDamage; j++) {

					if (((this.getX() + i + 1 - expDamage) < xSize)	& ((this.getY() + j - expDamage) < ySize)) {
						if (((this.getX() + i + 1 - expDamage) >0)	& ((this.getY() + j - expDamage) >0)) {
						if (toGive[i + expDamage][j + expDamage] != (1 + toGive[i + 1 + expDamage][j + expDamage])) {
							toGive[i + 1 + expDamage][j + expDamage] = toGive[1 + expDamage][j	+ expDamage] - 1;
							
							if(!buf[getX()+i+1 - expDamage][getY()+j - expDamage].getTop().ifCanDestroy())
								toGive[i + 1 + expDamage][j + expDamage] = 0;
							if(toGive[i + 1 + expDamage][j + expDamage] <0)
								toGive[i + 1 + expDamage][j + expDamage] = 0;
						}
						}
					}

				}
			}
		}

		for (int i = 1; i < 2 * expDamage; i++) {
			for (int j = 1; j < 2 * expDamage; j++) {
				buf[(int) this.x + i - expDamage][(int) this.y + j - expDamage]
						.tryToDestroy(toGive[i][j]);
			}
		}

	}

	public void setDirection(String direction) {
		this.direction=direction;
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
}
