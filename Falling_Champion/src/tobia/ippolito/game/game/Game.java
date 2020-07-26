package tobia.ippolito.game.game;

import javax.imageio.ImageIO;
import javax.swing.*;

import tobia.ippolito.game.start.GameControl;
import tobia.ippolito.game.start.MusicManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Game extends JPanel{

	//Variablen
		//konstanten für das spiel
	private final int UPDATES_PER_SECOND = 60;
	private final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SECOND;	//nanosec -> 1 Sekunde wird durch die Anzahl der Frames Per Seconds geteilt -> man erhält die dauer für einen durchgang 
	private final String scorePfad = "C:\\Tobia\\Zeitmanager\\Abitur 2020 - Ostern_17_03_2020-\\CT-mündlich\\Java Speichermanagment\\Saves";
	
		//zustände des spiels
	static enum GameState{
		INITIALIZED, DIFFICULTY, PLAYING, PAUSED, GAMEOVER, DESTROYED
	}
	
	static GameState state;		//zustände -> öffentlich
	private Thread gameThread = null;
	private GameControl control;
	private GameCanvas renderer;	//steuerung der angepassten Zeichnens per panel
	private int width, height;
	private Boolean stoneUpdating = true;	//for going through a arrayList and change something and go again through
	private Boolean breakingDown = false, showCollisionStones = false, showCollisionItems = false, stop = false, scoreshow = false;
	private long pauseBegin = 0;
	private long pauseEnd = 0;
	private float colorInt1 = 255;
	private float colorInt2 = 187;
	private float colorInt3 = 255;
	private float c1Changer = 0.1f, c2Changer = 0.2f, c3Changer = 0.3f;
	private int anzStones;
	private boolean scoreAvaible = false;
	private int yourScore = 0;
	private int min, max;
	
		//UI
	private Button btnBackToGame;
	private Button btnBackToTitle;
	private Button btnExit;
	private Button btnOptions;
	private Button btnBack;
	private Button btnCollisionShowStones;
	private Button btnCollisionShowItems;
	private Button btnGC;
	private Button btnGameover;
	//private Button btnScoreSave;
	private Button btnScorelistShow;
	private Button btnDifficulty1;
	private Button btnDifficulty2;
	private Button btnDifficulty3;
	private Button btnDifficulty4;
	private int difficulty = 0;
	private Cursor cursor1, cursor2;
	private JFrame f;
	private Color colorGameover1 = new Color(188, 210, 238);
	private ArrayList<String> scores = new ArrayList<String>();
	
		//Spiele-objekte
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();	//Player, background, TimeBeam
	private ArrayList<Stone> stones = new ArrayList<Stone>();	
	private ArrayList<Ball> balls = new ArrayList<Ball>();	//für Gameover
	private Background background;
	private TimeBeam timebeam;
	private Player player;
	private ArrayList<Item> items = new ArrayList<Item>();
	private MusicManager musicManager;
		
		
	//Konstruktor -> init der UI-Komponenten und der Spiel-Objekte
	public Game(GameControl control, JFrame f, MusicManager musicManager) {
		this.f = f;
		this.musicManager = musicManager;
		//cursor setzten
		setCursor(f);
		
		width = (int) control.getSize().getWidth();	
		height = (int) control.getSize().getHeight();	
		//init der Spiele-Objekte
		gameInit(control);
		this.control = control;
		
		//UI-Komponenten
		renderer = new GameCanvas(this);	//angepasstes zeichnen
		renderer.setPreferredSize(control.getSize());
		this.add(renderer);		//als JPanel zum rendern
		this.addKeyListener(renderer);	//als keyListener
		this.addMouseListener(renderer);
		this.addMouseMotionListener(renderer);
		
		
	}
	
	public void gameInit(GameControl control) {
		//Spiele-objekte erzeugen
		background = new Background(width, height);
		timebeam = new TimeBeam(width, height);
		player = new Player(width, height, this, musicManager);
		
		//adden
		gameObjects.add(background);
		gameObjects.add(player);
		gameObjects.add(timebeam);
		
		initButtons();
		initItems();
		
		state = GameState.INITIALIZED;
	}
	
	private void initButtons() {	
		//pause menu buttons
		btnBackToGame = (new Button(width/2 - 100, 50, 200, 50, "continue") {	//innere anonyme Klasse -> erbt von button
			@Override
			public void clickAction() {
				musicManager.playCheck();
				pause();
			}
		});
		
		btnBackToTitle = (new Button(width/2 - 100, 200, 200, 50, "Back to the Title") {
			@Override
			public void clickAction() {
				musicManager.playBackToTitle();
				player.destroy();
				state = GameState.DESTROYED;
				control.backToTitle();
			}
		});
		
		btnOptions = (new Button(width/2 - 100, 350, 200, 50, "Options") {
			@Override
			public void clickAction() {
				musicManager.playCheck();
				showOptionMenu();
			}
		});
		
		btnExit = (new Button(width/2 - 100, 500, 200, 50, "Exit") {
			@Override
			public void clickAction() {
				musicManager.playCheck();
				System.exit(0);
				
			}
		});
		
		//option Menu button
		btnCollisionShowStones = (new Button(200, 50, 400, 50, "Collision Stones Show") {	//innere anonyme Klasse -> erbt von button
			@Override
			public void clickAction() {
				musicManager.playCheck();
				if(showCollisionStones) {
					showCollisionStones = false;
					btnCollisionShowStones.setFocusColor(Color.RED);
				}else {
					showCollisionStones = true;
					btnCollisionShowStones.setFocusColor(Color.GREEN);
				}
			}
		});
		btnCollisionShowStones.setVisible(false);
		
		btnCollisionShowItems = (new Button(200, 150, 400, 50, "Collision Items Show") {	//innere anonyme Klasse -> erbt von button
			@Override
			public void clickAction() {
				musicManager.playCheck();
				if(showCollisionItems) {
					showCollisionItems = false;
					btnCollisionShowItems.setFocusColor(Color.RED);
				}else {
					showCollisionItems = true;
					btnCollisionShowItems.setFocusColor(Color.GREEN);
				}
			}
		});
		btnCollisionShowItems.setVisible(false);
		
		btnGC = (new Button(200, 250, 400, 50, "Stonelist delete") {	//innere anonyme Klasse -> erbt von button
			@Override
			public void clickAction() {
				musicManager.playCheck();
				stones = null;
				System.gc();
			}
		});
		btnGC.setVisible(false);
		
		btnBack = (new Button(200, 250, 400, 50, "<-") {	//innere anonyme Klasse -> erbt von button
			@Override
			public void clickAction() {
				musicManager.playCheck();
				showPauseMenu();
			}
		});
		btnBack.setVisible(false);
		
		//Gameover
		btnGameover = (new Button(30, 40, 50, 50, "<-") {
			@Override
			public void clickAction() {
				musicManager.playBackToTitle();
				control.backToTitle();
				state = GameState.DESTROYED;
			}
		});
		
		btnScorelistShow = (new Button(350, 40, 100, 50, "Scores") {
			@Override
			public void clickAction() {
				musicManager.playCheck();
				if(!scoreAvaible) System.out.println("Watch the 'scorePfad'!");
				if(scoreshow) {
					scoreshow = false;
				}else {
					scoreshow = true;
				}
			}
		});
		
		//difficulty
		btnDifficulty1 = (new Button(25, height/2-25, 150, 50, "easy") {	//innere anonyme Klasse -> erbt von button
			@Override
			public void clickAction() {
				musicManager.playCheck();
				difficulty = 1;
				anzStones = 4;	//4
				min = -6;
				max = -1;
				state = GameState.PLAYING;
				initStones();
				player.startTime();
				player.gameStarts();
				//vlt inits erst jz? -> außer buttons
			}
		});
		
		btnDifficulty2 = (new Button(225, height/2-25, 150, 50, "makeable") {
			@Override
			public void clickAction() {
				musicManager.playCheck();
				difficulty = 2;
				anzStones = 5;
				min = -10;
				max = -2;
				state = GameState.PLAYING;
				initStones();
				player.startTime();
				player.gameStarts();
			}
		});
		
		btnDifficulty3 = (new Button(425, height/2-25, 150, 50, "heavy") {
			@Override
			public void clickAction() {
				musicManager.playCheck();
				difficulty = 3;
				anzStones = 20;
				min = -15;
				max = -4;
				state = GameState.PLAYING;
				initStones();
				player.startTime();
				player.gameStarts();
			}
		});
		
		btnDifficulty4 = (new Button(625, height/2-25, 150, 50, "godlike") {
			@Override
			public void clickAction() {
				musicManager.playCheck();
				difficulty = 4;
				anzStones = 30;
				min = -20;
				max = -5;
				state = GameState.PLAYING;
				initStones();
				player.startTime();
				player.gameStarts();
			}
		});
	}
	
	private void initStones() {
		for(int i = 0; i < anzStones; i++) {	
			stones.add(new Stone(this, width, height));
		}
	}
	
	private void initItems() {
		for(int i = 0; i < 4; i++) {	//4
			items.add(generiereItem());
		}
	}
	
	private Item generiereItem() {
		int rand = (int) (Math.random() * 100);
		
		if(rand >= 0 && rand <= 40) {
			return new ItemShield(width, height, player, this);
		}else if(rand > 40 && rand <= 70){
			return new ItemTimeBeam(width, height, player, this);	
		}else if(rand > 70 && rand <= 90){
			return new ItemAddLife(width, height, player, this);	
		}else if(rand > 90 && rand <= 100){
			return new ItemTimelessJewel(width, height, player, this);	
		}else {
			return new ItemTimeBeam(width, height, player, this);
		}
	}
	
	public void setCursor(JFrame f) {
		try {
			URL url = getClass().getClassLoader().getResource("images/cursor3.png");
			BufferedImage img = ImageIO.read(url);
			Point hotSpot = new Point(0, 0);
			cursor1 = Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, "standart_cursor");
			f.setCursor(cursor1);
			
			url = getClass().getClassLoader().getResource("images/cursor1.png");
			img = ImageIO.read(url);
			hotSpot = new Point(0, 0);
			cursor2 = Toolkit.getDefaultToolkit().createCustomCursor(img, hotSpot, "gaming_cursor");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeStone(Stone s) {
		if(stones.contains(s)) {
			stones.remove(s);
			stones.add(new Stone(this, width, height));
		}
	}
	
	public void removeItem(Item i) {
		if(items.contains(i)) {
			items.remove(i);
			items.add(generiereItem());
		}
	}
	
	public void setFocus() {	//muss gefocust werden
		requestFocus();
	}
	
	//Shutdown the game -> räumt auf -> nötig?
	public void gameShutdown() {
		//...
	}
	
	//start / restart the game
	public void gameStart() {
		musicManager.playGame();
		//cursor ändern
		f.setCursor(cursor2);
		
		try {
			scoreDateiTest();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//Create new Thread -> wegen UI
		
		timebeam.restart();
		
		
		gameThread = new Thread() {	
			@Override
			public void run() {
				gameLoop();
			}
		};
		gameThread.setName("Game");
		gameThread.start();
		
	}
	
	//Gameloop -> aufgerufen von dem thread -> beinhaltet ein game
	public void gameLoop() {
		//Regenerate the game-objects for a new game
		//...
		state = GameState.DIFFICULTY;
		
		//Gameloop
		long beginTime, timetaken, timeLeft; //in millisekunden
		
		while(state != GameState.DESTROYED) {
			if(state != GameState.GAMEOVER && state != GameState.DIFFICULTY) {
				beginTime = System.nanoTime();
				
				if(!stop) {		//freeze the Game
					gameUpdate();
					repaint();	//refresh the display
				}
				
				//Warten -> um die Wiederholungsrate korrekt aufrecht zu erhalten
				timetaken = System.nanoTime() - beginTime;
				timeLeft = (UPDATE_PERIOD_NSEC - timetaken) / 1000000L;		//im Millisekunden 
				if(timeLeft < 10) timeLeft = 10;	//Minimum an Wartezeit
				try {
					Thread.sleep(timeLeft);
				} catch(InterruptedException e) {System.out.println("Thread wurde beim Warten unterbrochen");}
			}else if(state == GameState.GAMEOVER){	//gameover
				beginTime = System.nanoTime();
			
				gameoverUpdate();
				repaint();
				
				//Warten -> um die Wiederholungsrate korrekt aufrecht zu erhalten
				timetaken = System.nanoTime() - beginTime;
				timeLeft = (UPDATE_PERIOD_NSEC - timetaken) / 1000000L;		//im Millisekunden 
				if(timeLeft < 10) timeLeft = 10;	//Minimum an Wartezeit
				try {
					Thread.sleep(timeLeft);
				} catch(InterruptedException e) {System.out.println("Thread wurde beim Warten unterbrochen");}
			}else if(state == GameState.DIFFICULTY) {	//choicing difficulty
				beginTime = System.nanoTime();
				
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
		
	}
	
	//updating
	public void gameUpdate() {
		if(state == GameState.PLAYING) {
			//GameObjects Updaten
			for(GameObject g: gameObjects) {
				g.update();
			}
			
			for(int i = 0; i < stones.size(); i++) {
				stones.get(i).update();
				if(breakingDown) {
					i = 0;
					breakingDown = false;
				}
			}
			//stoneSpeed += speedChanger;
			
			for(int i = 0; i < items.size(); i++) {
				items.get(i).update();
				if(breakingDown) {
					i= 0;
					breakingDown = false;
				}
			}
			
			//Collision-detection
			for(Stone s:stones) {
				if( player.collisionWithStone(s.getMiddleX(), s.getMiddleY(), s.getWidth(), s.getHeight()) ) {
					//System.out.println("Player dies on STONE!");
					player.reduceLifes(1);
					musicManager.playCollision();
				}
			}
			
			player.collisionWithBorder();
			
			if(player.collisionWithTimeBeam(timebeam.getPosition())) {
				//System.out.println("Player dies on TIMEBEAM!");
				player.reduceLifes(2);
				musicManager.playTimebeamDeath();
			}
			
			for(int i = 0; i < items.size(); i++) {
				collisionPlayerItem(items.get(i));
				if(breakingDown) {
					i= 0;
					breakingDown = false;
				}
			}
			
		}else if(state == GameState.PAUSED) {
			
			setColorChanger();
			colorInt1 += c1Changer;
			colorInt2 += c2Changer;
			colorInt3 += c3Changer;
		}
	}
	
	public void gameDraw(Graphics g) {
		g.clearRect(0, 0, width, height);
		
		for(GameObject go: gameObjects) {
			go.draw(g);
		}
		
		for(Stone s:stones) {
			s.draw(g);	
			if(showCollisionStones) {
				s.collisionDraw(g);
				player.collisionDraw(g, s.getMiddleX(), s.getMiddleY(), s.getWidth(), s.getHeight());
			}
		}
		
		for(Item i:items) {
			i.draw(g);
			if(showCollisionItems) {
				player.collisionItemDraw(g, i.getX(), i.getY(), i.getRadius());
			}
		}
		
		player.drawTime(g);	//soll alles überschreiben
	
	}
	
	public void gameoverUpdate() {
		for(Ball b:balls) {
			b.update(); 
		}
	}
	
	public void gameoverDraw(Graphics g) {
		g.clearRect(0, 0, width, height);
		
		g.setColor(colorGameover1);
		g.fillRect(0, 0, width, height);
		
		btnGameover.draw(g);
		btnScorelistShow.draw(g);
		
		if(!scoreshow) {
			player.drawGameover(g); 
		}else {
			drawScore(g);
		}
		
		for(Ball b:balls) {
			b.draw(g);
		}
	}
	
	public void drawPause(Graphics g) {
		g.setColor(new Color((int)colorInt1, (int)colorInt2, (int)colorInt3)); 
		g.fillRect(0, 0, width, height);
		
		btnBackToGame.draw(g);
		btnBackToTitle.draw(g);
		btnExit.draw(g); 
		btnOptions.draw(g);
		
		btnBack.draw(g);
		btnCollisionShowStones.draw(g);
		btnCollisionShowItems.draw(g);
		btnGC.draw(g);
		
		player.drawTime(g);	//soll alles überschreiben
	}
	
	public void drawChoice(Graphics g) {
		btnDifficulty1.draw(g);
		btnDifficulty2.draw(g);
		btnDifficulty3.draw(g);
		btnDifficulty4.draw(g);
	}
	
	public void drawScore(Graphics g) {
		g.setColor(Color.BLACK);
		
		int y = 250;
		int position = 1;
		
		for(String s:scores) {
			g.setColor(Color.BLACK);
			
			if(yourScore == scores.indexOf(s)) {
				g.setColor(new Color(154, 50, 205));
				
				int yPoints[] = {y-30, y- 30+30, y- 30+15};
				int xPoints[] = {120,120,140}; 
				
				g.fillPolygon(xPoints, yPoints, 3);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 36));
			g.drawString("Score "+position+": ", 150, y);
			
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString(s+" Sekunden", 150+300, y);
			y += 50;
			position++;
		}
	}
	
	public void pause() {	//show menu -> vlt jpanel switchen
		if(state == GameState.PAUSED && !stop) {
			f.setCursor(cursor2);
			state = GameState.PLAYING;
			pauseEnd = System.currentTimeMillis();
			player.setTime(pauseEnd - pauseBegin);
		}else if(state == GameState.PLAYING && !stop){
			f.setCursor(cursor1);
			state = GameState.PAUSED;
			pauseBegin = System.currentTimeMillis();
			player.setPause();
		}
	}
	
	//key-listening
	public void gameKeyPressed(int keyCode) {
		switch(keyCode) {
		//W-A-S-D
		case KeyEvent.VK_W:
			player.up();
			break;
		case KeyEvent.VK_S:
			player.down();
			break;
		case KeyEvent.VK_A:
			player.left();
			break;
		case KeyEvent.VK_D:
			player.right();
			break;
			
		//Pfeil-Tasten
		case KeyEvent.VK_UP:
			player.jumpUp();
			break;
		case KeyEvent.VK_DOWN:
			player.jumpDown();
			break;
		case KeyEvent.VK_LEFT:
			player.jumpLeft();
			break;
		case KeyEvent.VK_RIGHT:
			player.jumpRight();
			break;
			
		case KeyEvent.VK_SPACE:
			//pause();
			stop();
			break;
		}
	}
	
	//Mouse listening
	public void mouseClicked(int x, int y, int mouseCode) {
		
		if(mouseCode == MouseEvent.BUTTON3) {
			pause();
		}
		
		if(mouseCode == MouseEvent.BUTTON1 && state == GameState.PLAYING && !stop) {
			player.itemActivate();
		}
		
		if(mouseCode == MouseEvent.BUTTON2 && state == GameState.PLAYING) {
			stop();
		}
		
		if(state == GameState.PAUSED && mouseCode == MouseEvent.BUTTON1) {
			btnBackToGame.contains(x, y); 
			btnBackToTitle.contains(x, y);
			btnExit.contains(x, y);
			btnOptions.contains(x, y);
			
			btnBack.contains(x, y); 
			btnCollisionShowStones.contains(x, y);
			btnCollisionShowItems.contains(x, y);
			btnGC.contains(x, y);
			
		}else if(state == GameState.GAMEOVER && mouseCode == MouseEvent.BUTTON1) {
			btnGameover.contains(x, y);
			btnScorelistShow.contains(x, y);
		}else if(state == GameState.DIFFICULTY && mouseCode == MouseEvent.BUTTON1) {
			btnDifficulty1.contains(x);
			btnDifficulty2.contains(x);
			btnDifficulty3.contains(x);
			btnDifficulty4.contains(x);
		}
	}
	
	public void mouseMoved(int x, int y) {
		player.setPosition(x, y);
		
		if(state == GameState.PAUSED) {
			btnBackToGame.hovers(x, y); 
			btnBackToTitle.hovers(x, y);
			btnExit.hovers(x, y);
			btnOptions.hovers(x, y); 
			btnBack.hovers(x, y);
			btnCollisionShowStones.hovers(x, y);
			btnCollisionShowItems.hovers(x, y);
			btnGC.hovers(x, y);
		}else if(state == GameState.GAMEOVER) {
			btnGameover.hovers(x, y); 
			btnScorelistShow.hovers(x, y);
		}else if(state == GameState.DIFFICULTY) {
			btnDifficulty1.hovers(x);
			btnDifficulty2.hovers(x);
			btnDifficulty3.hovers(x);
			btnDifficulty4.hovers(x);
			
		}
	}
	
	//Other methods
	public void setBreak() {
		breakingDown = true;
	}
	
	public void stop() {
		if(state == GameState.PLAYING) {
			if(stop) {
				pauseEnd = System.currentTimeMillis();
				player.setTime(pauseEnd - pauseBegin);
				stop = false;
			}else {
				pauseBegin = System.currentTimeMillis();
				player.setPause();
				stop = true;
			}
		}
	}
	
	public void showOptionMenu() {
		btnExit.setVisible(false);
		btnBackToGame.setVisible(false);
		btnBackToTitle.setVisible(false);
		btnOptions.setVisible(false);
		
		btnBack.setVisible(true);
		btnCollisionShowStones.setVisible(true);
		btnCollisionShowItems.setVisible(true);
		btnGC.setVisible(true);
	}
	
	public void showPauseMenu() {
		btnExit.setVisible(true);
		btnBackToGame.setVisible(true);
		btnBackToTitle.setVisible(true);
		btnOptions.setVisible(true);
		
		btnBack.setVisible(false);
		btnCollisionShowStones.setVisible(false);
		btnCollisionShowItems.setVisible(false);
		btnGC.setVisible(false);
	}
	
	public void collisionPlayerItem(Item i) {
		int pX = player.getX();
		int pY = player.getY();
		int pR = player.getRadius();
		int iX = i.getX();
		int iY = i.getY();
		int iR = i.getRadius();
		
		int distanceX = Math.abs(pX - iX);
		int distanceY = Math.abs(pY - iY);
		
		int distance = (int) Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY, 2) );
		int distanceRadius = pR + iR;
		
		if(distance <= distanceRadius) {
			//kollision
			player.setItem(i);
			removeItem(i);
			breakingDown = true;
			musicManager.playItem1();
		}
	}
	
	public void setTimeBeamBack(int i) {
		timebeam.addY(i);
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
	
	public void scoreDateiTest() throws IOException {
		File file1 = new File(scorePfad+"/EasyScoreList.txt");
		File file2 = new File(scorePfad+"/MiddleScoreList.txt");
		File file3 = new File(scorePfad+"/HeavyScoreList.txt");
		File file4 = new File(scorePfad+"/GodlikeScoreList.txt");
		
		File file = new File(scorePfad);	//ordner auch als file wählbar?
		Boolean pathIsRight = false;
		if(file.exists() && file.canWrite() && file.canRead()) {
			pathIsRight = true;
		}else {
			pathIsRight = false;
		}
		
		if(pathIsRight) {
			if(!file1.exists()) {
				file1.createNewFile();
			}
			
			if(!file2.exists()) {
				file2.createNewFile();
			}
			
			if(!file3.exists()) {
				file3.createNewFile();
			}
			
			if(!file4.exists()) {
				file4.createNewFile();
			}
			
			for(int i = 0; i < 4; i++) {
				if(file1.canWrite() && file1.canRead() && file2.canWrite() && file2.canRead() && file3.canWrite() && file3.canRead() && file4.canWrite() && file4.canRead()) {
					scoreAvaible = true;
				}else {
					System.out.println("There are problems with the rights of the scorePfad!\nPlease change the variable 'scorePfad'");
					scoreAvaible = false;
				}
			}
		}
	}
	
	
	public void ladeScore() {
		scores.clear();
		String datei;
		
		switch(difficulty) {
		case 1:	datei = "/EasyScoreList.txt";
			break;
		case 2:	datei = "/MiddleScoreList.txt";
			break;
		case 3:	datei = "/HeavyScoreList.txt";
			break;
		case 4:	datei = "/GodlikeScoreList.txt";
			break;
		default: datei = "/MiddleScoreList.txt";
			break;
		}
		
        File file = new File(scorePfad+datei);
        
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));	
           
            String zeile = null;
            int i = 0;	//damit die erste zeile nicht mit gespeichert wird
            
            while ((zeile = reader.readLine()) != null && !zeile.equalsIgnoreCase("")) {
            	if(i > 0) {
            		scores.add(zeile);
            	}
            	i++;
                //System.out.println("Gelesene Zeile: " + zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {}
        }
    } 
	
	public void proofScore(long score) {
		score = score/1000;
		if(scores.size() == 0) {
			yourScore = 0;
			scores.add(String.valueOf(score));
		}else if(scores.size() < 5){
			boolean isAdded = false;
			for(String s:scores) {
				int score1 = Integer.parseInt(s);
				if(score > score1) {
					yourScore = scores.indexOf(s); 
					scores.add(scores.indexOf(s), String.valueOf(score));
					isAdded = true;
					return;
				}else if(score == score1) {
					yourScore = scores.indexOf(s); 
					scores.set(scores.indexOf(s), String.valueOf(score));
					isAdded = true;
					return;
				}
				//yourScore = 5;
			}
			if(!isAdded) {
				scores.add(String.valueOf(score));
				yourScore = scores.size();
			}
		}else {
		
			for(String s:scores) {
				int score1 = Integer.parseInt(s);
				if(score > score1) {
					yourScore = scores.indexOf(s);
					scores.add(scores.indexOf(s), String.valueOf(score));
					scores.remove(scores.size()-1);	//letztes objekt entfernen
					return;	//aus der for schleife raus
				}else if(score == score1) {
					yourScore = scores.indexOf(s); 
					scores.set(scores.indexOf(s), String.valueOf(score));
					return;
				}
				yourScore = 5;
			}
		}
		//in datei speichern
		writeScore();
	}
	
//	public void proofArrayList(){
//		int i = 13;
//		int i2 = 14;
//		int i3 = 15;
//		
//		ArrayList<Integer> liste = new ArrayList<Integer>();
//		liste.add(i);
//		liste.add(i2);
//		liste.add(i3);
//		
//		int i4 = 13;
//		liste.set(liste.indexOf(i2), i4);
//		//liste.add(liste.indexOf(i2), i4);
//		
//		for(int integer: liste) {
//			System.out.println(integer);
//		}
//	}
	
	public void writeScore() {
		String datei;
		
		switch(difficulty) {
		case 1:	datei = "/EasyScoreList.txt";
			break;
		case 2:	datei = "/MiddleScoreList.txt";
			break;
		case 3:	datei = "/HeavyScoreList.txt";
			break;
		case 4:	datei = "/GodlikeScoreList.txt";
			break;
		default: datei = "/MiddleScoreList.txt";
			break;
		}
		
		File file = new File(scorePfad+datei);
        BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write("Scorelist");
			writer.newLine();
			for(String s:scores) {
				writer.write(s);
				writer.newLine();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				writer.flush();
				writer.close();
			}catch(IOException e) {}
		}
	}
	
	public void setStoneSpeed(Stone s) {
		s.setSpeed(min, max);
	}
	
	public void gameover(long timeScore) {
		state = GameState.GAMEOVER;
		
		musicManager.playGameover();
		f.setCursor(cursor1);
		//ball wird erstellt
		
		for(int i = 0; i < 20; i++) {
			balls.add(new Ball(width, height));
		}
		
		if(scoreAvaible) {
			ladeScore();
			proofScore(timeScore);
			writeScore();
		}
	}

}
