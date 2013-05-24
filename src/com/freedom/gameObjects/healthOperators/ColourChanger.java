package com.freedom.gameObjects.healthOperators;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Element;

import com.freedom.gameObjects.base.Stuff;
import com.freedom.model.GameField;

public class ColourChanger extends Stuff{
	
	private static Image textureb;
	private static Image texturer;
	private static Image textureg;
	
	static {
		try {
			textureb = ImageIO.read(new File("Resource/Textures/CCHb.png")).getScaledInstance(getSize(), getSize(),
					BufferedImage.SCALE_SMOOTH);
			texturer = ImageIO.read(new File("Resource/Textures/CCHr.png")).getScaledInstance(getSize(), getSize(),
					BufferedImage.SCALE_SMOOTH);;
			textureg = ImageIO.read(new File("Resource/Textures/CCHg.png")).getScaledInstance(getSize(), getSize(),
							BufferedImage.SCALE_SMOOTH);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ColourChanger(){
		super(true, false, 0, 10);
		this.textureBlue=textureb;
		this.textureGreen=textureg;
		this.textureRed=texturer;
	}
	
	@Override
	public void loadToFile(Element obj) {
		super.loadToFile(obj);
		obj.setAttribute("class", "com.freedom.gameObjects.healthOperators.ColourChanger");
	}
	
	@Override
	//TODO переделать, если будем убивать "в глубину"
	public void interact(Stuff interactor){
		interactor.setColour(this.getColour());
		if(GameField.getInstance().getRobot().container[0] == this)
			GameField.getInstance().getRobot().container[0] = null;
		else this.die();
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
