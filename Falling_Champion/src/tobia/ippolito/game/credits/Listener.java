package tobia.ippolito.game.credits;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Listener extends JPanel implements MouseListener, MouseMotionListener{

	private Credits credits;
//	private JButton btnBackToTitle;
	
	public Listener(Credits credits) {
		this.credits = credits;
		setFocusable(true);	//so that can receive key-events
		requestFocus();
		
//		//init JButton back to title
//		btnBackToTitle = new JButton("<-");
//		btnBackToTitle.setBounds(30, 30, 75, 50);
//		//btnBackToTitle.setContentAreaFilled(false);
//		btnBackToTitle.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				credits.back();
//			}
//		});
//		add(btnBackToTitle);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		credits.creditsDraw(g);
	}
	
	//Mouse Listener
	@Override
	public void mouseClicked(MouseEvent e) {
		credits.mouseClicked(e.getX(), e.getY(), e.getButton());
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
		credits.mouseMoved(e.getX(), e.getY());
	}
}
