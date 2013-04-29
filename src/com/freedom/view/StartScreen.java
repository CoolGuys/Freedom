package com.freedom.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

<<<<<<< HEAD
import com.freedom.gameObjects.GameField;
import com.freedom.utilities.LevelChoiceScreenModel;
=======
>>>>>>> eea82d5996ffd291d973fef291cd68f23e18472a
import com.freedom.utilities.GAction;
import com.freedom.utilities.LoadScreenModel;
import com.freedom.utilities.StartScreenModel;
import com.freedom.utilities.TextFieldScreenModel;

@SuppressWarnings("serial")
public class StartScreen extends AbstractScreen {
	private StartScreen()
	{
		logger.setLevel(Level.OFF);

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
	
	public void prepareModel () {
		StartScreenModel.getInstance().addButtons();
	}

	public void activateModel() {
		startScreenModel.activate();
	}

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

			String s = startScreenModel.reactToClick(e.getPoint());
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
			startScreenModel.reactToRollOver(e.getPoint());
		}
	}

	public static class StartGameAction extends GAction {
		public void performAction() {	
			//GameField.getInstance().loadLevel(GameField.getInstance().getPathToSave());	

<<<<<<< HEAD
			TextFieldScreenModel.getInstance().setDescriptor("Choose Save Name");
			TextFieldScreenModel.getInstance().addEntries();	
			ScreensHolder.swapScreens(TextFieldScreen.getInstance(),
					StartScreen.getInstance());
=======
			/*TextFieldScreenModel.getInstance().setDescriptor("Choose Save Name");
			TextFieldScreenModel.getInstance().addEntries();	
			ScreensHolder.getInstance().swapScreens(TextFieldScreen.getInstance(),
					StartScreen.getInstance());*/
>>>>>>> eea82d5996ffd291d973fef291cd68f23e18472a

		}
	}
	
	public static class NewGameAction extends GAction {
		public void performAction() {
<<<<<<< HEAD
			LevelChoiceScreenModel.getInstance().setListedDirectory("Levels");

			LevelChoiceScreenModel.getInstance().newLevel=true;
			ScreensHolder.swapScreens(LevelChoiceScreen.getInstance(),
=======
			LoadScreenModel.getInstance().setListedDirectory("Levels");

			//LoadScreenModel.getInstance().addEntries();
			LoadScreenModel.getInstance().newLevel=true;
			ScreensHolder.getInstance().swapScreens(LoadScreen.getInstance(),
>>>>>>> eea82d5996ffd291d973fef291cd68f23e18472a
					StartScreen.getInstance());

		}
	}
	
	public static class LoadGameAction extends GAction {
		public void performAction() {
<<<<<<< HEAD
			LevelChoiceScreenModel.getInstance().newLevel=false;
			LevelChoiceScreenModel.getInstance().setListedDirectory("Saves");
			ScreensHolder.swapScreens(LevelChoiceScreen.getInstance(),
=======
			LoadScreenModel.getInstance().setListedDirectory("Saves");
			LoadScreenModel.getInstance().newLevel=false;
			LoadScreenModel.getInstance().addEntries();
			ScreensHolder.getInstance().swapScreens(LoadScreen.getInstance(),
>>>>>>> eea82d5996ffd291d973fef291cd68f23e18472a
					StartScreen.getInstance());

		}
	}

	public static class ExitGameAction extends GAction {
		public void performAction() {
			System.exit(0);
		}
	}
}
