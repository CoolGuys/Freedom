import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * GraphicsHolderAndController - главный, контролирующий, все класс. Здесь будет осуществляться как
 * рисование всех возможных частей графического интерфея программы
 * (приветственное окно, сама игра, окно, всплывающее при нажатии Esc и тп), так
 * контроль за управляемыми объектами (например, робот или ИИ) при помощи слушателей событий
 * 
 * @author gleb
 * 
 */
@SuppressWarnings("serial")
public class GraphicsHolderAndController extends JPanel
{
	public GraphicsHolderAndController()
	{
		super();
		this.setBackground(Color.BLACK);
		setPreferredSize(new Dimension(1000,600));
		player = new Player(10,10);
		
		this.createInputMap();
		this.createMovementController();
		this.addMouseListener(new MouseHandler());
		
		startScreen = new StartScreen();
		whatIsDrawn = "StartScreen";
		gameField = new GameField();
		
	}
	
	//Метод, создающий таблицу ввода нашей JPanel
	public void createInputMap()
	{
		InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("W"), "move.up");
		imap.put(KeyStroke.getKeyStroke("A"), "move.right");
		imap.put(KeyStroke.getKeyStroke("D"), "move.left");
		imap.put(KeyStroke.getKeyStroke("S"), "move.down");	
	}
	
	// Метод, который сопоставляет соответствующие движению поля таблицы ввода
	// полям таблицы действий, которые будут вызывать методы движения робота
	private void createMovementController()
	{
		MovementAction moveUp = new MovementAction("up");
		MovementAction moveDown = new MovementAction("down"); 
		MovementAction moveLeft = new MovementAction("left"); 
		MovementAction moveRight = new MovementAction("right");
		ActionMap amap = this.getActionMap();
		amap.put("move.up", moveUp);
		amap.put("move.down", moveDown);
		amap.put("move.left", moveLeft);
		amap.put("move.right", moveRight);
	}
	
	private void createStuffOperationsController()
	{

	}
	

	@Override
	public void paintComponent(Graphics g)
	{
		if (whatIsDrawn.equals("Field"))
		{
		super.paintComponent(g);
		player.draw(g);
		}
		else if (whatIsDrawn.equals("StartScreen"))
		{
			startScreen.draw(g);
		}
	}
	
	private StartScreen startScreen; 
	private GameField gameField;
	private Player player;
	private String whatIsDrawn;
	
	
	//Это вложенный класс обработчика событий (команд на движение), такая форма записи распространена для них
	//Профит в том, что он имеет доступ к плееру, который, вроде бы, приватное поле другого класса.
	private class MovementAction extends AbstractAction
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
	
	private class MouseHandler extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			System.out.println("LOL");
			if (startScreen.reactToClick(e.getPoint())==1)
			{
				
				whatIsDrawn = "Field";
				repaint();
			}
		}
	}
}
