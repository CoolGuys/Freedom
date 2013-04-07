package com.freedom.gameObjects;
import java.util.Scanner;


public class Stuff implements IStuff{
	
	protected int x; 
	protected int y; 
	private boolean pickable; 
	private boolean passable; 
	private int size = GameField.getcellSize();
	
	public void readLvlFile(Scanner sc) {
		this.x=sc.nextInt();
		this.y=sc.nextInt();
	}


	public Stuff(boolean pickable, boolean passable){

		this.pickable = pickable;
		this.passable = passable;
	}
	
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
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
	
}
