package com.freedom.gameObjects;
import java.awt.Graphics;
import java.awt.Image;

import org.w3c.dom.Element;


public class Stuff implements IStuff{
	
	protected double x; 
	protected double y; 
	protected Image texture;
	private boolean pickable; 
	private boolean passable;
	private int size = GameField.getInstance().getCellSize();
	
	private int damage; //number of lives you loose
	private boolean ifDestroyable;
	protected int lives;
	
	public void readLvlFile(Element obj) {
		this.x=Integer.parseInt(obj.getAttribute("x"));
		this.y=Integer.parseInt(obj.getAttribute("y"));
//			this.x=sc.nextInt();
//			this.y=sc.nextInt();
	}
	/**
	 * Метод, который добавляет инфу в файл
	 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
	 * @author UshAle
	 */
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int)this.x));
		obj.setAttribute("y", String.valueOf((int)this.y));
		obj.setAttribute("class","com.freedom.gameObjects.Stuff");
	} 

	public Stuff(){
		this.pickable = true;
		this.passable = false;
		this.damage = 0;
	}
	
	
	//if lives==0 , we cannot destroy this stuff
	public Stuff(boolean pickable, boolean passable, int damage, int lives){ 
		this.pickable = pickable;
		this.passable = passable;
		this.damage = damage;
		
		if(lives==0){
			this.lives = 1;
			this.ifDestroyable = false;
		}
		else {
			this.lives = lives;
			this.ifDestroyable = true;
		}
	}
	
	protected  void buttonPressed(){
		return;
	}
	
	
	// конструктор для совсем убогих объектов, которые
	// безвредны и которые не уничтожишь.
	public Stuff(boolean pickable, boolean passable){  
		this.pickable = pickable;
		this.passable = passable;
		this.damage = 0;
		this.ifDestroyable = false;
		this.lives = 1;
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

	public Image getTexture() {
		return this.texture;
	}
	
	public int getLives(){
		return this.lives;
	}
	
	public boolean ifCanDestroy(){
		return this.ifDestroyable;
	}

	public void draw(Graphics g) {
		g.drawImage(texture, (int) (x * getSize()), (int) (y * getSize()),
				getSize(), getSize(), null);
	}
	
}
