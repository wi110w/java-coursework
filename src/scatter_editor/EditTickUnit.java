package scatter_editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditTickUnit extends Application {

    private ScatterChart<Number, Number> chart;

    public void setChart(ScatterChart<Number, Number> sc) {
        chart = sc;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SDE");
        GridPane root = new GridPane();

        CheckBox editX = new CheckBox("X Axis");
        CheckBox editY = new CheckBox("Y Axis");

        TextField tickXInput = new TextField();
        TextField tickYInput = new TextField();

        tickXInput.setDisable(true);
        tickYInput.setDisable(true);

        editX.setOnAction(actionEvent -> {
            if(editX.isSelected()) {
                tickXInput.setDisable(false);
            }
            else {
                tickXInput.setDisable(true);
            }
        });

        editY.setOnAction(actionEvent -> {
            if(editY.isSelected()) {
                tickYInput.setDisable(false);
            }
            else {
                tickYInput.setDisable(true);
            }
        });

        Button edit = new Button("Edit");

        edit.setOnAction(actionEvent -> {
            int tickXUnit = 0;
            int tickYUnit = 0;
            if(!tickXInput.isDisabled()) {
                tickXUnit = Integer.parseInt(tickXInput.getText());
            }
            if(!tickYInput.isDisabled()) {
                tickYUnit = Integer.parseInt(tickYInput.getText());
            }

            ScatterPlot.editTickUnits(chart, tickXUnit, tickYUnit);
            primaryStage.close();
        });

        root.setVgap(10);
        root.add(editX, 0,0);
        root.add(tickXInput, 1,0);
        root.add(editY, 0,1);
        root.add(tickYInput, 1,1);
        root.add(edit, 0,2);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
