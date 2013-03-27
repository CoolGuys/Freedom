package com.freedom.core;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

public class SoundEngine {

	public static void playClip(File clipFile) {
		Runnable player = new SoundPlayerThread(clipFile);
		
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.submit(player);
	}
	
	public static class SoundPlayerThread implements Runnable {
		
		public SoundPlayerThread(File soundFile) {
		this.soundFile = soundFile;
		}
		
		public void run() {
			logger.setLevel(Level.ALL);
			logger.info("Entering RUN: "+this.toString());
			try {
				AudioListener listener = new AudioListener();
				AudioInputStream input = AudioSystem.getAudioInputStream(soundFile);
				logger.info("--InputSream: "+input.toString());
				Clip clip = AudioSystem.getClip();
				clip.open(input);
				clip.addLineListener(listener);
				
				
				clip.start();
				logger.info("--Waiting");
				listener.waitUntilDone(clip);
				logger.info("--Waited");
				
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		File soundFile;
		long clipLength;
		Logger logger = Logger.getLogger("SoundPlayer");
		
		private class AudioListener implements LineListener {
			 private boolean done = false;
			    @Override 
			    public synchronized void update(LineEvent event) {
			      Type eventType = event.getType();
			      if (eventType == Type.STOP || eventType == Type.CLOSE) {
			        done = true;
			        notifyAll();
			      }
			    }
			    public synchronized void waitUntilDone(Clip clip) throws InterruptedException {
			    	while (!done) { wait(); }
			    	clip.close();
			    }
		}
	}
}