package com.freedom.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.freedom.utilities.TextFieldScreenModel;

@SuppressWarnings("serial")
public class TextFieldScreen extends AbstractScreen {
	

private static Image background;

static {
	try {
		background = ImageIO.read(new File(
				"Resource/UtilityPictures/pauseScreenBackground.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	private TextFieldScreen()
	{
		logger.setLevel(Level.OFF);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());

		InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
		BackAction resume = new BackAction();
		ActionMap amap = this.getActionMap();
		amap.put("back", resume);
	}
	
	public static TextFieldScreen getInstance() {

		//logger.info("Giving INSTANCE");
		if(INSTANCE==null)
			return INSTANCE = new TextFieldScreen();
		else
			return INSTANCE;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		logger.info(""+this.getWidth());
		g.drawImage(background, 0, 0, this.getHeight(), this.getWidth(), null);
		g.drawImage(background, 0, 0, this.getHeight(), this.getWidth(), null);

		textFieldScreenModel.draw(g);
	}
	
	public void prepareModel() {

		logger.info("Preparing");
		//textFieldScreenModel.addEntries();

	}
	
	public void deactivateModel() {
		textFieldScreenModel.deactivate();
	}
	
	private TextFieldScreenModel textFieldScreenModel = TextFieldScreenModel.getInstance();
	private static TextFieldScreen INSTANCE;
	private static Logger logger = Logger.getLogger("TextFieldScreen");
	
	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.swapScreens(LevelChoiceScreen.getInstance(), INSTANCE);
			}
		
	} 
}
