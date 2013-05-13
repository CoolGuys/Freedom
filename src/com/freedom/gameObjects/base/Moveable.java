package com.freedom.gameObjects.base;

import java.awt.Point;

public interface Moveable {
	public void move(String diretion);
	public boolean checkIfBeingMoved();
	public void tellIfBeingMoved(boolean isMoved);
	public void recalibrate();
	public int getX();
	public int getY();
	public double getStep();
	public void setDirection(String direction);
	public void activate();
	public boolean canGo();
	public Point getTargetCellCoordinates(String direction);
	
}
