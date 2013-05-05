package com.freedom.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

/*
 * признак конца - this.next.next = this.next;
 */

public class LaserBeam extends Stuff {

	LaserBeam next;
	String direction;
	private static int absorbReduce = 5;
	
	private static Image textureVertical;
	private static Image textureNW;
	private static Image textureHorisontal;
	private static Image textureNE;
	
	public LaserBeam(String direction, int x, int y) {
		super(false, false, true, false, 10, 0);
		super.x = x;
		super.y = y;
		this.direction = direction;
		next = null;
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
	 * у нас луч попал на отражающую клетку. 
	 * этот метод перемещает его и поворачивает
	 * Север- Юг не совпадают с реальным представлением
	 * если луч попадает на грань кубика, я имею возможность устроить произвол - 
	 * этим и пользуюсь)
	 * урон после отражения уменьшается на 1
	 */
	private void reflect() {
		
		if (this.direction.equals("N")){
			this.direction = "S";
			this.y = this.y + 1;
		}
		
		else if (this.direction.equals("S")){
			this.direction ="N";
			this.y = this.y -1; 
		}
		
		else if (this.direction.equals("W")){
			this.direction ="E";
			this.x = this.x - 1;
		}
		
		else if (this.direction.equals("E")){
			this.direction ="W";
			this.x = this.x + 1;
		}
		
		else if (this.direction.equals("NE")){
			if(!GameField.getInstance().cells[this.getX()][this.getY() + 1].getTop().getIfReflect()){
				this.direction = "SE";
				this.y = this.y + 1; 
			}
			else if(!GameField.getInstance().cells[this.getX()-1][this.getY()].getTop().getIfReflect()){
				this.direction = "NW";
				this.x = this.x - 1;
			}
			else{
				this.direction = "SW";
				this.y = this.y + 1;
				this.x = this.x - 1;
			}
		}
		
		else if (this.direction.equals("NW")){
			if(!GameField.getInstance().cells[this.getX()][this.getY() + 1].getTop().getIfReflect()){
				this.direction = "SW";
				this.y = this.y + 1; 
			}
			else if(!GameField.getInstance().cells[this.getX()+1][this.getY()].getTop().getIfReflect()){
				this.direction = "NE";
				this.x = this.x + 1;
			}
			else{
				this.direction = "SE";
				this.y = this.y + 1;
				this.x = this.x + 1;
			}
		}
		
		else if (this.direction.equals("SE")){
			if(!GameField.getInstance().cells[this.getX()][this.getY() - 1].getTop().getIfReflect()){
				this.direction = "NE";
				this.y = this.y - 1; 
			}
			else if(!GameField.getInstance().cells[this.getX() - 1][this.getY()].getTop().getIfReflect()){
				this.direction = "SW";
				this.x = this.x - 1;
			}
			else{
				this.direction = "NW";
				this.y = this.y - 1;
				this.x = this.x - 1;
			}
		}
		else{
			if(!GameField.getInstance().cells[this.getX()][this.getY() - 1].getTop().getIfReflect()){
				this.direction = "NW";
				this.y = this.y - 1; 
			}
			else if(!GameField.getInstance().cells[this.getX() + 1][this.getY()].getTop().getIfReflect()){
				this.direction = "SE";
				this.x = this.x + 1;
			}
			else{
				this.direction = "NE";
				this.y = this.y - 1;
				this.x = this.x + 1;
			}
			
		}
		this.reduceDamage(1);
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
	 * если поглощается - делает touch()
	 * иначе ничего в принципе не происходит
	 * пока нет возможности одновременно поглощать и отражать
	 */
	void buildBeam() {
		Cell[][] buf = GameField.getInstance().getCells();
		
		//если поглощает
		if(buf[this.getTargetCellCoordinates().x][this.getTargetCellCoordinates().y].getTop().getIfAbsorb()){
			this.next = null;
			buf[this.getTargetCellCoordinates().x][this.getTargetCellCoordinates().y].getTop().touch();
			//this.next.reduceDamage(absorbReduce);
		}
		//если отражает
		else if(buf[this.getTargetCellCoordinates().x][this.getTargetCellCoordinates().y].getTop().getIfReflect()){
	
			this.next = new LaserBeam(this.direction, this.getTargetCellCoordinates().x,this.getTargetCellCoordinates().y);
			this.next.reflect();
			
			if(this.next.getDamage() == 0){
				this.next = null;
				return;
			}
			
			buf[this.next.getX()][this.next.getY()].add(this.next);
			this.next.buildBeam();
		}
		else{
			this.next = new LaserBeam(this.direction, this.getTargetCellCoordinates().x,this.getTargetCellCoordinates().y);
			buf[this.next.getX()][this.next.getY()].add(this.next);
			this.next.buildBeam();
		}

	}
	
	
	/*
	 * удалится все после этого элемента
	 */
	void deleteBeam(){
		LaserBeam buf = this.next;
		Cell[][] cellBuf = GameField.getInstance().cells;
		while(!buf.equals(null)){
			cellBuf[buf.getX()][buf.getY()].deleteStuff(buf);
			buf = buf.next;
		}
		this.next = null;
	}
}
