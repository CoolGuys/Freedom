package com.freedom.gameObjects;

import java.awt.Image;
/**
 * хочу уметь добавлять объекты типа Stuff в массив Tiles
 * @author ush
 *
 */

public class Tile extends Stuff{
	

	public Tile(int x,int y){
		super(x,y,false,true);
	}
	
	public Tile() {
		
	}
	
	
	private int x;
	private int y;
	private Image texture;

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	
}
