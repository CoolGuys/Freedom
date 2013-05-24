package com.freedom.gameObjects.characters;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.controlled.Teleport;
import com.freedom.model.GameField;
import com.freedom.utilities.game.Mover;
import com.freedom.view.EditorScreen;
import com.freedom.view.GameScreen;
import com.freedom.view.ScreensHolder;

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
	public void moveCoarse(String direction) {
		if (Math.abs(GameScreen.getInstance()
				.calculateDistanceFromRobotToScreenCenter(this).x) > ScreensHolder
				.getInstance().getWidth() / 2 - 4 * getSize())
			GameScreen.getInstance().centerByRobotHorisontally(this);

		if (Math.abs(GameScreen.getInstance()
				.calculateDistanceFromRobotToScreenCenter(this).y) > ScreensHolder
				.getInstance().getHeight() / 2 - 4 * getSize())
			GameScreen.getInstance().centerByRobotVertically(this);

		Runnable r = new Mover<Robot>(this, direction, 1, 10,
				EditorScreen.getInstance());
		Thread t = new Thread(r);
		t.start();

	}

	@Override
	public void moveFine(String direction) {
		if (!direction.equals(this.direction)) {
			this.direction = direction;
			EditorScreen.getInstance().repaint();
			return;
		}
		Runnable r = new Mover<Robot>(this, direction, 1, 10,
				EditorScreen.getInstance());

		Thread t = new Thread(r);
		t.start();
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
		EditorScreen.getInstance().repaint();
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

		EditorScreen.getInstance().repaint();
		return;
	}
}
