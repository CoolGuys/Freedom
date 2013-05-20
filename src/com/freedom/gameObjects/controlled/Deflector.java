package com.freedom.gameObjects.controlled;

import java.awt.Graphics;
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

public class Deflector extends Laser {

	private static Image texture1;
	private int toDeflect;

	public Deflector()
	{
		super(true);
		textureRed = textureBlue=textureGreen=texture1;
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
	public boolean isCoolEnough(Stuff element) {
		if (this.getColor() == element.getColor())
			return true;
		else
			return false;
	}

	public void touch(Stuff element) {
		if (!isCoolEnough(element) || ifActive) {
			return;
		}

		LaserBeam buf = (LaserBeam) element;// /dangerous!!!!
		if (!buf.getSource().equals(this.beamHead.getSource())) {
			this.laserDamage = buf.getDamage() - 1;
			this.direction = Deflector.list.get(this.revolve(
					list.indexOf(buf.direction), this.toDeflect));
			super.useOn();
			GameScreen.getInstance().repaint();
		}
	}

	@Override
	public void untouch(Stuff toucher) {
		if (!isCoolEnough(toucher)) {
			return;
		}
		LaserBeam buf = (LaserBeam) toucher;
		if (!buf.getSource().equals(this.beamHead.getSource())) {
			breaker.setToucher(toucher);
			inertion.restart();
		}
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
				"com.freedom.gameObjects.controlled.Deflector");
	}

	@Override
	public void draw(Graphics g) {

		switch (getColor()) {
		case RED: {
			g.drawImage(textureRed, (int) (x * getSize()),
					(int) (y * getSize()), null);
			return;
		}
		case GREEN: {
			g.drawImage(textureGreen, (int) (x * getSize()),
					(int) (y * getSize()), null);
			return;
		}
		case BLUE:
			g.drawImage(textureBlue, (int) (x * getSize()),
					(int) (y * getSize()), null);

		}
	}

	private Image textureRed, textureGreen, textureBlue;
	private InertedCircuitBreaker breaker = new InertedCircuitBreaker();
	private Timer inertion = new Timer(200, breaker);
	private static Logger logger = Logger.getLogger("Laser");

	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/Deflector/NW.png"))
					.getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Logger message
			e.printStackTrace();
		}
		logger.setLevel(Level.WARNING);
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