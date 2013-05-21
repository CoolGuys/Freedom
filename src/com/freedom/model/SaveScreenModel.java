package com.freedom.model;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import com.freedom.utilities.interfai.GLabel;
import com.freedom.utilities.interfai.GLabel.Alignment;
import com.freedom.view.LoadingScreen;
import com.freedom.view.SaveScreen;

public class SaveScreenModel {

	private SaveScreenModel()
	{
		logger.setLevel(Level.WARNING);
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
		descriptionLabel = new GLabel(descriptor, 1, Alignment.CENTER, textFont);
		interactionLabel = new GLabel("", 2, Alignment.CENTER, textFont);
		textField.setSize(400, (int) (SaveScreen.getInstance().getHeight() / 13f));
		textField.setLocation(SaveScreen.getInstance().getWidth() / 2 - textField.getWidth()
				/ 2, descriptionLabel.getY() + 2 * textSize);
		
		if (!ready) {
			SaveScreen.getInstance().add(textField);
			textField.setFont(textFont);
			textField.addActionListener(l);
			ready = true;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, SaveScreen.getInstance().getWidth(),
				SaveScreen.getInstance().getHeight(), null);
		descriptionLabel.draw(g);
		interactionLabel.draw(g);
		textField.requestFocusInWindow();
	}

	public void deactivate() {
		textField.setText("");
	}

	public void setSourcePack(String sourcePack) {
		this.sourcePack = sourcePack;
	}

	private GLabel descriptionLabel;
	private GLabel interactionLabel;
	private JTextField textField = new JTextField();
	private String descriptor;
	private String sourcePack;

	private TextFieldListener l = new TextFieldListener();
	private static SaveScreenModel INSTANCE;
	private Logger logger = Logger.getLogger("LoadingScreenModel");
	public int textSize = LoadingScreen.getInstance().getHeight() / 20;
	private Font textFont = new Font("Monospaced", Font.PLAIN, textSize);
	private boolean ready;

	
	private static Image background;

	static {
		try {
			background = ImageIO.read(new File(
					"Resource/UtilityPictures/pauseScreenBackground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public enum State {
		NORMAL, OVEWRITING
	}

	public class TextFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (textField.getText().equals("")) {
				state = State.NORMAL;
				return;
			}

			switch (state) {

			case OVEWRITING:
				if (textField.getText().equals(lastName)) {
					GameField.getInstance().setPathToSave(
							"Saves/" + lastName + ".lvl");
					GameField.getInstance().saveCurrentLevelToPackage();
					GameField.getInstance().setPathToSave("TmpSave");
					textField.setText("");

					interactionLabel.setText("Overwrote");
					state = State.NORMAL;
					return;
				} else {
					state = State.NORMAL;
				}

			case NORMAL:
				try {
					File src = new File(sourcePack);
					File dst = new File("Saves/" + textField.getText() + ".lvl");
					Files.copy(src.toPath(), dst.toPath());
				} catch (FileAlreadyExistsException exc) {
					interactionLabel
							.setText("File exists. Press enter to overwrite");
					state = State.OVEWRITING;
					lastName = textField.getText();
					return;
				} catch (IOException eio) {
					logger.warning("Something bad happened while saving \n"
							+ eio.getMessage());
					System.exit(-1);
				}
				GameField.getInstance().setPathToSave(
						"Saves/" + textField.getText() + ".lvl");
				GameField.getInstance().saveCurrentLevelToPackage();
				GameField.getInstance().setPathToSave("TmpSave");
				textField.setText("");
				interactionLabel.setText("Saved");

				return;
			}
		}

		private String lastName;
		private State state = State.NORMAL;

	}

}
