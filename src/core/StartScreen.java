package core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class StartScreen extends JLayeredPane {
	public StartScreen(GraphicsController aCreator)
	{
		this.addMouseListener(new MouseHandler());

		controller = aCreator;
		buttons = new GButton[3];
		buttons[1] = new GButton("Start", 100, 100);
		buttons[2] = new GButton("Exit", 100, 300);
		try
		{
			wallpaper = ImageIO.read(new File(
					"Textures/StartScreenWallpaper.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int reactToClick(Point2D p) {
		if ((p.getX() >= buttons[1].positionX
				&& p.getX() <= buttons[1].positionX + 100
				&& p.getY() >= buttons[1].positionY && p.getY() <= buttons[1].positionY + 35))
			return 1;
		else if((p.getX() >= buttons[2].positionX
				&& p.getX() <= buttons[2].positionX + 100
				&& p.getY() >= buttons[2].positionY && p.getY() <= buttons[2].positionY + 35))
			return 2;
			
			return 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(wallpaper, 0, 0, this.getWidth(), this.getHeight(), null);
		buttons[1].draw(g);
		buttons[2].draw(g);
	}

	private GButton[] buttons;
	private Image wallpaper;
	private GraphicsController controller;

	private class MouseHandler extends MouseAdapter {
		
		//1==start
		//2=exit
		@Override
		public void mouseClicked(MouseEvent e) {
			if (reactToClick(e.getPoint()) == 1)
			{
				controller.swapDisplays(controller.getGameField(), controller.getStartScreen());
			}
			else if (reactToClick(e.getPoint()) == 2)
			{
				System.exit(0);
			}
		}
	}
}

class GButton {
	public GButton(String aText, int posX, int posY)
	{
		positionX = posX;
		positionY = posY;
		text = aText;
		try
		{
			texture = ImageIO.read(new File("Textures/GButton.gif"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		g.drawImage(texture, positionX, positionY, 100, 35, null);
		g.drawString(text, positionX + 40, positionY + 15);
	}

	int positionX, positionY;
	String text;
	Image texture;

}