package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Button extends Stuff{
	
	protected boolean ifPressed;
	
	ArrayList<Stuff> leadTo = new ArrayList<Stuff>();
	
	//при инициализации не нажата, что нормально
	//принимаются заявки на изменение входных данных)
	public Button(int x, int y, Stuff[] objects){
		super(false,true);
		super.x = x;
		super.y = y;
		
		for(int i = 0; i < objects.length; i++){
			this.leadTo.add(objects[i]);
		}
		
		try {
			texture = ImageIO.read(new File("Resource/Textures/Button.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
	}
	
	//конструктор для кнопки, которая будет ссылаться на 1 элем
	//, неудобно ведь толкать в массив
	public Button(int x, int y, Stuff thing){
		super(false,true);
		super.x = x;
		super.y = y;
		
			this.leadTo.add(thing);
		
		try {
			texture = ImageIO.read(new File("Resource/Textures/Button.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ifPressed = false;
	}
	
	protected void touch(){
		
		for(int i = 0; i<this.leadTo.size()-1; i++){
				Stuff buf = this.leadTo.get(i);
			}
		this.ifPressed = !this.ifPressed;
	}

}
