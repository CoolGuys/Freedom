package com.freedom.gameObjects.controllers;

import java.io.File;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.StepListener;
import com.freedom.utilities.game.SoundEngine;

public class StepPlay extends StepListener{
	boolean used=false;
	
	@Override
	public void loadToFile(Element obj) {
		obj.setAttribute("class", "com.freedom.gameObjects.controllers.StepPlay");
	}
	
	@Override
	public void robotCome(){
		if(!used){
			used = true;
			File f = new File("Resource/Sound/Alert.wav");
			if (f.exists()) {
				SoundEngine.playClip(f, -5, -10);
			} else {
				//System.out.println("File ne naiden");
			}
			stopListening();
		}
	}
}
