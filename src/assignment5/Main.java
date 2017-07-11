package assignment5;

import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.*;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;    // scanner connected to keyboard input, or input file
    private static String inputFile;    // input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;    // if test specified, holds all console output
    private static String myPackage;    // package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = true; // Use it or not, as you wish!
    static PrintStream old = System.out;    // if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     *
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     *             and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }
        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        String amount;
        Integer count;

        while (true) {
            String input = kb.nextLine();
            String[] parseLine = input.split(" ");


            if (parseLine[0].equals("make")) {
                if(parseLine.length != 3){
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                    continue;
                }
                try {
                    amount = parseLine[2];
                    count = Integer.parseInt(amount);

                    for (int i = 0; i < count; i++) {
                        Critter.makeCritter(parseLine[1]);
                    }

                } catch (InvalidCritterException e) {
                    String errorText = input;
                    System.out.println("error processing: " + errorText);

                } catch (NumberFormatException e){
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                }

            } else if (parseLine[0].equals("quit")) {
                if(parseLine.length > 1) {
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                }
                else
                    return;

            } else if (parseLine[0].equals("show")) {
                if(parseLine.length > 1) {
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                }
                else
                    Critter.displayWorld();

            } else if (parseLine[0].equals("step")) {
                if(parseLine.length <= 2){
                    try{
                        amount = parseLine[1];
                        count = Integer.parseInt(amount);

                        for (int i = 0; i < count; i++) {
                            Critter.worldTimeStep();
                        }
                    } catch (IndexOutOfBoundsException e){
                        Critter.worldTimeStep();
                    } catch (NumberFormatException e){
                        String errorText = input;
                        System.out.println("error processing: " + errorText);
                    }
                }
                else{
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                }


            } else if (parseLine[0].equals("stats")) {
                if(parseLine.length != 2){
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                    continue;
                }

                String className = parseLine[1];
                List<Critter> list = null;
                Class<?> tempclass = null;

                try {
                    tempclass = Class.forName(myPackage + "." + className);
                    list = Critter.getInstances(className);
                    /*
                    if(list.size() == 0){
                        System.out.println("instance not available");
                        continue;
                    }
                    */

                    Class [] paramList = new Class[1];
                    paramList[0] = java.util.List.class;

                    java.lang.reflect.Method runStats = tempclass.getMethod("runStats", paramList);
                    runStats.invoke(tempclass, list);
                } catch (Exception e){
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                    continue;
                }

            } else if (parseLine[0].equals("seed")) {
                if(parseLine.length <= 2) {
                    try {
                        amount = parseLine[1];
                        count = Integer.parseInt(amount);
                        Critter.setSeed(count);
                    } catch (IndexOutOfBoundsException e) {
                        String errorText = input;
                        System.out.println("error processing: " + errorText);
                    } catch (NumberFormatException e) {
                        String errorText = input;
                        System.out.println("error processing: " + errorText);
                    }
                }
                else{
                    String errorText = input;
                    System.out.println("error processing: " + errorText);
                }
            } else {
                String line = String.join(" ", input);
                System.out.println("invalid command: " + line);
            }
        }

        // System.out.println("GLHF");
        
        /* Write your code above */
        //System.out.flush();

    }
}