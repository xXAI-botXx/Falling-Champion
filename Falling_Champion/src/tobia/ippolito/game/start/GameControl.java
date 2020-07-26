package tobia.ippolito.game.start;

import javax.swing.*;

import java.awt.*;

import tobia.ippolito.game.credits.*;
import tobia.ippolito.game.game.*;

public class GameControl extends JPanel{

	//variablen
		//konstanten für das spiel
	 final int WIDTH = 800, HEIGHT = 600;
	 final Dimension size = new Dimension(WIDTH, HEIGHT);
	 final static String TITLE = "Falling Champion";
	 
	
	 	//UI
	private StartScreen startScreen;
	private Game game;
	private Credits credits;
	private CardLayout layout;
	private JFrame f;
	private MusicManager musicManager;

	
	//Konstruktor -> init der UI-Komponenten und der Spiel-Objekte
	public GameControl(JFrame f) {
		this.f = f;
		this.setDoubleBuffered(true);
		
		//Sound - Komponenten
		musicManager = new MusicManager();
		Thread t = new Thread(musicManager);
		
		//UI-Komponenten
		layout = new CardLayout();
		
		setLayout(layout);
		setPreferredSize(new Dimension(800, 600));
		
		startScreen = new StartScreen(this, f, musicManager);
		add(startScreen, "start");
		
		game = new Game(this, f, musicManager);
		add(game, "game");
		
		credits = new Credits(this, f, musicManager);
		add(credits, "credits");
		
		backToTitle();
		musicManager.playAlternativeStart();
	}
	
	public void gameStart() {
		layout.show(this, "game");
		game.gameStart();
		game.setFocus();
	}
	
	public void creditsStart() {
		//CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, "credits"); 
        credits.creditsStart();
        credits.setFocus();
	}
	
	public void backToTitle() {
		layout.show(this, "start"); 
		startScreen.start();
		startScreen.setFocus();
	}
	
	public Dimension getSize() {
		return size;
	}
	
	//ausführung
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					JFrame frame = new JFrame(TITLE);
					
					frame.setContentPane(new GameControl(frame));
					//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//frame.setUndecorated(true);
					frame.pack();	//bindet komponenten ein -> passt sein Größe an seine Componenten an
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true); 
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
