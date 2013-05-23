package com.freedom.gameObjects.controllers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.utilities.game.SoundEngine;
import com.freedom.view.ScreensHolder;

public class ButtonAnd extends Stuff {
	public ButtonAnd()
	{
		super(false, true);
		super.type=LoadingType.OBJC;
		super.x = x;
		super.y = y;
		controlledCellsList = new int[10][2];

		textureRed = texturesDepressed[1];
		textureGreen = texturesDepressed[2];
		textureBlue = texturesDepressed[3];

	}
	
	//конструктор ТОЛЬКо для детектора
	public ButtonAnd(boolean ifLaserDetector){
		super(false, false);
		super.type=LoadingType.OBJC;
		super.x = x;
		super.y = y;
		controlledCellsList = new int[10][2];

		textureRed = texturesDepressed[1];
		textureGreen = texturesDepressed[2];
		textureBlue = texturesDepressed[3];
	}
	@Override
	public void untouch(Stuff element) {
		textureRed = texturesDepressed[1];
		textureGreen = texturesDepressed[2];
		textureBlue = texturesDepressed[3];
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
		textureRed = texturesPressed[1];
		textureGreen = texturesPressed[2];
		textureBlue = texturesPressed[3];
		sender = new SignalOnSender();
		for (int i = 0; i < controlledCellsAmount; i++)
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]].counter++;

		GameField.getInstance().getTicker().addActionListener(sender);

	}
	

	public int getUseAmount() {
		return controlledCellsAmount;
	}

	public int[][] getUseList() {
		return controlledCellsList;
	}
	/**
	 * Метод, который считывает всю инфу из файла с лвлами
	 * 
	 * @param - Scanner файла
	 */
	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
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
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.controllers.ButtonAnd");
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

	public class SignalOnSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < controlledCellsAmount; i++) {
				// & action
				if (!GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
						.allConnectedButtonsOn())
					continue;
				//

				if (GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
						.useOn()) {
					ScreensHolder.getInstance().getCurrentScreen()
							.instance()
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
	private static Image[] texturesPressed = new Image[4];
	private static Image[] texturesDepressed = new Image[4];
	protected int[][] controlledCellsList;// массив с координатами селлов на
	private static File f2; // которые действует
	// батон
	protected int controlledCellsAmount; // количество целлов на которые действует
										// батон
	protected ActionListener sender;

	private static Logger logger = Logger.getLogger("ButtonAnd");
	static {
		try {
			for(int i=1; i<=3;i++) {
				texturesPressed[i] = ImageIO.read(
						new File("Resource/Textures/ButtonAND/Pressed"+i+".png"))
						.getScaledInstance(getSize(), getSize(),
								BufferedImage.SCALE_SMOOTH);
				texturesDepressed[i] = ImageIO.read(
						new File("Resource/Textures/ButtonAND/Depressed"+i+".png"))
						.getScaledInstance(getSize(), getSize(),
								BufferedImage.SCALE_SMOOTH);
				}
			f2 = new File("Resource/Sound/ButtonClicked.wav");
		} catch (IOException e) {
			logger.warning("Textures or sound corrupted");
		}
	}
	
	//вставляет новый элемент в контрольный список
	public void setControlled(Cell element) {
		this.controlledCellsList[controlledCellsAmount][0] = element.getX();
		this.controlledCellsList[controlledCellsAmount][1] = element.getY();
		element.buttonsNumber++;
		this.controlledCellsAmount++;
	}
	
	@Override
	public boolean absorbs(Stuff element){
		return false;
	}
	
	@Override
	public boolean reflects(Stuff element) {
		return false;
	}

}
