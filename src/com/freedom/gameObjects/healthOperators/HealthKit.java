package com.freedom.gameObjects.healthOperators;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class HealthKit extends Stuff{
	public HealthKit()
	{
		super(true, true);
		textureRed = texture1;
	}
	@Override
	public void readLvlFile(Element obj) {
		super.readLvlFile(obj);
		this.heals = Integer.parseInt(obj.getAttribute("heals"));
	}
	@Override
	public void loadToFile(Element obj) {
		obj.setAttribute("x", String.valueOf((int) this.x));
		obj.setAttribute("y", String.valueOf((int) this.y));

		//TODO придумать, как здесь обыграть цвет
		obj.setAttribute("heals", String.valueOf(this.heals));
		obj.setAttribute("class", "com.freedom.gameObjects.healthOperators.HealthKit");
	}
	@Override
	public void touch(Stuff element) {
		element.heal(this.heals);
		//heals=0;
		GameField.getInstance().getCells()[(int) this.x][(int) this.y].delete(this);
	}	
	
	private int heals;
	private static Image texture1;
	static {
		try {
			texture1 = ImageIO.read(new File("Resource/Textures/HealthKit.png")).getScaledInstance(getSize(),
					getSize(), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean absorbs(Stuff element){
		return false;
	}
	
	@Override
	public boolean reflects(Stuff element) {
		return false;
	}
}
