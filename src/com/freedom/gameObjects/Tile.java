package com.freedom.gameObjects;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * хочу уметь добавлять объекты типа Stuff в массив Tiles
 * @author ush
 *
 */

public class Tile extends Stuff{
	
	public Tile(int x,int y){
		super(x,y,false,false);
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
