package scatter_editor;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.ScatterChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class DataSeries extends Application {

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
        Scene scene = new Scene(root, 300, 500);



        Label dataSeriesX = new Label("X Data:");
        TextArea dataXInput = new TextArea();

        Label legend = new Label("Legend name:");
        TextField legendInput = new TextField();

        Label dataSeriesY = new Label("Y Data:");
        TextArea dataYInput = new TextArea();


        Label errorSeriesX = new Label();
        Label errorSeriesY = new Label();
        Label errorLegend = new Label();

        Button create = new Button("Create");
        create.setOnAction((actionEvent -> {
            String legendIn = legendInput.getText();
            String dataX = dataXInput.getText();
            String[] seriesX = dataX.split("\n");
            String dataY = dataYInput.getText();
            String[] seriesY = dataY.split("\n");

            ScatterPlot.setData(scatterChart, legendIn,seriesX,seriesY);
            primaryStage.close();
        }));

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(6));
        root.add(legend, 0, 1);
        root.add(legendInput, 0, 2);
        root.add(errorLegend, 1, 2);
        root.add(dataSeriesX, 0, 3);
        root.add(dataXInput, 0, 4);
        root.add(errorSeriesX, 0,5);
        root.add(create, 0, 6);
        GridPane.setColumnSpan(create, 2);
        GridPane.setHalignment(create, HPos.CENTER);
        root.add(dataSeriesY, 1, 3);
        root.add(dataYInput, 1, 4);
        root.add(errorSeriesY, 1,5);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
