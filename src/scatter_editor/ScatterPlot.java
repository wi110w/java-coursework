package scatter_editor;

import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ScatterPlot extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    }

    public static void setNames(ScatterChart<Number, Number> sc, String title, String xAxisName, String yAxisName) {
        NumberAxis xAxis = (NumberAxis) sc.getXAxis();
        NumberAxis yAxis = (NumberAxis) sc.getYAxis();
        sc.setTitle(title);
        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);
    }

    public static void setData(ScatterChart<Number, Number> sc, String legend, String[] dataX, String[] dataY) {
        XYChart.Series series = new XYChart.Series();
        series.setName(legend);
        for (int i = 0; i < dataX.length; i++) {
            series.getData().add(new XYChart.Data(Float.parseFloat(dataX[i]), Float.parseFloat(dataY[i])));
        }

        sc.getData().add(series);
    }

    public static void editData(ScatterChart<Number, Number> sc, int index, String legend, String[] dataX, String[] dataY) {
        sc.getData().remove(index);
        XYChart.Series series = new XYChart.Series();
        series.setName(legend);
        for (int i = 0; i < dataX.length; i++) {
            series.getData().add(new XYChart.Data(Float.parseFloat(dataX[i]), Float.parseFloat(dataY[i])));
        }

        sc.getData().add(series);
    }

    public static void editNames(ScatterChart<Number, Number> sc, String title, String xAxisName, String yAxisName) {

    }

    public static void deleteData(ScatterChart<Number, Number> sc, int index) {
        sc.getData().remove(index);
    }

    public static void deleteNames(ScatterChart<Number, Number> sc) {

    }
}
