package scatter_editor;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Names extends Application {

    ScatterChart<Number, Number> scatterChart;

    public void setChart(ScatterChart<Number, Number> sc) {
        scatterChart = sc;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SDE");
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 200, 250);

        Label title = new Label("Title:");
        TextField titleInput = new TextField();

        Label nameX = new Label("X Axis:");
        TextField nameXInput = new TextField();

        Label nameY = new Label("Y Axis:");
        TextField nameYInput = new TextField();

        Label errorTitle = new Label();
        Label errorNameX = new Label();
        Label errorNameY = new Label();


        Button set = new Button("Set");
        set.setOnAction(actionEvent -> {
            String titleIn = titleInput.getText();
            String nameXIn = nameXInput.getText();
            String nameYIn = nameYInput.getText();

            ScatterPlot.setNames(scatterChart, titleIn, nameXIn,nameYIn);
            primaryStage.close();
        });

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(5,15,15,15));
        root.add(title, 0, 1);
        root.add(titleInput, 0, 2);
        root.add(nameX, 0, 3);
        root.add(nameXInput, 0, 4);
        root.add(set, 0, 7);
        GridPane.setHalignment(set, HPos.CENTER);
        root.add(nameY, 0, 5);
        root.add(nameYInput, 0, 6);


        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
