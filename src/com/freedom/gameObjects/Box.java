package com.freedom.gameObjects;


public class Box extends Stuff{
	private String colour; // договоренность: имеем R,G,B
	
	public Box(int a, int b, String colour){
		super(a,b,true,false);
		this.colour = colour;
		
	}
	
	/*
	 * К удалению, таких багов не будет, ситуация как и в роботе с направлением
	 * @gleb
	 */
	/*private boolean ifColourValide(char color){
		if (color == 'R') return true;
		if (color == 'G') return true;
		if (color == 'B') return true;
		return false;
	}*/
	
	public String getColour(){
		return this.colour;
	}
}
