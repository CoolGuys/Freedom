package com.freedom.view;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.model.LoadingScreenModel;

@SuppressWarnings("serial")
public class LoadingScreen extends AbstractScreen {

	private LoadingScreen()
	{
		logger.setLevel(Level.OFF);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setBackground(Color.BLACK);
		this.setOpaque(true);
	}
	
	public static LoadingScreen getInstance() {

		//logger.info("Giving INSTANCE");
		if(INSTANCE==null)
			return INSTANCE = new LoadingScreen();
		else
			return INSTANCE;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	//	super.paintComponent(g);
		loadingScreenModel.draw(g);
	}
	
	public void prepareModel() {

		//logger.info("Preparing");
		loadingScreenModel.addButtons();

	}
	
	private LoadingScreenModel loadingScreenModel = LoadingScreenModel.getInstance();
	private static LoadingScreen INSTANCE;
	private static Logger logger = Logger.getLogger("LoadingScreen");

}
