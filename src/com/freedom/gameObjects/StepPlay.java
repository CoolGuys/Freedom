package com.freedom.gameObjects;

import java.io.File;

import org.w3c.dom.Element;

import com.freedom.utilities.SoundEngine;
import com.freedom.utilities.SoundEngine.SoundPlayer;

public class StepPlay extends StepListener{
	boolean used=false;
	
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));
		obj.setAttribute("class", "com.freedom.gameObjects.StepPlay");
	}
	
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