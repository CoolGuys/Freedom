package com.freedom.gameObjects;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.view.GameScreen;

public class WalkieTalkie extends StepListener {

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		this.message = obj.getAttribute("messageToDisplay");
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
		//System.out.println(controlledCellsList[0][0]+" sdsd");
	}

	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.WalkieTalkie");
		obj.setAttribute("messageToDisplay", this.message);
	}
	
	public void robotIsOn() {
		//aif(this.controlledCellsList[0][0]==4){
		//	System.out.println(this.controlledCellsList[0][0]+"on");
		//}
	}
	
	public void robotIsNotOn() {
		//if(this.controlledCellsList[0][0]==4){
			//System.out.println(this.controlledCellsList[0][0]+"ne on");
		//}
	}
	public void robotCome() {
		GameScreen.getInstance().displayMessage(this.message);
		System.out.println(this.message);
	}

	public void robotGone() {
		GameScreen.getInstance().removeMessage();
	}

	String message;
}
