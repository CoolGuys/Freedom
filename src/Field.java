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
 * Класс Field в программе отвечает, в первую очередь, за оторажение объектов на
 * экране. Осуществляется это посредством пробега рисующего метода по массиву
 * объектов Stuff после каждого обновления программной внутренности.
 * Помимо этого сдесь установлены слушатели событий - нажатий на кнопки W,A,S,D, и 
 * их обработчик (он пока один, но потом его должно быть много)
 * Помимо этого сюда должен быть добавлен процесс создания уровня 
 * *****Ушаков, отредактируй это описание после того, как добавишь***
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
class Field extends JPanel
{

	
	public Field() //Ушаков, добавь заполнение массива клеток и размеров поля из файла 
	{
		super();
		this.setBackground(Color.BLACK);
		setPreferredSize(new Dimension(1000,600));
		player = new Player(10,10);

		// Здесь создаются объекты - обработчики событий с кодовыми именами
		// "вверх", "вниз" и тд (те можно было назвать их и "лес", "вода" и тд, и
		// потом доступаться по этим значениям имени, поэтому кодовые)
		MovementAction moveUp = new MovementAction("up");
		MovementAction moveDown = new MovementAction("down"); 
		MovementAction moveLeft = new MovementAction("left"); 
		MovementAction moveRight = new MovementAction("right");

		// Здесь происходит нетривиальщина, в результате внутри фрейма
		// появляются слушатели нажатий на нужные клавиши, правильным образом
		// ассоциированные с их обработчиками
		InputMap imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("W"), "move.up");
		imap.put(KeyStroke.getKeyStroke("A"), "move.right");
		imap.put(KeyStroke.getKeyStroke("D"), "move.left");
		imap.put(KeyStroke.getKeyStroke("S"), "move.down");
		ActionMap amap = getActionMap();
		amap.put("move.up", moveUp);
		amap.put("move.down", moveDown);
		amap.put("move.left", moveLeft);
		amap.put("move.right", moveRight);
		
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
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		player.draw(g);
	}
	
	private Tile [][] tiles;
	private int xSize; 	//размеры поля
	private int ySize;
	
	// здесь либо должен быть робот, либо робот должен
	// лежать в клетке. Во втором случае получаем лажу,
	// так как ему перестают быть нужны координаты. В
	// первом случае ему не нужно расширять класс Stuff.
	// WTF?!
	// Я подумал, что нужно переименовать Stuff в
	// Unmovable, и создать отдельный клас для робота и
	// ему подобных - Movable (в смысле, могут ли они
	// самопроизвольно сдвигаться). Тогда помимо tiles
	// будем создавать массив для Movable и управлять им
	// отдельно. Как-то так.
	private Player player;
	
	
	//Это вложенный класс слушателя событий, такая форма записи распространена для них
	//Профит в том, что он имеет доступ к плееру, который какбэ приватное поле другого класса.
	public class MovementAction extends AbstractAction
	{
		public MovementAction(String name)
		{
			putValue(Action.NAME, name);
		}

		public void actionPerformed(ActionEvent e)
		{
			String duty = (String) getValue(Action.NAME);
			player.move(duty);
			repaint();
		}
	}
	
}

