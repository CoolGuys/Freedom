package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
	public GraphicsController()
	{
		super();
		this.setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1000, 600));
		
		setStartScreen(new StartScreen(this));
		setGameField(new GameField(this));
		this.add(getStartScreen());
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

	private StartScreen startScreen;
	private GameField gameField;

}
