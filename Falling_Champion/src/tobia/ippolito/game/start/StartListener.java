package tobia.ippolito.game.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartListener extends JPanel implements MouseListener{

	private StartScreen start;
	private GameControl control;
	private JButton btnStart;
	private JButton btnCredits;
	private JButton btnExit;
	private JLabel title;
	private MusicManager musicManager;
	
	public StartListener(StartScreen start, GameControl control, MusicManager musicManager) {
		this.start = start;
		this.control = control;
		this.musicManager = musicManager;
		
		setLayout(null);
		
		setFocusable(true);	//so that can receive key-events
		requestFocus();
		
		//title label
		title = new JLabel("Falling Champion");
		title.setBounds(400-125, 50, 400, 100);
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		add(title);
		
		
		//Start-Button init
		btnStart = new JButton("Start");
		btnStart.setBounds(150, 300-25, 100, 50);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicManager.playCheck();
				start.setOff();
				control.gameStart();
			}
		});
		add(btnStart);
		btnStart.setBackground(new Color(156, 156, 156));	//255, 106, 106
		
		//Credit Button init
		btnCredits = new JButton("Credits");
		btnCredits.setBounds(400-50, 300-25, 100, 50);
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicManager.playCheck();
				start.setOff();
				control.creditsStart();
			}
		});
		add(btnCredits);
		btnCredits.setBackground(new Color(156, 156, 156));
		
		
		//Exit button init
		btnExit = new JButton("Exit");
		btnExit.setBounds(550, 300 -25, 100, 50);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicManager.playCheck();
				System.exit(0); 
			}
		});
		add(btnExit);
		btnExit.setBackground(new Color(156, 156, 156));
		
		btnExit.setVisible(false);
		btnCredits.setVisible(false);
		btnStart.setVisible(false);
		title.setVisible(false);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 800, 600);
		start.drawStart(g);
	}
	
	public void viewMenu() {
		btnExit.setVisible(true);
		btnCredits.setVisible(true);
		btnStart.setVisible(true);
		title.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		start.mouseClicked();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
