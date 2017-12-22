package scatter_editor;

import javafx.application.Application;
import javafx.geometry.HPos;
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
        Scene scene = new Scene(root, 550, 550);

        NumberAxis xAxis = new NumberAxis(0, 40, 3);
        NumberAxis yAxis = new NumberAxis(-500, 1000, 100);
        ScatterChart<Number,Number> sc = new ScatterChart<>(xAxis,yAxis);
        sc.setTitle("Untitled");

        Button setNames = new Button("Set title, names...");
        Button editNames = new Button("Edit title, names");
        Button deleteNames = new Button("Delete title, names");
        Button newData = new Button("New data series...");
        Button editData = new Button("Edit data series");
        Button deleteData = new Button("Delete data series");

        setNames.setOnAction(actionEvent -> {
            Names names = new Names();
            names.setChart(sc);
            names.start(new Stage());
        });

        editNames.setOnAction(actionEvent -> {
            EditNames edit = new EditNames();
            edit.setChart(sc);
            edit.start(new Stage());
        });

        deleteNames.setOnAction(actionEvent -> {

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

        deleteData.setOnAction(actionEvent -> {
            DeleteDataChoser deleteDataChoser = new DeleteDataChoser();
            deleteDataChoser.setChart(sc);
            deleteDataChoser.start(new Stage());
        });

        root.add(setNames, 1,1);
        root.add(editNames, 1, 2);
        root.add(deleteNames, 1,3);
        root.add(newData, 2, 1);
        root.add(editData,2,2);
        root.add(deleteData, 2, 3);
        root.add(sc, 0,0);
        GridPane.setColumnSpan(sc, 3);
        GridPane.setHalignment(sc, HPos.CENTER);GridPane.setMargin(sc, new Insets(0, 0,25,0));
        root.setPadding(new Insets(20));
        root.setHgap(80);
        root.setVgap(10);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
