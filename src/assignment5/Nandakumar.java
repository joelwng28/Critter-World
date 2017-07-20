package assignment5;
/* CRITTERS Nandakumar.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Xiangxing Liu
 * xl5587
 * 76175
 * Zi Zhou Wang
 * zw3948
 * 76175
 * Slip days used: <0>
 * Git URL: https://github.com/joelwng28/assignment4
 * Summer 2017
 */

import javafx.scene.paint.Color;

public class Nandakumar extends Critter {
	
	@Override
	public String toString() { return "N"; }
	
	private String color = "Yellow";
	private int dir;
	
	public Nandakumar() {
		int gene = Critter.getRandomInt(4);
		if(gene == 0){
			color = "Black";
		}
		if(gene == 1){
			color = "Brown";
		}
		if(gene == 2){
			color = "Orange";
		}
		if(gene == 3){
			color = "White";
		}
		dir = 0;
	}
	
	public boolean fight(String opponent) {
		if(opponent.equals("Moez")){
			return false;
		}
		return true; 
		}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		if (getEnergy() > 120) {
			Nandakumar child = new Nandakumar();
			child.color = this.color;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
	}

	public static void runStats(java.util.List<Critter> Nandakumars) {
		int brown = 0;
		int black = 0;
		int orange = 0;
		int white = 0;
		for (Object obj : Nandakumars) {
			Nandakumar c = (Nandakumar) obj;
			if (c.color.equals("Brown")){
				brown++;
			}
			if (c.color.equals("Black")){
				black++;
			}
			if (c.color.equals("Orange")){
				orange++;
			}
			if (c.color.equals("White")){
				white++;
			}
		}
		System.out.print("" + Nandakumars.size() + " total Nandakumars    ");
		System.out.print("" + brown + " brown   ");
		System.out.print("" + black + " black   ");
		System.out.print("" + orange + " orange   ");
		System.out.print("" + white + " white   ");
		System.out.println();
	}

	@Override
	public CritterShape viewShape() { return CritterShape.CIRCLE; }

	@Override
	public javafx.scene.paint.Color viewColor() { return Color.DARKGREEN; }
}
