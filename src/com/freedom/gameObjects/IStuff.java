package com.freedom.gameObjects;

import java.util.Scanner;
/**
 * Это интерфей для класса Stuff
 * Мне он нужен, т.к. я хочу чтобы объекты сами читали информацию о себе из файла
 * Вы вы тоже можете добавлять сюда что - нить, только пишите коменты
 * @author ush
 * 
 */
public interface IStuff {
	void ReadLvlFile(Scanner sc);//название функции говорит само за себя
}