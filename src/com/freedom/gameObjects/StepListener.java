package com.freedom.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.view.GameScreen;

/**
 * Это класс который прослеживает находится ли робот в какой-то области и
 * исполняет robotIsOn robotIsNotOn robotGone robotCome назвения которых кагбэ
 * намекают не то что происходит в файле данный объект прописывается так: <obj
 * class="com.freedom.gameObjects.StepListener" x="3" y="3"> <cels x="4" y="4"/>
 * <cels x="4" y="5"/> </obj> чтобы сделать что то своё на основe этого класса
 * нужно создать подкласс дописать в неё функцию loadToFile, а также
 * заоверрайдить все методы вроде robotCome, который нужны для нового класса.
 * 
 * @author ushale
 * 
 */
public class StepListener extends Stuff {
	protected int[][] controlledCellsList;
	protected int controlledCellsAmount;
	private static Image texture1;
	private boolean alive;
	private boolean robotOn;
	private Checker p;
	private Logger gleblo = Logger.getLogger("StepListener");

	static {
		try {
			texture1 = ImageIO.read(new File(
					"Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public StepListener()
	{

		super(false, true, false, false, 0, 0);
		gleblo.setLevel(Level.OFF);
		texture = texture1;
		alive = true;
		robotOn = false;
		gleblo.info("creating");

	}

	/**
	 * просто так не юзать
	 */

	public void itsAlive() {
		this.p = new Checker();
		GameField.getInstance().getThreads().execute(this.p);
		gleblo.info("Aliving");
	}

	/**
	 * Робот это он
	 */
	public void robotIsOn() {
		gleblo.info("The robot is on");
	}

	/**
	 * Робот это не он
	 */
	public void robotIsNotOn() {
		gleblo.info("The robot is not on");
	}

	/**
	 * Робот ушёл
	 */
	public void robotGone() {
		gleblo.info("The robot had gone");
	}

	/**
	 * Робот пришёл
	 */
	public void robotCome() {
		gleblo.info("The robot had come");
	}

	/**
	 * Используется для того чтобы остановить этот объект
	 */
	public void stopListening() {
		this.alive = false;
	}

	/**
	 * этот метод нужно заоверрайдить при наследовании
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.StepListener");
		gleblo.info("Saving");
	}

	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		NodeList list = obj.getElementsByTagName("cels");
		int length = list.getLength();
		this.controlledCellsList = new int[length][2];
		for (int i = 0; i < length; i++) {
			Element buf = (Element) list.item(i);
			controlledCellsList[i][0] = Integer.parseInt(buf.getAttribute("x"));
			controlledCellsList[i][1] = Integer.parseInt(buf.getAttribute("y"));
		}
		this.controlledCellsAmount = length;
		itsAlive();
	}

	/**
	 * Кастыльный метод
	 * 
	 * @return Возвращает кастыль
	 */
	public boolean obj() {
		return false;
	}

	/**
	 * Кастыльный метод
	 * 
	 * @return Возвращает кастыль
	 */
	public boolean objc() {
		return true;
	}

	/**
	 * просто так не юзать
	 */
	public int getUseAmount() {
		return controlledCellsAmount;
	}

	/**
	 * просто так не юзать
	 */
	public int[][] getUseList() {
		return controlledCellsList;
	}

	private class Checker implements Runnable {
		public void run() {
			boolean ok;
			while (alive) {
				try {
					Thread.sleep(30);
				} catch (InterruptedException e1) {
					alive = false;
				}

				//System.out.println(alive);

				if (GameField.getInstance().active) {
					int x;
					int y;
					try {
						x = GameField.getInstance().getRobot().getX();
						y = GameField.getInstance().getRobot().getY();
					} catch (Exception e) {
						x = -1;
						y = -1;
						gleblo.info("Erro	r occured");
					}
					ok = false;
					for (int i = 0; i < controlledCellsAmount; i++) {
						if ((controlledCellsList[i][0] == x)
								&& (controlledCellsList[i][1] == y)) {
							if (robotOn) {
								robotIsOn();

							} else {
								robotOn = true;
								robotCome();
							}
							ok = true;
						}

					}
					if (!ok) {
						if (robotOn) {
							robotGone();
							robotOn = false;
						} else {
							robotIsNotOn();
						}
					}

				}
			}
		}
	}
}