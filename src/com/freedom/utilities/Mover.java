package com.freedom.utilities;

import java.util.logging.Logger;

import javax.swing.JLayeredPane;

import com.freedom.gameObjects.GameField;
import com.freedom.gameObjects.Moveable;
import com.freedom.gameObjects.Stuff;
import com.freedom.view.GameScreen;

public final class Mover<MovingObj extends Moveable> implements Runnable {

	public Mover(MovingObj mover, String direction, int distance, int delay)
	{
		this.theOneToRepaint = GameScreen.getInstance();
		this.direction = direction;
		this.theOneToMove = mover;
		this.delay = delay;
		this.distance = distance;
	}

	public void run() {
		if(theOneToMove.checkIfBeingMoved())
			return;
		//logger.info(this.toString());
		theOneToMove.tellIfBeingMoved(true);
		try {
			for (int k = 0; k < distance; k++) {
				if (theOneToMove.canGo())
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
//				GameField.getInstance().cells[(int) theOneToMove
//						.getTargetCellCoordinates(invertDirection()).x][(int) theOneToMove
//						.getTargetCellCoordinates(invertDirection()).y]
//						.robotOff();
				GameField.getInstance().cells[(int) theOneToMove
						.getTargetCellCoordinates(invertDirection()).x][(int) theOneToMove
						.getTargetCellCoordinates(invertDirection()).y]
						.deleteStuff((Stuff) theOneToMove);
				logger.info("prev calc: "+ theOneToMove
						.getTargetCellCoordinates(invertDirection()).x+" "+theOneToMove
						.getTargetCellCoordinates(invertDirection()).y);
//				GameField.getInstance().cells[(int) theOneToMove.getX()][(int) theOneToMove
//						.getY()].robotOn();

				GameField.getInstance().cells[(int) theOneToMove.getX()][(int) theOneToMove
						.getY()].add((Stuff) theOneToMove);
				
				
				logger.info( "current"+theOneToMove
						.getX()+" "+theOneToMove.getY());

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

	private MovingObj theOneToMove;
	private String direction;
	private JLayeredPane theOneToRepaint;
	private int delay;
	private int distance;
	private static Logger logger = Logger.getLogger("");
}
