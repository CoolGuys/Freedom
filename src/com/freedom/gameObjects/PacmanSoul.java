package com.freedom.gameObjects;

import com.freedom.utilities.MovementAnimator;
import com.freedom.utilities.PathFinder;
import com.freedom.view.GameScreen;
import com.freedom.view.ScreensHolder;

public class PacmanSoul implements Runnable{
	private int StepRate;
	private int dx;
	private int dy;
	private PacmanBody body;
	private PathFinder finder;
	private int widh=3;
	/**
	 * 
	 * @param rate период двигания в мс 
	 * @param Pack тот кого нужно двигать
	 */
	public PacmanSoul(int rate,PacmanBody Pack,int wid){
		this.StepRate=rate;
		this.body=Pack;
		this.finder=new PathFinder();
		this.widh=wid;
		this.dx=GameField.getInstance().getRobot().getX();
		this.dy=GameField.getInstance().getRobot().getY();
		(new Thread(new Changer())).start();
	}

	public void run() {
		while (true) {
			int x = body.getX();
			int y = body.getY();
			this.dx=GameField.getInstance().getRobot().getX();
			this.dy=GameField.getInstance().getRobot().getY();
			System.out.println("dx="+this.dx+"dy="+this.dy);
			if ((x != dx) || (y != dy)) {
				String dir = finder.find(x, y, dx, dy, widh);
				System.out.println("dir="+dir+" x="+x+" y="+y);
				try {
					/*
					 * убогий быдло код
					 */
					if (!dir.equals("0")) {
						/*Runnable r = new MovementAnimator<PacmanBody>(body,
								(String) dir.subSequence(0, 1));
						Thread t = new Thread(r);
						t.start();*/
						body.move1((String) dir.subSequence(0, 1));
						GameScreen.getInstance().repaint();
					}
				} catch (Exception e) {
					// System.out.println("Проблема у пакмана в душе. dir="+dir);
					e.printStackTrace();
				}
			}else {
				//System.out.println(((x != dx) || (y != dy))+" x="+ x + " y="+ y);
			}
			try {
				Thread.sleep(this.StepRate);
			} catch (InterruptedException e) {
				// TODO Автоматически созданный блок catch
				e.printStackTrace();
			}
		}
	}
	
	private class Changer implements Runnable{
		public Changer(){
			
		}
		public void run(){
			while(true){
				body.changeTexture();
				GameScreen.getInstance().repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Автоматически созданный блок catch
					e.printStackTrace();
				}
			}
		}
	}
}
