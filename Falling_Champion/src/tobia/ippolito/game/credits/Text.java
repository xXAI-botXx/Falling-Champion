package tobia.ippolito.game.credits;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text {

	private int x, y;
	private int size = 15;
	private String titel, name = "Tobia Ippolito";
	private int width = 350;
	private Font font = new Font("Arial", Font.PLAIN, size);
	private Credits credits;
	
	public Text(Credits credits, int x, int y) {
		this.credits = credits;
		this.x = x;
		this.y = y;
		
		chooseText();
	}
	
	private void chooseText() {
		int rand = (int) (Math.random() *  17);
		
		switch(rand) {
		case 0: 
			titel = "Executive Producer";
			name =  "     Tobia Ippolito";
			break;
		case 1:
			titel = "Special Thanks To";
			name =  "      Tobia Ippolito";
			break;
		case 2:
			titel = "Tool Programmer";
			name =  "     Tobia Ippolito";
			break;
		case 3:
			titel = "Gameplay Design Lead";
			name =  "         Tobia Ippolito";
			width = 410;
			break;
		case 4:
			titel = "Gameplay Programmer";
			name =  "        Tobia Ippolito";
			width = 400;
			break;
		case 5:
			titel = "Technical Director";
			name =  "     Tobia Ippolito";
			break;
		case 6:
			titel = "Engine Programmer";
			name =  "       Tobia Ippolito";
			width = 400;
			break;
		case 7:
			titel = "Combat Design";
			name =  "  Tobia Ippolito";
			break;
		case 8:
			titel = "Head Of Studio";
			name =  "  Tobia Ippolito";
			break;
		case 9:
			titel = "Design Director";
			name =  "  Tobia Ippolito";
			break;
		case 10:
			titel = "Creative Director";
			name =  "    Tobia Ippolito";
			break;
		case 11:
			titel = "        UI";
			name =  "Tobia Ippolito";
			break;
		case 12:
			titel = " Art Director";
			name =  "Tobia Ippolito";
			break;
		case 13:
			titel = "Character Designer";
			name =  "      Tobia Ippolito";
			break;
		case 14:
			titel = "Cinematics Director";
			name =  "       Tobia Ippolito";
			break;
		case 15:
			titel = "Interface Artist";
			name =  "  Tobia Ippolito";
			break;
		case 16:
			titel = "Audio Director";
			name =  "  Tobia Ippolito";
			break;
		case 17:
			titel = "Collision Programmer";
			name =  "        Tobia Ippolito";
			break;
		}
	}
	
	public void draw(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		g.setColor(Color.BLACK);
		g.drawString(titel, x, y);
		
		g.setFont(new Font("Arial", Font.PLAIN, size));
		g.drawString(name, x, y + size);
	}
	
	public void update() {
		if(size <= 36) {
			size++;
		}
		
		y--;
		
		//collision
		if(y+size <= 0) {
			credits.removeText(this);
		}
		
		//wand collision
		if(x <= 0) {
			boolean collision = true;
			while(collision) {
				if(x <= 0) {
					x += 1;
					break;
				}
				collision = false;
			}
		}else if(x + width >= 800) {
			boolean collision = true;
			while(collision) {
				if(x + width >= 800) {
					x -= 1;
					break;
				}
				collision = false;
			}
		}
	}
	
	public void collision(int tX, int tY) {
		//g.drawRect(x, y- 50, 350, 150);
		
		//linke obere ecke
		int t1LOX = tX;
		int t1LOY = tY -50;
		//rechte obere ecke
		int t1ROX = tX + 350;
		int t1ROY = tY - 50;
		//linke untere ecke
		int t1LUX = tX;
		int t1LUY = tY + 150;
		//rechte untere ecke
		int t1RUX = tX + 350;
		int t1RUY = tY + 150;
		
		boolean collision = true;
		while(collision) {
		//zu jeder ecke = 8 int anlegen
			if(t1LOX >= x && t1LOX <= x + width && t1LOY >= y-50 && t1LOY <= y + 150) {	//linke obere ecke
				x -= 1;
				y -= 1;
				break;
			}else if(t1ROX >= x && t1ROX <= x + width && t1ROY >= y-50 && t1ROY <= y + 150) {
				x += 1;
				y -= 1;
				break;
			}else if(t1LUX >= x && t1LUX <= x + width && t1LUY >= y-50 && t1LUY <= y + 150) {
				x -= 1;
				y += 1;
				break;
			}else if(t1RUX >= x && t1RUX <= x + width && t1RUY >= y-50 && t1RUY <= y + 150) {
				x += 1;
				y += 1;
				break;
			}
			collision = false;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
