package scatter_editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class DeleteDataChooser extends Application {

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

        root.setVgap(10);

        List<XYChart.Series<Number, Number>> data = chart.getData();
        for (int i = 0; i < data.size(); i++) {
            Button chooseData = new Button(data.get(i).getName());
            final int index = i;
            chooseData.setOnAction(actionEvent -> {
                ScatterPlot.deleteData(chart, index);
                primaryStage.close();
            });

            root.add(chooseData, 0, i);
        }

        Scene scene = new Scene(root, 300, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
