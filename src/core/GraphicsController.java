package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.*;

/**
 * GraphicsController - контролирующий интерфейс класс. Здесь будет
 * осуществляться рисование всех возможных частей графического интерфея
 * программы 
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
public class GraphicsController extends JPanel {
	public GraphicsController(int dimensionX, int dimensionY)
	{
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setSize(new Dimension(dimensionX, dimensionY));
		
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		
		setStartScreen(new StartScreen(this));
		setGameField(new GameField(this));
		this.add(getStartScreen());
		startScreen.setBounds(0, 0, dimensionX, dimensionY);
	}


	public StartScreen getStartScreen() {
		return startScreen;
	}

	public void setStartScreen(StartScreen startScreen) {
		this.startScreen = startScreen;
	}

	public GameField getGameField() {
		return gameField;
	}

	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}
	
	public void swapDisplays(JLayeredPane toAdd, JLayeredPane toRemove) {
		remove(toRemove);
		add(toAdd);
		toAdd.requestFocusInWindow();
		toAdd.setBounds(0, 0, dimensionX, dimensionY);
		revalidate();
		repaint();
	}

	private StartScreen startScreen;
	private GameField gameField;
	
	private int dimensionX;
	private int dimensionY;
	

}
