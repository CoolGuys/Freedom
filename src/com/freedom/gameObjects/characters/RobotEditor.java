package com.freedom.gameObjects.characters;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.controlled.Teleport;
import com.freedom.model.GameField;

public class RobotEditor extends Robot {
	public Stuff source;
	public Teleport teleport;

	public RobotEditor(int posX, int posY, String direction)
	{
		super(posX, posY, direction, null, 0);
		god = true;
	}

	public void assign() {
		if (this.source == null) {
			source = GameField.getInstance().cells[this
					.getTargetCellCoordinates(direction).x][this
					.getTargetCellCoordinates(direction).y].containsObjc();
		} else {
			Cell element = GameField.getInstance().cells[this
					.getTargetCellCoordinates(direction).x][this
					.getTargetCellCoordinates(direction).y];
			this.source.setControlled(element);
			this.source = null;
		}
		if (teleport == null) {
			teleport = GameField.getInstance().cells[this
					.getTargetCellCoordinates(direction).x][this
					.getTargetCellCoordinates(direction).y].containsTeleport();
		} else {
			teleport.setDestination(this.getTargetCellCoordinates(direction));
		}
	}

	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("editor", "true");
	}

	@Override
	public boolean canGo() {
		return true;
	}


	@Override
	public void take() {
		if (this.container[0] != null)
			return;
		this.container[0] = GameField.getInstance().cells[this
				.getTargetCellCoordinates(getDirection()).x][this
				.getTargetCellCoordinates(getDirection()).y].deleteStuff();
		if (this.container[0] == null)
			return;
		container[0].x = x;
		container[0].y = y;
		repaintNeighbourhood();
	}

	@Override
	public void put() {
		if (isMoving
				|| GameField.getInstance().cells[this
						.getTargetCellCoordinates(getDirection()).x][this
						.getTargetCellCoordinates(getDirection()).y].locked)
			return;

		int targetX = this.getTargetCellCoordinates(getDirection()).x;
		int targetY = this.getTargetCellCoordinates(getDirection()).y;
		if (this.container[0] == null)
			return;

		if (!GameField.getInstance().cells[targetX][targetY]
				.add(this.container[0]))
			return;
		this.container[0] = null;

		repaintNeighbourhood();	
		return;
	}
}
