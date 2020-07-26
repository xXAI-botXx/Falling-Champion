package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public abstract class Button{

	protected int x, y;
	protected int width, height;
	protected String content;
	protected Boolean visible = true, focus = false;
	protected Color c = new Color(161, 161, 161);
	protected Color c2 = new Color(219, 219, 219);
	protected int bigX, bigY, bigWidth, bigHeight;
	
	public Button(int x, int y, int width, int height, String content) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.content = content;
		
		bigX = x - 10;
		bigY = y - 10;
		bigWidth = width + 20;
		bigHeight = height + 20;
	}
	
	public abstract void clickAction() ;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void contains(int x, int y) {
		if(visible) {
			if(x >= this.x && x <= this.x + width
				&& y >= this.y && y <= this.y + height) {
				focus = false;
				clickAction();
			}
		}
	}
	
	public void contains(int x) {
		if(visible) {
			if(x >= this.x - 25 && x <= this.x+25+width) {
				focus = false;
				clickAction();
			}
		}
	}
	
	public void hovers(int x, int y) {
		if(visible) {
			if(x >= this.x && x <= this.x + width
					&& y >= this.y && y <= this.y + height) {
				focus = true;
			}else {
				focus = false;
			}
		}
	}
	
	public void hovers(int x) {
		if(visible) {
			if(x >= this.x - 25 && x <= this.x+25+width) {
				focus = true;
			}else {
				focus = false;
			}
		}
	}
	
	public void draw(Graphics g) {
		if(visible) {
			if(!focus) {
				g.setColor(c);
				g.fillRect(x, y, width, height);
				
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 20));
				int middleX = x + (width/2) - content.length()/2*10;
				int middleY = y + (height/2) + 5;
				g.drawString(content, middleX, middleY);
			}else {
				g.setColor(c2);
				g.fillRect(bigX, bigY, bigWidth, bigHeight);
				
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 24));
				int middleX = bigX + (width/2) - content.length()/2*10;
				int middleY = bigY + (height/2) + 15;
				g.drawString(content, middleX, middleY);
				
				g.setColor(Color.BLACK);
				g.drawRect(bigX, bigY, bigWidth, bigHeight);
			}
		}
	}
	
	public void setVisible(Boolean b) {
		visible = b;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public void setFocusColor(Color c) {
		c2 = c;
	}
}
