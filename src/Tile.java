import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//объект класса Плитка.
public class Tile {
	
	private int x;
	private int y;
	private Image texture;
	
	public Stuff [] content; //change to private later
	private int contentAmount; // кол-во объектов на клетке, может стать багом в случае лазера
	
	public int getContentAmount(){
		return(contentAmount);
	}
	
	
	
	public Tile(int a, int b){//Ушаков, здесь добавь заполнение массива content
		this.content = new Stuff[3]; //это - дефолт. 
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
