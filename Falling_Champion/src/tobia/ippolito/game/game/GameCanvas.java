package tobia.ippolito.game.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import tobia.ippolito.game.game.Game.GameState;

//angepasstes zeichnen mit panel -> muss dem panel von gamemain hinzugefügt werden!
public class GameCanvas extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
	
	//variablen
	private Game game;
	
	//Konstruktor
	public GameCanvas(Game game) {		//kann gut zeichnen und ist ein guter zuhörer
		this.game = game;
		setFocusable(true);	//so that can receive key-events
		requestFocus();
		
		System.out.println("DpoubleBuffering: "+this.isDoubleBuffered());
	}
	
	//angepasstes zeichnen, wird automatisch (indirekt) aufgerufen (repaint refreshed den Bildschirm)
	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g); 	//Hintergrund zeichnen
		//setBackground(Color.BLACK);
		
		if(game.state == GameState.PLAYING) {
			game.gameDraw(g);	//drawing game-objects
		}else if(game.state == GameState.PAUSED) {
			game.drawPause(g);
		}else if(game.state == GameState.GAMEOVER){
		
			game.gameoverDraw(g);
		}else if(game.state == GameState.DIFFICULTY) {
			game.drawChoice(g);
		}
	}
	
	//key-listening
	@Override
	public void keyPressed(KeyEvent e) {
		game.gameKeyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }

	
	//Mouse Listener
	@Override
	public void mouseClicked(MouseEvent e) {
		game.mouseClicked(e.getX(), e.getY(), e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	
	//Mouse Motion Listener
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		game.mouseMoved(e.getX(), e.getY());
	}
	
}
