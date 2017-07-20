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

/*
 * Do not change or submit this file.
 */
import assignment5.Critter.TestCritter;
import javafx.scene.paint.Color;

public class Algae extends TestCritter {

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}

	@Override
	public CritterShape viewShape() { return CritterShape.CIRCLE; }

	@Override
	public javafx.scene.paint.Color viewFillColor() { return Color.ORANGE; }

    @Override
	public javafx.scene.paint.Color viewOutlineColor () { return Color.BLACK; }
}
