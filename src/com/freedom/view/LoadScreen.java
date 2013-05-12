package com.freedom.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.freedom.model.LoadScreenModel;

//comment
@SuppressWarnings("serial")
public class LoadScreen extends AbstractScreen {
	private LoadScreen()
	{
		logger.setLevel(Level.ALL);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());

		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
		BackAction resume = new BackAction();
		ActionMap amap = this.getActionMap();
		amap.put("back", resume);
	}

	public static LoadScreen getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new LoadScreen();
		else
			return INSTANCE;
	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);
		loadScreenModel.draw(g);
	}

	public void activateModel() {
		loadScreenModel.activate();
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		this.addMouseWheelListener(l);
	}

	public void deactivateModel() {
		loadScreenModel.deactivate();
		this.removeMouseListener(l);
		this.removeMouseMotionListener(l);
		this.removeMouseWheelListener(l);
	}

	private Logger logger = Logger.getLogger("PauseScreen");
	private LoadScreenModel loadScreenModel = LoadScreenModel.getInstance();
	private static LoadScreen INSTANCE;

	private MouseHandler l = new MouseHandler();

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			loadScreenModel.reactToClick(e.getPoint());
		}

		public void mouseMoved(MouseEvent e) {
			loadScreenModel.reactToRollOver(e.getPoint());
		}

		public void mouseWheelMoved(MouseWheelEvent e) {
			int notches = e.getWheelRotation();
			logger.info(""+getY()+"||"+ -LoadScreen.getInstance().getHeight()/5);
			if (getY() < 0
					&& notches<0) {
				setLocation(0, getY() - notches * 50);
			} else if(getY() > -LoadScreen.getInstance().getHeight()/3
					&& notches>0) {
				setLocation(0, getY() - notches * 50);
			}
				
		}
	}

	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.getInstance().swapScreens(StartScreen.getInstance(),
					INSTANCE);
		}

	}
}
