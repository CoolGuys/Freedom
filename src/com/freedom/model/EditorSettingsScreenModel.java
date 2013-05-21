package com.freedom.model;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private EditorSettingsScreenModel() {
		logger.setLevel(Level.WARNING);
	}

	public static EditorSettingsScreenModel getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new EditorSettingsScreenModel();
		else
			return INSTANCE;
	}

	public void addEntries() {
		descriptionLabel  = new GLabel(
				"Enter dimensions of new level", 1, Alignment.CENTER, textFont);
		dimensionXField.setSize(200, (int) (ScreensHolder.getInstance()
				.getHeight() / 13f));
		dimensionXField.setLocation(ScreensHolder.getInstance().getWidth() / 2
				- dimensionXField.getWidth() / 2, descriptionLabel.getY() + 2
				* textSize);
		dimensionYField.setSize(200, (int) (ScreensHolder.getInstance()
				.getHeight() / 13f));
		dimensionYField.setLocation(ScreensHolder.getInstance().getWidth() / 2
				- dimensionYField.getWidth() / 2, descriptionLabel.getY() + 4
				* textSize);

		dimensionXField.setText("10");
		dimensionYField.setText("10");
		if (!ready) {
			EditorSettingsScreen.getInstance().add(dimensionXField);
			dimensionXField.setFont(textFont);
			dimensionXField.addActionListener(x);
			EditorSettingsScreen.getInstance().add(dimensionYField);
			dimensionYField.setFont(textFont);
			dimensionYField.addActionListener(y);
			ready = true;
		}
	}

	public void draw(Graphics g) {
		descriptionLabel.draw(g);
		doneButton.draw(g);
		dimensionXField.requestFocusInWindow();
	}
	
	public void deactivate() {
		dimensionXField.setText("");
		dimensionYField.setText("");
	}

	private int levelX = 10, levelY = 10;
	public int textSize = LoadingScreen.getInstance().getHeight() / 20;
	private Font textFont = new Font("Monospaced", Font.PLAIN, textSize);
	private GButtonLite doneButton = new GButtonLite("Done", 5, new CreateLevel());
	private GLabel descriptionLabel;
	private JFormattedTextField dimensionXField = new JFormattedTextField(
			NumberFormat.INTEGER_FIELD);
	private JFormattedTextField dimensionYField = new JFormattedTextField(
			NumberFormat.INTEGER_FIELD);
	private static Logger logger = Logger
			.getLogger("EditorSettingsScreenModel");
	private static EditorSettingsScreenModel INSTANCE;
	private boolean ready;

	private XFieldListener x = new XFieldListener();
	private YFieldListener y = new YFieldListener();

	private class XFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			levelX = Integer.parseInt(dimensionXField.getText());
		}
	}

	private class YFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			levelY = Integer.parseInt(dimensionYField.getText());
		}
	}

	private class CreateLevel extends GAction {
		
		@Override
		public void performAction() {
			levelX = Integer.parseInt(dimensionXField.getText());
			levelY = Integer.parseInt(dimensionYField.getText());
			ScreensHolder.getInstance().swapScreens(LoadingScreen.getInstance(),
					EditorSettingsScreen.getInstance());
			Loader.createNewField(levelX, levelY, true, "TmpSave", 1);
			GameField.getInstance().loadLevel("TmpSave");
			ScreensHolder.getInstance().swapScreens(EditorScreen.getInstance(),
					LoadingScreen.getInstance());
		}
	}

	public void reactToClick(Point point) {
		doneButton.checkIfPressed(point);
	}

	public void reactToRollOver(Point point) {
		if(doneButton.checkRollOver(point))
			EditorSettingsScreen.getInstance().repaint();
		}

	

}
