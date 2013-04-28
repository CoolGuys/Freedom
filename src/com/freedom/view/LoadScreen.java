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

import com.freedom.utilities.LoadScreenModel;
import com.freedom.utilities.GAction;


//comment
@SuppressWarnings("serial")
public class LoadScreen extends AbstractScreen {
	private LoadScreen()
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
	
	public static LoadScreen getInstance() {
		if(INSTANCE==null)
			return INSTANCE = new LoadScreen();
		else
			return INSTANCE;
	}
	

	@Override
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
		loadScreenModel.draw(g);
	}

	public void activateModel() {
		loadScreenModel.activate();
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}
	
	public void deactivateModel() {
		loadScreenModel.deactivate();
		this.removeMouseListener(l);
		this.addMouseMotionListener(l);
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
	}
	
	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.getInstance().swapScreens(StartScreen.getInstance(), INSTANCE);
		}
		
	}
}
