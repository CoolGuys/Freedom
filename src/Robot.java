
public class Robot extends Stuff {
	
	private char direction;
	Stuff container;
	
	public Robot(int a, int b, char direct, Stuff c)
	{ // Ушаков, допиши! @ivan /**А почему у робота уже в конструкторе контейнер
		// заполняется? И еще я перенес конструктор перед методами, так типа
		// прилично делать) @gleb
		super(a, b);
		direction = direct;
		container = c;
	}
	
	public char getDirection(){  //you'll get 'F' in case of mistake
		if (this.direction == 'N') return ('N');
		if (this.direction == 'S') return ('S');
		if (this.direction == 'W') return ('W');
		if (this.direction == 'E') return ('E');
		//System.out.println("wrong direction!");
		return('F');
	}
	
	
	public boolean canGo( Tile [][] tiles, int xMax,int  yMax ){  //стоит объект - false
		int x = this.getX();
		int y = this.getY();
		
		if (this.direction == 'N') {
			if(tiles[x][y+1].getContentAmount()==0) return true;
		}
		
		if (this.direction == 'S') {
			if(tiles[x][y-1].getContentAmount()==0) return true;
		}
		
		if (this.direction == 'W') {
			if(tiles[x-1][y].getContentAmount()==0) return true;
		}
		
		if (this.direction == 'E') {
			if(tiles[x+1][y].getContentAmount()==0) return true;
		}
		
		return false;
		
	}

}
