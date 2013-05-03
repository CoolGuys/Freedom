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
	private int widh;
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
		GameField.otherThreads.execute(new Changer());
	}

	Logger gleblo = Logger.getLogger("soul");

	public void InHell() {
		this.alive = false;
		gleblo.info("weed");
	}

	public void run() {
		while (alive) {

			try {
				Thread.sleep(this.stepRate);
			} catch (InterruptedException e) {
				// TODO Автоматически созданный блок catch
				
			}
			int x = body.getX();
			int y = body.getY();
			boolean b = (GameField.getInstance().getRobot() == null);

			if (!b &&GameField.getInstance().active) {
				//System.out.println("fsd");
				this.destinationX = GameField.getInstance().getRobot().getX();
				this.destinationY = GameField.getInstance().getRobot().getY();
				//System.out.println("fsd");
				String dir = finder.find(x, y, destinationX, destinationY, widh);
				if (!dir.equals("0")) {
					//System.out.println(dir);
					if (dir.length() > 1) {
						try {
							body.move1((String) dir.subSequence(0, 1));
						} catch (Exception e) {
							//e.printStackTrace();
							//System.out.println("pacman не двигается");
						}
					} else {
						GameField.getInstance().getRobot().punch(1);
						//System.out.println(GameField.getInstance().getRobot()
								//.getLives());
					}
				}
			}else {
				this.destinationX = y;
				this.destinationY = x;
				this.alive=true;
				//System.out.println("Else");
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
