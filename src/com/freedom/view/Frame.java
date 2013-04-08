package com.freedom.view;

import java.awt.Dimension;
import java.awt.Toolkit; 
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		super("Freedom");
		setUndecorated(true);
		getContentPane().setLayout(null);
		try {
			setIconImage(ImageIO.read(new File("Resource/Textures/RobotN.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
