package com.freedom.core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundEngine {
	static void playClip(File clipFile) {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem
					.getAudioInputStream(clipFile);
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
