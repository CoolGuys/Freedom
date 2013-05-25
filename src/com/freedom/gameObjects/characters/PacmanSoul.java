package com.freedom.gameObjects.characters;

import java.util.logging.Logger;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.utilities.game.PathFinder;
import com.freedom.view.GameScreen;

public class PacmanSoul implements Runnable {
	private int stepRate;
	private int destinationX;
	private int destinationY;
	private PacmanBody body;
	private PathFinder finder;
	private int widh;
	boolean alive;

	/**
	 * 
	 * @param rate
	 *            период двигания в мс
	 * @param Pack
	 *            тот кого нужно двигать
	 */
	public PacmanSoul(int rate, PacmanBody Pack, int wid) {
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
		GameField.otherThreads.execute(new Changer());
	}

	Logger gleblo = Logger.getLogger("soul");

	public void InHell() {
		this.alive = false;
		gleblo.info("weed");
	}

	@Override
	public void run() {
		Thread.currentThread().setName("Pacman Soul " + body);
		while (alive && !Thread.currentThread().isInterrupted()) {

			try {
				Thread.sleep(this.stepRate);

				int x = body.getX();
				int y = body.getY();
				boolean b = (GameField.getInstance().getRobot() == null);

				if (!b && GameField.getInstance().active) {
					this.destinationX = GameField.getInstance().getRobot()
							.getX();
					this.destinationY = GameField.getInstance().getRobot()
							.getY();
					String dir = finder.find(x, y, destinationX, destinationY,
							widh);
					if (!dir.equals("0")) {
						if (dir.length() > 1) {
							body.move1((String) dir.subSequence(0, 1));
						} else {
							GameField.getInstance().getRobot().punch(1);
						}
					}
				} else {
					this.destinationX = y;
					this.destinationY = x;
					this.alive = true;
				}
			} catch (InterruptedException e) {
				this.alive = false;
			}
		}

	}

	private class Changer implements Runnable {

		@Override
		public void run() {
			Thread.currentThread().setName("Pacman texture changer " + body);
			while (alive) {
				if (GameField.getInstance().active) {
					body.changeTexture();
					GameScreen.getInstance().repaint(
							body.getX() * Stuff.getSize(),
							body.getY() * Stuff.getSize(), Stuff.getSize(),
							Stuff.getSize());

				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					alive = false;
				}
			}
		}
	}
}
