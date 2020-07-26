package tobia.ippolito.game.game;

import java.awt.*;
import java.util.Formatter;

public class Ball {

	private float x, y;
	private float radius;
	private Color color;
	private static final Color DEFAULT_COLOR = Color.BLUE;
	private float speed;
	private int area = 0;	//0 = links, 1 = mitte, 2 = rechts
	private int width, height;
	
	
	public Ball(int width, int height) {
		this.width = width;
		this.height = height;
		
		radius = (int) (Math.random() * 75) + 10;
		spawnX();
		y = (int) (Math.random() * height - radius*2 - 20) + 10; 
		speed = (int) (Math.random() * 30) + 5;
		
		spawnColor();
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (x - radius), (int) (y - radius), (int) (2*radius), (int) (2*radius));
		
		g.setColor(Color.BLACK);
		//g.drawOval((int) (x - radius), (int) (y - radius), (int) (2*radius), (int) (2*radius));
	}
	
	public void update() {
		float ballMinX = 0 + radius;	//links
		float ballMinY = 0 + radius;	//oben
		float ballMaxX = width + radius;	//rechts
		float ballMaxY = height + radius;	//unten
		
		//update
		y += speed;
		
		//x collision
		if(x < ballMinX) {
			
		}else if(x > ballMaxX) {
			
		}

		//y collision
		if(y < ballMinY) {
			
		}else if(y > ballMaxY) {
			spawnNew();
		}
	}
	
	public void spawnNew() {
		radius = (int) (Math.random() * 75) + 10;
		y = 0 - radius; //Ball soll rein sliden
		spawnX(); 
		//y = (int) (Math.random() * height - radius*2 - 20) + 10; 
		speed = (int) (Math.random() * 30) + 5;
		spawnColor();
	}
	
	private void spawnX(){
		//x spawning
		int rand = (int) (Math.random()*100);	//0 - 100
		
		if(rand >= 0 && rand <= 45) {		//40 % rechts, 40% links, 20% in der Mitte
			area = 0;
		}else if(rand > 45 && rand <= 90) {
			area = 2;
		}else {
			area = 1;
		}
		
		switch(area) {
		case 0:	x = (int) (Math.random() * 300) - radius; 	//0 - 300
			break;
		case 1: x = (int) (Math.random() * (500-300) ) + 300; 	//300 - 500
			break;
		case 2:	x = (int) (Math.random() * (800-500) )+ 500 + radius; 
			break;
		default: x = (int) (Math.random() * width); 	//500 - 800
			break;
		}
	}
	
	private void spawnColor() {
		int rand1 = (int) (Math.random()*255);
		int rand2 = (int) (Math.random() * 255);
		int rand3 = (int) (Math.random() * 255);
		color = new Color(rand1, rand2, rand3);
	}
	
}
