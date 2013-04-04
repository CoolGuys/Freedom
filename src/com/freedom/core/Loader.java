package com.freedom.core;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import com.freedom.gameObjects.Stuff;
/**
 * Данный класс предназначен для чтения уровней из файла
 * В данный момент он не закончен, потому что требуется возможность добавлять объекты в массив Tiles
 * @author ush
 * 
 */
public class Loader {
	public static void test(int Number) throws FileNotFoundException {
		Scanner sc;
		sc = new Scanner(new File("Level1.lvl"));
		while (sc.next().equals("Lavel")) {
			int LevelNum = sc.nextInt();
			String StrDump = sc.next();
			while (!StrDump.equals("/level")) {
				if (Number == LevelNum) {
					Stuff newstuff;
					try {
						newstuff = (Stuff) Class.forName(StrDump).newInstance();
						newstuff.ReadLvlFile(sc);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Ошибка при создании объекта типа stuff! --"+e.getMessage());
						break;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Ошибка при создании объекта типа stuff! --"+e.getMessage());
						break;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Ошибка при создании объекта типа stuff! --"+e.getMessage());
						break;
					}					
				}
			}
		}
	}

	private static Logger logger = Logger.getLogger("Loader");
}
