import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static int DELAY = 150;
	final int x[] = new int [GAME_UNITS];
	final int y[] = new int [GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	static char direction ='R';
	boolean running = false;
	Timer timer;
	Random random;
	static JButton reset = new JButton();

GamePanel(){
	random = new Random();
	this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
	this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
	this.setLocation(0,50);
	this.setBackground(Color.white);
	this.setFocusable(true);
	this.addKeyListener(new MyKeyAdapter());
	startGame();
}

public void startGame(){
	newApple();
	running = true;
	timer = new Timer(DELAY,this);
	timer.start();
}
public void paintComponent(Graphics g){
	super.paintComponent(g);
	draw(g);
}
public void draw(Graphics g){
	if (running) {
		g.drawLine(0, 0, 0, SCREEN_HEIGHT);
		g.setColor(new Color(50,50,50));
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

		for (int i = 0; i < bodyParts; i++) {
			g.setColor(Color.black);
			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		}
	}
	else{
		gameOver(g);
	}
}
public void newApple(){
	appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;	
}
public void move(){
	for (int i = bodyParts; i > 0; i--) {
		x[i] = x[i-1];
		y[i] = y[i-1];
	}
	switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
}
public void checkApple(){
	if((x[0] == appleX) && (y[0] == appleY)) {
		bodyParts++;
		applesEaten++;
		newApple();
		DELAY=DELAY-25;
	}
}
public void checkCollision(){
	//head collision
	for (int i = bodyParts; i > 0; i--) {
		if (x[0]==x[i] && y[0]==y[i]) {
			running=false;
		}
	}
	//vheck left touch
	if(x[0]<0){
		running=false;
	}
	//vheck right touch
	if(x[0]>SCREEN_WIDTH){
		running=false;
	}
	//chech top touch
	if(y[0]<0){
		running=false;
	}
	//check bottum touch
	if(y[0]>SCREEN_HEIGHT){
		running=false;
	}

	if (!running) {
		timer.stop();
	}
}
public void gameOver(Graphics g){
	g.setColor(new Color(255,22,33));
	g.setFont(new Font("Ink Free",Font.BOLD,40));
	FontMetrics metrics1 = getFontMetrics(g.getFont());
	g.drawString("Score : "+applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score : "+applesEaten*5))/2, g.getFont().getSize());
	
	g.setColor(Color.RED);
	g.setFont(new Font("Ink Free",Font.BOLD,60));
	FontMetrics metrics2 = getFontMetrics(g.getFont());
	g.drawString("Game Over", (SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

}
@Override
public void actionPerformed(ActionEvent e){
	if (running) {
		move();
		checkApple();
		checkCollision();
	}
	repaint();
}

public class MyKeyAdapter extends KeyAdapter{
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyChar()) {
			case 'd':
				if (GamePanel.direction!='L') {
					GamePanel.direction='R';
				}
				break;
			case 'a':
				if (GamePanel.direction!='R') {
					GamePanel.direction='L';
				}
				break;
			case 'w':
				if (GamePanel.direction!='D') {
					GamePanel.direction='U';
				}
				break;
			case 's':
				if (GamePanel.direction!='U') {
					GamePanel.direction='D';
				}
				break;
			default:
				break;
		}		
	}

	@Override
	public void keyPressed(KeyEvent e){
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT :
				if (direction!='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction!='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction!='D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction!='U') {
					direction='D';
				}
				break;
			default:
				break;
		}	
	}
		@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
}