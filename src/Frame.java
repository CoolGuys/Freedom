import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
class Frame extends JFrame
{

	public Frame(int aWidth, int aHeight)
	{
		this.height = aHeight;
		this.width = aWidth;
		this.setSize(width, height);
		this.setTitle("FreedomGame");

		Field playerHolder = new Field();
		add(playerHolder);
	}

	private int height;
	private int width;
}

