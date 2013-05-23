package com.freedom.utilities.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.freedom.gameObjects.base.Ghost;
import com.freedom.gameObjects.base.Stuff;
import com.freedom.gameObjects.base.Stuff.LoadingType;
import com.freedom.gameObjects.base.Stuff.StuffColor;
import com.freedom.gameObjects.characters.Robot;
import com.freedom.gameObjects.characters.RobotEditor;
import com.freedom.gameObjects.uncontrolled.Tile;
import com.freedom.gameObjects.uncontrolled.Wall;
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

	public static void createNewField(int x, int y, boolean withWalls,
			String lvlfile, int lvl) {
		// Tile tile= new Tile();
		// Wall wall = new Wall();
		GameField.getInstance().cells = new Cell[x + 2][y + 2];
		GameField.getInstance().newGhosts(0);
		GameField.getInstance().setCurrentLevel(lvl);
		for (int i = 1; i <= x; i++) {
			for (int j = 1; j <= y; j++) {
				if ((i != 1) && (j != 1) && (i != x) && (j != y)) {
					GameField.getInstance().cells[i][j] = new Cell(i, j);
					Tile tile = new Tile(i, j, StuffColor.BLUE);
					GameField.getInstance().cells[i][j].add(tile);
				} else {
					if (withWalls) {
						GameField.getInstance().cells[i][j] = new Cell(i, j);
						Wall wall = new Wall(i, j, StuffColor.BLUE);
						GameField.getInstance().cells[i][j].add(wall);
					} else {
						GameField.getInstance().cells[i][j] = new Cell(i, j);
						Tile tile = new Tile(i, j, StuffColor.BLUE);
						GameField.getInstance().cells[i][j].add(tile);
					}
				}
			}
		}
		RobotEditor robot = new RobotEditor(2, 2, "S");
		GameField.getInstance().cells[2][2].add(robot);
		GameField.getInstance().setRobotEditor(robot);
		GameField.getInstance().setRobot(robot);
		File fXml = new File(lvlfile);
		try {
			fXml.createNewFile();
			createNewXml(lvlfile, lvl);
		} catch (IOException e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}

		lvlToSv(lvl, lvlfile);
	}

	/*
	 * <?xml version="1.0" encoding="UTF-8" standalone="no"?> <levels
	 * startLvl="2">
	 * "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
	 */
	private static void createNewXml(String lvlfile, int stlvl) {
		File fXml = new File(lvlfile);
		try {
			FileOutputStream stream = new FileOutputStream(fXml, false);
			stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
					.getBytes());
			stream.write("<levels startLvl=\"".getBytes());
			stream.write((stlvl + "").getBytes());
			stream.write("\">\n</levels>".getBytes());
			stream.close();
		} catch (Exception e) {
			// TODO Автоматически созданный блок catch
			e.printStackTrace();
		}

	}

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
				doc.getDocumentElement().setAttribute("startLvl",
						String.valueOf(num));
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
				for (int x = 1; x < width - 1; x++) {// writing objects
					for (int y = 1; y < height - 1; y++) {
						Stuff[] stu = GameField.getInstance().cells[x][y]
								.getContent();
						int l = stu.length;
						for (int i = 0; i < l; i++) {
							try {
								if (stu[i].getLoadingType() == LoadingType.OBJ) {
									Element obj = doc.createElement("obj");
									stu[i].loadToFile(obj);
									obj.setTextContent("\n");
									lvl.appendChild(obj);
								}
								if (stu[i].getLoadingType() == LoadingType.OBJC) {
									// System.out.print(stu[i].getClass().getSimpleName());
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
										cels.setTextContent("\n");
										obj.appendChild(cels);
									}
									lvl.appendChild(obj);
								}

							} catch (Exception ei) {

							}
						}
					}
				}
				Ghost gsts[] = GameField.getInstance().newGhosts(0);
				for (int i = 0; i < gsts.length; i++) {
					try {
						if (gsts[i].getLoadingType() == LoadingType.OBJ) {
							Element obj = doc.createElement("gst");
							gsts[i].loadToFile(obj);
							obj.setTextContent("\n");
							lvl.appendChild(obj);
						}
						if (gsts[i].getLoadingType() == LoadingType.OBJC) {
							// System.out.print(stu[i].getClass().getSimpleName());
							Element obj = doc.createElement("gst");
							gsts[i].loadToFile(obj);
							obj.setTextContent("\n");
							int useam = gsts[i].getUseAmount();
							int useList[][] = gsts[i].getUseList();
							for (int i1 = 0; i1 < useam; i1++) {
								Element cels = obj.getOwnerDocument()
										.createElement("cels");
								cels.setAttribute("x",
										String.valueOf((int) useList[i1][0]));
								cels.setAttribute("y",
										String.valueOf((int) useList[i1][1]));
								cels.setTextContent("\n");
								obj.appendChild(cels);
							}
							lvl.appendChild(obj);
						}

					} catch (Exception ei) {

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
				GameField.getInstance().getRobot().loadToFile(robo);
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
			int tl;
			tl = Integer.parseInt(doc.getDocumentElement().getAttribute(
					"startLvl"));
			GameField.getInstance().setCurrentLevel(tl);
			GameField.getInstance().setPreviousLevel(tl);
			Loader.readLvl(tl, lvlfile);
		} catch (Exception ei) {
			ei.printStackTrace();
		}
	}

	public static void readLvl(int Number, String lvlfile) {
		logger.setLevel(Level.OFF);
		File fXml = new File(lvlfile);
		LoadingScreenModel lsm = LoadingScreenModel.getInstance();
		// lsm.setLoadingObjectName("Starting");
		// lsm.setProgressPercent(0);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fXml);
			doc.getDocumentElement().normalize();
			logger.info("Open <" + doc.getDocumentElement().getTagName()
					+ "> in " + fXml.getPath());
			// lsm.setLoadingObjectName("Importing file "+fXml.getName());
			// lsm.setProgressPercent(100);
			NodeList lvllist = doc.getElementsByTagName("level");
			logger.info("Getting level N=" + Number);
			for (int lvli = 0; lvli < lvllist.getLength(); lvli++) {
				logger.info("lvli=" + lvli + " length=" + lvllist.getLength());
				Node lvlTag = lvllist.item(lvli);
				Element lvl = (Element) lvlTag;
				// lsm.setProgressPercent(0);
				// lsm.setLoadingObjectName("Searching level №"+Number);
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
					int objTagLeng = objTag.getLength();

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
						lsm.setLoadingObjectName(obj.getAttribute("class")
								.substring(24));
						lsm.setProgressPercent(obji * 100 / objTagLeng);
					}

					logger.info("Creating ghosts");
					objTag = lvl.getElementsByTagName("gst");
					logger.info("amount " + objTag.getLength());
					lsm.setProgressPercent(0);
					objTagLeng = objTag.getLength();
					GameField.getInstance().newGhosts(objTagLeng);
					for (int obji = 0; obji < objTagLeng; obji++) {
						Element obj = (Element) objTag.item(obji);
						logger.info("reading class="
								+ obj.getAttribute("class"));
						Object newgst;
						Class<?> cla = Class.forName(obj.getAttribute("class"));
						newgst = cla.newInstance();
						((Ghost) newgst).readLvlFile(obj);
						GameField.getInstance().setGhost(obji, (Ghost) newgst);
						lsm.setLoadingObjectName(obj.getAttribute("class")
								.substring(24));
						lsm.setProgressPercent(obji * 100 / objTagLeng);
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
						if (obj.getAttribute("editor").equals("")) {
							if (robostuff.getLength() == 0) {
								GameField.getInstance().setRobot(
										new Robot(Integer.parseInt(obj
												.getAttribute("x")),
												Integer.parseInt(obj
														.getAttribute("y")),
												obj.getAttribute("dir"), null,
												Robot.maxLives));
								GameField.getInstance().getRobot()
										.readLvlFile(obj);
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
												.getAttribute("x")),
												Integer.parseInt(obj
														.getAttribute("y")),
												obj.getAttribute("dir"),
												((Stuff) newstuff),
												Robot.maxLives));
								GameField
										.getInstance()
										.getRobot()
										.getContent()
										.setXY(GameField.getInstance()
												.getRobot().getX(),
												GameField.getInstance()
														.getRobot().getY());
								GameField.getInstance().getRobot()
										.readLvlFile(obj);
							}
						} else {
							GameField.getInstance().setRobot(
									new RobotEditor(Integer.parseInt(obj
											.getAttribute("x")), Integer
											.parseInt(obj.getAttribute("y")),
											obj.getAttribute("dir")));
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
