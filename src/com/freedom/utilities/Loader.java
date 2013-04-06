package com.freedom.utilities;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import com.freedom.gameObjects.*;

/**
 * Данный класс предназначен для чтения уровней из файла В данный момент он не
 * закончен, потому что требуется возможность добавлять объекты в массив Tiles
 * 
 * @author ush
 * 
 */
public class Loader {
	public static void ReadLvl(int Number) throws FileNotFoundException {
		logger.info("loader starting");
		Scanner sc;
		sc = new Scanner(new File("Level1.lvl"));
		String StrDump = sc.next();
		logger.info("1Dump=|" + StrDump + "|");
		while (StrDump.equals("Level")) {
			int LevelNum = sc.nextInt();
			StrDump = sc.next();
			logger.info("2Dump=|" + StrDump + "|");
			while (!StrDump.equals("/Level")) {
				if (Number == LevelNum) {
					Object newstuff;
					try {
						Class<?> cla = Class.forName(StrDump);
						
							newstuff = cla.newInstance();
							logger.info("creating Stuff--"
									+ newstuff.getClass().getName());
							((Stuff) newstuff).ReadLvlFile(sc);
				

					} catch (InstantiationException e) {
						e.printStackTrace();
						logger.severe("Error creating stuff! --"
								+ e.getMessage());
						break;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						logger.severe("Error creating stuff! --"
								+ e.getMessage());
						break;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						logger.severe("Error creating stuff! --"
								+ e.getMessage());
						break;
					}
				}
				StrDump = sc.next();
				logger.info("3Dump=|" + StrDump + "|");
			}
			StrDump = sc.next();
			logger.info("4Dump=|" + StrDump + "|");

		}
		logger.info("end");

	}

	private static Logger logger = Logger.getLogger("Loader");
}
