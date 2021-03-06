package com.freedom.gameObjects.controllers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class LaserDetectorOr extends ButtonOr {

	public LaserDetectorOr() {
		super(true);
		this.textureRed = this.textureGreen = this.textureBlue = textureOff;
		nativeLevel = GameField.getInstance().getCurrentLevelId();
	}

	@Override
	public void touch(Stuff toucher) {
		if (!sendingSignal) {
			GameField.getInstance().getTicker().addActionListener(sender);
			sendingSignal = true;
		}
		switch (toucher.getColor()) {
		case RED:
			this.textureRed = this.textureGreen = this.textureBlue = texturesOn[1];
			repaintSelf();
			return;

		case GREEN:
			this.textureRed = this.textureGreen = this.textureBlue = texturesOn[2];
			repaintSelf();
			return;

		case BLUE:
			this.textureRed = this.textureGreen = this.textureBlue = texturesOn[3];
			repaintSelf();
			return;

		}
	}

	@Override
	public void untouch(Stuff toucher) {
		breaker.setToucher(toucher);
		inertion.restart();
	}

	public void realUntouch(Stuff toucher) {
		if (!GameField.getInstance().active
				|| !(nativeLevel == GameField.getInstance().getCurrentLevelId())) {
			inertion.stop();
			return;
		}

		inertion.stop();
		GameField.getInstance().getTicker().removeActionListener(sender);
		sendingSignal = false;
		for (int i = 0; i < controlledCellsAmount; i++) {
			GameField.getInstance().getCells()[controlledCellsList[i][0]][controlledCellsList[i][1]]
					.useOff();
		}
		this.textureRed = this.textureGreen = this.textureBlue = textureOff;
		repaintSelf();
	}

	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class",
				"com.freedom.gameObjects.controllers.LaserDetectorOr");
	}

	private static Image textureOff;
	protected static Image[] texturesOn = new Image[4];
	private InertedCircuitBreaker breaker = new InertedCircuitBreaker();
	private Timer inertion = new Timer(500, breaker);
	protected SignalOnSender sender = new SignalOnSender();
	private boolean sendingSignal;
	private int nativeLevel;
	

	static {
		try {
			textureOff = ImageIO
					.read(new File("Resource/Textures/LaserDetector/0.png"))
					.getScaledInstance(getSize(), getSize(), Image.SCALE_SMOOTH);
			for (int i = 1; i <= 3; i++) {
				texturesOn[i] = ImageIO.read(
						new File("Resource/Textures/LaserDetector/" + i
								+ ".png")).getScaledInstance(getSize(),
						getSize(), Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			logger.warning("Laser detector texture was corrupted");
		}
	}

	

	@Override
	public boolean absorbs(Stuff element) {
		if (element.getColour() != this.getColour())
			return true;
		else
			return false;
	}

	@Override
	public boolean reflects(Stuff element) {
		return !this.absorbs(element);
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