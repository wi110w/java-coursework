package scatter_editor;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditNames extends Application {

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

        titleInput.setText(scatterChart.getTitle());
        nameXInput.setText(scatterChart.getXAxis().getLabel());
        nameYInput.setText(scatterChart.getYAxis().getLabel());

        Button editNames = new Button("Edit");
        editNames.setOnAction(actionEvent -> {
            String edTitle = titleInput.getText();
            String edNameX = nameXInput.getText();
            String edNameY = nameYInput.getText();

            ScatterPlot.editNames(scatterChart, edTitle, edNameX, edNameY);
            primaryStage.close();
        });

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(5,15,15,15));
        root.add(title, 0, 1);
        root.add(titleInput, 0, 2);
        root.add(nameX, 0, 3);
        root.add(nameXInput, 0, 4);
        root.add(editNames, 0, 7);
        GridPane.setHalignment(editNames, HPos.CENTER);
        root.add(nameY, 0, 5);
        root.add(nameYInput, 0, 6);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
