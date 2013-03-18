//общее для все имеющихся объектов
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stuff {
	//ДОБАВЛЕНО	 поле "могу ли взять"
	
	private int x;
	private int y;
	private boolean ifCanTake; //"поднимаем" ли объект?
	//private Image texture;
	
	public Stuff(int a, int b, boolean ifIcan){ //Глеб, допиши здесь создание рисунка
		this.x = a;
		this.y = b;
		this.ifCanTake = ifIcan;
	}
	
	//////////////
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}
	
	public boolean getIfCanTake(){
		return this.ifCanTake;
	}
	/////////////////////
	
}
