package tobia.ippolito.game.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Titel {

	private int x, y;
	private int speedX = 0, speedY = 0;
	private int size = 36;
	private String titel = "Timerift";
	private boolean event1 = true, event2 = false, event3 = false;
	private int minY, maxY;
	private boolean up = true;
	private Font font = new Font("Arial", Font.PLAIN, size);	//emulogic Cooper Black
	
	public Titel() {
		x = 800;
		y = 300;
		
		maxY = 325;
		minY = 275;
		
		speedX = -10;
	}
	
	
	public void draw(Graphics g) {
		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString(titel, x, y);
		
		if(event1) {
			g.drawLine(x + 40, y - 40, x + 80, y - 40);
			g.drawLine(x + 5, y + 9, x + 15, y + 9);
			g.drawLine(x - 15, y + 5, x - 5, y + 5);
			g.drawLine(x + 90, y + 14, x + 130, y + 14);
			g.drawLine(x + 200, y + 8, x + 240, y + 8);
			g.drawLine(x + 220, y + 15, x + 228, y + 15);
			g.drawLine(x + 268, y, x + 330, y);
			g.drawLine(x + 253, y - 5, x + 267, y - 5);
			g.drawLine(x + 245, y - 18, x + 300, y - 18);
			g.drawLine(x + 180, y - 33, x + 200, y - 33);
			g.drawLine(x + 197, y - 38, x + 255, y - 38);
		}
	}
	
	public void update() {
		y += speedY;
		x += speedX;
		
		if(x == 400 - 140) {
			speedX = 0;
			event1 = false;
		}
		
		if(event2) {	//wird nichtmerh benötigt
			if(up) {
				if(y < maxY) {
					y += 15;
				}else {
					up = false;
				}
			}else {
				if(y > minY) {
					y -= 15;
				}else {
					up = true;
				}
			}
		}
		
		if(event3) {
			speedY = 20;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setEvent2() {
		event2 = true;
	}
	
	public void setEvent3() {
		event2 = false;
		event3 = true;
	}
}
