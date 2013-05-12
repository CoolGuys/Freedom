package com.freedom.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.freedom.model.PauseScreenModel;

@SuppressWarnings("serial")
public class PauseScreen extends AbstractScreen {
	private PauseScreen()
	{
		logger.setLevel(Level.OFF);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());

		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "resume");
		ResumeAction resume = new ResumeAction();
		ActionMap amap = this.getActionMap();
		amap.put("resume", resume);
	}

	public static PauseScreen getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new PauseScreen();
		else
			return INSTANCE;
	}

	public void prepareModel() {
		pauseScreenModel.addButtons();
	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);
		pauseScreenModel.draw(g);
	}

	public void activateModel() {
		pauseScreenModel.activate();
	}

	public void deactivateModel() {
		pauseScreenModel.deactivate();
	}

	private Logger logger = Logger.getLogger("PauseScreen");
	private PauseScreenModel pauseScreenModel = PauseScreenModel.getInstance();
	private static PauseScreen INSTANCE;

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			pauseScreenModel.reactToClick(e.getPoint());
		}

		public void mouseMoved(MouseEvent e) {
			pauseScreenModel.reactToRollOver(e.getPoint());
		}
	}

	public static class ResumeAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.getInstance().removeScreen(getInstance());
		}
	}
}
