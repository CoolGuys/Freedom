package com.freedom.utilities;

import com.freedom.gameObjects.GameField;
import com.freedom.gameObjects.Moveable;
import com.freedom.view.ScreensHolder;

public class MovementAnimator<MovingObj extends Moveable> implements Runnable {

	public MovementAnimator(MovingObj mover,
			String direction)
	{
		this.theOneToRepaint = ScreensHolder.getInstance();
		this.direction = direction;
		this.theOneToMove = mover;
	}

	public void run() {
		try
		{
			for (int i = 0; i < 5; i++)
			{
				theOneToMove.move(direction);
				//theOneToRepaint.repaint();
				theOneToRepaint.repaint((theOneToMove.getX()-1)*50, (theOneToMove.getY()-1)*50, GameField.getcellSize()*3, GameField.getcellSize()*3);
				Thread.sleep(10);
			}
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		theOneToRepaint.repaint();
		theOneToMove.recalibrate();
		theOneToMove.tellIfBeingMoved(false);
	}

	private MovingObj theOneToMove;
	private String direction;
	private ScreensHolder theOneToRepaint;

}
