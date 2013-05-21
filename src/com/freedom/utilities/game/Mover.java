package com.freedom.utilities.game;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.gameObjects.base.Moveable;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.view.AbstractScreen;

public final class Mover<MO extends Moveable> implements Runnable {

	public Mover(MO mover, String direction, int distance, int delay, AbstractScreen theOneToRepaint)
	{
		this.theOneToRepaint = theOneToRepaint;
		this.direction = direction;
		this.theOneToMove = mover;
		this.delay = delay;
		this.distance = distance;
	}

	public synchronized void run() {
		Thread.currentThread().setName(
				"Mover@" + theOneToMove.getClass().toString());
		theOneToMove.setDirection(direction);
		// logger.info(this.toString());
		try {
			if (GameField.getInstance().cells[theOneToMove
					.getTargetCellCoordinates(direction).x][theOneToMove
					.getTargetCellCoordinates(direction).y].locked == true
					|| theOneToMove.checkIfBeingMoved())
				return;
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

						theOneToRepaint.repaint(
								(int) ((theOneToMove.getX() - 1) * GameField
										.getInstance().getCellSize()),
								(int) ((theOneToMove.getY() - 1) * GameField
										.getInstance().getCellSize()),
								GameField.getInstance().getCellSize() * 3,
								GameField.getInstance().getCellSize() * 3);

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
				theOneToRepaint.repaint();
			}

			theOneToMove.tellIfBeingMoved(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	private AbstractScreen theOneToRepaint;
	private int delay;
	private int distance;
	private static Logger logger = Logger.getLogger("");
	static {
		logger.setLevel(Level.OFF);
	}
}
