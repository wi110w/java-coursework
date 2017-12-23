package scatter_editor;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    private ScatterChart<Number, Number> scatterChart;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SDE");

        GridPane root = new GridPane();

        NumberAxis xAxis = new NumberAxis(0, 40, 3);
        NumberAxis yAxis = new NumberAxis(-500, 1000, 100);
        scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Untitled");

        Button setNames = new Button("Set title, names...");
        Button editNames = new Button("Edit title, names");
        Button deleteNames = new Button("Delete title, names");
        Button newData = new Button("New data series...");
        Button editData = new Button("Edit data series");
        Button deleteData = new Button("Delete data series");
        Button editTicks = new Button("Edit ticks");
        Button saveDiagram = new Button("Export diagram");
        Button saveProject = new Button("Save project");
        Button loadData = new Button("Load data");

        setNames.setOnAction(actionEvent -> {
            Names names = new Names();
            names.setChart(scatterChart);
            names.start(new Stage());
        });

        editNames.setOnAction(actionEvent -> {
            EditNames edit = new EditNames();
            edit.setChart(scatterChart);
            edit.start(new Stage());
        });

        deleteNames.setOnAction(actionEvent -> {
            DeleteNamesChooser deleteNamesChoser = new DeleteNamesChooser();
            deleteNamesChoser.setChart(scatterChart);
            deleteNamesChoser.start(new Stage());
        });

        newData.setOnAction((actionEvent -> {
            DataSeries dataSeries = new DataSeries();
            dataSeries.setChart(scatterChart);
            dataSeries.start(new Stage());
        }));

        editData.setOnAction(actionEvent -> {
            EditDataChooser dataChoser = new EditDataChooser();
            dataChoser.setChart(scatterChart);
            dataChoser.start(new Stage());
        });

        deleteData.setOnAction(actionEvent -> {
            DeleteDataChooser deleteDataChoser = new DeleteDataChooser();
            deleteDataChoser.setChart(scatterChart);
            deleteDataChoser.start(new Stage());
        });

        editTicks.setOnAction(actionEvent -> {
            EditTickUnit editTickUnit = new EditTickUnit();
            editTickUnit.setChart(scatterChart);
            editTickUnit.start(new Stage());
        });

        saveDiagram.setOnAction(this::showExportDiagramDialog);
        saveProject.setOnAction(this::showSaveProjectDialog);

        loadData.setOnAction(actionEvent -> {
            LoadDataChooser loadDataChooser = new LoadDataChooser();
            loadDataChooser.start(new Stage());
        });

        root.add(scatterChart, 0, 0);

        root.add(setNames, 0, 1);
        root.add(newData, 1, 1);
        root.add(editTicks, 2, 1);

        root.add(editNames, 0, 2);
        root.add(editData, 1, 2);
        root.add(saveDiagram, 2, 2);

        root.add(deleteNames, 0, 3);
        root.add(deleteData, 1, 3);
        root.add(saveProject, 2, 3);

        root.add(loadData, 2, 4);

        GridPane.setColumnSpan(scatterChart, 4);
        GridPane.setHalignment(scatterChart, HPos.CENTER);
        GridPane.setMargin(scatterChart, new Insets(0, 0, 25, 0));

        root.setPadding(new Insets(20));
        root.setHgap(80);
        root.setVgap(10);

        scene = new Scene(root, 650, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showExportDiagramDialog(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save diagram");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(scene.getWindow());
        if (file == null) {
            return;
        }

        scatterChart.snapshot(snapshotResult -> {
            saveSnapshot(snapshotResult, file);
            return null;
        }, new SnapshotParameters(), null);
    }

    private void saveSnapshot(SnapshotResult snapshotResult, File f) {
        WritableImage image = snapshotResult.getImage();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bufferedImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSaveProjectDialog(ActionEvent actionEvent) {

    }
}
