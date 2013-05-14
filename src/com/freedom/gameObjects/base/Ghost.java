package com.freedom.gameObjects.base;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff.LoadingType;

public class Ghost {
	
	protected LoadingType type = LoadingType.DNW;

	public void itsAlive() {

	}
	
	public void readLvlFile(Element obj) {
		
	}
	
	public void loadToFile(Element obj) {
		
	}
	
	public void die(){
		
	}
	
	public boolean obj() {
		return true;
	}

	public boolean objc() {
		return false;
	}
	
	public LoadingType getLoadingType() {
		return this.type;
	}
	
	public int getUseAmount(){
		return 0;
	}
	
	public int[][] getUseList(){
		return null;
	}
}
