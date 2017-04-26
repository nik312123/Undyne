package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Composes an arrow to be implemented in the Attack class
 */
public class Arrow {
	/*
	 * This determines the speed the arrow should go at between 1 – 100
	 */
	private int speed;
	/*
	 * This determines whether or not the arrow will be reversed
	 */
	private boolean reverse;
	/*
	 * This determines the direction the arrow should come from
	 */
	private char direction;
	/*
	 * These are the coordinates of the arrow
	 */
	private int x, y;
	/*
	 * Color of the arrow
	 */
	private Color arrColor;

	public Arrow(int speed, boolean reverse, char direction) {
		this.speed = speed;
		this.reverse = reverse;
		this.direction = direction;
		setCoordinates(direction);

	}

	/*
	 * Helper method for the constructor setting the arrow coordinates
	 */
	private void setCoordinates(char direction) {

		switch (direction) {
		case 'r':
			x = 0;
			y = 270;
			break;
		case 'l':
			x = 590;
			y = 270;
			break;
		case 'u':
			x = 285;
			y = 590;
			break;
		case 'd':
			x = 285;
			y = 0;
			break;
		}
	}

	public void tick() {
		switch (direction) {
		case 'l':
			x -= speed;
			break;
		case 'r':
			x += speed;
			break;
		case 'u':
			y -= speed;
			break;
		case 'd':
			y += speed;
			break;

		}

	}

	public void draw(Graphics g, Color c) throws IOException {
		BufferedImage arr = ImageIO.read(new File("arrowB.png"));

		if (c.equals(Color.RED)) {
			try {

				arr = ImageIO.read(new File("arrowR.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (reverse) {
			try {

				arr = ImageIO.read(new File("arrowRE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int angle = 0;

		switch (direction) {
		case 'r':
			angle = 0;
			break;
		case 'd':
			angle = 90;
			break;
		case 'l':
			angle = 180;
			break;
		case 'u':
			angle = -90;

		}

		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angle), arr.getMinX() + arr.getWidth() / 2, arr.getMinY() + arr.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		arr = op.filter(arr, null);

		g.drawImage(arr, x, y, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getDir() {
		return direction;
	}

}
