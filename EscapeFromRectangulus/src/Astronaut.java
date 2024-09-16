import java.awt.*;

public class Astronaut {
	private Rectangle body; 
	private int xVelocity, yVelocity, direction;
	private int jumps;
	
	public Astronaut() {
		body = new Rectangle(390, 370, 20, 30);
		xVelocity = 0;
		yVelocity = 0;
		direction = 0;
		jumps = 2;
	}
	
	public void draw(Graphics2D g) {
		g.fill(body);
	}
	
	public int getDirection() {
		return direction;
	}
	
	public Rectangle getBody() {
		return body;
	}
	
	public void setDirection(int d) {
		direction = d;
	}
	
	public void move() {
		if (direction == 1) {
			if (xVelocity < 8) {
				xVelocity++;
			}
		}
		if (direction == -1) {
			if (xVelocity > -8) {
				xVelocity--;
			}
		}
		if (direction == 0) {
			if (xVelocity < 0) {
				xVelocity++;
			}
			else if (xVelocity > 0) {
				xVelocity--;
			}
		}
		body.x += xVelocity;
		if (body.y < 370) {
			body.y -= yVelocity;
			yVelocity--;
		}
		if (body.x < 0) {
			body.x = 0;
		}
		if (body.x > 780) {
			body.x = 780;
		}
		if (body.y > 370) {
			body.y = 370;
		}
	}
	
	public void jump() {
		if (body.y == 370 && EscapeFromRectangulus.getScore() >= 350)
			jumps = 3;
		else if (body.y == 370)
			jumps = 2;
		if (jumps > 0) {
			yVelocity = 12;
			body.y -= yVelocity;
			jumps--;
		}
	}
	public void down() {
		if (body.y != 0) {
			yVelocity = -12;
			body.y += yVelocity;
		}
	}
}
