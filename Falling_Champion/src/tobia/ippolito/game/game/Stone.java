package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Graphics;

public class Stone implements GameObject{

	//variablen
	private Game game;
	private float y;
	private int maxWidth, maxHeight, x;
	private int width, height;
	private float v = -0.1f;
	private int min, max;
	private Color c = new Color(139, 126, 102 );
	private Boolean overLimit = false;
	
	//Konstruktor
	public Stone(Game game, int maxWidth, int maxHeight) {
		this.game = game;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		createAStone();
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, (int)y, width, height);
	}
	
	public void collisionDraw(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(x, (int)y, width, height);
		int xMiddle = x + width/2;
		int yMiddle = (int)y + height/2;
		g.fillOval(xMiddle-2, yMiddle-2, 4, 4);
	}

	@Override
	public void update() {
		y += v;
		
		if(y <= 0 - height && !overLimit) {
			game.removeStone(this);		//löscht sich und erschafft einen neuen anonymen Stein -> sollte vom GC geschreddert werden -> stimmt?
			game.setBreak();
			overLimit = true;
		}
	}
	
	public void createAStone() {
		game.setStoneSpeed(this);
		
		x = (int) (Math.random() * maxWidth) ;	
		y = (int) (Math.random() * 1000) + maxHeight;	
		height = (int) (Math.random() * 70) + 5;	//5 bis 75
		width = (int) (Math.random() * 70) + 5;	
		v = (int) (Math.random() * (max-min)) + min;	// -20 - -5 -> -20 ist min
		//v += vChanger;
		System.out.println(max+" "+min);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getMiddleX() {
		return x + width/2;
	}

	public int getMiddleY() {
		return (int)y + height/2;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setVChanger(float v) {
		this.v = v;
	}
	
	public void addSpeed(float f) {
		v += f;
	}
	
	public void setSpeed(int min, int max) {
		this.min = min;
		this.max = max;
	}
}
