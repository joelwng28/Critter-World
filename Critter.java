package assignment5;

import java.util.List;
import java.lang.reflect.*;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private boolean canMove = true;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected final String look(int direction, boolean steps) {
		this.energy -= Params.look_energy_cost;
		int range;
		int x = this.x_coord;
		int y = this.y_coord;
		if(steps){
			range = 2;
		}else{
			range = 1;
		}
		switch(direction){
		case 0:
			x += range;
			break;
		case 1:
			x += range;
			y -= range;
			break;
		case 2:
			y -= range;
			break;
		case 3:
			y -= range;
			x -= range;
			break;
		case 4:
			x -= range;
			break;
		case 5:
			x -= range;
			y += range;
			break;
		case 6:
			y += range;
			break;
		case 7:
			x += range;
			y += range;
			break;
		}
		if (x < 0){
			x += Params.world_width;
		}
		if (x > (Params.world_width - 1)){
			x -= Params.world_width;
		}
		if (y < 0){
			y += Params.world_height;
		}
		if (y > (Params.world_height - 1)){
			y -= Params.world_height;
		}
		for(Critter critter : population){
			if(critter.x_coord == x && critter.y_coord == y)
				if(critter.getEnergy() > 0){
					return critter.toString();
				}
		}
		
		return null;
	}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		if(canMove){
			canMove = false;
			switch(direction){
			case 0:
				this.x_coord++;
				break;
			case 1:
				this.x_coord++;
				this.y_coord--;
				break;
			case 2:
				this.y_coord--;
				break;
			case 3:
				this.y_coord--;
				this.x_coord--;
				break;
			case 4:
				this.x_coord--;
				break;
			case 5:
				this.x_coord--;
				this.y_coord++;
				break;
			case 6:
				this.y_coord++;
				break;
			case 7:
				this.x_coord++;
				this.y_coord++;
				break;
			}
			if (this.x_coord < 0){
				this.x_coord += Params.world_width;
			}
			if (this.x_coord > (Params.world_width - 1)){
				this.x_coord -= Params.world_width;
			}
			if (this.y_coord < 0){
				this.y_coord += Params.world_height;
			}
			if (this.y_coord > (Params.world_height - 1)){
				this.y_coord -= Params.world_height;
			}
		}
	}
	
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		if(canMove) {
			canMove = false;
			switch (direction) {
				case 0:
					this.x_coord += 2;
					break;
				case 1:
					this.x_coord += 2;
					this.y_coord -= 2;
					break;
				case 2:
					this.y_coord -= 2;
					break;
				case 3:
					this.y_coord -= 2;
					this.x_coord -= 2;
					break;
				case 4:
					this.x_coord -= 2;
					break;
				case 5:
					this.x_coord -= 2;
					this.y_coord += 2;
					break;
				case 6:
					this.y_coord += 2;
					break;
				case 7:
					this.x_coord += 2;
					this.y_coord += 2;
					break;
			}
			if (this.x_coord < 0){
				this.x_coord += Params.world_width;
			}
			if (this.x_coord > (Params.world_width - 1)){
				this.x_coord -= Params.world_width;
			}
			if (this.y_coord < 0){
				this.y_coord += Params.world_height;
			}
			if (this.y_coord > (Params.world_height - 1)){
				this.y_coord -= Params.world_height;
			}
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy){ return;}
		offspring.energy = this.energy/2;
		this.energy = (this.energy + 1)/2;

		offspring.walk(direction);
		offspring.energy += Params.walk_energy_cost;

		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	public static void worldTimeStep() {
		for (Critter critter : population){
			critter.doTimeStep();
			if (critter.getEnergy() <= 0){
				population.remove(critter);
			}
		}

		//then process encounter
		//identify if two or more critters are in the same spot
		for(Critter first: population){
			for(Critter second: population){
				if(first == second){
					continue;
				}
				if(first.getEnergy() <= 0 || second.getEnergy() <= 0){
					continue;
				}
				if((first.x_coord == second.x_coord) && (first.y_coord == second.y_coord)){
					int currentX = first.x_coord;
					int currentY = first.y_coord;
					boolean firstSame = false;
					boolean secondSame = false;
					boolean firstFight = first.fight(second.toString());
					if(!first.isEmpty()){
						first.x_coord = currentX;
						first.y_coord = currentY;
					}
					boolean secondFight = second.fight(first.toString());
					if(!second.isEmpty()){
						second.x_coord = currentX;
						second.y_coord = currentY;
					}
					firstSame = (first.x_coord == currentX && first.y_coord == currentY);
					secondSame = (second.x_coord == currentX && second.y_coord == currentY);
					if(firstSame && secondSame){
						if((first.getEnergy() >= 0) && (second.getEnergy() >= 0)){
							int firstRoll;
							int secondRoll;
							if(!firstFight){
								firstRoll = 0;
							}
							else{
								firstRoll = Critter.getRandomInt(first.getEnergy());
							}
							if(!secondFight){
								secondRoll = 0;
							}
							else{
								secondRoll = Critter.getRandomInt(second.getEnergy());
							}
							if(firstRoll >= secondRoll){
								first.energy += ((second.getEnergy())/2);
								second.energy = 0;
							}
							if(secondRoll > firstRoll){
								second.energy += ((first.getEnergy())/2);
								first.energy = 0;
							}
						}
					}
				}
			}
		}

		//count rest energy
		for (Critter critter : population){
			critter.energy = critter.energy - Params.rest_energy_cost;
		}

		//Algea spawn
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try {
				Critter.makeCritter("Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}

		//New babies spawn
		for(Critter baby : babies){
			population.add(baby);
		}

		//java.util.ArrayList<Critter> temp = new java.util.ArrayList<Critter>(babies);
		babies.clear();

		//clean up
		java.util.ArrayList<Critter> remove = new java.util.ArrayList<Critter>();
		for(Critter toRemove : population){
			if(toRemove.energy <= 0){
				remove.add(toRemove);
			}
		}

		for(Critter critter : remove){
			population.remove(critter);
		}
	}
	
	public static void displayWorld(Object pane) {
		String[][] output = new String[Params.world_height+2][Params.world_width+2];
		int width = Params.world_width;
		int height = Params.world_height;
		for(int y = 0; y < (height + 2); y++){
			for(int x = 0; x < (width + 2); x++){
				if (y == 0 || y == (height + 1)) {
					if (x == 0 || x == (width + 1)) {
						output[y][x] = "+";
						continue;
					}
					else {
						output[y][x] = "-";
						continue;
					}
				}
				else if (x == 0 || x == (width + 1)) {
					output[y][x] = "|";
					continue;
				}
				else{
					output[y][x] = " ";
				}
			}
		}
		for (Critter critter : population) {
			output[critter.y_coord + 1][critter.x_coord + 1] = critter.toString();			
		}
		for(String[] row: output){
			for(String element: row){
				System.out.print(element);
			}
			System.out.println();
		}
	} 
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> critterClass;
		Constructor<?> critterConstructor;
		try {
			critterClass = Class.forName(myPackage + "." + critter_class_name);
			critterConstructor = critterClass.getConstructor();
			Critter newCritter = (Critter)critterConstructor.newInstance();
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			newCritter.energy = Params.start_energy;
			population.add(newCritter);
		} catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
			//e.printStackTrace();
		}
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> c_class = null;
		try {
			c_class = Class.forName(myPackage + "." + critter_class_name);
		} catch (Exception e){
			e.printStackTrace();
		}

		for(Critter critter : population){
			if(c_class.isInstance(critter)){
				result.add(critter);
			}
		}

		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();	
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}
	private boolean isEmpty(){
		for (Critter other : population){
			if(this == other){
				continue;
			}
			if((this.x_coord == other.x_coord) && (this.y_coord == other.y_coord)){
				if(other.energy > 0){
					return false;
				}
			}
		}
		return true;
	}
	
}
