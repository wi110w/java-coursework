package scatter_editor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;


public class Main extends Application {

    GridPane root = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SDE");
        Scene scene = new Scene(root, 800, 500);

        NumberAxis xAxis = new NumberAxis(0, 40, 3);
        NumberAxis yAxis = new NumberAxis(-500, 1000, 100);
        ScatterChart<Number,Number> sc = new ScatterChart<>(xAxis,yAxis);
        sc.setTitle("Untitled");

        Button setNames = new Button("Set title, names...");
        Button newData = new Button("New data series...");
        Button editData = new Button("Edit data series");

        setNames.setOnAction(actionEvent -> {
            Names names = new Names();
            names.setChart(sc);
            names.start(new Stage());
        });

        newData.setOnAction((actionEvent -> {
            DataSeries dataSeries = new DataSeries();
            dataSeries.setChart(sc);
            dataSeries.start(new Stage());
        }));

        editData.setOnAction(actionEvent -> {
            EditDataChoser dataChoser = new EditDataChoser();
            dataChoser.setChart(sc);
            dataChoser.start(new Stage());
        });

        root.add(setNames, 0,0);
        root.add(newData, 0, 1);
        root.add(editData,0,2);
        root.add(sc, 1,0);
        root.setPadding(new Insets(10));
        root.setHgap(80);
        root.setVgap(5);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
