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
 * Это класс который прослеживает находится ли робот в какой-то области
 * и исполняет
 * robotIsOn
 * robotIsNotOn
 * @author ushale
 *
 */
public class StepListener extends Stuff {
	private int[][] controlledCellsList;// массив с координатами селлов на
	// которые действует
	// батон
	private int controlledCellsAmount; // количество целлов на которые действует
	// батон
	private static Image texture1;
	private boolean alive;
	private boolean robotOn;
	private Checker p;
	Logger gleblo = Logger.getLogger("StepListener");
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/EmptyTexture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public StepListener()
	{	
		
		super(false, false, false , false);
		gleblo.setLevel(Level.OFF);
		texture=texture1;
		lives=10000;
		alive=true;
		robotOn=false;
		gleblo.info("creating");
	
	}
	
	public void itsAlive(){
		this.p = new Checker();
		GameField.getInstance().getThreads().execute(this.p);
		gleblo.info("Aliving");
	}
	
	public void robotIsOn(){
		gleblo.info("The robot is on");
	}
	
	public void robotIsNotOn(){
		gleblo.info("The robot is not on");
	}
	
	public void robotGone(){
		gleblo.info("The robot had gone");
	}
	
	public void robotCome(){
		gleblo.info("The robot had come");
	}
	
	
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

	// кастыли
	public boolean objc() {
		return true;
	}
	
	public int getUseAmount() {
		return controlledCellsAmount;
	}

	public int[][] getUseList() {
		return controlledCellsList;
	}
	private class Checker implements Runnable{
		public Checker(){
			
		}
		public void run(){
			boolean ok;
			while(alive){				
				try {
					int x;
					int y;
					try {
						x = GameField.getInstance().getRobot().getX();
						y = GameField.getInstance().getRobot().getY();
					} catch (Exception e) {
						x = -1;
						y = -1;
						gleblo.info("Error occured");
					}
					Thread.sleep(30);
					ok=false;
					for(int i=0;i<controlledCellsAmount;i++){
						if((controlledCellsList[i][0]==x)&&(controlledCellsList[i][1]==y)){
							if(robotOn){
								robotIsOn();
								
							}else{
								robotOn=true;
								robotCome();
							}
							ok=true;
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
				} catch (InterruptedException e) {
					// TODO Автоматически созданный блок catch
					alive = false;
				}
			}
		}
	}
}