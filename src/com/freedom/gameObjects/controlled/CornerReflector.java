package com.freedom.gameObjects.controlled;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.view.GameScreen;

public class CornerReflector extends Laser {

	private static Image texture1;
	private int toDeflect;

	public CornerReflector() {
		super(true);
		textureRed = texture1;
		this.beamHead = new LaserBeam("N", this.getX(), this.getY(), 6);
	}

	/*
	 * полагаем здесь, что он поворачивает свой цвет на 1 квант угла
	 */

	// УПЧК!!!
	private static ArrayList<String> list = new ArrayList<String>();

	private int revolve(int revolveFrom, int toRevolve) {
		int buf;
		buf = revolveFrom + toRevolve;

		if (buf > 7)
			return (buf - 8);
		return buf;
	}

	/*
	 * private boolean getDirection(String dir){ int bufEntry =
	 * list.indexOf(dir); int bufMine = list.indexOf(this.direction);
	 * 
	 * if(bufEntry == this.revolve(bufMine, 4)) return true;
	 * 
	 * if(bufEntry == this.revolve(bufMine, 7)){ this.direction =
	 * list.get(this.revolve(bufEntry, 1)); return true; }
	 * 
	 * if(bufEntry == this.revolve(bufMine, -7)){ this.direction =
	 * list.get(this.revolve(bufEntry, -1)); return true; } return false; }
	 */
	//

	public boolean useOn() {
		return false;
	}

	public boolean useOff() {
		return false;
	}

	@Override
	public boolean ifCoolEnough(Stuff element) {
		if (this.getColor() == element.getColor())
			return true;
		else
			return false;
	}

	public void touch(Stuff element) {
		if (!ifCoolEnough(element) || ifActive) {
			return;
		}

		LaserBeam buf = (LaserBeam) element;// /dangerous!!!!
		this.laserDamage = buf.getDamage() - 1;
		if (!buf.getSource().equals(this.beamHead.getSource())) {
			this.direction = CornerReflector.list.get(this.revolve(
					list.indexOf(buf.direction), this.toDeflect));
			super.useOn();
			GameScreen.getInstance().repaint();
		}
	}

	@Override
	public void untouch(Stuff toucher) {
		if (!ifCoolEnough(toucher)) {
			return;
		}
		breaker.setToucher(toucher);
		inertion.restart();
	}

	public void realUntouch(Stuff element) {

		LaserBeam buf;
		try {
			buf = (LaserBeam) element;
			if (buf.getSource().equals(this.beamHead.getSource()))
				return;
			inertion.stop();

			super.useOff();
			GameScreen.getInstance().repaint();
		} catch (ClassCastException e) {
			// TODO Logger message
		}

	}

	@Override
	public void interact(Stuff interactor) {

		super.useOff();
		this.toDeflect++;
		if (this.toDeflect > 7)
			this.toDeflect = this.toDeflect - 7;
	}

	public void readLvlFile(Element obj) {
		this.x = Integer.parseInt(obj.getAttribute("x"));
		this.y = Integer.parseInt(obj.getAttribute("y"));
		setColour(obj.getAttribute("color"));
		this.toDeflect = Integer.parseInt(obj.getAttribute("toDeflect"));
		beamHead.setSource(this);
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("toDeflect", toDeflect + "");
		obj.setAttribute("class",
				"com.freedom.gameObjects.controlled.CornerReflector");
	}

	private InertedCircuitBreaker breaker = new InertedCircuitBreaker();
	private Timer inertion = new Timer(500, breaker);
	private static Logger logger = Logger.getLogger("Laser");

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Tile2.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Logger message
			e.printStackTrace();
		}
		logger.setLevel(Level.WARNING);
		texture1 = null;
		list.add("N");
		list.add("NE");
		list.add("E");
		list.add("SE");
		list.add("S");
		list.add("SW");
		list.add("W");
		list.add("NW");
	}

	private class InertedCircuitBreaker implements ActionListener {
		public void setToucher(Stuff c) {
			toucher = c;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			realUntouch(toucher);
		}

		private Stuff toucher;
	}
}