package com.freedom.gameObjects;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.view.GameScreen;

public class ButtonAnd extends Stuff {

	private boolean ifPressed;
	private static Image texturePressed;
	private static Image textureDepressed;
	private int[][] useList;// массив с координатами селлов на которые действует
							// батон
	private int useAmount; // количество целлов на которые действует батон
	private ActionListener sender;
	
	static {
		try {
			texturePressed = ImageIO.read(new File(
					"Resource/Textures/ButtonPressed.png"));
			textureDepressed = ImageIO.read(new File(
					"Resource/Textures/ButtonDepressed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getUseAmount() {
		return useAmount;
	}

	public int[][] getUseList() {
		return useList;
	}

	public boolean obj() {
		return false;
	}

	// кастыли
	public boolean objc() {
		return true;
	}

	public ButtonAnd() {
		super(false, true,true, false);
		super.x = x;
		super.y = y;
		useList = new int[10][2];
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
			useList[i][0] = Integer.parseInt(buf.getAttribute("x"));
			useList[i][1] = Integer.parseInt(buf.getAttribute("y"));
			GameField.getInstance().cells[useList[i][0]][useList[i][1]].buttonsNumber++;
		}
		this.useAmount = length;
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.ButtonAnd");
		// obj.setAttribute("Press", String.valueOf(this.ifPressed));
	}

	 void touch() {

		this.ifPressed = !this.ifPressed;
		if (this.ifPressed) {
			texture = texturePressed;
			sender = new SignalOnSender();
			for (int i = 0; i < useAmount; i++) {
				GameField.getInstance().getCells()[useList[i][0]][useList[i][1]]
						.counter++;
			}
			
			GameField.getInstance().getTicker().addActionListener(sender);
		} else {
			texture = textureDepressed;
			for (int i = 0; i < useAmount; i++) {
				GameField.getInstance().getCells()[useList[i][0]][useList[i][1]]
						.counter--;
			}
			GameField.getInstance().getTicker().removeActionListener(sender);
			for (int i = 0; i < useAmount; i++) {
				GameField.getInstance().getCells()[useList[i][0]][useList[i][1]]
						.useOff();
			}
		}

	}

	private class SignalOnSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < useAmount; i++) {
				//& action
				if (!GameField.getInstance().getCells()[useList[i][0]][useList[i][1]].ifCanBePressed())
					continue;
				//
				
				if (GameField.getInstance().getCells()[useList[i][0]][useList[i][1]]
						.useOn()) {
					GameScreen
							.getInstance()
							.repaint(
									GameField.getInstance().getCells()[useList[i][0]][useList[i][1]].getX()
											* getSize(),
									GameField.getInstance().getCells()[useList[i][0]][useList[i][1]]
											.getY() * getSize(), getSize(),
									getSize());
				}
			}
		}
	}
	
	//костылики
	 void robotOn(){
		if(!this.ifPressed)
			this.touch();
		return;
	}
	 void robotOff(){
		if(this.ifPressed)
			this.touch();
		return;
	}

}
