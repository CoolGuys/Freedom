package com.freedom.core;

//package com.freedom.gameObjects;
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
						Class cla = Class.forName(StrDump);
						
							newstuff = cla.newInstance();
						//	System.out.println(newstuff.getClass().g);
							//if(newstuff.getClass().equals("com.freedom.gameObjects.Stuff")){
							logger.info("creating Stuff--"
									+ newstuff.getClass().getName());
							((Stuff) newstuff).ReadLvlFile(sc);
				
						// newstuff.ReadLvlFile(sc);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Error creating stuff! --"
								+ e.getMessage());
						break;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Error creating stuff! --"
								+ e.getMessage());
						break;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
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
