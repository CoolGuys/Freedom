package com.freedom.gameObjects;
import java.util.Scanner;


public class Stuff implements IStuff{
	//ДОБАВЛЕНО	 поле "могу ли взять"
	
	protected int x;
	protected int y;
	private boolean pickable; //"поднимаем" ли объект?
	private boolean passable; //можем ли пройти сквозь объект?
	//private Image texture;
	
	public void ReadLvlFile(Scanner sc){//чтение инфы о себе из файла
		this.x=sc.nextInt();
		this.y=sc.nextInt();
	}
	public Stuff(){
		
	}

	public Stuff(int posX, int posY, boolean pickable, boolean passable){
		this.x = posX;
		this.y = posY;
		this.pickable = pickable;
		this.passable = passable;
	}
	
	//////////////
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
	/////////////////////
	
}
