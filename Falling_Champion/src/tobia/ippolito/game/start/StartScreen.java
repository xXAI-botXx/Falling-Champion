package tobia.ippolito.game.start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen extends JPanel{

	//Variablen
	static final int UPDATES_PER_SECOND = 60;
	static final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SECOND;
	private Thread t;
	private Cursor cursor1;
	private Cursor cursor2;
	private StartListener listener;
	private GameControl control;
	private JFrame f;
	private long timeStart;
	private boolean running = true;
	private Font font1;
	private Font font2;		//ändern
	private Titel titel;
	private Titel2 titel2;
	private float colorInt1 = 0;
	private float colorInt2 = 150;
	private float colorInt3 = 90;
	private float c1Changer = 0.1f, c2Changer = 0.08f, c3Changer = 0.2f;
	private int runs = 0;
	private MusicManager musicManager;
	
	static enum StartState{
		PREVIEW, WAITING, MENU
	}
	
	static StartState state;	
	
	//Konstruktor
	public StartScreen(GameControl control, JFrame f, MusicManager musicManager) {
		this.control = control;
		this.f = f;
		this.musicManager = musicManager;
		
		setCursor(f);
		f.setCursor(cursor2);
		//this.setLayout(null);
		this.setPreferredSize(new Dimension(800, 600));
		
		listener = new StartListener(this, control, musicManager);
		listener.setPreferredSize(new Dimension(800, 600));
		this.add(listener);
		this.addMouseListener(listener);
		
		titel = new Titel();
		titel2 = new Titel2();
		
		timeStart = System.currentTimeMillis();	//soll nur das allererstemal sein
		
		font1 = new Font("Arial", Font.ITALIC, 24);
		font2 = new Font("Adobe Devanagari", Font.PLAIN, 72);	//72	//36
		//font2.getSize();
		
		state = StartState.PREVIEW;
	}
	
	public void start() {
		running = true;
		
		if(runs <= 0) {
			runs++;
			f.setCursor(cursor2);
		}else {
			runs++;
			f.setCursor(cursor1);
		}
		
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				startLoop();
			}
		});
		t.setName("Start_Screen");
		t.start();
	}
	
	public void startLoop() {
		long beginTime, timetaken, timeLeft; //in millisekunden
		
		while(running) {
			beginTime = System.nanoTime();
			
			
			startUpdate();
			repaint();	//refresh the display
			
			//Warten -> um die Wiederholungsrate korrekt aufrecht zu erhalten
			timetaken = System.nanoTime() - beginTime;
			timeLeft = (UPDATE_PERIOD_NSEC - timetaken) / 1000000L;		//im Millisekunden 
			if(timeLeft < 10) timeLeft = 10;	//Minimum an Wartezeit
			try {
				Thread.sleep(timeLeft);
			} catch(InterruptedException e) {System.out.println("Thread wurde beim Warten unterbrochen");}
		}
	}
	
	public void startUpdate() {
		timeProof();
		
		if(state == StartState.PREVIEW) {
			titel.update();
			titel2.update();
		}else if(state == StartState.WAITING) {
			setColorChanger();
			colorInt1 += c1Changer;
			colorInt2 += c2Changer;
			colorInt3 += c3Changer;
		}else if(state == StartState.MENU) {
			setColorChanger();
			colorInt1 += c1Changer;
			colorInt2 += c2Changer;
			colorInt3 += c3Changer;
		}
	}
	
	public void drawStart(Graphics g) {

		if(state == StartState.PREVIEW) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			titel.draw(g);
			titel2.draw(g); 
		}else if(state == StartState.WAITING) {
			g.setColor(new Color((int) colorInt1, (int) colorInt2, (int) colorInt3));
			g.fillRect(0, 0, 800, 600);
			   
			g.setFont(font2);	
			g.setColor(Color.BLACK);
			g.drawString("Falling Champion", 120, 275);	//150
			
//			String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//		    for ( int i = 0; i < fonts.length; i++ ){
//		      System.out.println(fonts[i]);
//		    }
			
			g.setFont(font1);
			g.setColor(Color.BLACK);
			g.drawString("Press any Mouse Button to Continue", 195, 325);
		}else if(state == StartState.MENU) {
			g.setColor(new Color((int) colorInt1, (int) colorInt2, (int) colorInt3));	//new Color(198, 226, 255)
			g.fillRect(0, 0, 800, 600);
		}
	}
	
	private void timeProof() {
		if(state == StartState.PREVIEW) {
			long pastTime = (System.currentTimeMillis() - timeStart) / 1000;
			//System.out.println(pastTime);
			if((System.currentTimeMillis() - timeStart) >= 5000) {
				state = StartState.WAITING;
				musicManager.playWait();
			}
			
			if( pastTime == 4) {
				titel.setEvent3();
				titel2.setEvent3();
			}
		}
	}
	
	public void setFocus() {	//muss gefocust werden
		requestFocus();
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
	
	public void setCursorNow(int i) {
		f.setCursor(cursor1);
	}
	
	public void setColorChanger() {
		if(colorInt1 + c1Changer >= 255) {
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
	
	public void setOff() {
		running = false;
	}
	
	//Mouse listening
	public void mouseClicked() {
		if(state == StartState.WAITING) {
			state = StartState.MENU;
			listener.viewMenu();
			f.setCursor(cursor1);
			musicManager.playCheck();
			//musicManager.playMenu();
		}
	}
}
