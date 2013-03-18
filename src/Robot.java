
public class Robot  {
	//Я отказался от наследования stuffа роботом, т.к. это вызывает траблы. рисуем робота отдельно
	//Траблы: надо давать изменять координаты stuff
	//проблемы с поднятием
	
	//Сухой остаток: похоже, надо забивать на координаты у stuffa и рисовать его "по месту".
	//иначе надо давать к его координатам полный доступ, а мы можем обойтись без этого.
	
	int x;
	int y;
	private char direction;
	private Stuff container;
	private boolean ifEmpty; //пуст ли контейнер
	
	///конструкторы
	public Robot(int a, int b, char direct, Stuff c) //в случае, если изначально есть объект
	{ 
		x=a;
		y=b;
		direction = direct;
		container = c;
		ifEmpty = false;
	}
	
	public Robot(int a, int b, char direct) // в случае, если объекта нет
	{ 
		x=a;
		y=b;
		direction = direct;
		container = null;
		ifEmpty = true;
	}
	/////
	
	private boolean ifDirection(char dir){ //проверка, является ли направление правильно заданным
		if (dir == 'N') return true;
		if (dir == 'S') return true;
		if (dir == 'W') return true;
		if (dir == 'E') return true;
		return false;
	}
	
	//блок выдачи полей
	
	public int getX(){ 
		return(this.x);
	}
	
	public int getY() {
		return(this.y);
	}
	
	public char getDirection(){  //you'll get 'F' in case of mistake
		if(this.ifDirection(this.direction)) 
			return this.direction;
		else
			return('F');
	}
	
	public boolean getIfEmpty(){
		return(this.ifEmpty);
	}
	
	public Stuff getContent(){
		return(this.container);
	}
	
	//конец блока выдачи
	
	
	public boolean canGo( Tile [][] tiles){  //стоит объект - false;
		
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
	
	public void move(char way,Tile [][] tiles){ 
		//основной метод: подсовываем ему направление, если оно валидно и мы туда не смотрим,
		// поворачиваемся туда, иначе начинаем туда двигаться, если на соседней клетке нет объектов
		if (ifDirection(way)){
			if (way != this.direction){
				this.direction = way;
				return;
			}
			
			else {
				if (canGo(tiles)) {
					if (this.direction == 'N') y++ ;
					if (this.direction == 'S') y-- ;
					if (this.direction == 'W') x-- ;					
					if (this.direction == 'E') x++ ;
					return;
				}
				else return;
			}
		}
			 
		else return;
	}
	
	public void take(Tile tile){ //метод поднятия объекта: если объект берется и есть куда положить - делаем это
		if(!this.ifEmpty) return;
		
		int n = tile.getContentAmount() - 1;
		if (!tile.content[n].getIfCanTake()) return;
		//исключения обработаны, далее:
		
		this.container = tile.content[n];
		this.ifEmpty = false;
		tile.content[n] = null;
		tile.contentAmount--;
		
	}

}
