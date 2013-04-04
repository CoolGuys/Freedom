package com.freedom.core;

import com.freedom.gameObjects.Moveable;

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
			for (int i = 0; i <= 5; i++)
			{
				theOneToMove.move(direction);
				theOneToRepaint.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		theOneToMove.tellIfBeingMoved(false);
	}

	private MovingObj theOneToMove;
	private String direction;
	private ScreensHolder theOneToRepaint;

}
