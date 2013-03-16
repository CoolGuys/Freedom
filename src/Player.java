import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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