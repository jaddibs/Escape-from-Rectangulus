import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class EscapeFromRectangulus extends JPanel implements KeyListener,
    ActionListener {

  // DECLARE ALL INSTANCE VARIABLES HERE..

  private ArrayList<Rectangle> aliens = new ArrayList<Rectangle>();
  private Astronaut astro = new Astronaut();
  public static final Rectangle BORDER = new Rectangle(0, 0, 800, 400);
  private static int frameCount;// used for the score
  private int hiScore = 0; // used for high score
  private int sRec = 0;
  private int x = 0;
  private int m = 0;
  private String title1 = "-Escape From Rectangulus-";
  private String[] title2 = {"CONTROLS:", "r = start/restart", "right/left arrows = move", "up arrow/space = jump"};
  private String title3 = "TRIPLE JUMP AND SPEED DOWN ACTIVATED!!";
  private String title4 = "Powers Unlocked:";
  private String[] title5 = {"Triple Jump - Press Up Arrow Three Times!", "Speed Down - Press Down Arrow!", "", "None! Keep progressing to unlock!"};
  private Timer timer;// handles animation
  private static Image offScreenBuffer;// needed for double buffering graphics
  private Color b;
  private Graphics offScreenGraphics;// needed for double buffering graphics

  /**
   * main() is needed to initialize the window.<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! .. <br>
   * you should write all necessary initialization code in initRound()
   */
  public static void main(String[] args) {
    JFrame window = new JFrame("Escape From Rectangulus");
    window.setBounds(100, 100, 807, 440);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    EscapeFromRectangulus game = new EscapeFromRectangulus();
    game.setBackground(Color.WHITE);
    window.getContentPane().add(game);
    window.setVisible(true);
    game.init();
    window.addKeyListener(game);
  }
  
  public static int getScore() {
	  return frameCount;
  }

  /**
   * init method needed to initialize non-static fields<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! ..
   */
  public void init() {
    aliens = new ArrayList<Rectangle>();
	offScreenBuffer = createImage(getWidth(), getHeight());// should be 800x400
    offScreenGraphics = offScreenBuffer.getGraphics();
    timer = new Timer(30, this);
    // timer fires every 30 milliseconds.. invokes method actionPerformed()
    initRound();
  }

  /**
   * initializes all fields needed for each round of play (i.e. restart)
   */
  public void initRound() {
	  astro = new Astronaut();
	  frameCount = 0;
	  hiScore = sRec;
    // YOUR CODE GOES HERE..
    // initialize instance variables here
	  aliens = new ArrayList<Rectangle>();
  }

  /**
   * Called automatically after a repaint request<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! ..
   */
  public void paint(Graphics g) {
    if(offScreenGraphics == null)
        return;
  
    draw((Graphics2D) offScreenGraphics);
    g.drawImage(offScreenBuffer, 0, 0, this);
  }

  /**
   * renders all objects to Graphics g
   */
  public void draw(Graphics2D g) {
    super.paint(g);// refresh the background
    g.setColor(Color.BLACK);
    g.drawString(title1, 326, 20);// draw the title towards the top
    
	int j = 20;
    for (int i = 0; i < title2.length; i++) {
    	g.drawString(title2[i], 660, j);
    	j += 15;
    }
    //g.drawString(title2, 210, 60); draw the title towards the top
    
    if (frameCount >= 300 + 300*x && frameCount <= 400 + 300*x) {
    	g.drawString("Beware! Boss Incoming!", 340, 170);
    	if (frameCount == 400 + 300*x)
        	x++;
    }
    
    if (frameCount <= 450 && frameCount >= 350)
    	g.drawString(title3, 280, 140);
    
    g.drawString(title4, 10, 20);
    
    if (frameCount < 350) {
    	g.drawString(title5[3], 10, 35);
    }
    
    if (frameCount >= 350) {
    	g.drawString(title5[0], 10, 35);
    	g.drawString(title5[1], 10, 50);
    }
    
    g.drawRect(BORDER.x, BORDER.y, BORDER.width - 1, BORDER.height - 1);
    g.drawString("score:  " + frameCount, 380, 100);// approximate middle
    
    if (frameCount > hiScore) {
    	g.drawString("high score: " + frameCount, 368, 80);
    	hiScore = frameCount;
    	sRec = hiScore;
    }
    else {
    	g.drawString("high score: " + hiScore, 368, 80);
    }

    astro.draw(g);
    
    for (Rectangle x : aliens) {
    	g.setColor(b);
    	g.draw(x);
    	g.fill(x);
    }
    // YOUR CODE GOES HERE..
    // render all game objects here
  }

  /**
   * Called automatically when the timer fires<br>
   * this is where all the game fields will be updated
   */
  public void actionPerformed(ActionEvent e) {

    astro.move();

    // Simple AI: Each frame there is a 5% chance of creating an alien Rectangle
    // with a random position and width. (note: its width is also its velocity)
    if (Math.random() < 0.05) {
    	aliens.add(new Rectangle(BORDER.width, 360 - (int) (Math.random() *
    	40), (int) (Math.random() * 15 + 1), 20));
    	b = new Color((int) (Math.random()*50) + 150, (int) (Math.random()*50) + 150, (int) (Math.random()*50) + 150);
    }
    
  	if (frameCount % 300 == 100 && frameCount / 400.0 >= 1.0) {
  		Rectangle boss = new Rectangle(BORDER.width, 300, 100, 100);
		aliens.add(0, boss);
		b = new Color(100, 100, 100);
  	}
		
    // YOUR CODE GOES HERE..
  	if (frameCount == 500 + 300*m)
  		m++;
  	if (frameCount >= 350 + 300*m && frameCount <= 500 + 300*m) {
	  	for (int i = 0; i < aliens.size(); i++) {
			Rectangle a = aliens.get(i);
			a.setLocation((int) a.x - 10, (int) a.y);
	    	if (aliens.get(i).getX() < 0.0) {
	    		aliens.remove(i);
	    		i--;
	    	}
	  	}
    }
    else {
	  	for (int i = 0; i < aliens.size(); i++) {
			Rectangle a = aliens.get(i);
			a.setLocation((int) a.x - (int) a.width, (int) a.y);
	    	if (aliens.get(i).getX() < 0.0) {
	    		aliens.remove(i);
	    		i--;
	    	}
	  	}
    }
    
    // update all alien Rectangles and stop the timer if any intersect with astro
    for (Rectangle x : aliens) {
    	if (astro.getBody().intersects(x))
    		timer.stop();
    }
    
    frameCount++;// used for the score
    repaint();// needed to refresh the animation
  }

  /**
   * handles any key pressed events and updates the player's direction by
   * setting their direction to either 1 or -1 for right or left respectively
   * and updates their jumping status by invoking jump()
   */
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_LEFT) {
      astro.setDirection(-1);
    } else if (keyCode == KeyEvent.VK_RIGHT) {
      astro.setDirection(1);
    } else if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_UP) {
    		astro.jump();
    }
    else if (keyCode == KeyEvent.VK_DOWN) {
    	if (frameCount >= 350) {
    		astro.down();
    }
    }
  }

  /**
   * handles any key released events and updates the player's direction by
   * setting their direction to 0 if they need to stop and restarts the game if
   * the Timer is not running and user types 'r'
   */
  public void keyReleased(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_LEFT /* && astro.getDirection() == -1 */) {
      astro.setDirection(0);
    } else if (keyCode == KeyEvent.VK_RIGHT/* && astro.getDirection() == 1 */) {
      astro.setDirection(0);
    } else if (keyCode == KeyEvent.VK_R) {
      if (!timer.isRunning()) {
        timer.start();
        initRound();
      }
    }
  }

  /**
   * this method is needed for implementing interface KeyListener<br>
   * THIS METHOD SHOULD NOT BE MODIFIED! ..
   */
  public void keyTyped(KeyEvent e) {
  }

}// end class EscapeFromRectangulus
