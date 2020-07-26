package tobia.ippolito.game.game;

public abstract class Item implements GameObject{

	protected int x, y;
	protected int speed;
	protected int radius, durchmesser;
	protected int maxWidth, maxHeight;
	protected Player p1;
	protected Boolean instaEffect;
	protected Game game;
	
	public Item(int maxWidth, int maxHeight, Player p1, Game game, Boolean instaEffect) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.p1 = p1;
		this.game = game;
		this.instaEffect = instaEffect;
		
		x = (int) (Math.random() * maxWidth - radius);
		y = (int) (Math.random() * (maxHeight*10-maxHeight) ) + maxHeight;	//maxheight - maxHeight*5
		radius = (int) (Math.random() * 10) + 10;
		durchmesser = radius * 2;
		speed = (int) (Math.random() * 4) - 5;	//-5 - -1
	}
	
	public void toHigh() {
		if(y <= 0 - durchmesser) {
			game.removeItem(this);
		}
	}
	
	public void activate() {
			effect();
	}
	
	public abstract void effect();
	
	public abstract String getName();
	
	public int getX() {
		return x + radius;
	}
	
	public int getY() {
		return y + radius;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public Boolean getInstEffect() {
		return instaEffect;
	}
}
