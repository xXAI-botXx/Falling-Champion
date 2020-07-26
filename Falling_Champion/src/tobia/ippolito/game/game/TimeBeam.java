package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class TimeBeam implements GameObject{

	//variablen
	private float v = 0.3f;
	private float y = -100;
	private int height = 10;
	private int maxWidth, maxHeight;
	private Color c = new Color(238, 0, 0);
	private ArrayList<TimeLine> timeLines = new ArrayList<TimeLine>();
	private TimeLine line1;
	private TimeLine line2;
	private TimeLine line3;
	private TimeLine line4;
	private TimeLine line5;
	
	//Konstruktor
	public TimeBeam(int maxWidth, int maxHeight) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		
		init();
	}
	
	private void init() {
		line1 = new TimeLine(maxWidth, maxHeight, -18);
		line2 = new TimeLine(maxWidth, maxHeight, -7);
		line3 = new TimeLine(maxWidth, maxHeight, 15);
		line4 = new TimeLine(maxWidth, maxHeight, 30);
		line5 = new TimeLine(maxWidth, maxHeight, 25);
		
		timeLines.add(line1);
		timeLines.add(line2);
		timeLines.add(line3);
		timeLines.add(line4);
		timeLines.add(line5);
	}
	
	//Methoden
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(0, (int)y, maxWidth, height);
		
		for(TimeLine t:timeLines) {
			t.draw(g);
		}
	}

	@Override
	public void update() {
		y += v;
		
		for(TimeLine t:timeLines) {
			t.setYTimeBeam((int)y);
			t.update();
		}
	}
	
	public void restart() {
		y = -100;
	}
	
	public void addVelocity(float f) {
		v += f;
	}
	
	public void addY(int i) {
		i = i*-1;
		y += i;
	}
	
	public float getPosition() {
		return (y + height);
	}
}
