package com.freedom.gameObjects;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile extends Stuff{
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Tile(int x,int y){
		super(x,y,false,false);
	}
}