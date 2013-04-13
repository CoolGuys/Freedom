package com.freedom.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import com.freedom.gameObjects.GameField;
import com.freedom.utilities.AbstractScreen;
import com.freedom.utilities.GAction;
import com.freedom.utilities.PauseScreenModel;

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
		if(INSTANCE==null)
			return INSTANCE = new PauseScreen();
		else
			return INSTANCE;
	}
	
	public void prepareModel() {
		pauseScreenModel.addButtons();
	}

	@Override
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
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

			String s = pauseScreenModel.reactToClick(e.getPoint());
			if (!s.equals("NothingHappened")) {
				GAction m;
				try {
					m = (GAction) Class.forName(s).newInstance();
					m.performAction();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		public void mouseMoved(MouseEvent e) {
			pauseScreenModel.reactToRollOver(e.getPoint());
		}
	}
	
	public static class QuitAction extends GAction {
		public void performAction() {
			GameScreen.getInstance().deactivateModel();
			ScreensHolder.swapScreens(StartScreen.getInstance(),
					INSTANCE);
			ScreensHolder.getInstance().removeScreen(GameScreen.getInstance());
		}
	}
	
	public static class ResumeAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			GameScreen.getInstance().activateModel();
			GameScreen.getInstance().requestFocusInWindow();
			ScreensHolder.getInstance().removeScreen(getInstance());
		}
	}

	public static class SaveLevelAction extends GAction {
		public void performAction() {
			GameField.getInstance().saveLevel("Levels/LevelSaveTest.lvl", 1);
		}
	}
}
