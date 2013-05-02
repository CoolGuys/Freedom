package com.freedom.utilities;

import javax.swing.JLayeredPane;

import com.freedom.gameObjects.GameField;
import com.freedom.gameObjects.Moveable;
import com.freedom.view.GameScreen;

public final class MovementAnimator<MovingObj extends Moveable> implements Runnable {

	public MovementAnimator(MovingObj mover, String direction)
	{
		this.theOneToRepaint = GameScreen.getInstance();
		this.direction = direction;
		this.theOneToMove = mover;
	}

	public void run() {
		GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].robotOff();
		GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].deleteStuff(GameField.getInstance().getRobot());
//		if(!GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].ifCanPassThrough()){
//			GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].add(GameField.getInstance().getRobot());
//			return;
//		}
		try {
			for (int i = 0; i < 1.0/theOneToMove.getStep(); i++) {
				theOneToMove.move(direction);

				theOneToRepaint.repaint((int)((theOneToMove.getX() - 1) * 50),
						(int)((theOneToMove.getY() - 1) * 50),
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
		GameField.getInstance().cells[(int)theOneToMove.getX()][(int)theOneToMove.getY()].add(GameField.getInstance().getRobot());
	}

	private MovingObj theOneToMove;
	private String direction;
	private JLayeredPane theOneToRepaint;

}
