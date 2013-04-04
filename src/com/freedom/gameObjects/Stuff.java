package com.freedom.gameObjects;

public class Stuff {
	
	protected int x; //вообще говоря, они не нужны. 
	protected int y; // если они нигде не упростят реализации, избавиться!
	private boolean pickable; //"поднимаем" ли объект?
	private boolean passable; //можем ли пройти сквозь объект?
	
	public Stuff(int posX, int posY, boolean pickable, boolean passable){
		this.x = posX;
		this.y = posY;
		this.pickable = pickable;
		this.passable = passable;
	}
	
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}
	
	public boolean getIfCanTake(){
		return this.pickable;
	}
	
	public boolean ifCanPass(){
		return this.passable;
	}
	
}
