package com.freedom.core;

import java.io.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

public class SoundEngine {

	public static SoundPlayer playClip(File clipFile, String loop) {
		
		
		SoundPlayer player;

		if(loop.equals("LoopContinuously"))
			player = new SoundPlayer(clipFile, true);
		else
			player = new SoundPlayer(clipFile, true);
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.submit(player);
		return player;
	}
	
	public static Runnable playClip(File clipFile) {
		SoundPlayer player = new SoundPlayer(clipFile, false);

		ExecutorService pool = Executors.newCachedThreadPool();
		pool.submit(player);
		return player;
	}

		
	//public static stopClip
//	static ArrayList<Future<?>> players;
	
	public static class SoundPlayer implements Runnable {

		public SoundPlayer(File soundFile, boolean loop)
		{
			this.soundFile = soundFile;
			this.looping = loop;
		}

		public void run() {
			logger.setLevel(Level.OFF);
			logger.info("Entering RUN: " + this.toString());
			try {
				AudioListener listener = new AudioListener();
				AudioInputStream input = AudioSystem
						.getAudioInputStream(soundFile);
				logger.info("--InputSream: " + input.toString());
				clip = AudioSystem.getClip();
				clip.open(input);

				if (looping == false) {
					clip.addLineListener(listener);
					clip.start();

					logger.info("--Waiting");
					listener.waitUntilDone(clip);
					logger.info("--Waited");
				} else
					clip.loop(Clip.LOOP_CONTINUOUSLY);

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
		
		public void stopPlaying()
		{
			clip.stop();
		}

		private boolean looping;
		File soundFile;
		long clipLength;
		Clip clip;
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

			public synchronized void waitUntilDone(Clip clip)
					throws InterruptedException {
				while (!done) {
					wait();
				}
				clip.close();
			}
		}
	}
}