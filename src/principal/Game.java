package principal;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = -8114915336626931959L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9/16;

	public boolean running = false;
	private Thread gameThread;
	
	private Ball ball;
	private Paddle paddle1;
	private Paddle paddle2;
	
	
	public Game(){
		canvasSteup();
		initialize();
		new Window("pong",this);
		
		this.addKeyListener(new KeyInput(paddle1, paddle2));
		this.setFocusable(true);
	}

	private void initialize() {
		// inicializar a bola
		ball = new Ball();
		
		//inicializar as raquetes
		paddle1 = new Paddle(Color.green, true);
		paddle2 = new Paddle(Color.red, false);
	}

	private void canvasSteup() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH,HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		
	}

	@Override
	public void run() {
		
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
				draw();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
		
	}

	private void draw() {
		//inicializar ferramentas de desenho
		
		BufferStrategy buffer = this.getBufferStrategy();
		
		if(buffer == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = buffer.getDrawGraphics();
		
		//desenha o fundo
		drawBackground(g);
		//desenha a bola
		ball.draw(g);
		//desenha as raquetes e a pontuação
		paddle1.draw(g);
		paddle2.draw(g);
		//desenhar
		g.dispose();
		buffer.show();
		
	}

	private void drawBackground(Graphics g) {
		//fundo preto
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//linha pontilhada do meio
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
		
	}

	private void update() {
		//update bola
		ball.update(paddle1,paddle2);
		//update raquetes
		paddle1.update(ball);
		paddle2.update(ball);
	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;
		
	}
	
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int sign(double d) {
		if (d<=0) {
			return -1;
		}
		return 1;
	}
	
	public static void main(String[] args) {
		new Game();
		
	}

	public static int esureRange(int val, int min, int max) {
		
		return Math.min(Math.max(val, min), max);
	}
}
