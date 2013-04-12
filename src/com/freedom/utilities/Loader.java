package com.freedom.utilities;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;



import com.freedom.gameObjects.*;


/**
 * !!!!!!!!!!!!!!!!!!!Attention please!!!!!!!!!!!
 * 
 * Итак все костыли убраны и пришло время написать инструкцию по применению
 * сейчас файл имеет вид xml документа
 * 
 * <?xml version="1.0"?>
 * <levels> //здеся хранится набор лвлов и у каждого есть поле num с номером
 *   <level num="1" width="Ширина в клктках" height="Высота в клетках">
 *		//внутри тега level хранится информация об объектах
 *		<obj class="com.freedom.gameObjects.Tile" x="1" y="1"></obj>
 *		// тег obj создаёт объект который наследует стафф
 *		// в атребуте класс должно содераться полное имя объекта
 *		//в самом объекте должен содежаться пустой конструктор и метод 
 *		// readLvlFile(Element obj) который обращается к атрибутам тега через
 *		// функции вроде obj.getAttribute("x")
 *		<robot x="1" y="1"></robot>//robot имеет такой вид
 *   </level>
 *   <level num="2" width="Ширина в клктках" height="Высота в клетках">
 *		  
 *   </level>  
 * </levels>
 * 
 * @author ushale
 *
 */

/**
 * Также добавлена возможность сохранять лвл в файл функцией lvlToFile(int num, String lvlfile, Cell[][] cells)
 * Все параметры аналогичны параметрам readlvl 
 * Пример использования в комменте к GameField.loadLevel();
 * @author ushale
 *
 */


public class Loader {
	
	public static void lvlToFile(int num, String lvlfile, Cell[][] cells){
		logger.setLevel(Level.OFF);
		File fXml=new File(lvlfile);
		logger.info("Oppening file "+fXml.getPath());
		if(fXml.exists()){
			logger.info("Deliting file "+fXml.getPath());
			fXml.delete();			
		}
		try {
			logger.info("Creating file "+fXml.getPath());
			fXml.createNewFile();
		} catch (IOException e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}
        try
        {
        	logger.info("Writing document");
        	DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
	        DocumentBuilder db=dbf.newDocumentBuilder();
	        Document doc=db.newDocument();
	        logger.info("createElement levels");
	        Element lvls = doc.createElement("levels");
	        doc.appendChild(lvls);
	        Element lvl = doc.createElement("level");
	        lvl.setAttribute("num", String.valueOf(num));
	        lvl.setAttribute("width", String.valueOf(cells.length-1));
	        lvl.setAttribute("height", String.valueOf(cells[0].length-1));
	        lvls.appendChild(lvl);
	    	int width=cells.length;
	    	int height=cells[0].length;	
	    	for(int x=1;x<width;x++){//writing objects
	    		for(int y=1;y<height;y++){
	    			Stuff[] stu = cells[x][y].getContent();
	    			int l=stu.length;
	    			for(int i=0;i<l;i++){
		    			try{
		    				Element obj=doc.createElement("obj");
		    				stu[i].loadToFile(obj);
		    				//System.out.println("x="+obj.getAttribute("x")+" y="+obj.getAttribute("y")+" class="+obj.getAttribute("class"));
		    				lvl.appendChild(obj);
		    				//obj.setAttribute("x", String.valueOf(stu[i].getX())
		    				//System.out.println("x="+x+" y="+y+" l="+stu[i].getClass().getSimpleName());
		    			}catch(Exception ei){
		    				
		    			}
	    			}
	    		}
	    	}
	    	Element robo=doc.createElement("robot");//writing robot
	    	robo.setAttribute("x", String.valueOf(GameField.getInstance().getRobot().getX()));
	    	robo.setAttribute("y", String.valueOf(GameField.getInstance().getRobot().getY()));
	    	lvl.appendChild(robo);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fXml);
			transformer.transform(source, result);
        }catch(Exception ei){
        	ei.printStackTrace();
        }
	}
	
	public static Cell[][] readLvl(int Number, String lvlfile){
		
		logger.setLevel(Level.OFF);
		Cell[][] cells = null;
        File fXml=new File(lvlfile);
        try
        {
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(fXml);
            doc.getDocumentElement().normalize();
            logger.info("Open <"+doc.getDocumentElement().getTagName()+"> in "+fXml.getPath());
            NodeList lvllist=doc.getElementsByTagName("level");           
            logger.info("Getting level N="+Number);
			for (int lvli = 0; lvli < lvllist.getLength(); lvli++) {
				logger.info("lvli="+lvli+" length="+lvllist.getLength());
				Node lvlTag = lvllist.item(lvli);
				Element lvl = (Element) lvlTag;
			    if(Integer.parseInt(lvl.getAttribute("num"))==Number){
			    	int width=Integer.parseInt(lvl.getAttribute("width"));
			    	int height=Integer.parseInt(lvl.getAttribute("height"));
			        cells = new Cell[width+1][height+1];
			        logger.info("Creating cells array w="+width+" h="+height);
					for(int x=1;x<=width;x++){
						for(int y=1;y<=height;y++){
							cells[x][y]=new Cell(x, y);
						}
					}
					logger.info("Creating cells array-ok");
					NodeList objTag=lvl.getElementsByTagName("obj");
					logger.info("amount"+objTag.getLength());
					for(int obji=0;obji<objTag.getLength();obji++){
						Element obj=(Element)objTag.item(obji);
						logger.info("reading x="+obj.getAttribute("x")+" y="+obj.getAttribute("y")+" class="+obj.getAttribute("class"));
						//System.out.println("x="+obj.getAttribute("x")+" y="+obj.getAttribute("y")+" class="+obj.getAttribute("class"));
						Object newstuff;
						Class<?> cla = Class.forName(obj.getAttribute("class"));
						newstuff = cla.newInstance();
						((Stuff) newstuff).readLvlFile(obj);
						cells[((Stuff) newstuff).getX()][((Stuff) newstuff).getY()].add(((Stuff) newstuff));
					}
				    NodeList robotlist=doc.getElementsByTagName("robot");
				    for (int rbti = 0; rbti < robotlist.getLength(); rbti++) {
				    	Element obj=(Element)robotlist.item(rbti);			    	
				    	//System.out.println(obj.getAttribute("x")+"|"+obj.getAttribute("y"));
				    	GameField.getInstance().setRobot(new Robot(Integer.parseInt(obj.getAttribute("x")),Integer.parseInt(obj.getAttribute("y")),"N",null,cells));
				    	//System.out.println(robot.toString());
				    	//logger.info("2Dump=|" + StrDump + "|");
				    }
			    }
			}
			
        }
        catch(Exception ei){
        	ei.printStackTrace();
        }
        return cells;
	}
	
	private static Logger logger = Logger.getLogger("Loader");
}
