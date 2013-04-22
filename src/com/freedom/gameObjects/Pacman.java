package com.freedom.gameObjects;

import com.freedom.utilities.PathFinder;

public class Pacman implements Runnable{
	private PathFinder finder;
	private int stepRate;

	/**
	 * 
	 * @param rate
	 *            Number of millis between Pacmans steps
	 */
	public Pacman(int rate) {
		finder = new PathFinder();
		this.stepRate = rate;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
    		System.out.println("i="+i);
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Автоматически созданный блок catch
				e.printStackTrace();
			}
    	}
    }
}
