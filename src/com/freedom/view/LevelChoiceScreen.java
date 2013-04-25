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

import com.freedom.utilities.LevelChoiceScreenModel;
import com.freedom.utilities.GAction;


//comment
@SuppressWarnings("serial")
public class LevelChoiceScreen extends AbstractScreen {
	private LevelChoiceScreen()
	{
		logger.setLevel(Level.OFF);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		
		

		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
		BackAction resume = new BackAction();
		ActionMap amap = this.getActionMap();
		amap.put("back", resume);
	}
	
	public static LevelChoiceScreen getInstance() {
		if(INSTANCE==null)
			return INSTANCE = new LevelChoiceScreen();
		else
			return INSTANCE;
	}
	

	@Override
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
		levelChoiceScreenModel.draw(g);
	}

	public void activateModel() {
		levelChoiceScreenModel.activate();
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}
	
	public void deactivateModel() {
		levelChoiceScreenModel.deactivate();
		this.removeMouseListener(l);
		this.addMouseMotionListener(l);
	}
	
	private Logger logger = Logger.getLogger("PauseScreen");
	private LevelChoiceScreenModel levelChoiceScreenModel = LevelChoiceScreenModel.getInstance();
	private static LevelChoiceScreen INSTANCE;
	
	private MouseHandler l = new MouseHandler();
	
	
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			levelChoiceScreenModel.reactToClick(e.getPoint());
		}
		public void mouseMoved(MouseEvent e) {
			levelChoiceScreenModel.reactToRollOver(e.getPoint());
		}
	}
	
	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.swapScreens(StartScreen.getInstance(), INSTANCE);
		}
		
	}
}
