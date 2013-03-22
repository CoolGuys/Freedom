package core;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Robot implements Movable {

	int x;
	int y;
	private String direction;
	private Stuff container;
	private boolean isEmpty; // пуст ли контейнер
	private Tile[][] environment;
	private GraphicsController painter;
	private boolean isMoving;
	public static int step = 6;

	Image textureN;
	Image textureS;
	Image textureE;
	Image textureW;

	private static Logger logger = Logger.getLogger("Core.Robot");

	// /конструктор (я выпилил второй, буду ставить null в вызове @gleb)
	public Robot(int posX, int posY, String direction, Stuff c, Tile[][] tiles,
			GraphicsController painter)
	{
		this.x = posX;
		this.y = posY;
		this.direction = direction;
		this.container = c;
		this.environment = tiles;
		this.painter = painter;
		isEmpty = false;

		try
		{
			textureN = ImageIO.read(new File("Textures/RobotN.png"));
			textureS = ImageIO.read(new File("Textures/RobotS.png"));
			textureE = ImageIO.read(new File("Textures/RobotE.png"));
			textureW = ImageIO.read(new File("Textures/RobotW.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/*
	 * Этот метод выглядит ненужным)) @gleb
	 */
	private boolean ifDirection(char dir) { // проверка, является ли направление
											// правильно заданным
		if (dir == 'N')
			return true;
		if (dir == 'S')
			return true;
		if (dir == 'W')
			return true;
		if (dir == 'E')
			return true;
		return false;
	}

	// блок выдачи полей

	public int getX() {
		return (this.x);
	}

	public int getY() {
		return (this.y);
	}

	// Нет, это было точно бесполезно, если у робота в поле направления
	// рандомная
	// буква - это уже все, пипец программе настал @gleb
	public String getDirection() {
		return this.direction;
	}

	public boolean getIfEmpty() {
		return (this.isEmpty);
	}

	public Stuff getContent() {
		return (this.container);
	}

	// конец блока выдачи

	public boolean canGo(Tile[][] tiles) { // стоит объект - false;

		if (this.direction.equals("N"))
		{
			if (tiles[x][y - 1].getContentAmount() == 0)
				return true;
		}

		if (this.direction.equals("S"))
		{
			if (tiles[x][y + 1].getContentAmount() == 0)
				return true;
		}

		if (this.direction.equals("W"))
		{
			if (tiles[x - 1][y].getContentAmount() == 0)
				return true;
		}

		if (this.direction.equals("E"))
		{
			if (tiles[x + 1][y].getContentAmount() == 0)
				return true;
		}

		return false;

	}

	// Ваня, верни проверку canGo потом
	public void moveToNextTile(String direction) {
		// основной метод: подсовываем ему направление, если оно валидно и мы
		// туда не смотрим,
		// поворачиваемся туда, иначе начинаем туда двигаться, если на соседней
		// клетке нет объектов

		if (!direction.equals(this.direction))
		{
			this.direction = direction;
			painter.repaint();
		}

		else if (!isMoving)
		{
			isMoving = true;
			Runnable r = new MovementAnimator<Robot>(painter, this,
					this.direction);
			Thread t = new Thread(r);
			t.start();
		} else
			return;
	}

	public void move(String direction) {

		if (direction.equals("N"))
			y -= step;
		else if (direction.equals("S"))
			y += step;
		else if (direction.equals("E"))
			x += step;
		else
			x -= step;
	}

	// Здесь аргумент не имеет смысла, робот может брать Stuff только перед
	// собой!
	// Нужно доработать и брать с уловиями на направление
	// @gleb
	public void take() { // метод поднятия объекта: если объект берется
							// и есть куда положить - делаем это
		if (!this.isEmpty)
			return;

		// this.container = environment[].takeObject();
		if (this.container != null)
		{
			this.isEmpty = false;
		}
	}

	public void draw(Graphics g) {
		if (direction.equals("N"))
			g.drawImage(textureN, x, y, 30, 30, null);
		else if (direction.equals("S"))
			g.drawImage(textureS, x, y, 30, 30, null);
		else if (direction.equals("E"))
			g.drawImage(textureE, x, y, 30, 30, null);
		else
			g.drawImage(textureW, x, y, 30, 30, null);
	}

	@Override
	public boolean checkIfBeingMoved() {
		// TODO Auto-generated method stub
		return isMoving;
	}

	@Override
	public void tellIfBeingMoved(boolean isMoved) {
		// TODO Auto-generated method stub
		isMoving = isMoved;
	}

}
