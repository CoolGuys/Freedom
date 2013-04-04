package com.freedom.gameObjects;


//ящик, имеет по большому счету лишь цвет
public class Box extends Stuff{
	private char colour; // договоренность: имеем R,G,B
	
	public Box(int a, int b, char colour){//введешь невалидный цвет - ящик обагрится кровью.
		super(a,b,true,false);
		
		if(this.ifColourValide(colour)) 
			this.colour = colour;
		
		else this.colour = 'R';
	}

	private boolean ifColourValide(char color){
		if (color == 'R') return true;
		if (color == 'G') return true;
		if (color == 'B') return true;
		return false;
	}
	
	public char getColour(){
		return this.colour;
	}
}
