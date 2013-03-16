//общее для все имеющихся объектов
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stuff {
	private int x;
	private int y;
	//private Image texture;
	
	public Stuff(int a, int b){ //Глеб, допиши здесь создание рисунка
		this.x = a;
		this.y = b;
	}
	
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}

}
