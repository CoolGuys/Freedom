import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;


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
		
		startScreen = new StartScreen(this);
		this.add(startScreen);
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
	
	public void setWhatIsDrawn(String target)
	{
		this.whatIsDrawn = target;
	}

	@Override
	public void paintComponent(Graphics g)
	{
	/*	if (whatIsDrawn.equals("Field"))
		{
		super.paintComponent(g);
		player.draw(g);
		}
		else if (whatIsDrawn.equals("StartScreen"))
		{
			startScreen.draw(g);
		}*/
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
			duty = (String) getValue(Action.NAME);
			ActionListener smoother = new MovementSmoother();
			t = new Timer(2, smoother);
			t.start();
		}
		
		public void repaintOuter()
		{
			repaint();
		}
		
		Timer t;
		String duty;
		
		private class MovementSmoother implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if (whatIsDrawn.equals("Field")) {
					if (counter <= 5) {
						player.move(duty);
						GraphicsHolderAndController.MovementAction.this
								.repaintOuter();
						System.out.print(counter);
						counter++;
					} else {
						System.out.println("Stopped");
						t.stop();
						counter = 0;
					}
				}
			}
			
			int counter=0;
		}
	}

	
	
}
