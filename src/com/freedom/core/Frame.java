package com.freedom.core;

import javax.swing.*;

/**
 * Класс Frame расширяет класс JFrame. Разница между ними лишь размере, см. документацию javax.swing.JFrame
 * 
 * @author gleb
 * 
 */

@SuppressWarnings("serial")
class Frame extends JFrame {
	
	public Frame(int aWidth, int aHeight)
	{
		setUndecorated(true);
		getContentPane().setLayout(null);
		this.setSize(aWidth, aHeight);
		ScreensHolder.setDimensions(aWidth, aHeight);
		ScreensHolder.getInstance().createScreens();
		this.add(ScreensHolder.getInstance());
	}
}
