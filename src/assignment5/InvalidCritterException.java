package assignment5;
/* CRITTERS ICE.java
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


public class InvalidCritterException extends Exception {
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

	public String InvalidProcess(String input[]){
		String newString = String.join(" ", input);
		return "error processing: " + newString;
	}

}
