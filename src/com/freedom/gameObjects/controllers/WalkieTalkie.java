package com.freedom.gameObjects.controllers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.freedom.gameObjects.base.StepListener;
import com.freedom.view.GameScreen;

public class WalkieTalkie extends StepListener {

	@Override
	public void readLvlFile(Element obj) {		
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

	@Override
	public void loadToFile(Element obj) {
		obj.setAttribute("class", "com.freedom.gameObjects.controllers.WalkieTalkie");
		obj.setAttribute("messageToDisplay", this.message);
	}
	
	@Override
	public void robotIsOn() {
		//aif(this.controlledCellsList[0][0]==4){
		//	System.out.println(this.controlledCellsList[0][0]+"on");
		//}
	}
	
	@Override
	public void robotIsNotOn() {
		//if(this.controlledCellsList[0][0]==4){
			//System.out.println(this.controlledCellsList[0][0]+"ne on");
		//}
	}
	@Override
	public void robotCome() {
		GameScreen.getInstance().displayMessage(this.message);
		System.out.println(this.message);
	}

	@Override
	public void robotGone() {
		GameScreen.getInstance().removeMessage();
	}

	String message;
}
