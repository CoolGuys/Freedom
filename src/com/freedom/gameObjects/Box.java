package com.freedom.gameObjects;

import java.util.Scanner;


public class Box extends Stuff {
	private String colour; // договоренность: имеем R,G,B

	public void ReadLvlFile(Scanner sc) {// Метод, который считывает всю инфу из
											// файла с лвлами
		this.x = sc.nextInt(); // В данном случае считывает x y и color
		this.y = sc.nextInt();
		this.colour = sc.next();
	}


	public Box() {
	
	}

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
>>>>>>> branch 'master' of https://github.com/CoolGuys/Freedom.git
		return false;
<<<<<<< HEAD
	}

	public char getColour() {
=======
	}*/
	
	public String getColour(){
		return this.colour;
	}
}
