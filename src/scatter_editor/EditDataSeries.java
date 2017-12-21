package scatter_editor;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditDataSeries extends Application {

    XYChart.Series data;
    int index;
    ScatterChart<Number, Number> scatterChart;

    public void setData(XYChart.Series d) {
        data = d;
    }

    public void setIndex(int i) {
        index = i;
    }

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
        Scene scene = new Scene(root, 300, 300);

        Label dataSeriesX = new Label("X Data:");
        TextArea dataXInput = new TextArea();

        Label legend = new Label("Legend name:");
        TextField legendInput = new TextField();

        Label dataSeriesY = new Label("Y Data:");
        TextArea dataYInput = new TextArea();

        legendInput.setText(data.getName());

        ObservableList<XYChart.Data> series = data.getData();
        String dataX = "";
        String dataY = "";

        for (int i = 0; i < series.size(); i++) {
            dataX += series.get(i).getXValue() + "\n";
            dataY += series.get(i).getYValue() + "\n";
        }

        dataXInput.setText(dataX);
        dataYInput.setText(dataY);

        Button edit = new Button("Edit");
        edit.setOnAction(actionEvent -> {
            String EdLegend = legendInput.getText();
            String EdDataX = dataXInput.getText();
            String EdDataY = dataYInput.getText();
            String[] seriesX = EdDataX.split("\n");
            String[] seriesY = EdDataY.split("\n");

            ScatterPlot.editData(scatterChart, index, EdLegend, seriesX, seriesY);
            primaryStage.close();
        });

        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(6));
        root.add(legend, 0, 1);
        root.add(legendInput, 0, 2);
        root.add(dataSeriesX, 0, 3);
        root.add(dataXInput, 0, 4);
        root.add(edit, 0, 6);
        GridPane.setColumnSpan(edit, 2);
        GridPane.setHalignment(edit, HPos.CENTER);
        root.add(dataSeriesY, 1, 3);
        root.add(dataYInput, 1, 4);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
