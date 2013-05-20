package com.freedom.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.freedom.model.SaveScreenModel;

@SuppressWarnings("serial")
public class SaveScreen extends AbstractScreen {
	


	private SaveScreen()
	{
		logger.setLevel(Level.WARNING);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());

		InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
		BackAction resume = new BackAction();
		ActionMap amap = this.getActionMap();
		amap.put("back", resume);
	}
	
	public static SaveScreen getInstance() {

		//logger.info("Giving INSTANCE");
		if(INSTANCE==null)
			return INSTANCE = new SaveScreen();
		else
			return INSTANCE;
	}
	
	@Override
	public void paintComponent(Graphics g) {

		saveScreenModel.draw(g);
	}
	
	public void prepareModel() {

		logger.info("Preparing");
		//textFieldScreenModel.addEntries();

	}
	
	public void deactivateModel() {
		saveScreenModel.deactivate();
	}
	
	private SaveScreenModel saveScreenModel = SaveScreenModel.getInstance();
	private static SaveScreen INSTANCE;
	private static Logger logger = Logger.getLogger("TextFieldScreen");
	
	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
				ScreensHolder.getInstance().swapScreens(ScreensHolder.getInstance().getLastScreen(), INSTANCE);
			}
		
	} 
}
