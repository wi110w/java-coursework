package scatter_editor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class EditDataChooser extends Application {
    private ScatterChart<Number, Number> scatterChart;

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
        Scene scene = new Scene(root, 180, 250);

        root.setVgap(10);
        root.setPadding(new Insets(20));

        List<XYChart.Series<Number, Number>> data = scatterChart.getData();
        for (int i = 0; i < data.size(); i++) {
            Button chooseData = new Button(data.get(i).getName());
            final int index = i;
            chooseData.setOnAction(actionEvent -> {
                EditDataSeries editDataSeries = new EditDataSeries();
                editDataSeries.setSeries(data.get(index));
                editDataSeries.setIndex(index);
                editDataSeries.setChart(scatterChart);
                editDataSeries.start(new Stage());
                primaryStage.close();
            });

            root.add(chooseData, 0, i);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
