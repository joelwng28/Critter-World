/* CRITTERS Liuxx2.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Xiangxing Liu
 * xl5587
 * 76175
 * Zi Zhou Wang
 * zw3948
 * 76175
 * Slip days used: <0>
 * Git URL: https://github.com/xxuil/Critter
 * Summer 2017
 */
package assignment5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Moez extends Critter {
	
	@Override
	public String toString() { return "M"; }
	
	private String color = "Meh";
	private int dir;
	
	public Moez() {
		int gene = Critter.getRandomInt(4);
		if(gene == 0){
			color = "Happy";
		}
		if(gene == 1){
			color = "Sad";
		}
		if(gene == 2){
			color = "Excited";
		}
		if(gene == 3){
			color = "Depressed";
		}
		dir = 2;
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
			Moez child = new Moez();
			child.color = this.color;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
	}

	public static void runStats(java.util.List<Critter> Moezs) {
		int Happy = 0;
		int Sad = 0;
		int Excited = 0;
		int Depressed = 0;
		for (Object obj : Moezs) {
			Moez c = (Moez) obj;
			if (c.color.equals("Happy")){
				Happy++;
			}
			if (c.color.equals("Sad")){
				Sad++;
			}
			if (c.color.equals("Excited")){
				Excited++;
			}
			if (c.color.equals("Depressed")){
				Depressed++;
			}
		}
		System.out.print("" + Moezs.size() + " total Moezs    ");
		System.out.print("" + Happy + " Happy   ");
		System.out.print("" + Sad + " Sad   ");
		System.out.print("" + Excited + " Excited   ");
		System.out.print("" + Depressed + " Depressed   ");
		System.out.println();
	}

	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }

	@Override
	public javafx.scene.paint.Color viewFillColor() { return Color.RED; }
    @Override
    public javafx.scene.paint.Color viewOutlineColor () { return Color.BLACK; }

	public static Shape getShape(double x, double y, double radius){
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(
				x + 1,
				y + 2 * radius - 1,
				x + radius,
				y + 1,
				x + 2 * radius - 1,
				y + 2 * radius - 1
		);

		return triangle;
	}
}