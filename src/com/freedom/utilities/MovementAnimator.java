package com.freedom.utilities;

import javax.swing.JLayeredPane;

import com.freedom.gameObjects.GameField;
import com.freedom.gameObjects.Moveable;
import com.freedom.view.GameScreen;
import com.freedom.view.ScreensHolder;

public final class MovementAnimator<MovingObj extends Moveable> implements Runnable {

	public MovementAnimator(MovingObj mover, String direction)
	{
		this.theOneToRepaint = GameScreen.getInstance();
		this.direction = direction;
		this.theOneToMove = mover;
	}

	public void run() {
		GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].robotOff();
		try {
			for (int i = 0; i < 1.0/theOneToMove.getStep(); i++) {
				theOneToMove.move(direction);

				theOneToRepaint.repaint((theOneToMove.getX() - 1) * 50,
						(theOneToMove.getY() - 1) * 50,
						GameField.getInstance().getCellSize() * 3,
						GameField.getInstance().getCellSize() * 3);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		theOneToRepaint.repaint();
		theOneToMove.recalibrate();
		theOneToMove.tellIfBeingMoved(false);
		GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].robotOn();
	}

	private MovingObj theOneToMove;
	private String direction;
	private JLayeredPane theOneToRepaint;

}
