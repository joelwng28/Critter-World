package assignment5;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {
    static Group world = new Group();
    static Set<String> critterTypes = new HashSet<String>();
    static GridPane grid = new GridPane();
    static BorderPane window = new BorderPane();
    static int row = 0;
    static int worldWidthGUI = 700;
    static int worldHeightGUI = 700;
    static Shape s;
    static boolean stopTimers = true;
    static boolean stopFast = false;
    static boolean stopSlow = false;
    assignment5.Main.Animator fast_animator;
    assignment5.Main.Animator slow_animator;


    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Java FX Critters");
            s = new Rectangle(worldWidthGUI, worldHeightGUI);
            s.setFill(Color.LIGHTBLUE);
            s.setStroke(Color.BLACK);
            s.setStrokeDashOffset(10);
            s.setStrokeWidth(2);
            world.getChildren().add(s);

            // Add a grid pane to lay out the buttons and text fields.


            window.setLeft(grid);
            window.setCenter(world);
            grid.setAlignment(Pos.CENTER_LEFT);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPrefWidth(400);
            grid.setPadding(new Insets(25, 25, 25, 25));


            // Add Field for Critter type.
            Label critName = new Label("Critter Name:");
            grid.add(critName, 0, row);
//			TextField critNameField = new TextField();
//			//row++;
//			grid.add(critNameField, 1, row);

            ComboBox critNameField = new ComboBox();
            ObservableList<String> critterList = FXCollections.observableArrayList(
                    "Algae",
                    "Craig",
                    "Female",
                    "Male",
                    "Longhorn",
                    "Spider",
                    "AlgaephobicCritter",
                    "TragicCritter");
            critNameField.setItems(critterList);
            grid.add(critNameField, 1, row);

            // Add Field for No. of Critters
            Label numCrits = new Label("No of critters:");
            row++;
            grid.add(numCrits, 0, row);
            TextField critNumField = new TextField();
            //row++;
            grid.add(critNumField, 1, row);

            // Add Button to add Critters.
            Button addBtn = new Button("Add critters");
            HBox hbAddBtn = new HBox(10);
            hbAddBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbAddBtn.getChildren().add(addBtn);
            row += 2;
            grid.add(hbAddBtn, 1, row);

            // Action when Add Critters Button is pressed.
            final Text actionTarget = new Text();
            row += 2;
            grid.add(actionTarget, 1, row);
            Scene scene = new Scene(window, 1400, 800);
            primaryStage.setScene(scene);
            primaryStage.show();

            Label timeStep = new Label("# of Time Steps:");
            row++;
            grid.add(timeStep, 0, row);
            TextField timeField = new TextField();
            grid.add(timeField, 1, row);

            Button stepBtn = new Button("Step");
            HBox hbStepBtn = new HBox(10);
            hbStepBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbStepBtn.getChildren().add(stepBtn);
            row += 2;
            grid.add(hbStepBtn, 1, row);

            Button quitBtn = new Button("Quit");
            quitBtn.setPrefWidth(70);
            HBox hbQuitBtn = new HBox(10);
            hbQuitBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbQuitBtn.getChildren().add(quitBtn);
            row += 8;
            grid.add(hbQuitBtn, 0, row);

            Button resetButton = new Button("Clear");
            resetButton.setPrefWidth(70);
            HBox hbResetBtn = new HBox(10);
            hbResetBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbResetBtn.getChildren().add(resetButton);
            grid.add(hbResetBtn, 1, row);

            Button playSlowButton = new Button("Slow");
            playSlowButton.setPrefWidth(70);
            HBox hbPSBtn = new HBox(10);
            hbPSBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbPSBtn.getChildren().add(playSlowButton);
            grid.add(hbPSBtn, 2, row + 5);

            Button playFastButton = new Button("Fast");
            playFastButton.setPrefWidth(70);
            HBox hbPFBtn = new HBox(10);
            hbPFBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbPFBtn.getChildren().add(playFastButton);
            grid.add(hbPFBtn, 2, row + 6);

            Button stopAnmtButton = new Button("Pause");
            stopAnmtButton.setPrefWidth(70);
            HBox hbStpAnmtBtn = new HBox(10);
            hbStpAnmtBtn.setAlignment(Pos.BOTTOM_LEFT);
            hbStpAnmtBtn.getChildren().add(stopAnmtButton);
            grid.add(hbStpAnmtBtn, 2, row + 7);

            //grid.setGridLinesVisible(true);

            updateStats();

            // Action when add critters button is pressed. Call makeCritter.
            // Uses something called an anonymous class of type EventHandler<ActionEvent>, which is a class that is
            // defined inline, in the curly braces.
            addBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });
            stepBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            quitBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Platform.exit();
                }

            });

            resetButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                }

            });
            //Play Fast Button
            playFastButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }

            });
            //Play Slow
            playSlowButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }

            });
            //Pause
            stopAnmtButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void displayWorldGUI(ArrayList<Shape> population) {
        population.add(0, s);
        world.getChildren().setAll(population);
    }

    public static void updateStats() {
        GridPane stats = new GridPane();
        int statsRow = 0;
        Text title = new Text("Stats:");
        title.setUnderline(true);
        title.setFill(Color.FIREBRICK);
        stats.add(title, 0, statsRow);
        statsRow++;

        ComboBox critNameField = new ComboBox();
        ObservableList<String> critterList = FXCollections.observableArrayList(
                "Algae",
                "Craig",
                "Female",
                "Male",
                "Longhorn",
                "Spider",
                "AlgaephobicCritter",
                "TragicCritter");
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
        while (iterator.hasNext()) {
            String stat;
            int critterCount = 0;
            String critter = iterator.next();
            try {
                critterCount = Critter.getInstances("project5." + critter).size();
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
        //stats.setGridLinesVisible(true);
        stats.setAlignment(Pos.CENTER_LEFT);
        stats.setPadding(new Insets(25, 25, 25, 25));
        stats.setPrefWidth(300);
        window.setRight(stats);

        statBtn.setOnAction(new EventHandler<ActionEvent>() {

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
            timer.schedule(new assignment5.Main.Animator.AnimateTask(), 0, milliseconds);
        }

        class AnimateTask extends TimerTask {
            public void run() {

            }
        }

    }
}