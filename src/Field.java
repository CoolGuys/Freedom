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

@SuppressWarnings("serial")
class Field extends JPanel
{
	public Field()
	{
		super();
		this.setBackground(Color.BLACK);
		setPreferredSize(new Dimension(1000,600));
		player = new Player(10,10);
		
		MovementAction moveUp = new MovementAction("up");
		MovementAction moveDown = new MovementAction("down");
		MovementAction moveLeft = new MovementAction("left");
		MovementAction moveRight = new MovementAction("right");
		
		
		InputMap imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("pressed W"), "move.up");
		imap.put(KeyStroke.getKeyStroke("pressed A"), "move.right");
		imap.put(KeyStroke.getKeyStroke("pressed D"), "move.left");
		imap.put(KeyStroke.getKeyStroke("S"), "move.down");
		ActionMap amap = getActionMap();
		amap.put("move.up", moveUp);
		amap.put("move.down", moveDown);
		amap.put("move.left", moveLeft);
		amap.put("move.right", moveRight);
		
	}

	@Override
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

