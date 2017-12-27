package scatter_editor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoadDataChooser extends Application {

    private Scene scene;
    private File file;
    private ScatterChart<Number, Number> chart;

    public void setChart(ScatterChart<Number, Number> sc) {chart = sc;}

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SDE");
        GridPane root = new GridPane();
        scene = new Scene(root, 400, 230);

        ToggleGroup group = new ToggleGroup();
        RadioButton loadFromFile = new RadioButton("Local file");
        RadioButton loadFromServer = new RadioButton("Remote server");
        loadFromFile.setToggleGroup(group);
        loadFromFile.setSelected(true);
        loadFromServer.setToggleGroup(group);

        Label datasetLabel = new Label("Dataset name:");
        TextField datasetNameInput = new TextField();
        TextField urlInput = new TextField();
        urlInput.setDisable(true);

        Button browseFile = new Button("Browse...");
        Button load = new Button("Load");

        loadFromServer.setOnAction(actionEvent -> {
            urlInput.setDisable(false);
            loadFromFile.setSelected(false);
            browseFile.setDisable(true);
        });
        loadFromFile.setOnAction(actionEvent -> {
            browseFile.setDisable(false);
            loadFromServer.setSelected(false);
            urlInput.setDisable(true);
        });

        browseFile.setOnAction(this::loadDataSetDialog);

        load.setOnAction(actionEvent -> {
            String datasetName = datasetNameInput.getText();
            try {
                DataSetLoader loader;
                if (!urlInput.isDisabled()) {
                    URL url = new URL(urlInput.getText());
                    loader = new DataSetLoader(url, datasetName);
                } else {
                    loader = new DataSetLoader(file, datasetName);
                }
                XYChart.Series<Number, Number> s = loader.load();
                chart.getData().add(s);
            } catch (IOException e) {
                System.err.println("Failed to load dataset: " + e);
            }

            primaryStage.close();
        });

        root.setVgap(15);
        root.setPadding(new Insets(15));
        root.add(datasetLabel, 0,0);
        root.add(datasetNameInput, 1,0);
        root.add(loadFromFile,0,1);
        root.add(browseFile, 1,1);
        root.add(loadFromServer, 0,2);
        root.add(urlInput, 0,3);
        root.add(load, 0,4);

        GridPane.setColumnSpan(urlInput, 2);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadDataSetDialog(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Scatted Diagram project");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("DataSet files (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        file = fileChooser.showOpenDialog(scene.getWindow());
    }
}
