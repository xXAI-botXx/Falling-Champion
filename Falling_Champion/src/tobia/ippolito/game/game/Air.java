package tobia.ippolito.game.game;

import java.awt.*;


public class Air implements GameObject{

	private int y, x;
	private int length;
	private int v = -20;
	private int maxWidth, maxHeight;
	private Color c = new Color(64, 64, 64);
	
	public Air(int maxWidth, int maxHeight) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		aAirShouldBorn();
	}
	
	private void aAirShouldBorn() {
		x = (int) (Math.random() * maxWidth) ;	//zwischen 0 und 800
		y = (int) (Math.random() * 2000) + maxHeight;	
		length = (int) (Math.random() * (300-5)) + 5;	
	}
	
	public void restart() {
		aAirShouldBorn();
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, 1, length);
	}

	@Override
	public void update() {
		y += v;
		
		if(y <= 0 - length) {
			aAirShouldBorn();
		}
	
	}

}
