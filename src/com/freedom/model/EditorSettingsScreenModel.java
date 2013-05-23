package com.freedom.model;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;

import com.freedom.utilities.game.Loader;
import com.freedom.utilities.interfai.GAction;
import com.freedom.utilities.interfai.GButtonLite;
import com.freedom.utilities.interfai.GLabel;
import com.freedom.utilities.interfai.GLabel.Alignment;
import com.freedom.view.EditorScreen;
import com.freedom.view.EditorSettingsScreen;
import com.freedom.view.LoadingScreen;
import com.freedom.view.ScreensHolder;

public class EditorSettingsScreenModel {
	private EditorSettingsScreenModel()
	{
		logger.setLevel(Level.WARNING);
	}

	public static EditorSettingsScreenModel getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new EditorSettingsScreenModel();
		else
			return INSTANCE;
	}

	public void addEntries() {
		levelDimensionXField.setSize(200, (int) (ScreensHolder.getInstance()
				.getHeight() / 20f));
		levelDimensionXField.setLocation(ScreensHolder.getInstance().getWidth() / 2
				- levelDimensionXField.getWidth() / 2, descriptionLabel.getY()
				+ textSize);
		levelDimensionYField.setSize(200, (int) (ScreensHolder.getInstance()
				.getHeight() / 20f));
		levelDimensionYField.setLocation(ScreensHolder.getInstance().getWidth() / 2
				- levelDimensionYField.getWidth() / 2, descriptionLabel.getY() + 2
				* textSize);
		levelIDField.setSize(200, (int) (ScreensHolder.getInstance()
				.getHeight() / 20f));
		levelIDField.setLocation(ScreensHolder.getInstance().getWidth() / 2
				- levelIDField.getWidth() / 2, descriptionLabel.getY() + 3
				* textSize);

		levelDimensionXField.setText("10");
		levelDimensionYField.setText("10");
		levelIDField.setText("1");
		if (!ready) {
			EditorSettingsScreen.getInstance().add(levelDimensionXField);
			levelDimensionXField.setFont(textFont);
			EditorSettingsScreen.getInstance().add(levelDimensionYField);
			levelDimensionYField.setFont(textFont);
			EditorSettingsScreen.getInstance().add(levelIDField);
			levelIDField.setFont(textFont);
			ready = true;
		}
	}

	public void draw(Graphics g) {
		descriptionLabel.draw(g);
		doneButton.draw(g);
		levelDimensionXField.requestFocusInWindow();
	}

	private int levelDimensionX = 10, levelDimensionY = 10, levelID=1;
	public int textSize = LoadingScreen.getInstance().getHeight() / 20;
	private Font textFont = new Font("Monospaced", Font.PLAIN, textSize);
	
	
	private GButtonLite doneButton = new GButtonLite("Done", 1,
			new CreateLevel(), textFont);
	private GLabel descriptionLabel = new GLabel(
			"Enter dimensions and ID of the new level", -2, Alignment.CENTER, textFont);
	private JFormattedTextField levelDimensionXField = new JFormattedTextField(
			NumberFormat.INTEGER_FIELD);
	private JFormattedTextField levelDimensionYField = new JFormattedTextField(
			NumberFormat.INTEGER_FIELD);
	private JFormattedTextField levelIDField = new JFormattedTextField(
			NumberFormat.INTEGER_FIELD);
	
	
	private static Logger logger = Logger
			.getLogger("EditorSettingsScreenModel");
	private static EditorSettingsScreenModel INSTANCE;
	private boolean ready;

	private class CreateLevel extends GAction {

		@Override
		public void performAction() {
			try {
				levelDimensionX = Integer.parseInt(levelDimensionXField.getText());
			} catch (NumberFormatException e) {
				levelDimensionXField.setText("0");
			}
			try {
				levelDimensionY = Integer.parseInt(levelDimensionYField.getText());
			} catch (NumberFormatException e) {
				levelDimensionYField.setText("0");
			}
			try {
				levelID = Integer.parseInt(levelIDField.getText());
			} catch (NumberFormatException e) {
				levelIDField.setText("1");
			}
			ScreensHolder.getInstance().swapScreens(
					LoadingScreen.getInstance(),
					EditorSettingsScreen.getInstance());
			try {
				Files.delete((new File("TmpSave")).toPath());
				Files.createFile((new File("TmpSave").toPath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Loader.createNewField(levelDimensionX, levelDimensionY, true, "TmpSave", levelID);
			GameField.getInstance().loadLevel("TmpSave");
			ScreensHolder.getInstance().swapScreens(EditorScreen.getInstance(),
					LoadingScreen.getInstance());
		}
	}

	public void reactToClick(Point point) {
		doneButton.checkIfPressed(point);
	}

	public void reactToRollOver(Point point) {
		if (doneButton.checkRollOver(point))
			EditorSettingsScreen.getInstance().repaint();
	}

}
