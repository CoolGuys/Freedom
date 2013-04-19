package com.freedom.utilities;

import java.nio.file.Path;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.freedom.gameObjects.Cell;
import com.freedom.gameObjects.GameField;

/**
 * You cannot pass! I am a servant of the Secret Fire, wielder of the Flame of
 * Anor. The dark fire will not avail you, Flame of Udun! Go back to the shadow.
 * You shall not pass!
 * А вообще это класс который позволяет искать путь по которому можно пройти.
 * 	
 * @author Gandalf; UshAle 
 * 
 */

public class PathFinder {

	private static Logger logger = Logger.getLogger("PathFinder");

	private static int generateRandom(int n) {
		Random random = new Random();
		return Math.abs(random.nextInt()) % n;
	}
	
	/**
	 * 
	 * @param xs x начала
	 * @param ys y начала
	 * @param xe x конца
	 * @param ye y конца
	 * @param width Задаёт максимальную дину пути, по которому можно дойти. 
	 * @return Возвращает случайный путь до пункта назначения в виде строки "NWES" или 0 если нельзя дойти
	 */

	public static String find(int xs, int ys, int xe, int ye, int width){
		
		logger.setLevel(Level.OFF);	
		logger.info("starting from ("+xs+","+ys+") to ("+xe+","+ye+")");
		logger.info("getting cells");
		Cell[][] cells = GameField.getInstance().getCells();
		Boolean[][] passable = new Boolean[2 * width + 1][2 * width + 1];
		for (int i = 0; i < 2 * width + 1; i++) {
			for (int j = 0; j < 2 * width + 1; j++) {
				try {
					//System.out.println((xs-width+i) + "," + (xs-width+j));
					passable[i][j] = cells[xs-width+i][ys-width+j].ifCanPassThrough();
				} catch (Exception e) {
					passable[i][j]=false;
				}
			}
		}
		String pathlist="";
		int PathAmount=1;
		int x=width;
		int y=width;
		
		int x1=width+xe-xs;
		int y1=width+ye-ys;
		//System.out.println(x1+" "+y1);
		int resultpath=0;
		int pathlength=0;
		String Pathes="";
		for(int i=0; i<width;i++){
			String newpath="";
			int newamount=0;
			Boolean[][] newpassable = new Boolean[2*width+1][2*width+1];
			/*
			for(int i1=0; i1<2*width+1;i1++){
				for(int j1=0; j1<2*width+1;j1++){
					System.out.print(passible[j1][i1]+" ");
				}
				System.out.println();
			}
			
			*/
			for(int j=0;j<PathAmount;j++){
				String buf=pathlist.substring(j*(i), (j+1)*i);
				logger.info("i="+i+" j="+j+" PathAmount="+PathAmount+" buf="+buf);
				int a[]=getPlace(buf,x,y);
				//System.out.println(a[0]+" "+a[1]);
				if(passable[a[0]][a[1]-1]){
					newpath=newpath+buf+"N";
					newamount++;
					newpassable[a[0]][a[1]-1]=false;
					if((a[0]==x1)&&((a[1]-1)==y1)){
						resultpath++;
						pathlength=i;
						//System.out.println(buf+"N");
						Pathes=Pathes+buf+"N";
					}
				}
				if(passable[a[0]+1][a[1]]){
					newpath=newpath+buf+"E";
					newamount++;
					newpassable[a[0]+1][a[1]]=false;
					if(((a[0]+1)==x1)&&(a[1]==y1)){
						resultpath++;
						pathlength=i+1;
						//System.out.println(buf+"E");
						Pathes=Pathes+buf+"E";
					}
				}
				if(passable[a[0]][a[1]+1]){
					newpath=newpath+buf+"S";
					newamount++;
					newpassable[a[0]][a[1]+1]=false;
					if((a[0]==x1)&&((a[1]+1)==y1)){
						resultpath++;
						pathlength=i+1;
						//System.out.println(buf+"S");
						Pathes=Pathes+buf+"S";
					}
				}
				if(passable[a[0]-1][a[1]]){
					newpath=newpath+buf+"W";
					newamount++;
					newpassable[a[0]-1][a[1]]=false;
					if(((a[0]-1)==x1)&&(a[1]==y1)){
						resultpath++;
						pathlength=i+1;
						//System.out.println(buf+"W");
						Pathes=Pathes+buf+"W";
					}
				}
			}	
			if(resultpath!=0){
				break;
			}
			logger.info("newpath=" + newpath);
			for (int i1 = 0; i1 < 2 * width + 1; i1++) {
				for (int j1 = 0; j1 < 2 * width + 1; j1++) {
					if (newpassable[j1][i1]!=null) {
						passable[j1][i1] = false;
					}
				}
			}

			pathlist = newpath;
			PathAmount = newamount;
						
		}
		/*
		for(int i1=0; i1<2*width+1;i1++){
			for(int j1=0; j1<2*width+1;j1++){
				//System.out.print(passible[j1][i1]+" ");
			}
			System.out.println();
		}
		*/
		
		if(resultpath!=0){
			int r = generateRandom(resultpath);
			return Pathes.substring(r*pathlength, (r+1)*pathlength);
		}else {
			return "0";
		}
	}
	
	private static int[] getPlace(String Path, int xs, int ys){
		int[] a = new int[2];
		a[0]=xs;
		a[1]=ys;
		for(int i=0; i<Path.length(); i++){
			if(Path.charAt(i)=='N'){
				a[1]--;
			}
			if(Path.charAt(i)=='E'){
				a[0]++;
			}
			if(Path.charAt(i)=='S'){
				a[1]++;
			}
			if(Path.charAt(i)=='W'){
				a[0]--;
			}
		}
		return a;
	}
	
	
}
