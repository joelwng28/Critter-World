package assignment5;

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

import java.util.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	static Group stage = new Group();
	static Set<String> critterTypes = new HashSet<String>();
	static GridPane grid = new GridPane();
	public static BorderPane window = new BorderPane();
	static int row = 0;
	static int worldWidth = 720;
	static int worldHeight = 720;
	static Shape shape;

	static Motion motion;
    static boolean stopMotion = true;
    static Text statsAlgae;
    static Text statsCraig;
    static Text statsLiuxx;
    static Text statsJoel;
    static Text statsMoez;
    static Text statsNanda;



	@Override
	public void start(Stage primaryStage){
		try{
			primaryStage.setTitle("Project 5");
			shape = new Rectangle(worldWidth + 2, worldHeight + 2);
			shape.setFill(Color.LAVENDER);
			shape.setStroke(Color.BLACK);
			shape.setStrokeDashOffset(10);
			shape.setStrokeWidth(2);
			stage.getChildren().add(shape);
			stage.setAutoSizeChildren(true);

			// Add a grid pane to lay out the buttons and text fields.
			window.setLeft(grid);
			window.setRight(stage);
			grid.setAlignment(Pos.CENTER_LEFT);
			grid.setHgap(5);
			grid.setVgap(5);
			grid.setPrefWidth(360);
			grid.setPadding(new Insets(10, 10, 10, 10));

			// Add Field for Critter type.
			Label type = new Label("Critter Type:");
			grid.add(type, 0, row);
			ComboBox typeSelection = new ComboBox();
			ObservableList<String> typeList = FXCollections.observableArrayList(
					"Algae",
					"Craig",
                    "Liuxx",
                    "Joel",
                    "Nandakumar",
                    "Moez");

            typeSelection.setItems(typeList);
			grid.add(typeSelection, 1, row);

			// Add Field for No. of Critters
			Label amount = new Label("Amount:");
			row++;
			grid.add(amount, 0, row);
			TextField amountField = new TextField();
			grid.add(amountField, 1, row);

			//Add button
			Button add = new Button("Add");
			HBox addBox = new HBox(10);
			addBox.setAlignment(Pos.BOTTOM_LEFT);
			addBox.getChildren().add(add);
			grid.add(addBox, 2, row);

			//Critter type warning
			final Text critterSelection = new Text();
			row += 2;
			grid.add(critterSelection, 1, row);

			Label steps = new Label("Steps:");
			row++;
			grid.add(steps, 0, row);
			TextField time = new TextField();
			grid.add(time, 1, row);

            //Add go button
            //Go button go for the amount of step selected by user
            Button go = new Button("Go");
            HBox goBox = new HBox(10);
            goBox.setAlignment(Pos.BOTTOM_LEFT);
            goBox.getChildren().add(go);
            grid.add(goBox, 2, row);

            final Text timeSelection = new Text();
            row += 2;
            grid.add(timeSelection, 2, row);

			//Add step button
            //Step only go for 1 step
            Button step = new Button("Step");
            HBox stepBox = new HBox(10);
            stepBox.setAlignment(Pos.BOTTOM_LEFT);
            stepBox.getChildren().add(step);
            grid.add(stepBox, 1, row);

            //Add buttion for setseed
            Label seed = new Label("Seed:");
            row += 4;
            grid.add(seed, 0, row);
            TextField seedInput = new TextField();
            grid.add(seedInput, 1, row);
            Button setSeed = new Button("Set");
            HBox seedBox = new HBox(10);
            seedBox.setAlignment(Pos.BOTTOM_LEFT);
            seedBox.getChildren().add(setSeed);
            grid.add(seedBox, 2, row);

			//Add buttons for stats
            Label stat = new Label("Stats Type:");
            row += 10;
            grid.add(stat, 0, row);
            ComboBox statSelection = new ComboBox();

            ObservableList<String> statsList = FXCollections.observableArrayList(
                    "Algae",
                    "Craig",
                    "Liuxx",
                    "Joel",
                    "Nandakumar",
                    "Moez",
                    "All"
            );

            statSelection.setItems(statsList);
            grid.add(statSelection, 1, row);

            Button show = new Button("Show");
            HBox showBox = new HBox(10);
            showBox.setAlignment(Pos.BOTTOM_LEFT);
            showBox.getChildren().add(show);
            grid.add(showBox, 2, row);

            row += 2;
            statsAlgae = new Text("");
            grid.add(statsAlgae, 1, row);

            Button off = new Button("Off");
            HBox offBox = new HBox(10);
            offBox.setAlignment(Pos.BOTTOM_LEFT);
            offBox.getChildren().add(off);
            grid.add(offBox, 2, row);

            statsCraig = new Text("");
            row += 1;
            grid.add(statsCraig, 1, row);

            statsLiuxx = new Text("");
            row += 1;
            grid.add(statsLiuxx, 1, row);

            statsJoel = new Text("");
            row += 1;
            grid.add(statsJoel, 1, row);

            statsMoez = new Text("");
            row ++;
            grid.add(statsMoez, 1, row);

            statsNanda = new Text("");
            row ++;
            grid.add(statsNanda, 1, row);


			//Add buttons for animation
			Button play = new Button("Play");
			play.setPrefWidth(70);
			HBox playBox = new HBox(10);
            playBox.setAlignment(Pos.BOTTOM_LEFT);
            playBox.getChildren().add(play);
			grid.add(playBox, 2, row + 5);

            Button slow = new Button("Slow");
            slow.setPrefWidth(70);
            HBox slowBox = new HBox(10);
            slowBox.setAlignment(Pos.BOTTOM_LEFT);
            slowBox.getChildren().add(slow);
            grid.add(slowBox, 2, row + 6);

			Button playFast = new Button("Fast");
			playFast.setPrefWidth(70);
			HBox fastBox = new HBox(10);
			fastBox.setAlignment(Pos.BOTTOM_LEFT);
			fastBox.getChildren().add(playFast);
			grid.add(fastBox, 2, row+7);

			Button stopPlay = new Button("Stop");
			stopPlay.setPrefWidth(70);
			HBox stopBox = new HBox(10);
			stopBox.setAlignment(Pos.BOTTOM_LEFT);
			stopBox.getChildren().add(stopPlay);
			grid.add(stopBox, 2, row+8);

            //Add exit button
            Button exit = new Button("Exit");
            exit.setPrefWidth(70);
            HBox exitBox = new HBox(10);
            exitBox.setAlignment(Pos.BOTTOM_RIGHT);
            exitBox.getChildren().add(exit);
            row += 15;
            grid.add(exitBox, 0, row);

            //Add reset button
            Button reset = new Button("Reset");
            reset.setPrefWidth(70);
            HBox resBox = new HBox(10);
            resBox.setAlignment(Pos.BOTTOM_LEFT);
            resBox.getChildren().add(reset);
            grid.add(resBox, 1, row);

            Scene scene = new Scene(window, 1650, 800);
            primaryStage.setScene(scene);
            primaryStage.show();

            //Set Action for each buttons
			add.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(stopMotion){
						String critterClassName = (String) typeSelection.getValue();
						String numString = amountField.getText();
						int newCount = 1;
						if(!numString.isEmpty()){ newCount = Integer.parseInt(numString); }
						try{
							for (int i = 0; i < newCount; i++) {
								Critter.makeCritter(critterClassName);
							}
							critterTypes.add(critterClassName);
							Critter.displayWorld(stage, shape); // or Whatever

						} catch(InvalidCritterException e) {
                            critterSelection.setFill(Color.RED);
                            critterSelection.setText("Invalid Critter!");
						}
					}
				}
			});

			go.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String steps = time.getText();
					int timeStepsCount = 1;

					if(steps.isEmpty()){
					    timeSelection.setFill(Color.RED);
					    timeSelection.setText("Invalid Steps");
					    return;
                    }

					if(!steps.isEmpty()){
					    timeStepsCount = Integer.parseInt(steps);
					}

					for(int i = 0; i < timeStepsCount; i++){
						Critter.worldTimeStep();
					}

					critterTypes.add("Algae");
					Critter.displayWorld(stage, shape); // or whatever
					//updateStats();
				}
			});

			step.setOnAction(new EventHandler<ActionEvent>(){
			    @Override
                public void handle(ActionEvent event){
                    Critter.worldTimeStep();
                    critterTypes.add("Algae");
                    Critter.displayWorld(stage, shape);
                }
            });


			setSeed.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    try{
                        String getSeed = seedInput.getText();
                        if(getSeed.isEmpty()){
                            return;
                        }

                        long seedNumber = Integer.parseInt(getSeed);
                        Critter.setSeed(seedNumber);
                    } catch (Exception e){
                        System.out.println("Invalid Seed");
                    }
                }
            });

			show.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    String temp = (String)statSelection.getValue();
                    int count;
                    if(temp == null || temp == "All"){
                        try {
                            temp = "Algae";
                            count = Critter.getInstances(temp).size();
                            statsAlgae.setFill(Color.ORANGE);
                            statsAlgae.setText("Algea: " + count);
                            temp = "Craig";
                            count = Critter.getInstances(temp).size();
                            statsCraig.setFill(Color.BLUE);
                            statsCraig.setText("Craig: " + count);
                            temp = "Liuxx";
                            count = Critter.getInstances(temp).size();
                            statsLiuxx.setFill(Color.BLACK);
                            statsLiuxx.setText("Liuxx: " + count);
                            temp = "Joel";
                            count = Critter.getInstances(temp).size();
                            statsJoel.setFill(Color.BLUEVIOLET);
                            statsJoel.setText("Joel  : " + count);
                            temp = "Moez";
                            count = Critter.getInstances(temp).size();
                            statsMoez.setFill(Color.RED);
                            statsMoez.setText("Moez  : " + count);
                            temp = "Nandakumar";
                            count = Critter.getInstances(temp).size();
                            statsNanda.setFill(Color.DARKGREEN);
                            statsNanda.setText("Nandakumar  : " + count);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        try{
                            count = Critter.getInstances(temp).size();

                            if(temp.equals("Algae")){
                                statsAlgae.setFill(Color.ORANGE);
                                statsAlgae.setText("Algea: " + count);
                            }
                            else if(temp.equals("Craig")){
                                statsCraig.setFill(Color.BLUE);
                                statsCraig.setText("Craig: " + count);
                            }
                            else if(temp.equals("Liuxx")){
                                statsLiuxx.setFill(Color.BLACK);
                                statsLiuxx.setText("Liuxx: " + count);
                            }
                            else if(temp.equals("Joel")){
                                statsJoel.setFill(Color.BLUEVIOLET);
                                statsJoel.setText("Joel  : " + count);
                            }

                            else if(temp.equals("Moez")){
                                statsMoez.setFill(Color.RED);
                                statsMoez.setText("Moez  : " + count);
                            }

                            else if(temp.equals("Nandakumar")){
                                statsNanda.setFill(Color.DARKGREEN);
                                statsNanda.setText("Nandakumar  : " + count);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });

			off.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    statsAlgae.setText("");
                    statsCraig.setText("");
                    statsLiuxx.setText("");
                    statsJoel.setText("");
                    statsMoez.setText("");
                    statsNanda.setText("");
                }
            });

			exit.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});

			reset.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					Critter.clearWorld();
                    statsAlgae.setText("");
                    statsCraig.setText("");
                    statsLiuxx.setText("");
                    statsJoel.setText("");
                    statsMoez.setText("");
                    statsNanda.setText("");
					Critter.displayWorld(stage, shape);
				}

			});



			play.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopMotion = false;
					motion = new Motion();
				}

			});

            slow.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (stopMotion) {
                        return;
                    }
                    motion.slow();
                }
            });

            playFast.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    if(stopMotion){
                        return;
                    }
                    motion.fast();
                }

            });


			stopPlay.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopMotion = true;
				}
			});

		} catch(Exception e) {
			e.printStackTrace();		
		}
	}

	public static void updateStats(String type){

    }
	
	public static void main(String[] args) {
		launch(args);
	}
    
	class Motion {
	    Timer timer;
	    private long period;

	    public Motion() {
	        timer = new Timer();
	        period = 1000;
	        timer.schedule(new MotionTask(), 0, period);
		}

		public void fast(){
	        if(period < 51){
	            return;
            }

	        period = period - 50;
            timer = new Timer();
            timer.schedule(new MotionTask(), 0, period);
        }

        public void slow(){
		    if(period > 999)
		        return;
		    period = period + 100;
            timer = new Timer();
            timer.schedule(new MotionTask(), 0, period);
        }
	    
	    class MotionTask extends TimerTask {
	        public void run() {
	        	Platform.runLater(new Runnable() {
	        		public void run() {
	        			critterTypes.add("Algae");
	        			if(stopMotion)
	        				timer.cancel();

	        			else{
	        				Critter.worldTimeStep();
		                    Critter.displayWorld(stage, shape);
                            try{
                                if(!statsAlgae.getText().equals("")){
                                    String temp = "Algae";
                                    int count = Critter.getInstances(temp).size();
                                    statsAlgae.setFill(Color.ORANGE);
                                    statsAlgae.setText("Algea: " + count);
                                }
                                if(!statsCraig.getText().equals("")){
                                    String temp = "Craig";
                                    int count = Critter.getInstances(temp).size();
                                    statsCraig.setFill(Color.BLUE);
                                    statsCraig.setText("Craig: " + count);
                                }
                                if(!statsLiuxx.getText().equals("")){
                                    String temp = "Liuxx";
                                    int count = Critter.getInstances(temp).size();
                                    statsLiuxx.setFill(Color.BLACK);
                                    statsLiuxx.setText("Liuxx: " + count);
                                }
                                if(!statsJoel.getText().equals("")){
                                    String temp = "Joel";
                                    int count = Critter.getInstances(temp).size();
                                    statsJoel.setFill(Color.BLUEVIOLET);
                                    statsJoel.setText("Joel: " + count);
                                }

                                if(!statsMoez.getText().equals("")){
                                    String temp = "Moez";
                                    int count = Critter.getInstances(temp).size();
                                    statsMoez.setFill(Color.RED);
                                    statsMoez.setText("Moez: " + count);
                                }

                                if(!statsNanda.getText().equals("")){
                                    String temp = "Nandakumar";
                                    int count = Critter.getInstances(temp).size();
                                    statsNanda.setFill(Color.DARKGREEN);
                                    statsNanda.setText("Nandakumar: " + count);
                                }
                            }
                            catch (InvalidCritterException e){

                            }

	        			}
	        		}
	        	});

	        }
	    }

	}

}