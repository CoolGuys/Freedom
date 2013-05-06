package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.view.GameScreen;

/*
 * признак конца - this.next.next = this.next;
 */

//TODO Запилить раздвоение луча на углах и обработку прерывания луча барахлом

public class LaserBeam extends Stuff {

	LaserBeam next;
	LaserBeam prev;
	LaserBeam secondNext;
	String direction;
	private Laser source;
	private static int absorbReduce = 5;

	private static Image textureVertical;
	private static Image textureNW;
	private static Image textureHorisontal;
	private static Image textureNE;

	static {
		try {
			textureNE = ImageIO.read(
					new File("Resource/Textures/LaserBeamSW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureHorisontal = ImageIO.read(
					new File("Resource/Textures/LaserBeamHor.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureVertical = ImageIO.read(
					new File("Resource/Textures/LaserBeamVer.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);

			textureNW = ImageIO.read(
					new File("Resource/Textures/LaserBeamNW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LaserBeam(String direction, int x, int y, int damage) {
		super(false, true, false, false, damage, 0);
		super.x = x;
		super.y = y;
		this.direction = direction;
		next = null;
		this.secondNext = null;
	}

	void setSource(Laser source) {
		this.source = source;
	}

	private void setPicture(String direct) {
		if (direct.equals("N"))
			this.texture = textureVertical;
		if (direct.equals("S"))
			this.texture = textureVertical;
		if (direct.equals("W"))
			this.texture = textureHorisontal;
		if (direct.equals("E"))
			this.texture = textureHorisontal;

		if (direct.equals("NW"))
			this.texture = textureNW;
		if (direct.equals("SW"))
			this.texture = textureNE;
		if (direct.equals("NE"))
			this.texture = textureNE;
		if (direct.equals("SE"))
			this.texture = textureNW;

		return;
	}

	/*
	 * у нас луч попал на отражающую клетку. этот метод перемещает его и
	 * поворачивает Север- Юг не совпадают с реальным представлением если луч
	 * попадает на грань кубика, я имею возможность устроить произвол - этим и
	 * пользуюсь) урон после отражения уменьшается на 1
	 */

	public boolean obj() {
		return false;
	}

	// кастыли
	public boolean objc() {
		return false;
	}

	private void reflect() {

		if (this.direction.equals("N")) {
			this.direction = "S";
			this.y = this.y + 1;
			this.reduceDamage(1);
		}

		else if (this.direction.equals("S")) {
			this.direction = "N";
			this.y = this.y - 1;
			this.reduceDamage(1);
		}

		else if (this.direction.equals("W")) {
			this.direction = "E";
			this.x = this.x - 1;
			this.reduceDamage(1);
		}

		else if (this.direction.equals("E")) {
			this.direction = "W";
			this.x = this.x + 1;
			this.reduceDamage(1);
		}

		else if (this.direction.equals("NE")) {
			if ((!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.getIfReflect())
					&& (!GameField.getInstance().cells[this.getX() - 1][this
							.getY()].getIfReflect())) {

				this.direction = "SE";
				this.y = this.y + 1;

				if (!GameField.getInstance().cells[this.getX() - 1][this.getY() - 1]
						.getIfAbsorb()
						&& ((int) Math.ceil(this.getDamage() / 2.0) - 1 > 0)) {

					this.prev.secondNext = new LaserBeam("NW", this.getX() - 1,
							this.getY() - 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].add(this.prev.secondNext);
					this.prev.secondNext.source = this.source;
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}

				if (GameField.getInstance().cells[this.getX() - 1][this.getY() - 1].getIfAbsorb()) {

					GameField.getInstance().cells[this.getX() - 1][this.getY() - 1].getTop().touch(this);

					GameField.getInstance().cells[this.getX() - 1][this.getY() - 1].dealDamageToContent((int) Math.ceil(this.getDamage() / 2.0) - 1);
					//this.prev.secondNext = null;
					//return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.getIfReflect()) {
				this.direction = "SE";
				this.y = this.y + 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() - 1][this
					.getY()].getIfReflect()) {
				this.direction = "NW";
				this.x = this.x - 1;
				this.reduceDamage(1);
			} else {
				this.direction = "SW";
				this.y = this.y + 1;
				this.x = this.x - 1;
				this.reduceDamage(1);
			}
		}

		else if (this.direction.equals("NW")) {
			if ((!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.getIfReflect())
					&& (!GameField.getInstance().cells[this.getX() + 1][this
							.getY()].getIfReflect())) {

				this.direction = "SW";
				this.y = this.y + 1;

				if (!GameField.getInstance().cells[this.getX() - 1][this.getY() - 1]
						.getIfAbsorb()
						&& (int) Math.ceil(this.getDamage() / 2.0) - 1 > 0) {

					this.prev.secondNext = new LaserBeam("NE", this.getX() + 1,
							this.getY() - 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);

					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].add(this.prev.secondNext);
					this.prev.secondNext.source = this.source;
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}

				if (GameField.getInstance().cells[this.getX() - 1][this.getY() - 1].getIfAbsorb()) {

					GameField.getInstance().cells[this.getX() - 1][this.getY() - 1].getTop().touch(this);

					GameField.getInstance().cells[this.getX() - 1][this.getY() - 1].dealDamageToContent((int) Math.ceil(this.getDamage() / 2.0) - 1);
					//this.prev.secondNext = null;
					//return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.getIfReflect()) {
				this.direction = "SW";
				this.y = this.y + 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() + 1][this
					.getY()].getIfReflect()) {
				this.direction = "NE";
				this.x = this.x + 1;
				this.reduceDamage(1);
			} else {
				this.direction = "SE";
				this.y = this.y + 1;
				this.x = this.x + 1;
				this.reduceDamage(1);
			}
		}

		else if (this.direction.equals("SE")) {

			if ((!GameField.getInstance().cells[this.getX()][this.getY() - 1]
					.getIfReflect())
					&& (!GameField.getInstance().cells[this.getX() - 1][this
							.getY()].getIfReflect())) {

				this.direction = "NE";
				this.y = this.y - 1;

				if (!GameField.getInstance().cells[this.getX() - 1][this.getY() + 1]
						.getIfAbsorb()
						&& (int) Math.ceil(this.getDamage() / 2.0) - 1 > 0) {

					this.prev.secondNext = new LaserBeam("SW", this.getX() - 1,
							this.getY() + 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].add(this.prev.secondNext);
					this.prev.secondNext.source = this.source;
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}
				
				if (GameField.getInstance().cells[this.getX() - 1][this.getY() + 1].getIfAbsorb()) {

					GameField.getInstance().cells[this.getX() - 1][this.getY() + 1].getTop().touch(this);

					GameField.getInstance().cells[this.getX() - 1][this.getY() + 1].dealDamageToContent((int) Math.ceil(this.getDamage() / 2.0) - 1);
					//this.prev.secondNext = null;
					//return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() - 1]
					.getIfReflect()) {
				this.direction = "NE";
				this.y = this.y - 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() - 1][this
					.getY()].getIfReflect()) {
				this.direction = "SW";
				this.x = this.x - 1;
				this.reduceDamage(1);
			} else {
				this.direction = "NW";
				this.y = this.y - 1;
				this.x = this.x - 1;
				this.reduceDamage(1);
			}
		} else {
			if ((!GameField.getInstance().cells[this.getX()][this.getY() - 1]
					.getIfReflect())
					&& (!GameField.getInstance().cells[this.getX() + 1][this
							.getY()].getIfReflect())) {

				this.direction = "NW";
				this.y = this.y - 1;

				if (!GameField.getInstance().cells[this.getX() + 1][this.getY() + 1]
						.getIfAbsorb()
						&& (int) Math.ceil(this.getDamage() / 2.0) - 1 > 0) {

					this.prev.secondNext = new LaserBeam("SE", this.getX() + 1,
							this.getY() + 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].add(this.prev.secondNext);
					this.prev.secondNext.source = this.source;
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}
				
				if (GameField.getInstance().cells[this.getX() + 1][this.getY() + 1].getIfAbsorb()) {

					GameField.getInstance().cells[this.getX() + 1][this.getY() + 1].getTop().touch(this);

					GameField.getInstance().cells[this.getX() + 1][this.getY() + 1].dealDamageToContent((int) Math.ceil(this.getDamage() / 2.0) - 1);
					//this.prev.secondNext = null;
					//return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() - 1]
					.getIfReflect()) {
				this.direction = "NW";
				this.y = this.y - 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() + 1][this
					.getY()].getIfReflect()) {
				this.direction = "SE";
				this.x = this.x + 1;
				this.reduceDamage(1);
			} else {
				this.direction = "NE";
				this.y = this.y - 1;
				this.x = this.x + 1;
				this.reduceDamage(1);
			}

		}
	}

	Point getTargetCellCoordinates() {
		Point point = new Point();
		if (direction.equals("N")) {
			point.x = (int) this.x;
			point.y = (int) this.y - 1;
		} else if (direction.equals("S")) {
			point.x = (int) this.x;
			point.y = (int) this.y + 1;
		} else if (direction.equals("E")) {
			point.x = (int) this.x + 1;
			point.y = (int) this.y;
		} else if (this.direction.equals("W")) {
			point.x = (int) this.x - 1;
			point.y = (int) this.y;
		} else if (direction.equals("SW")) {
			point.x = (int) this.x - 1;
			point.y = (int) this.y + 1;
		} else if (direction.equals("SE")) {
			point.x = (int) this.x + 1;
			point.y = (int) this.y + 1;
		} else if (this.direction.equals("NW")) {
			point.x = (int) this.x - 1;
			point.y = (int) this.y - 1;
		} else {
			point.x = (int) this.x + 1;
			point.y = (int) this.y - 1;
		}
		return point;
	}

	/*
	 * если поглощается - делает touch() иначе ничего в принципе не происходит
	 * пока нет возможности одновременно поглощать и отражать
	 */
	void buildBeam() {
		Cell[][] buf = GameField.getInstance().getCells();

		// если поглощает
		if (buf[this.getTargetCellCoordinates().x][this
				.getTargetCellCoordinates().y].getTop().getIfAbsorb()) {
			this.next = null;
			buf[this.getTargetCellCoordinates().x][this
					.getTargetCellCoordinates().y].getTop().touch(this);

			buf[this.getTargetCellCoordinates().x][this
					.getTargetCellCoordinates().y].dealDamageToContent(this
					.getDamage());
			// this.next.reduceDamage(absorbReduce);
		}
		// если отражает
		else if (buf[this.getTargetCellCoordinates().x][this
				.getTargetCellCoordinates().y].getTop().getIfReflect()) {

			this.next = new LaserBeam(this.direction,
					this.getTargetCellCoordinates().x,
					this.getTargetCellCoordinates().y, this.getDamage());
			this.next.prev = this;

			this.next.setSource(this.source);
			this.next.reflect();
			if (this.next.getDamage() == 0) {
				this.next = null;
				return;
			}

			if (GameField.getInstance().cells[this.next.getX()][this.next
					.getY()].getIfAbsorb()) {

				buf[this.next.getX()][this.next.getY()].getTop().touch(this);

				buf[this.next.getX()][this.next.getY()]
						.dealDamageToContent(this.getDamage());
				this.next = null;
				return;
			} else {
				buf[this.next.getX()][this.next.getY()].add(this.next);
				this.next.buildBeam();
			}
		} else {
			this.next = new LaserBeam(this.direction,
					this.getTargetCellCoordinates().x,
					this.getTargetCellCoordinates().y, this.getDamage());
			this.next.setSource(this.source);
			this.next.prev = this;
			buf[this.next.getX()][this.next.getY()].add(this.next);

			this.next.buildBeam();
		}
		GameScreen.getInstance().repaint();

	}

	/*
	 * удалится все после этого элемента
	 */
	void deleteBeam() {
		LaserBeam buf = this;
		Cell[][] cellBuf = GameField.getInstance().cells;
		while (buf != null) {

			if (buf.secondNext != null) {
				/*GameField.getInstance().cells[buf.secondNext.getX()][buf.secondNext
						.getY()].deleteStuff(buf.prev.secondNext);*/
				buf.secondNext.deleteBeam();
				

			}

			
			System.out.println(buf.getX() + " " + buf.getY() + cellBuf[buf.getX()][buf.getY()].deleteStuff(buf));
			buf = buf.next;
			
		}
		
		this.next = null;
	}

	public void draw(Graphics g) {
		setPicture(direction);
		g.drawImage(texture, (int) (x * getSize()), (int) (y * getSize()), null);
	}

	public void loadToFile(Element obj) {
		return;
	}

}
