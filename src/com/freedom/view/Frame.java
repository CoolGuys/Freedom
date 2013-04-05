package com.freedom.view;

import java.awt.Dimension;
import java.awt.Toolkit; 
import javax.swing.*;

/**
 * Класс Frame расширяет класс JFrame. Разница между ними лишь размере, см. документацию javax.swing.JFrame
 * 
 * @author gleb
 * 
 */

@SuppressWarnings("serial")
class Frame extends JFrame {
	
	public Frame()
	{
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		height = screenSize.height;
		width = screenSize.width;
		
		this.setSize(width, height);
		ScreensHolder.setDimensions(width, height);
		ScreensHolder.getInstance().createScreens();
		this.add(ScreensHolder.getInstance());
	}
	private int height, width;
	
}
