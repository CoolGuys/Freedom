package com.freedom.gameObjects;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LevelExit extends Stuff{
	
	private int nextLevelID;

	
	public LevelExit()
	{
		super(true, false,0,0);
	
		try {
			texture = ImageIO.read(new File("Resource/Textures/LevelExit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void robotOn(){
		GameField.getInstance().nextlvl(GameField.getInstance().getThisLevelID(), nextLevelID);
	}
}
