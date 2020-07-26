package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;

public class TimeLine implements GameObject{
	
	private int y, x;
	private int length;
	private int v = -20;
	private int maxWidth, maxHeight;
	private Color c = new Color(238, 0, 0);
	private int yTimeBeam = 0;
	private int yModifier;
	
	public TimeLine(int maxWidth, int maxHeight, int yModifier) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.yModifier = yModifier;
		aLineShouldBorn();
	}
	
	public void aLineShouldBorn() {
		x = 0;
		y = yTimeBeam + yModifier;
		x = (int) (Math.random() * 100) -100  ;	//zwischen -100 und 0
		//y = (int) (Math.random() * yTimeBeam) + yTimeBeam-20;		
		length = (int) (Math.random() * (300-5)) + 5;	
		v = (int) (Math.random() * 30)+ 10;	//von 10 bis 40
	}
	
	public void restart() {
		aLineShouldBorn();
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, length, 1);
	}

	@Override
	public void update() {
		x += v;
		y = yTimeBeam + yModifier;
		
		if(x >= maxWidth) {
			aLineShouldBorn();
		}
	
	}
	
	public void setYTimeBeam(int y) {
		yTimeBeam = y;
	}
}
