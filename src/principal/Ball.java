package principal;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	public static final int SIZE = 16;
	
	private int x, y;
	private int xVel, yVel; //tem um valor 1 ou -1
	private int speed = 5;
	
	public Ball() {
		reset();
	}

	private void reset() {
		// posiçao inicial
		x = Game.WIDTH / 2 - SIZE / 2;
		y = Game.HEIGHT / 2 - SIZE / 2;
		
		//velocidades iniciais
		xVel = Game.sign(Math.random() * 2.0 - 1);
		yVel = Game.sign(Math.random() * 2.0 - 1);
	}
	
	public void changeYDir() {
		yVel *= -1; 
	}
	
	public void changeXDir() {
		xVel *= -1;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, SIZE, SIZE);
		
	}

	public void update(Paddle p1, Paddle p2) {
		//update move
		x += xVel * speed;
		y += yVel * speed;
		
		//colisao
		if(y + SIZE >= Game.HEIGHT || y <= 0) {
			changeYDir();
		}
		//com paredes
		if(x + SIZE >= Game.WIDTH) {
			p1.addPoint();
			reset();
		}
		if(x <= 0) {
			p2.addPoint();
			reset();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

}
