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
public class StartScreen extends JPanel {
	public StartScreen(GraphicsController aCreator)
	{
		setPreferredSize(new Dimension(1000, 600));

		this.addMouseListener(new MouseHandler());

		creator = aCreator;
		buttons = new GButton[3];
		buttons[1] = new GButton("Start", 100, 100);
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
		else
			return 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(wallpaper, 0, 0, null);
		buttons[1].draw(g);

	}

	private GButton[] buttons;
	private Image wallpaper;
	private GraphicsController creator;

	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (reactToClick(e.getPoint()) == 1)
			{
				creator.remove(creator.getStartScreen());
				creator.add(creator.getGameField());
				creator.getGameField().requestFocusInWindow();
				creator.revalidate();
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