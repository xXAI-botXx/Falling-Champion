package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Background implements GameObject{

	//Variablen
	private ArrayList<Air> airLines;
	private Air line1;
	private Air line2;
	private Air line3;
	private Air line4;
	private Air line5;
	private Color c = new Color(135, 206, 235 );
	private int width, height, x = 0, y = 0;
	
	//Konstrukor
	public Background(int width, int height){
		this.width = width;
		this.height = height;
		init();
	}
	
	//Methoden
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
		
		for(Air r:airLines) {
			r.draw(g);
		}
	}

	@Override
	public void update() {
		for(Air r:airLines) {
			r.update();
		}
	}
	
	public void init() {
		airLines = new ArrayList<Air>();
		
		line1 = new Air(width, height);
		line2 = new Air(width, height);
		line3 = new Air(width, height);
		line4 = new Air(width, height);
		line5 = new Air(width, height);
		
		airLines.add(line1);
		airLines.add(line2);
		airLines.add(line3);
		airLines.add(line4);
		airLines.add(line5);
	}

}
