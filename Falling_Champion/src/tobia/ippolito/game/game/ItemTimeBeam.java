package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;

public class ItemTimeBeam extends Item{
	
	private int timeJump;
	private String name;
	private Color c = new Color(72, 118, 255);

	public ItemTimeBeam(int maxWidth, int maxHeight, Player p1, Game game) {
		super(maxWidth, maxHeight, p1, game, true);
		
		generateTimeJump();
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

	@Override
	public void effect() {
		p1.setTimeBeamBack(timeJump);
	}

	@Override
	public String getName() {
		return name;
	}
	
	private void generateTimeJump() {
		int rand = (int) (Math.random() * 100);
		
		if(rand >= 0 && rand <= 50) {
			timeJump = 200;
			name = "Small Time Adder";
		}else if(rand > 50 && rand <= 90) {
			timeJump = 400;
			name = "Middle Time Adder";
		}else if(rand > 90 && rand <= 99) {
			timeJump = 800;
			name = "Big Time Adder";
		}else if(rand == 100) {
			timeJump = 2000;
			name = "Giant BF Time Adder";
		}
	}

}
