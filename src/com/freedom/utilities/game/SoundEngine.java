package com.freedom.utilities.game;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Класс SoundEngine создает для переданного звукового файла отдельный поток, в
 * котором выполняется проигрывание этого файла.
 * 
 * @author gleb
 * 
 */

public class SoundEngine {

	/**
	 * 
	 * @param clipFile
	 *            файл для проигрывания
	 * @param loopCode
	 *            код для определения типа повтора 
	 *            -- отриц. - противоположное количество раз, целиком; 
	 *            -- 0 - бесконечно, целиком; 
	 *            -- полож. - куcок со значения loopCode и до конца, бесконечно
	 * @param volume
	 * 				громкость (0 - по умолчанию)
	 * @return
	 */
	public static SoundPlayer playClip(File clipFile, int loopCode, int volume) {

		SoundPlayer player;

		player = new SoundPlayer(clipFile, loopCode, volume);

		pool.submit(player);
		return player;
	}

	private static ExecutorService pool = Executors.newCachedThreadPool();

	public static class SoundPlayer implements Runnable {

		public SoundPlayer(File soundFile, int loop, int volume)
		{
			this.soundFile = soundFile;
			this.loopCode = loop;
			this.initVolumeValue = volume;
		}

		@Override
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
				volume = (FloatControl) clip
						.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(initVolumeValue);

				if (loopCode == -1) {
					clip.addLineListener(listener);
					clip.start();

					logger.info("--Waiting");
					listener.waitUntilDone(clip);
					logger.info("--Waited");
				} else if (loopCode < 0) {
					clip.loop(-loopCode);
				} else if (loopCode == 0)
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				else {
					clip.setLoopPoints(loopCode, -1);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}

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

		public void stopPlaying() {
			clip.stop();
		}

		public void fadeOut() {

			pool.submit(new Fader());
		}

		private int loopCode;
		private File soundFile;
		private Clip clip;
		private FloatControl volume;
		private int initVolumeValue;
		Logger logger = Logger.getLogger("SoundPlayer");

		private class Fader implements Runnable {

			@Override
			public void run() {
				for (float i = volume.getValue(); i > -70; i = (float) (i - 0.05)) {
					volume.setValue(i);
					try {
						Thread.sleep(7);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				clip.stop();
			}

		}

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