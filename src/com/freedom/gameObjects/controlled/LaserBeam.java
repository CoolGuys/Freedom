package com.freedom.gameObjects.controlled;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

/*
 * признак конца - this.next.next = this.next;
 */

public class LaserBeam extends Stuff {

	LaserBeam next;
	LaserBeam prev;
	LaserBeam secondNext;
	String direction;
	private Laser source;
	// private static int absorbReduce = 5;

	private static Image[] texturesVertical = new Image[4];
	private static Image[] texturesNW = new Image[4];
	private static Image[] texturesHorisontal = new Image[4];
	private static Image[] texturesNE = new Image[4];

	private static Logger logger = Logger.getLogger("LaserBeam");
	static {
		logger.setLevel(Level.WARNING);
		try {
			for (int i = 1; i <= 3; i++) {
				texturesNE[i] = ImageIO
						.read(new File("Resource/Textures/LaserBeam/SW" + i
								+ ".png")).getScaledInstance(getSize(),
								getSize(), Image.SCALE_SMOOTH);

				texturesHorisontal[i] = ImageIO
						.read(new File("Resource/Textures/LaserBeam/Hor" + i
								+ ".png")).getScaledInstance(getSize(),
								getSize(), Image.SCALE_SMOOTH);

				texturesVertical[i] = ImageIO
						.read(new File("Resource/Textures/LaserBeam/Ver" + i
								+ ".png")).getScaledInstance(getSize(),
								getSize(), Image.SCALE_SMOOTH);

				texturesNW[i] = ImageIO
						.read(new File("Resource/Textures/LaserBeam/NW" + i
								+ ".png")).getScaledInstance(getSize(),
								getSize(), Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			logger.warning("Textures were corrupted");
		}
	}

	public LaserBeam(String direction, int x, int y, int damage)
	{
		super(false, true, damage, 0);
		super.type=LoadingType.DNW;
		super.x = x;
		super.y = y;
		this.direction = direction;
		next = null;
		this.secondNext = null;
	}

	void setSource(Laser source) {
		this.source = source;
		this.setColour(source.getColour());
		logger.info(this.getColour()+"");
	}

	private void setPicture(String direct) {
		logger.info(""+getColour());
		if (direct.equals("N")) {
			this.textureRed = texturesVertical[1];
			this.textureGreen = texturesVertical[2];
			this.textureBlue = texturesVertical[3];
		}
		if (direct.equals("S")) {
			this.textureRed = texturesVertical[1];
			this.textureGreen = texturesVertical[2];
			this.textureBlue = texturesVertical[3];
		}
		if (direct.equals("W")) {
			this.textureRed = texturesHorisontal[1];
			this.textureGreen = texturesHorisontal[2];
			this.textureBlue = texturesHorisontal[3];
		}
		if (direct.equals("E")) {
			this.textureRed = texturesHorisontal[1];
			this.textureGreen = texturesHorisontal[2];
			this.textureBlue = texturesHorisontal[3];
		}

		if (direct.equals("NW")) {
			this.textureRed = texturesNW[1];
			this.textureGreen = texturesNW[2];
			this.textureBlue = texturesNW[3];
		}
		if (direct.equals("SW")) {
			this.textureRed = texturesNE[1];
			this.textureGreen = texturesNE[2];
			this.textureBlue = texturesNE[3];			
		}
		if (direct.equals("NE")){
			this.textureRed = texturesNE[1];
			this.textureGreen = texturesNE[2];
			this.textureBlue = texturesNE[3];			
		}
		if (direct.equals("SE")){
			this.textureRed = texturesNW[1];
			this.textureGreen = texturesNW[2];
			this.textureBlue = texturesNW[3];
		}

		return;
	}

	/*
	 * у нас луч попал на отражающую клетку. этот метод перемещает его и
	 * поворачивает Север- Юг не совпадают с реальным представлением
	 */

	private void reflect() {

		// сначала самое примитивное
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
			this.x = this.x + 1;
			this.reduceDamage(1);
		}

		else if (this.direction.equals("E")) {
			this.direction = "W";
			this.x = this.x - 1;
			this.reduceDamage(1);
		}

		/*
		 * далее - падение под углом. здесь я смотрю 2 соседние клетки.(так, для
		 * NW соседи - N и W)
		 * 
		 * если на обеих нет ничего отражающего - раздваиваю луч(1ый if) для
		 * вторичного луча проверяю поглощение и строю отдельно в этом методе
		 * 
		 * если на какой-то одной что-то отражает - луч без раздваивания идет на
		 * другую если отражают обе соседки, луч просто идет обратно
		 */
		else if (this.direction.equals("NE")) {
			if ((!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.reflects(this))
					&& (!GameField.getInstance().cells[this.getX() - 1][this
							.getY()].reflects(this))) {

				this.direction = "SE";
				this.y = this.y + 1;

				if (!GameField.getInstance().cells[this.getX() - 1][this.getY() - 1]
						.absorbs(this)
						&& ((int) Math.ceil(this.getDamage() / 2.0) - 1 > 0)) {

					this.prev.secondNext = new LaserBeam("NW", this.getX() - 1,
							this.getY() - 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);
					this.prev.secondNext.setSource(this.source);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].utilityAdd(this.prev.secondNext);
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}

				if (GameField.getInstance().cells[this.getX() - 1][this.getY() - 1]
						.absorbs(this)) {

					GameField.getInstance().cells[this.getX() - 1][this.getY() - 1]
							.getTop().touch(this);
					source.addTouchedCell(new Point(this.getX()-1, this.getY()-1));

					GameField.getInstance().cells[this.getX() - 1][this.getY() - 1]
							.punchContent((int) Math.ceil(this
									.getDamage() / 2.0) - 1);
					// this.prev.secondNext = null;
					// return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.reflects(this)) {
				this.direction = "SE";
				this.y = this.y + 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() - 1][this
					.getY()].reflects(this)) {
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
					.reflects(this))
					&& (!GameField.getInstance().cells[this.getX() + 1][this
							.getY()].reflects(this))) {

				this.direction = "SW";
				this.y = this.y + 1;

				if (!GameField.getInstance().cells[this.getX() + 1][this.getY() - 1]
						.absorbs(this)
						&& (int) Math.ceil(this.getDamage() / 2.0) - 1 > 0) {

					this.prev.secondNext = new LaserBeam("NE", this.getX() + 1,//////////////////////////////
							this.getY() - 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);

					this.prev.secondNext.setSource(this.source);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].utilityAdd(this.prev.secondNext);
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}

				if (GameField.getInstance().cells[this.getX() + 1][this.getY() - 1]
						.absorbs(this)) {

					GameField.getInstance().cells[this.getX() + 1][this.getY() - 1]
							.getTop().touch(this);
					source.addTouchedCell(new Point(this.getX()+1, this.getY()-1));

					GameField.getInstance().cells[this.getX() + 1][this.getY() - 1]
							.punchContent((int) Math.ceil(this
									.getDamage() / 2.0) - 1);
					// this.prev.secondNext = null;
					// return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() + 1]
					.reflects(this)) {
				this.direction = "SW";
				this.y = this.y + 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() + 1][this
					.getY()].reflects(this)) {
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
					.reflects(this))
					&& (!GameField.getInstance().cells[this.getX() - 1][this
							.getY()].reflects(this))) {

				this.direction = "NE";
				this.y = this.y - 1;

				if (!GameField.getInstance().cells[this.getX() - 1][this.getY() + 1]
						.absorbs(this)
						&& (int) Math.ceil(this.getDamage() / 2.0) - 1 > 0) {

					this.prev.secondNext = new LaserBeam("SW", this.getX() - 1,
							this.getY() + 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);
					this.prev.secondNext.setSource(this.source);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].utilityAdd(this.prev.secondNext);
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}

				if (GameField.getInstance().cells[this.getX() - 1][this.getY() + 1]
						.absorbs(this)) {

					GameField.getInstance().cells[this.getX() - 1][this.getY() + 1]
							.getTop().touch(this);
					source.addTouchedCell(new Point(this.getX()-1, this.getY()+1));

					GameField.getInstance().cells[this.getX() - 1][this.getY() + 1]
							.punchContent((int) Math.ceil(this
									.getDamage() / 2.0) - 1);
					// this.prev.secondNext = null;
					// return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() - 1]
					.reflects(this)) {
				this.direction = "NE";
				this.y = this.y - 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() - 1][this
					.getY()].reflects(this)) {
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
					.reflects(this))
					&& (!GameField.getInstance().cells[this.getX() + 1][this
							.getY()].reflects(this))) {

				this.direction = "NW";
				this.y = this.y - 1;

				if (!GameField.getInstance().cells[this.getX() + 1][this.getY() + 1]
						.absorbs(this)
						&& (int) Math.ceil(this.getDamage() / 2.0) - 1 > 0) {

					this.prev.secondNext = new LaserBeam("SE", this.getX() + 1,
							this.getY() + 1,
							(int) Math.ceil(this.getDamage() / 2.0) - 1);
					this.prev.secondNext.setSource(this.source);
					GameField.getInstance().cells[this.prev.secondNext.getX()][this.prev.secondNext
							.getY()].utilityAdd(this.prev.secondNext);
					this.prev.secondNext.prev = this.prev;
					this.prev.secondNext.buildBeam();
				}

				if (GameField.getInstance().cells[this.getX() + 1][this.getY() + 1]
						.absorbs(this)) {

					GameField.getInstance().cells[this.getX() + 1][this.getY() + 1]
							.getTop().touch(this);
					source.addTouchedCell(new Point(this.getX()+1, this.getY()+1));

					GameField.getInstance().cells[this.getX() + 1][this.getY() + 1]
							.punchContent((int) Math.ceil(this
									.getDamage() / 2.0) - 1);
					// this.prev.secondNext = null;
					// return;
				}

				this.reduceDamage(this.getDamage() / 2 + 1);

			} else if (!GameField.getInstance().cells[this.getX()][this.getY() - 1]
					.reflects(this)) {
				this.direction = "NW";
				this.y = this.y - 1;
				this.reduceDamage(1);
			} else if (!GameField.getInstance().cells[this.getX() + 1][this
					.getY()].reflects(this)) {
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
				.getTargetCellCoordinates().y].absorbs(this)) {
			this.next = null;
			buf[this.getTargetCellCoordinates().x][this
					.getTargetCellCoordinates().y].getTop().touch(this);
			this.source.addTouchedCell(getTargetCellCoordinates());

			buf[this.getTargetCellCoordinates().x][this
					.getTargetCellCoordinates().y].punchContent(this
					.getDamage());
			// this.next.reduceDamage(absorbReduce);
		}
		// если отражает
		else if (buf[this.getTargetCellCoordinates().x][this
				.getTargetCellCoordinates().y].reflects(this)) {

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
					.getY()].absorbs(this)) {

				buf[this.next.getX()][this.next.getY()].getTop().touch(this);
				source.addTouchedCell(new Point(this.next.getX(), this.next.getY()));
				
				buf[this.next.getX()][this.next.getY()]
						.punchContent(this.getDamage());
				this.next = null;
				return;
			} else {
				buf[this.next.getX()][this.next.getY()].utilityAdd(this.next);
				this.next.buildBeam();
			}
		} else {
			this.next = new LaserBeam(this.direction,
					this.getTargetCellCoordinates().x,
					this.getTargetCellCoordinates().y, this.getDamage());
			this.next.setSource(this.source);
			this.next.prev = this;
			buf[this.next.getX()][this.next.getY()].utilityAdd(this.next);

			this.next.buildBeam();
		}
		repaintSelf();

	}

	/*
	 * удалится все после этого элемента
	 */
	public void deleteBeam() {
		LaserBeam buf = this;
		Cell[][] cellBuf = GameField.getInstance().cells;
		while (buf.next != null) {
			
			if (buf.secondNext != null) {

				/*GameField.getInstance().cells[buf.secondNext.getX()][buf.secondNext
						.getY()].deleteStuff(buf.prev.secondNext);*/
				buf.secondNext.deleteBeam();
			}
			cellBuf[buf.getX()][buf.getY()].delete(buf);
			cellBuf[buf.getX()][buf.getY()].repaintSelf();
			
			// System.out.println(buf.getX() + " " + buf.getY() + );
			buf = buf.next;
		}
		
		if (buf.secondNext != null) {
			buf.secondNext.deleteBeam();	
		}
		cellBuf[buf.getX()][buf.getY()].delete(buf);
		cellBuf[buf.getX()][buf.getY()].repaintSelf();
		this.next = null;
	}

	@Override
	public void draw(Graphics g) {
		setPicture(direction);
		super.draw(g);
	}

	@Override
	public void loadToFile(Element obj) {
		return;
	}

	public Laser getSource() {
		return this.source;
	}
	
	@Override
	public boolean absorbs(Stuff element){
		return false;
	}
	
	@Override
	public boolean reflects(Stuff element) {
		return false;
	}

}
