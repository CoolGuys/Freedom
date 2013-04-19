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

import com.freedom.utilities.AbstractScreen;
import com.freedom.utilities.ChoiceScreenModel;
import com.freedom.utilities.GAction;

@SuppressWarnings("serial")
public class ChoiceScreen extends AbstractScreen {
	private ChoiceScreen()
	{
		logger.setLevel(Level.OFF);

		this.setBounds(0, 0, ScreensHolder.getInstance().getWidth(),
				ScreensHolder.getInstance().getHeight());
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		

		InputMap imap = this.getInputMap(JComponent.WHEN_FOCUSED);
		imap.put(KeyStroke.getKeyStroke("ESCAPE"), "back");
		BackAction resume = new BackAction();
		ActionMap amap = this.getActionMap();
		amap.put("back", resume);
	}
	
	public static ChoiceScreen getInstance() {
		if(INSTANCE==null)
			return INSTANCE = new ChoiceScreen();
		else
			return INSTANCE;
	}
	
	public void prepareModel() {
	}

	@Override
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
		choiceScreenModel.draw(g);
	}

	public void activateModel() {
		choiceScreenModel.activate();
	}
	
	public void deactivateModel() {
		choiceScreenModel.deactivate();
	}
	
	private Logger logger = Logger.getLogger("PauseScreen");
	private ChoiceScreenModel choiceScreenModel = ChoiceScreenModel.getInstance();
	private static ChoiceScreen INSTANCE;
	
	
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			String s = choiceScreenModel.reactToClick(e.getPoint());
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
			choiceScreenModel.reactToRollOver(e.getPoint());
		}
	}
	
	private class BackAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			ScreensHolder.swapScreens(StartScreen.getInstance(), INSTANCE);
		}
		
	}
}
