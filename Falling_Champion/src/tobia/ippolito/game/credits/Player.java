package tobia.ippolito.game.credits;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

	private Credits credits;
	private int maxWidth, maxHeight;
	private int speedY, speedX;
	private int radius, durchmesser;
	private int x, y;
	private Color c ;	//= new Color(84, 255, 159)
	
	
	public Player(int maxWidth, int maxHeight, Credits credits) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.credits = credits;
		
		generatePlayer();
	}
	
	private void generatePlayer() {
		speedY = (int) (Math.random() * 10) + 1;
		speedX = (int) (Math.random() * 10) - 5;	//-5 - 5
		y = (int) (Math.random() * 1000) - 1000;	// -1000 - 0
		x = (int) (Math.random() * 800 -radius*2) + radius;	//0 -800 (radius miteingerechnet)
		radius = (int) (Math.random() * 70) + 10;
		durchmesser = radius*2;
		
		int rand1 = (int) (Math.random() * 255);
		int rand2 = (int) (Math.random() * 255);
		int rand3 = (int) (Math.random() * 255);
		c = new Color(rand1, rand2, rand3);
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		
		g.fillOval(x - radius, y - radius, durchmesser, durchmesser);
	}
	
	public void update() {
		x += speedX;
		y += speedY;
		
		if(x - radius <= 0 ) {
			x = 0 + radius;
			speedX = speedX*-1;
		}
		
		if( x + radius >= maxWidth) {
			x = maxWidth -radius;
			speedX = speedX * -1;
		}
		
		if(y - radius >= maxHeight) {
			credits.removePlayer(this);
		}
	}
	
	public int getMiddleX() {
		return x;
	}
	
	public int getMiddleY() {
		return y;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void addX(int mult) {
		speedX = speedX * mult;
	}
}
