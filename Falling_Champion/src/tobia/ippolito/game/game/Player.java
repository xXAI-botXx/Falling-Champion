package tobia.ippolito.game.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tobia.ippolito.game.start.MusicManager;

public class Player implements GameObject{

	
	//variablen
	private int x, y;	//center
	private int xMiddle, yMiddle;
	private int radius, durchmesser;
	private int maxWidth, maxHeight;
	private float v, vJump;
	private Color c = new Color(84, 255, 159 );
	private Game game;
	private long beginTime, takenTime = 0, timeScore = 0; //in millisekunden
	private Timer timer;
	private int leben;
	private Thread timerThread;
	private Color colorGameover2 = new Color(154, 50, 205 );	//schreib damit gameover
	private Color colorShield = new Color(0, 255, 127);
	private Item activeItem, lastItem;
	private MusicManager musicManager;
	
	//konstruktor
	public Player(int maxWidth, int maxHeight, Game game, MusicManager musicManager) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.game = game;
		this.musicManager = musicManager;
		
		durchmesser = 50;
		radius = durchmesser/2;
		x = maxWidth/2 - radius;
		y = maxHeight/2 - radius;
		v = 70;
		vJump = 200;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, durchmesser, durchmesser);
		
		drawLife(g); 
		
		if(activeItem != null) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Item On: "+activeItem.getName(), 30, 80);
		}else {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Carries no Item", 30, 80);
		}
		

		if(lastItem != null) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 17));
			g.drawString("Last Item: "+lastItem.getName(), 30, 110);
		}else {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 17));
			g.drawString("Has no last item", 30, 110);
		}
		
		if(timer.isWorking() && timer.hasNoPause()) {
			g.setColor(colorShield);
			g.drawOval(x-4, y-4, durchmesser+8, durchmesser+8);
			g.drawOval(x-5, y-5, durchmesser+10, durchmesser+10);
			g.drawOval(x-6, y-6, durchmesser+12, durchmesser+12);
		}
	}

	@Override
	public void update() {
		xMiddle = x + radius;
		yMiddle = y + radius;
		
		takenTime = System.currentTimeMillis() - beginTime;
	}
	
	public void collisionDraw(Graphics g, int rectX, int rectY, int width, int height) {
		g.setColor(Color.RED);
		g.drawOval(x, y, durchmesser, durchmesser);
		g.fillOval(xMiddle - 2, yMiddle - 2, 4, 4);
		
		g.setColor(Color.RED);
		g.drawLine(xMiddle, yMiddle, rectX, rectY);
		
		g.drawRect(rectX-width/2 - radius, rectY-height/2 - radius, width + radius*2, height + radius*2);
		
//		g.setColor(Color.BLUE);
//		g.drawLine(xMiddle, yMiddle, rectX - width/2, yMiddle);
//		
//		g.setColor(Color.GREEN);
//		g.drawLine(rectX - width/2, yMiddle, rectX - width/2, rectY - height/2);
//		
//		g.setColor(Color.MAGENTA);
//		g.drawLine(xMiddle, yMiddle, rectX - width/2, rectY - height/2);
	}
	
	public void collisionItemDraw(Graphics g, int iX, int iY, int iR) {
		g.setColor(Color.BLUE);
		g.drawLine(iX, iY, iX, iY-iR);
		g.drawLine(xMiddle, yMiddle, xMiddle, yMiddle-radius);
		
		g.setColor(Color.BLUE);
		g.drawLine(iX, iY, xMiddle, iY);
		
		g.setColor(Color.GREEN);
		g.drawLine(xMiddle, iY, xMiddle, yMiddle);
		
		g.setColor(Color.RED);
		g.drawLine(xMiddle, yMiddle, iX, iY);
		
		g.drawOval(iX-iR, iY-iR, iR*2, iR*2);
		g.drawOval(x, y, durchmesser, durchmesser);
	}
	
	public void drawTime(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString(String.valueOf(takenTime/1000), 30, 40);
		
		//Timer
		if(timer.isWorking() && timer.hasNoPause()) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Unbesiegbar für: ", maxWidth - 150, maxHeight - 50);
			
			long timeTaken = timer.getTimeTaken();
			long timeRest = timer.getRestOfTime();
			g.drawString(String.valueOf(timeTaken/1000)+"/"+String.valueOf(timeRest/1000)+" s", maxWidth - 100, maxHeight - 30);
		}
	}
	
	public void drawLife(Graphics g) {
		g.setColor(new Color(238, 0, 0));
		switch(leben) {
		case 0:
			break;
		case 1:
			
			g.fillOval(maxWidth - 50, 30, 30, 30);
			break;
		case 2:
			g.fillOval(maxWidth - 50, 30, 30, 30);
			g.fillOval(maxWidth - 125, 30, 30, 30);
			break;
		case 3:
			g.fillOval(maxWidth - 200, 30, 30, 30);
			g.fillOval(maxWidth - 125, 30, 30, 30);
			g.fillOval(maxWidth - 50, 30, 30, 30);
			break;
		case 4:
			g.fillOval(maxWidth - 50, 30, 30, 30);
			g.fillOval(maxWidth - 125, 30, 30, 30);
			g.fillOval(maxWidth - 200, 30, 30, 30);
			g.fillOval(maxWidth - 275, 30, 30, 30);
			break;
		case 5:
			g.fillOval(maxWidth - 50, 30, 30, 30);
			g.fillOval(maxWidth - 125, 30, 30, 30);
			g.fillOval(maxWidth - 200, 30, 30, 30);
			g.fillOval(maxWidth - 275, 30, 30, 30);
			g.fillOval(maxWidth - 350, 30, 30, 30);
			break;
		default:
			g.fillOval(maxWidth - 50, 30, 30, 30);
			g.fillOval(maxWidth - 125, 30, 30, 30);
			g.fillOval(maxWidth - 200, 30, 30, 30);
			g.fillOval(maxWidth - 275, 30, 30, 30);
			g.fillOval(maxWidth - 350, 30, 30, 30);
			break;
		}
		
		//umrandung
		g.setColor(new Color(139, 0, 0));
		switch(leben) {
		case 0:
			break;
		case 1:
			g.drawOval(maxWidth - 50, 30, 30, 30);	
			break;
		case 2:
			g.drawOval(maxWidth - 50, 30, 30, 30);
			g.drawOval(maxWidth - 125, 30, 30, 30);
			break;
		case 3:
			g.drawOval(maxWidth - 50, 30, 30, 30);
			g.drawOval(maxWidth - 125, 30, 30, 30);
			g.drawOval(maxWidth - 200, 30, 30, 30);
			break;
		case 4:
			g.drawOval(maxWidth - 50, 30, 30, 30);
			g.drawOval(maxWidth - 125, 30, 30, 30);
			g.drawOval(maxWidth - 200, 30, 30, 30);
			g.drawOval(maxWidth - 275, 30, 30, 30);
			break;
		case 5:
			g.drawOval(maxWidth - 50, 30, 30, 30);
			g.drawOval(maxWidth - 125, 30, 30, 30);
			g.drawOval(maxWidth - 200, 30, 30, 30);
			g.drawOval(maxWidth - 275, 30, 30, 30);
			g.drawOval(maxWidth - 350, 30, 30, 30);
			break;
		default:
			g.drawOval(maxWidth - 50, 30, 30, 30);
			g.drawOval(maxWidth - 125, 30, 30, 30);
			g.drawOval(maxWidth - 200, 30, 30, 30);
			g.drawOval(maxWidth - 275, 30, 30, 30);
			g.drawOval(maxWidth - 350, 30, 30, 30);
			break;
		}
		
		if(leben > 5) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("+"+(leben-5), maxWidth - 420, 55);
		}
	}
	
	public void drawGameover(Graphics g) {
		//Werte -> Zeit
		g.setColor(colorGameover2);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("Game Over!", maxWidth/2 - 150, maxHeight/2 - 50);
		
		g.fillRect(260, 260, 2, 13);
		g.fillRect(270, 269, 2, 40);
		g.fillRect(290, 270, 2, 18);
		g.fillRect(295, 265, 2, 15);
		g.fillRect(322, 275, 2, 18);
		g.fillRect(330, 270, 2, 33);
		g.fillRect(337, 277, 2, 17);
		g.fillRect(348, 260, 2, 33);
		g.fillRect(380, 255, 2, 28);
		g.fillRect(394, 258, 2, 11);
		g.fillRect(419, 270, 2, 56);
		g.fillRect(430, 273, 2, 17);
		g.fillRect(438, 260, 2, 33);
		g.fillRect(455, 266, 2, 46);
		g.fillRect(459, 256, 2, 16);
		g.fillRect(466, 270, 2, 22);
		g.fillRect(475, 278, 2, 34);
		g.fillRect(488, 268, 2, 81);
		g.fillRect(497, 263, 2, 45);
		g.fillRect(516, 258, 2, 23);
		g.fillRect(525, 263, 2, 10);
		g.fillRect(538, 246, 2, 33);
		
		int changer = 30;
		
		g.drawString("Score: "+String.valueOf(timeScore/1000)+" Sekunden", maxWidth/2 - 250+changer, maxHeight/2 + 150);
		
		g.fillRect(135+changer, 440, 2, 55);
		g.fillRect(145+changer, 470, 2, 13);
		g.fillRect(158+changer, 475, 2, 44);
		g.fillRect(185+changer, 460, 2, 38);
		g.fillRect(190+changer, 470, 2, 16);
		g.fillRect(300+changer, 480, 2, 30);
		g.fillRect(330+changer, 499, 2, 20);
		g.fillRect(350+changer, 460, 2, 5);
		g.fillRect(360+changer, 468, 2, 27);
		g.fillRect(369+changer, 480, 2, 20);
		g.fillRect(600+changer, 460, 2, 20);
		g.fillRect(620+changer, 470, 2, 62);
		
	}
	
	//Handle KeyListener
	public void up() {
		y -= v;
	}
	
	public void down() {
		y += v;
	}
	
	public void right() {
		x += v;
	}
	
	public void left() {
		x -= v;
	}
	
	public void jumpUp() {
		y -= vJump;
		System.out.println("yes");
	}
	
	public void jumpDown() {
		y += vJump;
	}
	
	public void jumpRight() {
		x += vJump;
	}
	
	public void jumpLeft() {
		x -= vJump;
	}
	
	//collision detection
	public Boolean collisionWithStone(int rectX, int rectY, int width, int height) {
		int distanceX = Math.abs(xMiddle - rectX);
		int distanceY = Math.abs(yMiddle - rectY);
		
		if(distanceX > (width/2 + radius))  return false;
		if(distanceY > (height/2 + radius)) return false;
		
		if(distanceX <= (width/2 + radius)) return true;
		if(distanceY <= (height/2 + radius)) return true;
		
		return false;
	}
	
	public void collisionWithBorder() {
		//oben
		if(x < 0) {
			x = 0;
		}
		
		//unten
		if(x + durchmesser > maxWidth) {
			x = maxWidth - durchmesser;
		}
		
		//links
		if(y < 0) {
			y = 0;
		}
		
		//rechts
		if(y + durchmesser > maxHeight) {
			y = maxHeight - durchmesser;
		}
	}
	
	public Boolean collisionWithTimeBeam(float yBeam) {
		return (y <= yBeam);
	}
	
	public void startTime() {
		beginTime = System.currentTimeMillis();
	}
	
	public void setPause() {
		timer.setPause();
	}
	
	public void setTime (long t) {
		beginTime += t;
		
		timer.setTime(t);
	}
	
	public void setPosition(int x, int y) {
		this.x = x - 14;
		this.y = y - 14;
	}
	
	public int getX() {
		return xMiddle;
	}
	
	public int getY() {
		return yMiddle;
	}
	
	public int getRadius() {
		return radius;
	}

	public void reduceLifes(int i) {
		if(!timer.isWorking()) {
			leben -= i;
			
			if(leben <= 0) {
				timer.runningOff();
				timer = null;
				timerThread = null;
				timeScore = System.currentTimeMillis() - beginTime;
				game.gameover(timeScore);
			}else {
				timer.setHowLong(4000);
				timer.shouldWork();
			}
		}
	}
	
	public void addLifes(int i) {
			leben += i;
	}
	
	public void gameStarts() {
		activeItem = null;
		lastItem = null;
		
		timer = new Timer();
		timerThread = new Thread(timer);
		timerThread.setName("Timer");
		leben = 3;
		
		timerThread.start();
		
		//timerThread.notify();	//weckt ihn das auf?
		timer.setHowLong(5000);
		timer.shouldWork();
	}
	
	public void setItem(Item item) {	//item muss zusätzlich aus array genommen werden -> objekt wird nicht zerstört, da hier auf es gezeigt wird 
		if(activeItem == null) {
			activeItem = item;
			if(activeItem.getInstEffect()) {
				itemActivate();
			}
		}
	}
	
	public void itemActivate(){
		if(activeItem != null) {
			sound(activeItem);
			activeItem.activate();
			lastItem = activeItem;
			activeItem = null;	//kann wieder neu besetzt werden
		}
	}
	
	public void destroy() {
		timer.runningOff();
		timer = null;
		timerThread = null;
	}
	
	//Effekte der Items
	public void setShield(int time) {
		if(timer.isWorking()) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(timer.isWorking()) {
						System.out.println("Waiting for Timer");
					}
					timer.setHowLong(time);
					timer.shouldWork();
				}
			});
			t.start();
		}else {
			timer.setHowLong(5000);
			timer.shouldWork();
		}
	}
	
	public void setTimeBeamBack(int i) {
		game.setTimeBeamBack(i);
	}
	
	public void sound(Item i) {
		if(i.getClass() == ItemShield.class) {
			musicManager.playShield();
		}
	}
	
	public void addTime(int time) {
		beginTime -= time;
	}
}
