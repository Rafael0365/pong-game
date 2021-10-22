package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paddle {
	
	private int x, y;
	private int vel = 0;
	private int speed = 10;
	private int width = 22, height = 85;
	private int score = 0;
	private Color color;
	private boolean left;
	
	public Paddle (Color c, boolean left) {
		color = c;
		
		this.left = left;
		
		if(left) {
			x = 0;
		}else {
			x = Game.WIDTH - width;
		}
		y = Game.HEIGHT / 2 - height / 2;
		
	}
	
	public void addPoint() {
		score++;
	}

	public void draw(Graphics g) {
		//desenha as raquetes
		g.setColor(color);
		g.fillRect(x, y, width, height);
		//desenha a pontuaçao
		int sx;
		String scoreTest = Integer.toString(score);
		Font font = new Font("Roboto", Font.PLAIN, 50);
		int strWidth = g.getFontMetrics(font).stringWidth(scoreTest);
		int padding = 25;
		if (left) {
			sx = Game.WIDTH / 2 - padding - strWidth;
		}else {
			sx = Game.WIDTH / 2 + padding;
		}
		
		g.setFont(font);
		g.drawString(scoreTest, sx, 50);
	}

	public void update(Ball b) {
		//update posiçao
		y = Game.esureRange(y += vel, 0, Game.HEIGHT - height);
		
		int ballx = b.getX();
		int bally = b.getY();
		
		//colisoes com a bola
		if(left) {
			
			if(ballx <= width && bally + b.SIZE >= y && bally <= y + height ) {
				b.changeXDir();
			}
		}else {
			
			if(ballx + Ball.SIZE >= Game.WIDTH - width && bally + Ball.SIZE >= y && bally <= y + height)
			b.changeXDir();
		}
		
	}

	public void switchDirection(int direction) {
		vel = speed * direction;
		
	}
	
	public void stop() {
		vel = 0;
	}

}
