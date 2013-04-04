package com.freedom.gameObjects;

public class Stuff {
	
	protected int x;
	protected int y;
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
