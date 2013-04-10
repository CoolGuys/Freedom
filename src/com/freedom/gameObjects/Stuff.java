package com.freedom.gameObjects;
import java.awt.Graphics;
import java.util.Scanner;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import org.w3c.dom.Element;


public class Stuff implements IStuff{
	
	protected double x; 
	protected double y; 
	private boolean pickable; 
	private boolean passable;
	private int size = GameField.getcellSize();
	
	private int damage; //number of lives you loose
	
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
//			this.x=sc.nextInt();
//			this.y=sc.nextInt();
	}

	public Stuff(){
		this.pickable = true;
		this.passable = false;
		this.damage = 0;
	}
	

	public Stuff(boolean pickable, boolean passable, int damage){
		this.pickable = pickable;
		this.passable = passable;
		this.damage = damage;
	}
	
	public Stuff(boolean pickable, boolean passable){
		this.pickable = pickable;
		this.passable = passable;
		this.damage = 0;
	}
	
	
	
	public int getX(){ 
		return((int)this.x);
	}
	
	public int getY() {
		return((int)this.y);
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean getIfTakeable(){
		return this.pickable;
	}
	
	public boolean getIfPassable(){
		return this.passable;
	}
	
	public int getDamage(){
		return this.damage;
	}


	public void draw(Graphics g) {
		
	}
	
}
