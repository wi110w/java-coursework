package scatter_editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DeleteNamesChooser extends Application {

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
        Scene scene = new Scene(root, 200, 200);

        CheckBox titleDelete = new CheckBox("Title");
        CheckBox nameXDelete = new CheckBox("X Axis label");
        CheckBox nameYDelete = new CheckBox("Y Axis label");

        Button delete = new Button("Delete");
        delete.setOnAction(actionEvent -> {
            ScatterPlot.deleteNames(scatterChart, titleDelete.isSelected(), nameXDelete.isSelected(), nameYDelete.isSelected());
            primaryStage.close();
        });

        root.setVgap(10);
        root.add(titleDelete, 0,0);
        root.add(nameXDelete, 0,1);
        root.add(nameYDelete, 0,2);
        root.add(delete, 0,3);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
