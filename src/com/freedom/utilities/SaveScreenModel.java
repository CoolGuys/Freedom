package com.freedom.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import com.freedom.gameObjects.GameField;
import com.freedom.view.LoadScreen;
import com.freedom.view.LoadingScreen;
import com.freedom.view.PauseScreen;
import com.freedom.view.ScreensHolder;
import com.freedom.view.SaveScreen;

public class SaveScreenModel {


private static Image background;

static {
	try {
		background = ImageIO.read(new File(
				"Resource/UtilityPictures/pauseScreenBackground.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	
	private SaveScreenModel()
	{
		logger.setLevel(Level.ALL);
	}

	public static SaveScreenModel getInstance() {

		if (INSTANCE == null)
			return INSTANCE = new SaveScreenModel();
		else
			return INSTANCE;
	}

	public void setDescriptor(String desc) {
		this.descriptor = desc;
	}

	public void addEntries() {
		descriptionLabel = new GLabel(descriptor, 1, "center");
		tf.setSize(400, (int) (SaveScreen.getInstance().getHeight() / 13f));
		tf.setLocation(
				SaveScreen.getInstance().getWidth() / 2 - tf.getWidth()
						/ 2, descriptionLabel.positionY + 2 * textSize);
		if (!ready) {
			SaveScreen.getInstance().add(tf);
			tf.setFont(textFont);
			tf.addActionListener(l);
			ready = true;
		}
		if(descriptor.equals("Enter Save Name")) {
			File f = new File(sourcePack);
			String s = f.toPath().getFileName().toString();
			tf.setText(s.substring(0, s.length()-4));
		}
	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, SaveScreen.getInstance().getWidth(),
				SaveScreen.getInstance().getHeight(), null);
		descriptionLabel.draw(g);
		tf.requestFocusInWindow();
	}

	public void deactivate() {
		tf.setText("");
	}

	public void setSourcePack(String sourcePack) {
		this.sourcePack = sourcePack;
	}

	private GLabel descriptionLabel;
	private JTextField tf = new JTextField();
	private String descriptor;
	private String sourcePack;

	private TextFieldListener l = new TextFieldListener();
	private static SaveScreenModel INSTANCE;
	private Logger logger = Logger.getLogger("LoadingScreenModel");
	public int textSize = LoadingScreen.getInstance().getHeight() / 20;
	private Font textFont = new Font("Monospaced", Font.PLAIN, textSize);
	private boolean ready;

	private class GLabel {
		/**
		 * Класс неинтерактивных элементов экрана, несущих на себе текст и
		 * больше ничего
		 * 
		 * @param text
		 *            определяет отобоажаемую на метке информацию
		 * @param line
		 *            определяет уровень
		 * @param disposition
		 *            определяет стобец местоположения -центр "center" -левый
		 *            нижний угол "left bottom corner"
		 */
		public GLabel(String text, int line, String disposition)
		{
			if (disposition.equals("left bottom corner")) {
				this.text = text;
				this.positionX = textSize;
				this.positionY = LoadingScreen.getInstance().getHeight()
						* (12 + line) / 15;
			} else {
				this.text = text;
				Graphics2D g2 = (Graphics2D) ScreensHolder.getInstance()
						.getCurrentScreen().getGraphics();

				FontRenderContext context = g2.getFontRenderContext();
				Rectangle2D bounds = textFont.getStringBounds(text, context);
				this.positionX = (int) (LoadingScreen.getInstance().getWidth() / 2 - bounds
						.getWidth() / 2);
				this.positionY = (int) (LoadingScreen.getInstance().getHeight() * (1f / 3f + 1f / 14f * line));
			}

		}

		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g2.setFont(textFont);
			g2.setColor(Color.WHITE);
			g2.drawString(text, positionX, positionY);
		}

		private String text;
		public int positionX, positionY;
	}

	public class TextFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Path dir = Paths.get("Saves");
			DirectoryStream<Path> stream;
			try {
				if (tf.getText().equals("")
						|| tf.getText().equals("Not Valid")
						|| (lastName == null && tf.getText().equals(
								"File exists. Press Enter to overwrite."))) {
					tf.setText("    Not Valid   ");
					tf.setSize(calculateTextSize("####Not Valid####"), (int) (SaveScreen.getInstance()
							.getHeight() / 13f));
					tf.setLocation(SaveScreen.getInstance().getWidth() / 2
							- tf.getWidth() / 2, descriptionLabel.positionY + 2
							* textSize);
					return;
				}
				if (tf.getText().equals(
						"File exists. Press Enter to overwrite.")) {
					if (descriptor.equals("Choose Initial Save File")) {
						File src = new File(sourcePack);
						File dst = new File("Saves/" + lastName + ".lvl");
						Files.delete(dst.toPath());
						Files.copy(src.toPath(), dst.toPath());
						GameField.getInstance().setPathToSave(
								"Saves/" + lastName + ".lvl");
						GameField.getInstance().loadNewLevel(
								"Saves/" + lastName + ".lvl");
						return;
					} else {
						GameField.getInstance().setPathToSave(
								"Saves/" + lastName + ".lvl");
						GameField.getInstance().saveCurrentLevelToPackage();
						tf.setText("Saved");
						tf.setSize(calculateTextSize("Saved"), tf.getHeight());
						tf.setLocation(SaveScreen.getInstance().getWidth()
								/ 2 - tf.getWidth() / 2,
								descriptionLabel.positionY + 2 * textSize);
					}
				} else {
					stream = Files.newDirectoryStream(dir);
					for (Path file : stream) {
					//	logger.info(stream.toString());

						if (file.getFileName().toString()
								.equals(tf.getText() + ".lvl")) {

							lastName = tf.getText();
							tf.setSize(
									calculateTextSize("File exists. Press Enter to overwrite."),
									tf.getHeight());
							tf.setLocation(SaveScreen.getInstance()
									.getWidth() / 2 - tf.getWidth() / 2,
									descriptionLabel.positionY + 2 * textSize);
							tf.setText("File exists. Press Enter to overwrite.");
							return;
						}
					}
					if (descriptor.equals("Choose Initial Save File")) {
						File src = new File(sourcePack);
						File dst = new File("Saves/" + tf.getText() + ".lvl");
						Files.copy(src.toPath(), dst.toPath());
						GameField.getInstance().setPathToSave(
								"Saves/" + tf.getText() + ".lvl");
						GameField.getInstance().loadNewLevel(
								"Saves/" + tf.getText() + ".lvl");
						return;
					} else {

						GameField.getInstance().setPathToSave(
								"Saves/" + lastName + ".lvl");
						GameField.getInstance().saveCurrentLevelToPackage();

						tf.setText("Saved");
						tf.setSize(calculateTextSize("Saved"), tf.getHeight());
						tf.setLocation(SaveScreen.getInstance().getWidth()
								/ 2 - tf.getWidth() / 2,
								descriptionLabel.positionY + 2 * textSize);

					}
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private int calculateTextSize(String text) {
			Graphics2D g2 = (Graphics2D) SaveScreen.getInstance()
					.getGraphics();
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = textFont.getStringBounds(text, context);
			return (int) bounds.getWidth();

		}

		private String lastName;

	}

}
