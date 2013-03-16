import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FrameTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{

				// TODO Auto-generated method stub
				Frame frame = new Frame(1000, 600);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class Frame extends JFrame
{

	public Frame(int aWidth, int aHeight)
	{
		this.height = aHeight;
		this.width = aWidth;
		this.setSize(width, height);
		this.setTitle("Tile");

		Field playerHolder = new Field();
		add(playerHolder);
	}

	private int height;
	private int width;
}

class Field extends JPanel
{
	public Field()
	{
		super();
		setPreferredSize(new Dimension(1000,600));
		player = new Player(10,10);
		
		MovementAction moveUp = new MovementAction("up");
		MovementAction moveDown = new MovementAction("down");
		MovementAction moveLeft = new MovementAction("left");
		MovementAction moveRight = new MovementAction("right");
		
		
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

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		player.draw(g);
	}
	
	Player player;

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

class Player
{

	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
		try
		{
			texture = ImageIO.read(new File("Player.gif"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void draw(Graphics g)
	{
		g.drawImage(texture, x, y, 30, 30, null);
	}

	public void move(String direction)
	{
		if (direction.equals("up") == true)
			y -= 30;
		else if (direction.equals("down") == true)
			y += 30;
		else if (direction.equals("left") == true)
			x += 30;
		else
			x -= 30;
	}

	private Image texture;
	private int x;
	private int y;
}
