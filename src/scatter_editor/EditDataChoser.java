package scatter_editor;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class EditDataChoser extends Application {

    ScatterChart<Number, Number> scatterChart;
    static ObservableList<XYChart.Series<Number, Number>> data;

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


        root.setVgap(10);

        data = scatterChart.getData();
        for (int i = 0; i < data.size(); i++) {
            Button chooseData = new Button(data.get(i).getName());
            final int index = i;
            chooseData.setOnAction(actionEvent -> {
                EditDataSeries editDataSeries = new EditDataSeries();
                editDataSeries.setData(data.get(index));
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
