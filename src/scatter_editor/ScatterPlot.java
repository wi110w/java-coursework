package scatter_editor;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.List;

class ScatterPlot {
    static void setNames(ScatterChart<Number, Number> sc, String title, String xAxisName, String yAxisName) {
        NumberAxis xAxis = (NumberAxis) sc.getXAxis();
        NumberAxis yAxis = (NumberAxis) sc.getYAxis();
        sc.setTitle(title);
        xAxis.setLabel(xAxisName);
        yAxis.setLabel(yAxisName);
    }

    static void setData(ScatterChart<Number, Number> sc, String legend, String[] dataX, String[] dataY) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(legend);

        fillSeries(dataX, dataY, series);

        sc.getData().add(series);
    }

    static void editData(ScatterChart<Number, Number> sc, int index, String legend, String[] dataX, String[] dataY) {
        XYChart.Series<Number, Number> series = sc.getData().get(index);
        series.setName(legend);

        series.getData().clear();
        fillSeries(dataX, dataY, series);
    }

    private static void fillSeries(String[] x, String[] y, XYChart.Series<Number, Number> series) {
        List<XYChart.Data<Number, Number>> points = series.getData();
        for (int i = 0; i < x.length; i++) {
            try {
                XYChart.Data<Number, Number> p = new XYChart.Data<>(Float.parseFloat(x[i]), Float.parseFloat(y[i]));
                points.add(p);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    static void editNames(ScatterChart<Number, Number> sc, String title, String xAxisName, String yAxisName) {
        sc.setTitle(title);
        sc.getXAxis().setLabel(xAxisName);
        sc.getYAxis().setLabel(yAxisName);
    }

    static void deleteData(ScatterChart<Number, Number> sc, int index) {
        sc.getData().remove(index);
    }

    static void deleteNames(ScatterChart<Number, Number> sc, boolean deleteTitle, boolean deleteNameX, boolean deleteNameY) {
        if (deleteTitle) {
            sc.setTitle("");
        }
        if (deleteNameX) {
            sc.getXAxis().setLabel("");
        }
        if (deleteNameY) {
            sc.getYAxis().setLabel("");
        }
    }

    static void editTickUnits(ScatterChart<Number, Number> sc, int tickUnitX, int tickUnitY) {
        if (tickUnitX > 0) {
            NumberAxis xAxis = (NumberAxis) sc.getXAxis();
            xAxis.setTickUnit(tickUnitX);
        }

        if (tickUnitY > 0) {
            NumberAxis yAxis = (NumberAxis) sc.getYAxis();
            yAxis.setTickUnit(tickUnitY);
        }
    }
}
