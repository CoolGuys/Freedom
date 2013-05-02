package com.freedom.gameObjects;

import java.util.logging.Logger;

import com.freedom.utilities.PathFinder;
import com.freedom.view.GameScreen;

public class PacmanSoul implements Runnable{
	private int StepRate;
	private int dx;
	private int dy;
	private PacmanBody body;
	private PathFinder finder;
	private int widh=3;
	boolean alive;
	
	/**
	 * 
	 * @param rate
	 *            период двигания в мс
	 * @param Pack
	 *            тот кого нужно двигать
	 */
	public PacmanSoul(int rate, PacmanBody Pack, int wid) {
		this.StepRate = rate;
		this.body = Pack;
		this.finder = new PathFinder();
		this.widh = wid;
		this.alive=true;

		try {
			this.dx = GameField.getInstance().getRobot().getX();
			this.dy = GameField.getInstance().getRobot().getY();
		} catch (Exception e) {
			this.dx = body.getX();
			this.dy = body.getY();
		}
		GameField.getInstance().getThreads().execute(new Changer());
	}
	Logger gleblo = Logger.getLogger("soul");
	public void InHell() {
		this.alive=false;
		gleblo.info("weed");
	}
	public void run() {
		while (alive) {
			int x = body.getX();
			int y = body.getY();
			try {
				this.dx = GameField.getInstance().getRobot().getX();
				this.dy = GameField.getInstance().getRobot().getY();
				//System.out.println("вылтавы"+this.dx+" "+this.dy);
			} catch (Exception e) {
				this.dx = body.getX();
				this.dy = body.getY();
				//System.out.println("а робота то нету");
			}
			// System.out.println("dx="+this.dx+"dy="+this.dy);
			if ((x != dx) || (y != dy)) {
				String dir = "";
				dir = finder.find(x, y, dx, dy, widh);
				//System.out.println("lol "+dir);
				// System.out.println("dir="+dir+" x="+x+" y="+y);
				try {
					if (!dir.equals("0")) {
						if (dir.length() > 1) {
							try {
								body.move1((String) dir.subSequence(0, 1));
							} catch (Exception e) {

							}
						} else {
							GameField.getInstance().getRobot().punch(1);
//							System.out.println(GameField.getInstance().getRobot().getLives());
						}
						GameScreen.getInstance().repaint();
					}
				}catch (Exception e) {
					// System.out.println("Проблема у пакмана в душе. dir="+dir);
					e.printStackTrace();
				}
			}else {
				//System.out.println(((x != dx) || (y != dy))+" x="+ x + " y="+ y);
			}
			try {
				Thread.sleep(this.StepRate);
			} catch (InterruptedException e) {
				this.alive=false;				
			}
		}
	}
	
	private class Changer implements Runnable{
		public Changer(){
			
		}
		public void run(){
			while(alive){
				body.changeTexture();
				GameScreen.getInstance().repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Автоматически созданный блок catch
					alive=false;
				}
			}
		}
	}
}
