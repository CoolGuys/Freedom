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
	public int contentAmount; // кол-во объектов на клетке, может стать багом в случае лазера; public в силу необходимости изменения

	public Tile(int a, int b, int contentAmountin, Stuff stuffin[]){//зполнение массива content
		this.content = new Stuff[3]; //3 - это по максимуму, чтоб не париться. 
		this.x = a;
		this.y = b;
		this.contentAmount = contentAmountin;
		int i1;
		for(i1=0;i1<this.contentAmount;i1++)
		{
			this.content[i1]=stuffin[i1];
		}
	}
	
	public int getContentAmount(){
		return(contentAmount);
	}
	
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}

}
