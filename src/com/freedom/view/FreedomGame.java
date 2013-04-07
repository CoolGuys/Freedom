package com.freedom.view;

import java.awt.*;
import javax.swing.*;

/**
 * Класс FreedomGame производит начальный запуск программы.
 * 
 * @author gleb
 * 
 */
public class FreedomGame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				Frame frame = new Frame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
				GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice dev = env.getDefaultScreenDevice();
				if(dev.isFullScreenSupported())
				{
					dev.setFullScreenWindow(frame);
				}
						
				
			}
		});
	}
}
