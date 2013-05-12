package com.freedom.gameObjects.controllers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.utilities.game.SoundEngine;
import com.freedom.view.GameScreen;

public class ButtonAnd extends Stuff {

	private static Image texturePressed;
	private static Image textureDepressed;
	private int[][] controlledCellsList;// массив с координатами селлов на
	private static File f2; // которые действует
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
			f2 = new File("Resource/Sound/ButtonClicked.wav");
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

	public ButtonAnd()
	{
		super(false, true, true, false);
		super.x = x;
		super.y = y;
		controlledCellsList = new int[10][2];
		texture = textureDepressed;

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
		for (int i = 0; i < length; i++) {
			Element buf = (Element) list.item(i);
			controlledCellsList[i][0] = Integer.parseInt(buf.getAttribute("x"));
			controlledCellsList[i][1] = Integer.parseInt(buf.getAttribute("y"));
			GameField.getInstance().cells[controlledCellsList[i][0]][controlledCellsList[i][1]].buttonsNumber++;
		}
		this.controlledCellsAmount = length;
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.controllers.ButtonAnd");
		// obj.setAttribute("Press", String.valueOf(this.ifPressed));
	}

	@Override
	public void untouch(Stuff element) {
		texture = textureDepressed;
		for (int i = 0; i < controlledCellsAmount; i++) {
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]].counter--;
		}
		GameField.getInstance().getTicker().removeActionListener(sender);
		for (int i = 0; i < controlledCellsAmount; i++)
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
					.useOff();
	}

	@Override
	public void touch(Stuff element) {
		SoundEngine.playClip(f2, -1, -15);
		texture = texturePressed;
		sender = new SignalOnSender();
		for (int i = 0; i < controlledCellsAmount; i++)
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]].counter++;

		GameField.getInstance().getTicker().addActionListener(sender);

	}

	@Override
	public void giveInfo() {
		GameField.getInstance().getCells()[(int) x][(int) y].highlight();

		for (int i = 0; i < controlledCellsAmount; i++) {
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
					.highlight();
		}
	}

	@Override
	public void removeInfo() {
		GameField.getInstance().getCells()[(int) x][(int) y].unhighlight();

		for (int i = 0; i < controlledCellsAmount; i++) {
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
					.unhighlight();
		}
	}

	private class SignalOnSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < controlledCellsAmount; i++) {
				// & action
				if (!GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
						.ifCanBePressed())
					continue;
				//

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

}
