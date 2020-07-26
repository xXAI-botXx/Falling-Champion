package tobia.ippolito.game.credits;

import java.awt.Color;
import java.awt.Graphics;

public class Stone {
	
	private int x, y, width, height;
	private Color c = new Color(139, 126, 102);
	private Credits credits;

	public Stone(int x, Credits credits) {
		this.credits = credits;
		
		height = (int) (Math.random() * 70) + 5;	//5 bis 75
		width = (int) (Math.random() * 70) + 5;	
		
		y = 550 - height/2;
		this.x = x - width/2;
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		
		g.fillRect(x, y, width, height);
		
		//g.setColor(Color.RED);
		//g.fillOval(x + width/2 -2, y + height/2 -2, 4, 4);
	}
	
	public void update() {
		y -= 3;
		
		if(y + height <= 0) {
			credits.removeStone(this);
		}
	}
	
	public int getMiddleX() {
		return x + width/2;
	}
	
	public int getMiddleY() {
		return y + height/2;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
