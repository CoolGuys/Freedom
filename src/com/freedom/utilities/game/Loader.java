package com.freedom.utilities.game;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.freedom.gameObjects.base.Cell;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.characters.Robot;
import com.freedom.model.GameField;
import com.freedom.model.LoadingScreenModel;

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
 * @author UshAle
 *
 */

/**
 * Также добавлена возможность сохранять лвл в файл функцией lvlToFile(int num,
 * String lvlfile, Cell[][] GameField.getInstance().cells) Все параметры
 * аналогичны параметрам readlvl Пример использования в комменте к
 * GameField.loadLevel();
 * 
 * @author UshAle
 * 
 */

public class Loader {

	public static void lvlToSv(int num, String lvlfile) {
		logger.setLevel(Level.OFF);
		File fXml = new File(lvlfile);
		if (fXml.exists()) {
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(fXml);
				doc.getDocumentElement().normalize();
				doc.getDocumentElement().setAttribute("startLvl", String.valueOf(num));
				logger.info("Open <" + doc.getDocumentElement().getTagName()
						+ "> in " + fXml.getPath());
				NodeList lvllist = doc.getElementsByTagName("level");
				logger.info("Getting level N=" + num);
				for (int lvli = 0; lvli < lvllist.getLength(); lvli++) {
					logger.info("lvli=" + lvli + " length="
							+ lvllist.getLength());
					Node lvlTag = lvllist.item(lvli);
					Element lvl = (Element) lvlTag;
					if (Integer.parseInt(lvl.getAttribute("num")) == num) {
						lvlTag.getParentNode().removeChild(lvlTag);
					}
				}
				Element lvl = doc.createElement("level");
				lvl.setAttribute("num", String.valueOf(num));
				lvl.setAttribute("width", String.valueOf(GameField
						.getInstance().cells.length - 2));
				lvl.setAttribute("height", String.valueOf(GameField
						.getInstance().cells[0].length - 2));
				lvl.setTextContent("\n");
				doc.getDocumentElement().appendChild(lvl);
				int width = GameField.getInstance().cells.length;
				int height = GameField.getInstance().cells[0].length;
				for (int x = 1; x < width-1; x++) {// writing objects
					for (int y = 1; y < height-1; y++) {
						Stuff[] stu = GameField.getInstance().cells[x][y]
								.getContent();
						int l = stu.length;
						for (int i = 0; i < l; i++) {
							try {
								if (stu[i].obj()) {
									Element obj = doc.createElement("obj");
									stu[i].loadToFile(obj);
									obj.setTextContent("\n");
									lvl.appendChild(obj);
								}
								if (stu[i].objc()) {
									//System.out.print(stu[i].getClass().getSimpleName());
									Element obj = doc.createElement("obj");
									stu[i].loadToFile(obj);
									obj.setTextContent("\n");
									int useam = stu[i].getUseAmount();
									int useList[][] = stu[i].getUseList();
									for (int i1 = 0; i1 < useam; i1++) {
										Element cels = obj.getOwnerDocument()
												.createElement("cels");
										cels.setAttribute("x", String
												.valueOf((int) useList[i1][0]));
										cels.setAttribute("y", String
												.valueOf((int) useList[i1][1]));
										obj.appendChild(cels);
									}
									lvl.appendChild(obj);
								}

							} catch (Exception ei) {

							}
						}
					}
				}
				Element robo = doc.createElement("robot");// writing robot
				robo.setAttribute(
						"x",
						String.valueOf(GameField.getInstance().getRobot()
								.getX()));
				robo.setAttribute(
						"y",
						String.valueOf(GameField.getInstance().getRobot()
								.getY()));
				robo.setAttribute(
						"dir",
						String.valueOf(GameField.getInstance().getRobot()
								.getDirection()));
				robo.setTextContent("\n");
				if (!GameField.getInstance().getRobot().getIfEmpty()) {
					Element RoboObj = doc.createElement("objr");
					GameField.getInstance().getRobot().getContent()
							.loadToFile(RoboObj);
					robo.appendChild(RoboObj);
				}
				lvl.appendChild(robo);
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fXml);
				transformer.transform(source, result);
			} catch (Exception ei) {
				ei.printStackTrace();
			}
		}
	}

	public static void loadSave(String lvlfile) {
		File fXml = new File(lvlfile);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fXml);
			doc.getDocumentElement().normalize();
			int tl=1;
			tl=Integer.parseInt(doc.getDocumentElement().getAttribute("startLvl"));
			GameField.getInstance().setCurrentLevel(tl);			
			GameField.getInstance().setPreviousLevel(tl);
			//System.out.println("1");
			Loader.readLvl(tl, lvlfile);
		} catch (Exception ei) {
			ei.printStackTrace();
		}
	}

	public static void readLvl(int Number, String lvlfile) {
		logger.setLevel(Level.OFF);
		File fXml = new File(lvlfile);
		LoadingScreenModel lsm=LoadingScreenModel.getInstance();
		//lsm.setLoadingObjectName("Starting");
		//lsm.setProgressPercent(0);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fXml);
			doc.getDocumentElement().normalize();
			logger.info("Open <" + doc.getDocumentElement().getTagName()
					+ "> in " + fXml.getPath());
			//lsm.setLoadingObjectName("Importing file "+fXml.getName());
			//lsm.setProgressPercent(100);
			NodeList lvllist = doc.getElementsByTagName("level");
			logger.info("Getting level N=" + Number);
			for (int lvli = 0; lvli < lvllist.getLength(); lvli++) {
				logger.info("lvli=" + lvli + " length=" + lvllist.getLength());
				Node lvlTag = lvllist.item(lvli);
				Element lvl = (Element) lvlTag;
//				lsm.setProgressPercent(0);
//				lsm.setLoadingObjectName("Searching level №"+Number);
				if (Integer.parseInt(lvl.getAttribute("num")) == Number) {
					LoadingScreenModel.getInstance().setProgressPercent(100);
					int width = Integer.parseInt(lvl.getAttribute("width"));
					int height = Integer.parseInt(lvl.getAttribute("height"));
					GameField.getInstance().cells = new Cell[width + 2][height + 2];
					logger.info("Creating GameField.getInstance().cells array w="
							+ width + " h=" + height);
					for (int x = 1; x <= width; x++) {
						for (int y = 1; y <= height; y++) {
							GameField.getInstance().cells[x][y] = new Cell(x, y);
						}
					}
					logger.info("Creating GameField.getInstance().cells array-ok");
					NodeList objTag = lvl.getElementsByTagName("obj");
					logger.info("amount " + objTag.getLength());
					lsm.setProgressPercent(0);
					int objTagLeng=objTag.getLength();
					
					for (int obji = 0; obji < objTagLeng; obji++) {
						Element obj = (Element) objTag.item(obji);
						logger.info("reading x=" + obj.getAttribute("x")
								+ " y=" + obj.getAttribute("y") + " class="
								+ obj.getAttribute("class"));
						Object newstuff;
						Class<?> cla = Class.forName(obj.getAttribute("class"));
						newstuff = cla.newInstance();
						((Stuff) newstuff).readLvlFile(obj);
						GameField.getInstance().cells[((Stuff) newstuff).getX()][((Stuff) newstuff)
								.getY()].add(((Stuff) newstuff));
						
						lsm.setLoadingObjectName(obj.getAttribute("class").substring(24));
						lsm.setProgressPercent(obji*100/objTagLeng);
					}
					/*
					 * objTag=lvl.getElementsByTagName("objc");
					 * logger.info("amount objs "+objTag.getLength()); for(int
					 * obji=0;obji<objTag.getLength();obji++){
					 * //System.out.println("ololo"); Element
					 * obj=(Element)objTag.item(obji);
					 * logger.info("reading x="+obj
					 * .getAttribute("x")+" y="+obj.getAttribute
					 * ("y")+" class="+obj.getAttribute("class")); Object
					 * newstuff; Class<?> cla =
					 * Class.forName(obj.getAttribute("class")); newstuff =
					 * cla.newInstance(); ((Stuff) newstuff).readLvlFile(obj);
					 * GameField.getInstance().cells[((Stuff)
					 * newstuff).getX()][((Stuff) newstuff).getY()].add(((Stuff)
					 * newstuff)); }
					 */
					lsm.setLoadingObjectName("Robot creating");
					lsm.setProgressPercent(0);
					NodeList robotlist = lvl.getElementsByTagName("robot");
					for (int rbti = 0; rbti < robotlist.getLength(); rbti++) {
						Element obj = (Element) robotlist.item(rbti);
						NodeList robostuff = obj.getElementsByTagName("objr");
						if (robostuff.getLength() == 0) {
							GameField.getInstance().setRobot(
									new Robot(Integer.parseInt(obj
											.getAttribute("x")), Integer
											.parseInt(obj.getAttribute("y")),
											obj.getAttribute("dir"), null,
											10));
						} else {
							Element roboobj = (Element) robostuff.item(0);
							// logger.info("reading x="+obj.getAttribute("x")+" y="+obj.getAttribute("y")+" class="+obj.getAttribute("class"));
							Object newstuff;
							Class<?> cla = Class.forName(roboobj
									.getAttribute("class"));
							newstuff = cla.newInstance();
							((Stuff) newstuff).readLvlFile(roboobj);
							GameField.getInstance().setRobot(
									new Robot(Integer.parseInt(obj
											.getAttribute("x")), Integer
											.parseInt(obj.getAttribute("y")),
											obj.getAttribute("dir"),
											((Stuff) newstuff), 10));
							GameField.getInstance().getRobot().getContent().setXY(GameField.getInstance().getRobot().getX(), GameField.getInstance().getRobot().getY());
						}
					}
					lsm.setProgressPercent(100);

				}
			}

		} catch (Exception ei) {
			ei.printStackTrace();
		}
	}

	private static Logger logger = Logger.getLogger("Loader");
	static {
		logger.setLevel(Level.OFF);
	}
}
