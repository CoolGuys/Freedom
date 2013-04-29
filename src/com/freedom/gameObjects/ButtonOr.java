package com.freedom.gameObjects;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.view.GameScreen;

public class ButtonOr extends Stuff {

	private boolean ifPressed;
	private static Image texturePressed;
	private static Image textureDepressed;
	private int[][] controlledCellsList;// массив с координатами селлов на
										// которые действует
	// батон
	private int controlledCellsAmount; // количество целлов на которые действует
										// батон
	private ActionListener sender;

	static {
		try {
			texturePressed = ImageIO.read(
					new File("Resource/Textures/ButtonPressed.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureDepressed = ImageIO.read(
					new File("Resource/Textures/ButtonDepressed.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getUseAmount() {
		return controlledCellsAmount;
	}

	public int[][] getUseList() {
		return controlledCellsList;
	}

	public boolean obj() {
		return false;
	}

	// кастыли
	public boolean objc() {
		return true;
	}

	public ButtonOr()
	{
		super(false, true, true, false);
		super.x = x;
		super.y = y;

		controlledCellsList = new int[10][2];

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
		this.ifPressed = false;
		// this.ifPressed=Boolean.parseBoolean(obj.getAttribute("Press"));
		// System.out.println("KNOPKA");
		if (this.ifPressed) {
			texture = texturePressed;
		} else {
			texture = textureDepressed;
		}
		int length = list.getLength();
		for (int i = 0; i < length; i++) {
			Element buf = (Element) list.item(i);
			controlledCellsList[i][0] = Integer.parseInt(buf.getAttribute("x"));
			controlledCellsList[i][1] = Integer.parseInt(buf.getAttribute("y"));
		}
		this.controlledCellsAmount = length;
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.ButtonOr");
		// obj.setAttribute("Press", String.valueOf(this.ifPressed));
	}

	void touch() {

		this.ifPressed = !this.ifPressed;
		if (this.ifPressed) {
			texture = texturePressed;
			sender = new SignalOnSender();
			GameField.getInstance().getTicker().addActionListener(sender);
		} else {
			texture = textureDepressed;
			GameField.getInstance().getTicker().removeActionListener(sender);
			for (int i = 0; i < controlledCellsAmount; i++) {
				GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
						.useOff();
			}
		}

	}

	private class SignalOnSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < controlledCellsAmount; i++) {

				if (GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
						.useOn()) {
					GameScreen
							.getInstance()
							.repaint(
									GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]].getX()
											* getSize(),
									GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
											.getY() * getSize(), getSize(),
									getSize());
				}
			}
		}
	}

	public void giveInfo() {
		GameField.getInstance().getCells()[(int) x][(int) y].highlight();

		for (int i = 0; i < controlledCellsAmount; i++) {
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
					.highlight();
		}
	}

	public void removeInfo() {
		GameField.getInstance().getCells()[(int) x][(int) y].unhighlight();

		for (int i = 0; i < controlledCellsAmount; i++) {
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
					.unhighlight();
		}
	}
}

