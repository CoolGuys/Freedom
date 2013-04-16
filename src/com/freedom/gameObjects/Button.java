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

public class Button extends Stuff {

	private boolean ifPressed;
	private Image texturePressed;
	private Image textureDepressed;
	private int[][] useList;// массив с координатами селлов на которые действует
							// батон
	private int useAmount; // количество целлов на которые действует батон

	private ActionListener sender;

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

	public Button() {
		super(false, true);
		super.x = x;
		super.y = y;
		useList = new int[10][2];
		try {
			texturePressed  = ImageIO.read(new File("Resource/Textures/ButtonPressed.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
			textureDepressed = ImageIO.read(new File("Resource/Textures/ButtonDepressed.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		}
		this.useAmount = length;
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.Button");
		// obj.setAttribute("Press", String.valueOf(this.ifPressed));
	}

	protected void touch() {

		this.ifPressed = !this.ifPressed;
		if (this.ifPressed) {
			texture = texturePressed;
			sender = new SignalOnSender();
			GameField.getInstance().getTicker().addActionListener(sender);
		} else {
			texture = textureDepressed;
			GameField.getInstance().getTicker().removeActionListener(sender);
			for (int i = 0; i < useAmount; i++) {
				GameField.getInstance().getCells()[useList[i][0]][useList[i][1]]
						.useOff();	
			}
			GameScreen.getInstance().repaint();
		}

	}

	private class SignalOnSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < useAmount; i++) {
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
			//System.out.print("L\n");
		}
	}
	
	
	//костылики
	protected void robotOn(){
		if(!this.ifPressed)
			this.touch();
		return;
	}
	protected void robotOff(){
		if(this.ifPressed)
			this.touch();
		return;
	}

}
