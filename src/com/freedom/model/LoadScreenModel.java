package com.freedom.model;

import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.utilities.interfai.GButtonLoaderLite;
import com.freedom.view.LoadScreen;
import com.freedom.view.ScreensHolder;

public class LoadScreenModel {

	private LoadScreenModel()
	{
		logger.setLevel(Level.WARNING);
	}

	public void addEntries() {
		logger.info("Adding Entries");
		int counter = 0;
		Path dir = Paths.get(listedDirectory);
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(dir);
			for (Path file : stream) {
				buttons[counter] = new GButtonLoaderLite(file.getFileName()
						.toString(), counter, file.toString());
				counter++;
			}
			if (counter > 10)
				LoadScreen
						.getInstance()
						.setSize(
								LoadScreen.getInstance().getWidth(),
								(int) (ScreensHolder.getInstance().getHeight() * (1 + 1f / 6f * counter)));
		} catch (IOException e) {
			createSavesDirectory();
		}

	}
	
	private void createSavesDirectory() {
		Path dir = Paths.get(listedDirectory);
		try {
			Files.createDirectory(dir);
		} catch (IOException e) {
			logger.warning("Something bad while creating initial or something else directory "+e.getLocalizedMessage());
			System.exit(-1);
		}
		
		addEntries();
	}

	public static LoadScreenModel getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new LoadScreenModel();
		else
			return INSTANCE;
	}

	public void activate() {
		addEntries();
	}

	public void deactivate() {
		buttons = new GButtonLoaderLite[100];
		LoadScreen.getInstance().setLocation(0, 0);
	}

	public void draw(Graphics g) {

		for (GButtonLoaderLite b : buttons)
			if (b != null)
				b.draw(g);
	}

	public String reactToClick(Point p) {
		logger.info(p.toString());
		for (GButtonLoaderLite b : buttons) {
			if (b != null)
				b.checkIfPressed(p);
		}
		return "NothingHappened";
	}

	public void setListedDirectory(String dir) {
		listedDirectory = dir;
	}

	public void reactToRollOver(Point point) {
		for (GButtonLoaderLite b : buttons) {
			if (b != null)
				if (b.checkRollOver(point))
					LoadScreen.getInstance().repaint();
		}
	}

	private GButtonLoaderLite[] buttons = new GButtonLoaderLite[100];
	private String listedDirectory;
	private static LoadScreenModel INSTANCE;
	public boolean newLevel;

	private Logger logger = Logger.getLogger("PauseScreenModel");

	
}
