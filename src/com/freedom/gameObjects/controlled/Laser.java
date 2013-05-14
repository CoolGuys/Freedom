package com.freedom.gameObjects.controlled;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;
import com.freedom.view.GameScreen;

public class Laser extends Stuff {
	LaserBeam beamHead;
	boolean ifActive;
	String direction;
	private BeamSender sender;
	private ArrayList<Point> touchedCells = new ArrayList<Point>();

	public Laser()
	{
		super(false, false, true, false);
		ifActive = false;
		sender = new BeamSender();
	}

	public Point[] getTouchedCells() {
		return touchedCells.toArray(new Point[1]);
	}

	public void addTouchedCell(Point p) {
		touchedCells.add(p);
	}

	public void untouchTouched() {
		for (Point p : getTouchedCells())
			if (p != null)
				GameField.getInstance().cells[p.x][p.y].untouch(beamHead);
	}

	// метод для призмы
	public Laser(boolean forReflector)
	{
		super(false, false, false, true);
		ifActive = false;
		sender = new BeamSender();
	}

	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		// this.setColour("Green");
		this.direction = obj.getAttribute("direction");
		// System.out.println(""+getColour());
		chooseTexture();
	}

	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("direction", this.direction);
		obj.setAttribute("class", "com.freedom.gameObjects.controlled.Laser");
	}

	public void rebuidBeam() {
		untouchTouched();
		this.beamHead.deleteBeam();
		this.beamHead.buildBeam();
	}

	public boolean useOn() {
		if (this.ifActive)
			return false;
		else {
			this.ifActive = true;
			this.beamHead = new LaserBeam(this.direction, this.getX(),
					this.getY(), 8);
			this.beamHead.setSource(this);
			GameField.getInstance().getDeathTicker().addActionListener(sender);

			chooseTexture();
			return true;
		}
	}

	@Override
	public boolean useOff() {
		if (!this.ifActive)
			return false;
		else {
			untouchTouched();
			this.ifActive = false;
			GameField.getInstance().getDeathTicker()
					.removeActionListener(sender);

			this.beamHead.deleteBeam();
			chooseTexture();
			return true;
		}
	}

	@Override
	// вращаем по часовой
	public void interact(Stuff interactor) {
		boolean condition = this.useOff();
		if (this.direction.equals("N"))
			this.direction = "NE";
		else if (this.direction.equals("S"))
			this.direction = "SW";
		else if (this.direction.equals("E"))
			this.direction = "SE";
		else if (this.direction.equals("W"))
			this.direction = "NW";

		else if (this.direction.equals("NW"))
			this.direction = "N";
		else if (this.direction.equals("NE"))
			this.direction = "E";

		else if (this.direction.equals("SW"))
			this.direction = "W";

		else if (this.direction.equals("SE"))
			this.direction = "S";
		chooseTexture();
		GameScreen.getInstance().repaint();
		// System.out.println("Laser direction: "+ this.direction);
		if (condition)
			this.useOn();
	}

	private void chooseTexture() {
		if (ifActive) {
			if (this.direction.equals("N")) {
				textureRed = texturesOnN[1];
				textureGreen = texturesOnN[2];
				textureBlue = texturesOnN[3];
			} else if (this.direction.equals("S")) {
				textureRed = texturesOnS[1];
				textureGreen = texturesOnS[2];
				textureBlue = texturesOnS[3];
			} else if (this.direction.equals("E")) {
				textureRed = texturesOnE[1];
				textureGreen = texturesOnE[2];
				textureBlue = texturesOnE[3];
			} else if (this.direction.equals("W")) {
				textureRed = texturesOnW[1];
				textureGreen = texturesOnW[2];
				textureBlue = texturesOnW[3];
			} else if (this.direction.equals("NW")) {
				textureRed = texturesOnNW[1];
				textureGreen = texturesOnNW[2];
				textureBlue = texturesOnNW[3];
			} else if (this.direction.equals("NE")) {
				textureRed = texturesOnNE[1];
				textureGreen = texturesOnNE[2];
				textureBlue = texturesOnNE[3];
			} else if (this.direction.equals("SW")) {
				textureRed = texturesOnSW[1];
				textureGreen = texturesOnSW[2];
				textureBlue = texturesOnSW[3];
			} else if (this.direction.equals("SE")) {
				textureRed = texturesOnSE[1];
				textureGreen = texturesOnSE[2];
				textureBlue = texturesOnSE[3];
			}
		} else {
			if (this.direction.equals("N")) {
				textureRed = texturesOffN[1];
				textureGreen = texturesOffN[2];
				textureBlue = texturesOffN[3];
			} else if (this.direction.equals("S")) {
				textureRed = texturesOffS[1];
				textureGreen = texturesOffS[2];
				textureBlue = texturesOffS[3];
			} else if (this.direction.equals("E")) {
				textureRed = texturesOffE[1];
				textureGreen = texturesOffE[2];
				textureBlue = texturesOffE[3];
			} else if (this.direction.equals("W")) {
				textureRed = texturesOffW[1];
				textureGreen = texturesOffW[2];
				textureBlue = texturesOffW[3];
			} else if (this.direction.equals("NW")) {
				textureRed = texturesOffNW[1];
				textureGreen = texturesOffNW[2];
				textureBlue = texturesOffNW[3];
			} else if (this.direction.equals("NE")) {
				textureRed = texturesOffNE[1];
				textureGreen = texturesOffNE[2];
				textureBlue = texturesOffNE[3];
			} else if (this.direction.equals("SW")) {
				textureRed = texturesOffSW[1];
				textureGreen = texturesOffSW[2];
				textureBlue = texturesOffSW[3];
			} else if (this.direction.equals("SE")) {
				textureRed = texturesOffSE[1];
				textureGreen = texturesOffSE[2];
				textureBlue = texturesOffSE[3];
			}
		}
	}

	private class BeamSender implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rebuidBeam();
		}
	}

	private static BufferedImage[] texturesOffS = new BufferedImage[4];
	private static BufferedImage[] texturesOffE = new BufferedImage[4];
	private static BufferedImage[] texturesOffW = new BufferedImage[4];
	private static BufferedImage[] texturesOffN = new BufferedImage[4];
	private static BufferedImage[] texturesOffSW = new BufferedImage[4];
	private static BufferedImage[] texturesOffNW = new BufferedImage[4];
	private static BufferedImage[] texturesOffSE = new BufferedImage[4];
	private static BufferedImage[] texturesOffNE = new BufferedImage[4];
	private static BufferedImage[] texturesOnS = new BufferedImage[4];
	private static BufferedImage[] texturesOnW = new BufferedImage[4];
	private static BufferedImage[] texturesOnE = new BufferedImage[4];
	private static BufferedImage[] texturesOnN = new BufferedImage[4];
	private static BufferedImage[] texturesOnSW = new BufferedImage[4];
	private static BufferedImage[] texturesOnSE = new BufferedImage[4];
	private static BufferedImage[] texturesOnNW = new BufferedImage[4];
	private static BufferedImage[] texturesOnNE = new BufferedImage[4];

	private static Logger logger = Logger.getLogger("Laser");

	static {
		logger.setLevel(Level.ALL);
		try {
			initializeTextures();
			Image[] texturesOnStraight = new Image[4];
			Image[] texturesOnDiag = new Image[4];
			Image[] texturesOffStraight = new Image[4];
			Image[] texturesOffDiag = new Image[4];
			for (int i = 1; i <= 3; i++) {
				texturesOnStraight[i] = ImageIO.read(
						new File("Resource/Textures/Laser/Off" + i
								+ "Straight.png")).getScaledInstance(getSize(),
						getSize(), BufferedImage.SCALE_SMOOTH);
				texturesOffDiag[i] = ImageIO.read(
						new File("Resource/Textures/Laser/Off" + i
								+ "Diagonal.png")).getScaledInstance(getSize(),
						getSize(), BufferedImage.SCALE_SMOOTH);
				texturesOffStraight[i] = ImageIO.read(
						new File("Resource/Textures/Laser/Off" + i
								+ "Straight.png")).getScaledInstance(getSize(),
						getSize(), BufferedImage.SCALE_SMOOTH);
				texturesOnDiag[i] = ImageIO.read(
						new File("Resource/Textures/Laser/On" + i
								+ "Diagonal.png")).getScaledInstance(getSize(),
						getSize(), BufferedImage.SCALE_SMOOTH);

				texturesOffS[i].getGraphics().drawImage(texturesOffStraight[i],
						0, 0, null);
				texturesOnS[i].getGraphics().drawImage(texturesOffStraight[i],
						0, 0, null);
				texturesOffSW[i].getGraphics().drawImage(texturesOffDiag[i], 0,
						0, null);
				texturesOnSW[i].getGraphics().drawImage(texturesOnDiag[i], 0,
						0, null);

				double rotationRequired = Math.toRadians(90);
				int locationX = getSize() / 2;
				int locationY = getSize() / 2;
				AffineTransform tx = AffineTransform.getRotateInstance(
						rotationRequired, locationX, locationY);
				AffineTransformOp op = new AffineTransformOp(tx,
						AffineTransformOp.TYPE_BILINEAR);
				texturesOnW[i] = op.filter(texturesOnS[i], null);
				texturesOffW[i] = op.filter(texturesOffS[i], null);
				texturesOffNW[i] = op.filter(texturesOffSW[i], null);
				texturesOnNW[i] = op.filter(texturesOnSW[i], null);
				rotationRequired = Math.toRadians(180);
				tx = AffineTransform.getRotateInstance(rotationRequired,
						locationX, locationY);
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				texturesOnN[i] = op.filter(texturesOnS[i], null);
				texturesOffN[i] = op.filter(texturesOffS[i], null);
				texturesOffNE[i] = op.filter(texturesOffSW[i], null);
				texturesOnNE[i] = op.filter(texturesOnSW[i], null);
				rotationRequired = Math.toRadians(270);
				tx = AffineTransform.getRotateInstance(rotationRequired,
						locationX, locationY);
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				texturesOnE[i] = op.filter(texturesOnS[i], null);
				texturesOffE[i] = op.filter(texturesOffS[i], null);
				texturesOffSE[i] = op.filter(texturesOffSW[i], null);
				texturesOnSE[i] = op.filter(texturesOnSW[i], null);
			}
		} catch (IOException e) {
			logger.warning("Laser texture was corrupted or deleted");
			e.printStackTrace();
		}
	}

	private static void initializeTextures() {
		for (int i = 1; i <= 3; i++) {
			texturesOffE[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffW[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffS[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffN[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffNE[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffSE[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffNW[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOffSW[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);

			texturesOnE[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnS[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnW[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnN[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnSE[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnNE[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnNW[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
			texturesOnSW[i] = new BufferedImage(getSize(), getSize(),
					BufferedImage.TYPE_INT_ARGB);
		}

	}

}
