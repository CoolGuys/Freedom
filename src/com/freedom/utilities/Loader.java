package com.freedom.utilities;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.xml.parsers.*;
import org.w3c.dom.*;
/*import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;*/



import com.freedom.gameObjects.*;

/**
 * Данный класс предназначен для чтения уровней из файла В данный момент он не
 * закончен, потому что требуется возможность добавлять объекты в массив Tiles
 * 
 * @author ush
 * 
 */
public class Loader {
	public static Cell[][] readLvl(int Number){
		
		logger.setLevel(Level.OFF);
		
	/*	Cell[][] cells = new Cell[10][10];
		for(int x=1;x<10;x++){
			for(int y=1;y<10;y++){
				cells[x][y]=new Cell(x, y);
			}
		}*/
		Cell[][] cells = null;

        File fXml=new File("Level1.lvl");
        
        try
        {
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(fXml);
            
            doc.getDocumentElement().normalize();
            //System.out.println("doc["+doc.getDocumentElement().getNodeName()+"]");
            NodeList lvllist=doc.getElementsByTagName("level");           
			for (int lvli = 0; lvli < lvllist.getLength(); lvli++) {
				Node lvlTag = lvllist.item(lvli);
				Element lvl = (Element) lvlTag;
			    if(Integer.parseInt(lvl.getAttribute("num"))==Number){
			    	int width=Integer.parseInt(lvl.getAttribute("width"));
			    	int height=Integer.parseInt(lvl.getAttribute("height"));
			        cells = new Cell[width+1][height+1];
					for(int x=1;x<=width;x++){
						for(int y=1;y<=height;y++){
							cells[x][y]=new Cell(x, y);
						}
					}
					NodeList objTag=lvl.getElementsByTagName("obj");
					for(int obji=0;obji<objTag.getLength();obji++){
						Element obj=(Element)objTag.item(obji);
						Object newstuff;
						Class<?> cla = Class.forName(obj.getAttribute("class"));
						newstuff = cla.newInstance();
						((Stuff) newstuff).readLvlFile(obj);
						cells[((Stuff) newstuff).getX()][((Stuff) newstuff).getY()].add(((Stuff) newstuff));
					}
				    NodeList robotlist=doc.getElementsByTagName("robot");
				    for (int rbti = 0; rbti < robotlist.getLength(); rbti++) {
				    	Element obj=(Element)robotlist.item(rbti);			    	
				    	System.out.println(obj.getAttribute("x")+"|"+obj.getAttribute("y"));
				    	GameField.getInstance().setRobot(new Robot(Integer.parseInt(obj.getAttribute("x")),Integer.parseInt(obj.getAttribute("y")),"N",null,cells));
				    	//System.out.println(robot.toString());
				    
				    }
			    }
			    
				//NodeList nljx = elj.getElementsByTagName("number");
				//NodeList nljy = elj.getElementsByTagName("y");
				//Element eljy = (Element) nljy.item(0);
				//NodeList nljyc = eljy.getChildNodes();
			    //	System.out.println("x, y ["
				//		+ ((Node) nljxc.item(0)).getNodeValue() + ", "
				//		+ ((Node) nljyc.item(0)).getNodeValue() + "]");
				// }
			}
			
        }
        catch(Exception ei){
        	ei.printStackTrace();
        }
        return cells;
        
		/*
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
							((Stuff) newstuff).readLvlFile(sc);
							//System.out.println("GLEB="+((Stuff) newstuff).getX()+" "+((Stuff) newstuff).getClass().getSimpleName());
							cells[((Stuff) newstuff).getX()][((Stuff) newstuff).getY()].add(((Stuff) newstuff));
				

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
		return cells;*/
	}
	
	private static Logger logger = Logger.getLogger("Loader");
}
