package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;

public class ItemAddLife extends Item{
	
	private String name;
	private int add;
	
	public ItemAddLife(int maxWidth, int maxHeight, Player p1, Game game) {
		super(maxWidth, maxHeight, p1, game, true);
		
		generateLifeItem();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void effect() {
		p1.addLifes(add);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		
		g.fillOval(x, y, durchmesser, durchmesser);
	}

	@Override
	public void update() {
		y = y + speed;
		toHigh();
	}
	
	private void generateLifeItem() {
		int rand = (int) (Math.random() * 100);
		
		if(rand >= 0 && rand <= 50) {
			add = 1;
			name = "Small Healing Potion";
		}else if(rand > 50 && rand <= 90) {
			add = 2;
			name = "Middle Healing Potion";
		}else if(rand > 90 && rand <= 99) {
			add = 3;
			name = "Magical Healing Potion";
		}else if(rand == 100) {
			add = 10;
			name = "Heart of an Elder";	//oder soul
		}
	}
}
