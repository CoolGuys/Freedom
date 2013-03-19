package core;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Класс Field содержит все неуправляемые игровые объекты на уровне и
 * осуществляет операции с ними под контролем объекта класса
 * GraphicsHolderAndController Поэтому имеено сюда должен быть добавлен процесс
 * создания уровня, то есть метод, считывающий из файла уровень, удаляющий его
 * из памяти при прохождении, и еще что-нибудь. Сам знаешь, кто, тебе надо будет
 * над этим поработать *****Ушаков, отредактируй это описание после того, как
 * добавишь***
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
class GameField
{	
	//Иван, подумайте над конструктором, объекты ведь на вас, и напишите норм комментарий
	//@gleb
	public GameField() 
	{
		
		
	}
	

	public void loadLevel(String pathToPackage, int levelID)
	{
		
	}
	
	public void unloadLevel()
	{
		
	}
	
	public int getXsize(){
		return(xSize);
	}
	
	public int getYsize(){
		return(ySize);
	}
	
	
	public Tile [][] getTiles(){
		return this.tiles;
	}

	// Метод будет производить рисование всего, что лежит в массиве Tile (у
	// всего будет вызываться метод drawSelf)
	public void draw(Graphics g)
	{
		
	}
	
	private Tile [][] tiles;
	private int xSize; 	//размеры поля
	private int ySize;
}

