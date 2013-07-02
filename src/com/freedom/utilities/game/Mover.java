package com.freedom.utilities.game;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.gameObjects.base.Moveable;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public final class Mover<MO extends Moveable> implements Runnable {

	public Mover(MO mover, String direction, int distance, int delay)
	{
		this.direction = direction;
		this.theOneToMove = mover;
		this.delay = delay;
		this.distance = distance;
	}

	@Override
	public synchronized void run() {
		Thread.currentThread().setName(
				"Mover@" + theOneToMove.getClass().toString());
		try {
			if (GameField.getInstance().cells[theOneToMove
					.getTargetCellCoordinates(direction).x][theOneToMove
					.getTargetCellCoordinates(direction).y].locked == true
					|| theOneToMove.checkIfBeingMoved())
				return;
			theOneToMove.setDirection(direction);
			for (int k = 0; k < distance; k++) {
				if (theOneToMove.canGo()) {
					theOneToMove.tellIfBeingMoved(true);
					GameField.getInstance().cells[theOneToMove
							.getTargetCellCoordinates(direction).x][theOneToMove
							.getTargetCellCoordinates(direction).y].locked = true;

					GameField.getInstance().cells[theOneToMove
							.getTargetCellCoordinates(direction).x][theOneToMove
							.getTargetCellCoordinates(direction).y]
							.setMeta((Stuff) theOneToMove);

					GameField.getInstance().cells[theOneToMove.getX()][theOneToMove
							.getY()].delete((Stuff) theOneToMove);
					for (int i = 0; i < 1.0 / theOneToMove.getStep(); i++) {

						theOneToMove.move(direction);
						((Stuff) theOneToMove).repaintNeighbourhood();
						Thread.sleep(delay);
					}

					theOneToMove.recalibrate();
					// GameField.getInstance().cells[(int) theOneToMove
					// .getTargetCellCoordinates(invertDirection()).x][(int)
					// theOneToMove
					// .getTargetCellCoordinates(invertDirection()).y]
					// .utilityAdd((Stuff) theOneToMove);

					GameField.getInstance().cells[theOneToMove.getX()][theOneToMove
							.getY()].clearMeta();
					GameField.getInstance().cells[theOneToMove.getX()][theOneToMove
							.getY()].add((Stuff) theOneToMove);

					// logger.info("current" + theOneToMove.getX() + " "
					// + theOneToMove.getY());

				}
				theOneToMove.recalibrate();
				((Stuff)theOneToMove).repaintNeighbourhood();
			}

			theOneToMove.tellIfBeingMoved(false);
		} catch (InterruptedException e) {
			return;
		}

	}

	public String invertDirection() {
		if (direction.equals("N")) {
			return "S";
		} else if (direction.equals("S")) {
			return "N";
		} else if (direction.equals("E")) {
			return "W";
		} else {
			return "E";
		}
	}

	private MO theOneToMove;
	private String direction;
	private int delay;
	private int distance;
	private static Logger logger = Logger.getLogger("");
	static {
		logger.setLevel(Level.OFF);
	}
}
