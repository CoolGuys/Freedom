package com.freedom.gameObjects;

import java.util.Scanner;

//ящик, имеет по большому счету лишь цвет
public class Box extends Stuff {
	private char colour; // договоренность: имеем R,G,B

	public void ReadLvlFile(Scanner sc) {// Метод, который считывает всю инфу из
											// файла с лвлами
		this.x = sc.nextInt(); // В данном случае считывает x y и color
		this.y = sc.nextInt();
		this.colour = sc.next().charAt(0);
		System.out.println("x=" + this.x + "y=" + this.y + "c=" + this.colour);
	}

	public Box() {
		
	}

	public Box(int a, int b, char colour) {// введешь невалидный цвет - ящик
											// обагрится кровью.
		super(a, b, true);

		if (this.ifColourValide(colour))
			this.colour = colour;

		else
			this.colour = 'R';
	}

	private boolean ifColourValide(char color) {
		if (color == 'R')
			return true;
		if (color == 'G')
			return true;
		if (color == 'B')
			return true;
		return false;
	}

	public char getColour() {
		return this.colour;
	}
}
