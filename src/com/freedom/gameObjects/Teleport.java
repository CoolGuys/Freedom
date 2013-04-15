package com.freedom.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.w3c.dom.Element;

public class Teleport extends Stuff {

		
		private Image textureOpen;
		private Image textureClosed;
		
		private int xLeadTo;
		private int yLeadTo;
		
		protected int getXLeadTo(){
			return xLeadTo;
		}
		protected int getYLeadTo(){
			return yLeadTo;
		}
		
		
		//телепорт - хрупкая сущность.
		public Teleport(){
			super(false, false,0,1);
			try {
				textureClosed = ImageIO.read(new File("Resource/Textures/TeleportClosed.png"));
				textureOpen = ImageIO.read(new File("Resource/Textures/TeleportOpen.png"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			texture = textureClosed;
		}
		
		public void readLvlFile(Element obj) {
			this.x=Integer.parseInt(obj.getAttribute("x"));
			this.y=Integer.parseInt(obj.getAttribute("y"));
			this.xLeadTo = Integer.parseInt(obj.getAttribute("xLeadTo"));
			this.yLeadTo = Integer.parseInt(obj.getAttribute("yLeadTo"));
		}
		
		/**
		 * Метод, который добавляет инфу в файл
		 * если вы хотите чтоб всё работало пихайте такие методы везде где стафф!
		 * @author UshAle
		 */
		public void loadToFile(Element obj) {
			obj.setAttribute("x", String.valueOf((int)this.x));
			obj.setAttribute("y", String.valueOf((int)this.y));
			obj.setAttribute("xLeadTo", String.valueOf((int)this.xLeadTo));
			obj.setAttribute("yLadTo", String.valueOf((int)this.yLeadTo));
			obj.setAttribute("class","com.freedom.gameObjects.Teleport");
		} 
	

		protected void buttonPressed() {
			if (super.passable) {
					texture = textureClosed;
				super.passable = false;
			} else {
				texture = textureOpen;
				super.passable = true;
			}
		}
		
		
		
		public void draw(Graphics g) {
			g.drawImage(texture, (int)(getX()*getSize()), (int)(getY()*getSize()), getSize(), getSize(), null);
		}
		
		

	}