package core;

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
 * *#GraphicsController#*
 * *#  Все псевдоокна  #*
 * *####################*
 * **********************
 * 
 * @author gleb
 * 
 */

@SuppressWarnings("serial")
class Frame extends JFrame // это тестовый комент
{

	public Frame(int aWidth, int aHeight)
	{
		/* Это конструктор фрейма. Что в нем происходит,
		 * по поряку следования:
		 * 
		 * Получение высоты
		 * Получение ширины
		 * Задание размера полученными значениями
		 * Задание имени (оно будет отображено в панельке над окном, на которой еще крестик для закрытия)
		 * 
		 */
		
		
		getContentPane().setLayout(null);
		this.height = aHeight;
		this.width = aWidth;
		this.setSize(width, height);
		this.setTitle("FreedomGame");

		// Как раз тут и создается объект класса Field, а потом добавляется,
		// собственно, на фрейм this, то бишь, на конструирующийся
		GraphicsController GUI = new GraphicsController(width, height);
		this.add(GUI);
		
	}
	//Понятно, что значат эти поля
	private int height;
	private int width;
}

