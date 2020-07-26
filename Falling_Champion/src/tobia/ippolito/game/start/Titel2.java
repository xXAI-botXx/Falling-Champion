package tobia.ippolito.game.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Titel2 {

	private int x, y;
	private int speedX = 0, speedY = 0;
	private int size = 36;
	private String titel = "Studio";
	private boolean event2 = false, event3 = false;
	private int maxY, minY;
	private boolean up = false;
	private Font font = new Font("Arial", Font.PLAIN, size);
	
	public Titel2() {
		x = 800 + 140;
		y = 300;
		
		maxY = 325;
		minY = 275;
		
		speedX = -10;
	}
	
	
	public void draw(Graphics g) {
		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString(titel, x, y);
	}
	
	public void update() {
		y += speedY;
		x += speedX;
		
		if(x == 400) {
			speedX = 0;
		}
		
		if(event2) {
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
			speedY = -20;
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
