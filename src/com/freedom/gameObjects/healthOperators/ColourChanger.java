package com.freedom.gameObjects.healthOperators;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class ColourChanger extends Stuff{
	
	public ColourChanger(){
		super(true, false, false,false, 0, 10);
	}
	
	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.healthOperators.ColourChanger");
	}
	
	@Override
	//TODO переделать, если будем убивать "в глубину"
	public void interact(Stuff interactor){
		interactor.setColour(this.getColour());
		if(GameField.getInstance().getRobot().container[0] == this)
			GameField.getInstance().getRobot().container[0] = null;
		else this.die();
	}
	
}
