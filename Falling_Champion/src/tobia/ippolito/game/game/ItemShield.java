package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;

public class ItemShield extends Item{

	private Color c = new Color(238, 154, 0);
	private String name;
	private int howLong;
	
	public ItemShield(int maxWidth, int maxHeight, Player p1, Game game) {
		super(maxWidth, maxHeight, p1, game, false);
		
		generateShield();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void effect() {
		p1.setShield(howLong);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		
		g.fillOval(x, y, durchmesser, durchmesser);
	}

	@Override
	public void update() {
		y = y + speed;
		toHigh();
	}
	
	private void generateShield() {
		int rand = (int) (Math.random() * 100);
		
		if(rand >= 0 && rand <= 50) {
			howLong = 2000;
			name = "Bad Shield";
		}else if(rand > 50 && rand <= 90) {
			howLong = 5000;
			name = "Good Shield";
		}else if(rand > 90 && rand <= 99) {
			howLong = 8000;
			name = "Perfect Shield";
		}else if(rand == 100) {
			howLong = 20000;
			name = "Shield of a God";
		}
	}

}
