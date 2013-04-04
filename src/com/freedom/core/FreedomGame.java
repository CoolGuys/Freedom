package com.freedom.core;

import java.awt.*;
import java.io.FileNotFoundException;

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
				Frame frame = new Frame(1024, 532);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
