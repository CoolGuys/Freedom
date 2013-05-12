package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.model.StartScreenModel;

@SuppressWarnings("serial")
public class StartScreen extends AbstractScreen {
	private StartScreen()
	{
		logger.setLevel(Level.WARNING);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.setBackground(Color.BLACK);
		this.setOpaque(true);
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		startScreenModel.draw(g);
	}

	@Override
	public void prepareModel() {
		StartScreenModel.getInstance().addButtons();
	}

	@Override
	public void activateModel() {
		startScreenModel.activate();
	}

	@Override
	public void deactivateModel() {
		startScreenModel.deactivate();
	}
	
	public static StartScreen getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StartScreen();
			return INSTANCE;
		} else
			return INSTANCE;
	}

	private static StartScreenModel startScreenModel = StartScreenModel
			.getInstance();

	private static StartScreen INSTANCE;
	Logger logger = Logger.getLogger("StartScreen");

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			startScreenModel.reactToClick(e.getPoint());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			startScreenModel.reactToRollOver(e.getPoint());
		}
	}
}
