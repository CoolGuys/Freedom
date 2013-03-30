package com.freedom.core;

import javax.swing.*;

/**
 * Класс Frame является наследником класса JFrame. Различие между ними
 * заключается лишь в конструкторе: JFrame после сборки имеет довольно
 * бесполезный размер 0х0, размер же нашего фрейма задается разработчиком.
 * Конструктором объекта класса Frame создается объект класса Field (этот класс - наследник класса
 * JPanel от Swing)
 * 
 * Таким образом имеем следующую простую структуру:
 * 
 * *Frame***************х
 * *#ScreensHolder######*
 * *#  Все псевдоокна  #*
 * *####################*
 * **********************
 * 
 * @author gleb
 * 
 */

@SuppressWarnings("serial")
class Frame extends JFrame 
{

	public Frame(int aWidth, int aHeight)
	{
		setUndecorated(true);
		getContentPane().setLayout(null);
		this.height = aHeight;
		this.width = aWidth;
		this.setSize(width, height);
		
		ScreensHolder.setDimensions(aWidth, aHeight);
		ScreensHolder.getInstance().createScreens();
		this.add(ScreensHolder.getInstance());
		
	} 
	private int height;
	private int width;
}

