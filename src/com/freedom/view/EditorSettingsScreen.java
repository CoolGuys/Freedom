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

import com.freedom.model.EditorSettingsScreenModel;

@SuppressWarnings("serial")
public class EditorSettingsScreen extends AbstractScreen {
	private EditorSettingsScreen() {
		logger.setLevel(Level.WARNING);

		setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());

		InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
		BackAction resume = new BackAction();
		ActionMap amap = this.getActionMap();
		amap.put("back", resume);
		addMouseMotionListener(new MouseHandler());
		addMouseListener(new MouseHandler());
	}

	public static EditorSettingsScreen getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new EditorSettingsScreen();
		else
			return INSTANCE;
	}

	@Override
	public void paintComponent(Graphics g) {
		editorScreenModel.draw(g);
	}
	
	public void activateModel() {
		editorScreenModel.addEntries();
	}

	private EditorSettingsScreenModel editorScreenModel = EditorSettingsScreenModel.getInstance();
	private static EditorSettingsScreen INSTANCE;
	private static Logger logger = Logger.getLogger("TextFieldScreen");

	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.getInstance().swapScreens(
					ScreensHolder.getInstance().getLastScreen(), INSTANCE);
		}

	}
	
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			editorScreenModel.reactToClick(e.getPoint());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			editorScreenModel.reactToRollOver(e.getPoint());
		}
	}
}
