package tobia.ippolito.game.credits;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import tobia.ippolito.game.start.GameControl;
import tobia.ippolito.game.start.MusicManager;

public class Credits extends JPanel{
	
	//Variablen
	private final int UPDATES_PER_SECOND = 60;
	private final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SECOND;
	
	private JFrame f;
	private GameControl control;
	private int width = 800;
	private int height = 600;
	private int x;
	private Boolean breakDown = false, run = true;
	
		//Objekte
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Stone> stones = new ArrayList<Stone>();
	private ArrayList<Text> texts = new ArrayList<Text>();
	private Cursor cursor1, cursor2;
	private MusicManager musicManager;
	
		//UI - Listener
	private Listener listener;
	
	//Konstruktor
	public Credits(GameControl control, JFrame f, MusicManager musicManager) {
		this.f = f;
		this.control = control;
		this.musicManager = musicManager;
		
		//init
		generatePlayer();
		setCursor(f);
		listener = new Listener(this);	//oder lieber am anfang?
		listener.setPreferredSize(control.getSize());
		add(listener);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	
	public void back() {
		musicManager.playBackToTitle();
		
		f.setCursor(cursor1);
		run = false;
		control.backToTitle();	//cardlayout changing
	}
	
	public void setFocus() {	//muss gefocust werden
		requestFocus();
	}
	
	private void generatePlayer() {
		players.clear();
		for(int i = 0; i < 3; i++) {
			players.add(new Player(width, height, this));
		}
	}
	
	public void creditsStart() {
		musicManager.playCredits();
		
		f.setCursor(cursor2);
		run = true;
		
		//in einem Thread starten
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				creditLoop();
			}
		});
		
		t.start();
	}
	
	private void creditLoop() {
		long beginTime, timetaken, timeLeft;
		
		while(run) {
			beginTime = System.nanoTime();
			
			creditsUpdate();
			repaint();
			
			//Warten -> um die Wiederholungsrate korrekt aufrecht zu erhalten
			timetaken = System.nanoTime() - beginTime;
			timeLeft = (UPDATE_PERIOD_NSEC - timetaken) / 1000000L;		//im Millisekunden 
			if(timeLeft < 10) timeLeft = 10;	//Minimum an Wartezeit
			try {
				Thread.sleep(timeLeft);
			} catch(InterruptedException e) {System.out.println("Thread wurde beim Warten unterbrochen");}
		}
	}
	
	public void creditsUpdate() {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).update();
			if(breakDown) {
				i = 0;
				breakDown = false;
			}
		}
		
		for(int i = 0; i < stones.size(); i++) {
			stones.get(i).update();
			if(breakDown) {
				i = 0;
				breakDown = false;
			}
		}
		
		for(int i = 0; i < texts.size(); i++) {
			texts.get(i).update();
			if(breakDown) {
				i = 0;
				breakDown = false;
			}
		}
		
		//collision
		for(int i = 0; i < players.size(); i++) {
			for(int m = 0; m < stones.size(); m++) {
				if( collision(stones.get(m), players.get(i)) ) {
					texts.add(new Text(this, players.get(i).getMiddleX(), players.get(i).getMiddleY()));
					removePlayer(players.get(i));
					musicManager.playCollision();
				}
				
//				if(breakDown) {
//					m = 0;
//					breakDown = false;
//				}
			}
			if(breakDown) {
				i = 0;
				breakDown = false;
			}
		}
		
		for(int i = 0; i < texts.size()-1; i++) {
			for(int m = 1; m < texts.size(); m++) {
				texts.get(i).collision(texts.get(m).getX(), texts.get(m).getY());
			}
		}
	}
	
	public void creditsDraw(Graphics g) {
		
		for(Player p: players) {
			p.draw(g);
		}
		
		for(Stone s:stones) {
			s.draw(g);
		}
		
		g.setColor(Color.RED);
		g.drawLine(x, 0, x, 600);
		
		for(Text t:texts) {
			t.draw(g);
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 36));
		g.setColor(Color.BLACK);
		int anz = stones.size();
		g.drawString(anz+"/3", 700, 50);
	}
	
	private Boolean collision(Stone s, Player p) {
		int pX = p.getMiddleX();
		int pY = p.getMiddleY();
		int sX = s.getMiddleX();
		int sY = s.getMiddleY();
		
		int distanceX = Math.abs(pX - sX);
		int distanceY = Math.abs(pY - sY);
		
		int pR = p.getRadius();
		int sWidth = s.getWidth();
		int sHeight = s.getHeight();
		
//		if( (distanceX <= sWidth/2 && distanceY > (sHeight/2 + pR)) || (distanceY <= sHeight/2 && distanceX > (sWidth/2 + pR)) ) {
//			return true;
//		}
		
		if(distanceX > (sWidth/2 + pR) || distanceY > (sHeight/2 + pR)) {	//spieler kann nicht im bereich sein
			return false;
		}else if(distanceX <= (sWidth/2 + pR) || distanceY <= (sHeight/2 + pR) ){	//jz könnte es zu einer kollision kommen
			return true;
			//stein bleibt bestehen
		}
		return false;
	}
	
	public void setCursor(JFrame f) {
		try {
			URL url = getClass().getClassLoader().getResource("images/cursor3.png");
			BufferedImage img = ImageIO.read(url);
			Point hotSpot = new Point(0, 0);
			cursor1 = Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, "standart_cursor");
			
			url = getClass().getClassLoader().getResource("images/cursor1.png");
			img = ImageIO.read(url);
			hotSpot = new Point(0, 0);
			cursor2 = Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, "gaming_cursor");
			f.setCursor(cursor2);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removePlayer(Player p) {
		if(players.contains(p)) {
			players.remove(p);
			players.add(new Player(width, height, this));
			breakDown = true;
		}
	}
	
	public void removeStone(Stone s) {
		if(stones.contains(s)) {
			//stones.remove(s);
			//breakDown = true;
		}
	}
	
	public void removeText(Text t) {
		if(texts.contains(t)) {
			texts.remove(t);
			breakDown = true;
		}
	}
	
	private void generateStone(int x) {
		//if(stones.size() < 3) {
			musicManager.playCheck();
			//for(int m = 0; m < 10000; m++) {
				//for(int i = 0; i < 10000; i++) {
					stones.add(new Stone(x, this));
				//}
			//}
		//}
	}
	
	public void mouseClicked(int x, int y, int mouseCode) {
		
		if(mouseCode == MouseEvent.BUTTON1) {
			generateStone(x);
		}else if(mouseCode == MouseEvent.BUTTON3) {
			back();
		}
	}
	
	public void mouseMoved(int x, int y) {
		this.x = x;
	}
}
