package com.freedom.core;
//package com.freedom.gameObjects;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import com.freedom.gameObjects.*;
/**
 * Данный класс предназначен для чтения уровней из файла
 * В данный момент он не закончен, потому что требуется возможность добавлять объекты в массив Tiles
 * @author ush
 * 
 */
public class Loader {
	public static void test(int Number) throws FileNotFoundException {
		System.out.print("loader start ");
		Scanner sc;
		sc = new Scanner(new File("Level1.lvl"));
		String StrDump = sc.next();
		System.out.println(StrDump);
		while (StrDump.equals("Level")) {
			int LevelNum = sc.nextInt();
			StrDump = sc.next();
			System.out.println(StrDump);
			while (!StrDump.equals("/level")) {
				if (Number == LevelNum) {
					Object newstuff;
					try {
						Class cla=Class.forName(StrDump);
						newstuff = cla.newInstance();
						//newstuff.
						//System.out.println(newstuff.getClass().getName());
						newstuff.ReadLvlFile(sc);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Error creating stuff! --"+e.getMessage());
						break;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Error creating stuff! --"+e.getMessage());
						break;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.severe("Error creating stuff! --"+e.getMessage());
						break;
					}					
				}
			}
			
		}
		System.out.println("end");
		
	}
	
	private static Logger logger = Logger.getLogger("Loader");
}
