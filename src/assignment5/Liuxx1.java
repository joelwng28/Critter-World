/* CRITTERS Liuxx1.java
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
package assignment5;

public class Liuxx1 extends Critter{

    private static  final boolean Debug = false;
    private Integer direction;
    private Integer fights;
    private boolean move;


    public Liuxx1(){
        direction = Critter.getRandomInt(8);
        fights = 0;
        move = false;
    }

    public String toString(){
        return "X";
    }

    public int getDir(){
        return direction;
    }


    public void doTimeStep(){
        if(Debug){
        }
        //determine current energy
        int current = getEnergy();
        int res = (current - 50) / 20;

        if(current < 20){
            run(direction);
            move = true;
        }
        else if(current < 50){
            walk(direction);
            move = true;
        }

        else if(current < 100){
            //do nothing
        }
        //choose to reproduce or not
        else if(current >= 100){
            Liuxx1 child = new Liuxx1();
            int g = Critter.getRandomInt(8);
            reproduce(child, g);
        }

        //reset direction
        direction = Critter.getRandomInt(8);
    }


    public boolean fight(String opponent){
        if(opponent.equals("Algae")){
            fights ++;
            return true;
        }

        if(!move){
            // if has more than 50 energy, choose to fight
            if(getEnergy() > 50){
                fights ++;
                return true;
            }
            // if not, run away
            else{
                run(direction);
                return false;
            }
        }

        fights++;
        return true;
    }



    public static void runStats(java.util.List<Critter> Liuxx1){
        int fightCount = 0;
        if(Debug){
        }

        for(Critter critter : Liuxx1){
            Liuxx1 stats = (Liuxx1) critter;
            fightCount += stats.fights;
        }

        System.out.println("" + Liuxx1.size() + " total number of Liuxx1");
        System.out.println("" + fightCount + " total fights of Liuxx1");
    }

    @Override
    public CritterShape viewShape() { return CritterShape.SQUARE; }

}