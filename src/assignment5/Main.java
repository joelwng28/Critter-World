package assignment5;
/* CRITTERS <MyClass.java>
 * EE422C assignment 5 submission by
 * Matt Owens
 * mo8755
 * 16340
 * Yash Parekh
 * yjp246
 * 16345
 * Slip days used: 1
 * Fall 2015
 */
	

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	static Group stage = new Group();
	static Set<String> critterTypes = new HashSet<String>();
	static GridPane grid = new GridPane();
	static BorderPane window = new BorderPane();
	static int row = 0;
	static int worldWidthGUI = 640;
	static int worldHeightGUI = 640;
	static Shape shape;
	static boolean stopTimers = true;
	static boolean stopFast = false;
	static boolean stopSlow = false;
	Animator fast_animator;
	Animator slow_animator;


	@Override
	public void start(Stage primaryStage){
		try{
			primaryStage.setTitle("Project 5");
			shape = new Rectangle(worldWidthGUI, worldHeightGUI);
			shape.setFill(Color.LAVENDER);
			shape.setStroke(Color.BLACK);
			shape.setStrokeDashOffset(10);
			shape.setStrokeWidth(2);
			stage.getChildren().add(shape);

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
            statSelection.setItems(typeList);
            grid.add(statSelection, 1, row);

            Button show = new Button("Show");
            HBox showBox = new HBox(10);
            showBox.setAlignment(Pos.BOTTOM_LEFT);
            showBox.getChildren().add(show);
            grid.add(showBox, 2, row);

            row += 2;
            final Text statsAlgae = new Text();
            grid.add(statsAlgae, 1, row);

            final Text statsCraig = new Text();
            row += 1;
            grid.add(statsCraig, 1, row);

            final Text statsLiuxx = new Text();
            row += 1;
            grid.add(statsLiuxx, 1, row);

            final Text statsJoel = new Text();
            row += 1;
            grid.add(statsJoel, 1, row);


			//Add buttons for animation
			Button playSlowButton = new Button("Slow");
			playSlowButton.setPrefWidth(70);
			HBox hbPSBtn = new HBox(10);
			hbPSBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbPSBtn.getChildren().add(playSlowButton);
			grid.add(hbPSBtn, 2, row+5);

			Button playFastButton = new Button("Fast");
			playFastButton.setPrefWidth(70);
			HBox hbPFBtn = new HBox(10);
			hbPFBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbPFBtn.getChildren().add(playFastButton);
			grid.add(hbPFBtn, 2, row+6);

			Button stopAnmtButton = new Button("Pause");
			stopAnmtButton.setPrefWidth(70);
			HBox hbStpAnmtBtn = new HBox(10);
			hbStpAnmtBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbStpAnmtBtn.getChildren().add(stopAnmtButton);
			grid.add(hbStpAnmtBtn, 2, row+7);

            //Add exit button
            Button exit = new Button("Exit");
            exit.setPrefWidth(70);
            HBox exitBox = new HBox(10);
            exitBox.setAlignment(Pos.BOTTOM_RIGHT);
            exitBox.getChildren().add(exit);
            row += 20;
            grid.add(exitBox, 0, row);

            //Add reset button
            Button reset = new Button("Reset");
            reset.setPrefWidth(70);
            HBox resBox = new HBox(10);
            resBox.setAlignment(Pos.BOTTOM_LEFT);
            resBox.getChildren().add(reset);
            grid.add(resBox, 1, row);

            Scene scene = new Scene(window, worldWidthGUI + 362, worldHeightGUI + 2);
            primaryStage.setScene(scene);
            primaryStage.show();

            //Set Action for each buttons
			add.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(stopTimers){
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
                    try{
                        int count = Critter.getInstances(temp).size();

                        if(temp.equals("Algae")){
                            statsAlgae.setFill(Color.ORANGE);
                            statsAlgae.setText("Algea: " + count);
                        }
                        else if(temp.equals("Craig")){
                            statsCraig.setFill(Color.BLUE);
                            statsCraig.setText("Craig: " + count);
                        }
                        else if(temp.equals("Liuxx")){
                            statsLiuxx.setFill(Color.LIGHTBLUE);
                            statsLiuxx.setText("Liuxx: " + count);
                        }
                        else if(temp.equals("Joel")){
                            statsJoel.setFill(Color.BLUEVIOLET);
                            statsJoel.setText("Joel  : " + count);
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
					Critter.displayWorld(stage, shape);
				}

			});
			//Play Fast Button
			playFastButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopTimers = false;
					stopSlow = true;
					stopFast = false;
					fast_animator = new Animator(100, "fast");
				}

			});
			//Play Slow
			playSlowButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopTimers = false;
					stopFast = true;
					stopSlow = false;
					slow_animator = new Animator(300, "slow");
				}

			});
			//Pause
			stopAnmtButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopTimers = true;

				}

			});

		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void updateStats() {
		GridPane stats = new GridPane();
		int statsRow = 0;
		Text title = new Text("Stats:");
		title.setUnderline(true);
		title.setFill(Color.RED);
		stats.add(title, 0, statsRow);
		statsRow++;
		
		ComboBox critNameField = new ComboBox();
		ObservableList<String> critterList = FXCollections.observableArrayList(
				"Algae", 
				"Craig"
				);
		critNameField.setItems(critterList);
		stats.add(critNameField, 0, statsRow);
		statsRow++;
		Button statBtn = new Button("Get Stats");
		HBox hbStatBtn = new HBox(10);
		hbStatBtn.setAlignment(Pos.CENTER_LEFT);
		hbStatBtn.getChildren().add(statBtn);
		row += 2;
		stats.add(hbStatBtn, 0, statsRow);
		
		statsRow++;
		Iterator<String> iterator = critterTypes.iterator();
		while(iterator.hasNext()) {
			String stat;
			int critterCount = 0;
			String critter = iterator.next();
			try {
				critterCount = Critter.getInstances(critter).size();
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
			stat = critterCount + " " + critter + " Critters";
			Text statText = new Text("  " + stat);
			stats.add(statText, 0, statsRow);
			statsRow++;
		}
		stats.setHgap(5);
		stats.setVgap(5);
		stats.setAlignment(Pos.CENTER_LEFT);
		stats.setPadding(new Insets(25, 25, 25, 25));
		stats.setPrefWidth(300);
		window.setRight(stats);
		
		statBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				critterTypes.add((String) critNameField.getValue());
				updateStats();
			}
			
		});
	}


	//Handles animation
	class Animator {
	    Timer timer;
	    String speed;

	    public Animator(long milliseconds, String label) {
	        timer = new Timer();
	        speed = label;
	        timer.schedule(new AnimateTask(), 0, milliseconds);
		}
	    
	    class AnimateTask extends TimerTask {
	        public void run() {
	        	Platform.runLater(new Runnable() {
	        		public void run() {
	        			critterTypes.add("Algae");
	        			if(stopTimers)
	        				timer.cancel();
	        			else if(speed.equals("fast") && stopFast)
	        				timer.cancel();
	        			else if(speed.equals("slow") && stopSlow)
	        				timer.cancel();
	        			else{
	        				Critter.worldTimeStep();
		                    Critter.displayWorld(stage, shape);
		                    //updateStats();
	        			}
	        		}
	        	});

	        }
	    }

	}

}