package com.freedom.gameObjects;

import java.util.logging.Logger;

import com.freedom.utilities.PathFinder;
import com.freedom.view.GameScreen;

public class PacmanSoul implements Runnable {
	private int stepRate;
	private int destinationX;
	private int destinationY;
	private PacmanBody body;
	private PathFinder finder;
	private int widh = 3;
	boolean alive;

	/**
	 * 
	 * @param rate
	 *            период двигания в мс
	 * @param Pack
	 *            тот кого нужно двигать
	 */
	public PacmanSoul(int rate, PacmanBody Pack, int wid)
	{
		this.stepRate = rate;
		this.body = Pack;
		this.finder = new PathFinder();
		this.widh = wid;
		this.alive = true;

		try {
			this.destinationX = GameField.getInstance().getRobot().getX();
			this.destinationY = GameField.getInstance().getRobot().getY();
		} catch (Exception e) {
			this.destinationX = body.getX();
			this.destinationY = body.getY();
		}
		GameField.getInstance().getThreads().execute(new Changer());
	}

	Logger gleblo = Logger.getLogger("soul");

	public void InHell() {
		this.alive = false;
		gleblo.info("weed");
	}

	public void run() {
		while (alive) {
			int x = body.getX();
			int y = body.getY();
			if (GameField.getInstance().getRobot() != null) {
				this.destinationX = GameField.getInstance().getRobot().getX();
				this.destinationY = GameField.getInstance().getRobot().getY();

				String dir = finder.find(x, y, destinationX, destinationY, widh);
				if (!dir.equals("0")) {
					if (dir.length() > 1) {
						try {
							body.move1((String) dir.subSequence(0, 1));
						} catch (Exception e) {

						}
					} else {
					//	GameField.getInstance().getRobot().punch(1);
//						System.out.println(GameField.getInstance().getRobot()
//								.getLives());
					}
					GameScreen.getInstance().repaint();
					try {
						Thread.sleep(this.stepRate);
					} catch (InterruptedException e) {
						this.alive = false;
					}
				}
			}
		}
		

	}

	private class Changer implements Runnable {

		public void run() {
			while (alive) {
				 body.changeTexture();
				 GameScreen.getInstance().repaint((int)(body.getX() *
				 Stuff.getSize()),
				 (int)(body.getY() * Stuff.getSize()), Stuff.getSize(),
				 Stuff.getSize());
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					alive = false;
				}
			}
		}
	}
}
