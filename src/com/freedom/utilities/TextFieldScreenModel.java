package com.freedom.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

import javax.swing.JTextField;

import com.freedom.gameObjects.GameField;
import com.freedom.view.LevelChoiceScreen;
import com.freedom.view.LoadingScreen;
import com.freedom.view.TextFieldScreen;

public class TextFieldScreenModel {

	private TextFieldScreenModel()
	{

		logger.setLevel(Level.ALL);
	}

	public static TextFieldScreenModel getInstance() {

		if (INSTANCE == null)
			return INSTANCE = new TextFieldScreenModel();
		else
			return INSTANCE;
	}

	public void setDescriptor(String desc) {
		this.descriptor = desc;
	}

	public void addEntries() {
		descriptionLabel = new GLabel(descriptor, 1, "center");
		tf=new JTextField();
		TextFieldScreen.getInstance().add(tf);
		ready=true;
		tf.setSize(400, (int) (TextFieldScreen.getInstance().getHeight() / 13f));
		tf.setLocation(
				TextFieldScreen.getInstance().getWidth() / 2 - tf.getWidth()
						/ 2, descriptionLabel.positionY + 2
						* descriptionLabel.textSize);
		tf.setFont(descriptionLabel.textFont);
		tf.addActionListener(l);
	}

	public void draw(Graphics g) {
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
	private JTextField tf;
	private String descriptor;
	private String sourcePack;
	private boolean ready;

	private TextFieldListener l = new TextFieldListener();
	private static TextFieldScreenModel INSTANCE;
	private Logger logger = Logger.getLogger("LoadingScreenModel");

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
				Graphics2D g2 = (Graphics2D) LevelChoiceScreen.getInstance()
						.getGraphics();

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

		public int textSize = LoadingScreen.getInstance().getHeight() / 20;
		private String text;
		private Font textFont = new Font("Monospaced", Font.PLAIN, textSize);
		public int positionX, positionY;
	}

	public class TextFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Path dir = Paths.get("Saves");
			DirectoryStream<Path> stream;
			try {
				if(tf.getText().equals("") || tf.getText().equals("Not Valid") || (lastName==null && tf.getText().equals(
						"File exists. Press Enter to overwrite."))) {
					tf.setText("Not Valid");
					return;
				}
				if (tf.getText().equals(
						"File exists. Press Enter to overwrite.")) {
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
					stream = Files.newDirectoryStream(dir);
					for (Path file : stream) {
						logger.info(file.getFileName().toString());

						if (file.getFileName().toString()
								.equals(tf.getText() + ".lvl")) {

							lastName = tf.getText();
							tf.setSize(900, tf.getHeight());
							tf.setLocation(TextFieldScreen.getInstance()
									.getWidth() / 2 - tf.getWidth() / 2,
									descriptionLabel.positionY + 2
											* descriptionLabel.textSize);
							tf.setText("File exists. Press Enter to overwrite.");
							return;
						}
						File src = new File(sourcePack);
						File dst = new File("Saves/" + tf.getText() + ".lvl");
						Files.copy(src.toPath(), dst.toPath());
						GameField.getInstance().setPathToSave(
								"Saves/" + tf.getText() + ".lvl");
						GameField.getInstance().loadNewLevel(
								"Saves/" + tf.getText() + ".lvl");
						return;
					}
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private String lastName;

	}

}
