package scatter_editor;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DataSeries extends Application {

    ScatterChart<Number, Number> chart;

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

        Label dataSeriesX = new Label("X Data:");
        TextArea dataXInput = new TextArea();

        Label legend = new Label("Legend name:");
        TextField legendInput = new TextField();

        Label dataSeriesY = new Label("Y Data:");
        TextArea dataYInput = new TextArea();

        Button create = new Button("Create");
        create.setOnAction((actionEvent -> {
            String legendIn = legendInput.getText();
            String dataX = dataXInput.getText();
            String[] seriesX = dataX.split("\n");
            String dataY = dataYInput.getText();
            String[] seriesY = dataY.split("\n");

            ScatterPlot.setData(chart, legendIn, seriesX, seriesY);
            primaryStage.close();
        }));

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(6));
        root.add(legend, 0, 1);
        root.add(legendInput, 0, 2);
        root.add(dataSeriesX, 0, 3);
        root.add(dataXInput, 0, 4);
        root.add(create, 0, 6);
        GridPane.setColumnSpan(create, 2);
        GridPane.setHalignment(create, HPos.CENTER);
        root.add(dataSeriesY, 1, 3);
        root.add(dataYInput, 1, 4);

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
