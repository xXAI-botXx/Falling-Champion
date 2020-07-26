package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;

public class ItemTimelessJewel extends Item{

	private String name;
	private int time;
	private float colorInt1, colorInt2, colorInt3;
	private float c1Changer = 2, c2Changer = 1, c3Changer =3;
	
	public ItemTimelessJewel(int maxWidth, int maxHeight, Player p1, Game game) {
		super(maxWidth, maxHeight, p1, game, true);
	
		colorInt1 = (int) (Math.random() * 255);
		colorInt2 = (int) (Math.random() * 255);
		colorInt3 = (int) (Math.random() * 255);
		
		generateJewel();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color((int)colorInt1, (int) colorInt2, (int) colorInt3));
		
		g.fillOval(x, y, durchmesser, durchmesser);
	}

	@Override
	public void update() {
		y = y + speed;
		toHigh();
		
		setColorChanger();
		colorInt1 += c1Changer;
		colorInt2 += c2Changer;
		colorInt3 += c3Changer;
	}

	@Override
	public void effect() {
		p1.addTime(time);
	}

	@Override
	public String getName() {
		return name;
	}
	
	private void generateJewel() {
		int rand = (int) (Math.random() * 100);
		
		if(rand >= 0 && rand <= 50) {
			time = 5000;
			name = "Sparkling Jewel";
		}else if(rand > 50 && rand <= 95) {
			time = 10000;
			name = "Shiny Jewel";
		}else if(rand > 95 && rand <= 99) {
			time = 50000;
			name = "Mystical Jewel";
		}else if(rand == 100) {
			time = 100000;
			name = "Timeless Jewel";
		}
	}
	

	private void setColorChanger() {
		if(colorInt1 + c1Changer>= 255) {
			c1Changer = c1Changer*-1;
		}else if(colorInt1 + c1Changer <= 0) {
			c1Changer = c1Changer*-1;
		}
		
		if(colorInt2 + c2Changer >= 255) {
			c2Changer = c2Changer*-1;
		}else if(colorInt2 + c2Changer <= 0) {
			c2Changer = c2Changer*-1;
		}
		
		if(colorInt3 + c3Changer>= 255) {
			c3Changer = c3Changer*-1;
		}else if(colorInt3 + c3Changer <= 0) {
			c3Changer = c3Changer*-1;
		}
	}
}
