package defense;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.LinkedList;

public class Attack {
	Arrow TempArrow;

	/*
	 * List of Arrow objects that make up one attack
	 */
	private LinkedList<Arrow> attackPattern = new LinkedList<Arrow>();
	/*
	 * If the delay for an attack is constant, this is it (milliseconds)
	 */
	private double delay;
	/*
	 * If the delays vary in amount for the arrows, this is it (milliseconds)
	 */
	private double[] delayGroup;

	/*
	 * Constructor for constant delay
	 */
	public Attack(LinkedList<Arrow> attackPattern, double delay) {
		this.attackPattern = attackPattern;
		this.delay = delay;
		this.delayGroup = new double[0];
	}

	/*
	 * Constructor for varying delays
	 */
	public Attack(LinkedList<Arrow> attackPattern, double[] delayGroup) throws IOException {
		if (delayGroup.length == attackPattern.size() - 1)
			throw new IOException("Error: Number of delays must be one less than the number of attacks");
		this.attackPattern = attackPattern;
		this.delay = 0;
		this.delayGroup = delayGroup;
	}

	public Attack() {

	}

	public void tick() {
		for (int i = 0; i < attackPattern.size(); i++) {
			TempArrow = attackPattern.get(i);
			TempArrow.tick();
		}
	}

	public void addArrow(Arrow a) {
		attackPattern.add(a);
	}

	public void removeArrow(Arrow a) {
		attackPattern.remove(a);
	}

	public String removeArrow(char dir) {
		boolean hit = false;

		if (attackPattern.size() == 0)
			return "";

		for (int x = 0; x < attackPattern.size(); x++) {

			if (attackPattern.get(x).getDir() == 'l') {
				if (dir == 'r') {
					if (attackPattern.get(x).getX() < 325) {

						attackPattern.remove(x);
						hit = true;

					}
				} else if (attackPattern.get(x).getX() < 295) {

					attackPattern.remove(x);
				}
			} else if (attackPattern.get(x).getDir() == 'r') {
				if (dir == 'l') {
					if (attackPattern.get(x).getX() > 240) {

						attackPattern.remove(x);
						hit = true;

					}
				} else if (attackPattern.get(x).getX() > 280) {

					attackPattern.remove(x);
				}
			} else if (attackPattern.get(x).getDir() == 'u') {
				if (dir == 'd') {
					if (attackPattern.get(x).getY() < 320) {

						attackPattern.remove(x);
						hit = true;

					}
				} else if (attackPattern.get(x).getY() < 280) {

					attackPattern.remove(x);
				}
			} else if (attackPattern.get(x).getDir() == 'd') {
				if (dir == 'u') {
					if (attackPattern.get(x).getY() > 230) {

						attackPattern.remove(x);
						hit = true;

					}
				} else if (attackPattern.get(x).getY() > 260) {

					attackPattern.remove(x);
				}
			}

		}
		if (hit)
			return "H";

		return "";

	}

	public void draw(Graphics g) throws IOException {
		if (attackPattern.size() == 0)
			return;
		attackPattern.get(0).draw(g, Color.RED);
		for (int i = 1; i < attackPattern.size(); ++i) {
			attackPattern.get(i).draw(g, Color.BLUE);
		}
	}

}
