package com.freedom.gameObjects;

public class Tile extends Stuff{
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Tile(int x,int y){
		super(x,y,false,true);
	}
}