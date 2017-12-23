package scatter_editor;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class EditDataSeries extends Application {
    private XYChart.Series<Number, Number> series;
    private int index;
    private ScatterChart<Number, Number> scatterChart;

    void setSeries(XYChart.Series d) {
        series = d;
    }

    void setIndex(int i) {
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

        Label dataSeriesX = new Label("X Data:");
        TextArea dataXInput = new TextArea();

        Label legend = new Label("Legend name:");
        TextField legendInput = new TextField();

        Label dataSeriesY = new Label("Y Data:");
        TextArea dataYInput = new TextArea();

        legendInput.setText(series.getName());

        List<XYChart.Data<Number, Number>> data = series.getData();
        StringBuilder dataX = new StringBuilder();
        StringBuilder dataY = new StringBuilder();

        for (XYChart.Data<Number, Number> s : data) {
            dataX.append(s.getXValue()).append("\n");
            dataY.append(s.getYValue()).append("\n");
        }

        dataXInput.setText(dataX.toString());
        dataYInput.setText(dataY.toString());

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

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
