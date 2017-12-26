package scatter_editor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;

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
        scene = new Scene(root, 500, 300);

        RadioButton loadFromFile = new RadioButton("Local");
        RadioButton loadFromServer = new RadioButton("Remote server");

        Label datasetLabel = new Label("Dataset name:");
        TextField datasetNameInput = new TextField();
        TextField urlInput = new TextField();
        urlInput.setDisable(true);

        Button browseFile = new Button("Browse...");
        Button load = new Button("Load");
        browseFile.setDisable(true);

        loadFromServer.setOnAction(actionEvent -> {
            if(loadFromServer.isSelected()) {
                urlInput.setDisable(false);
                loadFromFile.setSelected(false);
                browseFile.setDisable(true);
            } else {
                urlInput.setDisable(true);
            }
        });
        loadFromFile.setOnAction(actionEvent -> {
            if(loadFromFile.isSelected()) {
                browseFile.setDisable(false);
                loadFromServer.setSelected(false);
                urlInput.setDisable(true);
            } else {
                browseFile.setDisable(true);
            }
        });

        browseFile.setOnAction(this::loadDataSetDialog);

        load.setOnAction(actionEvent -> {
            String datasetName = datasetNameInput.getText();

            if(!urlInput.isDisabled()) {
                String url = urlInput.getText();
                ChartLoader.loadDataSet(url, datasetName);
            }
            else if(!browseFile.isDisabled()) {
                try {
                    new DataSetLoader(file, datasetName, chart).load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            primaryStage.close();
        });

        root.setVgap(10);
        root.setPadding(new Insets(10));
        root.add(datasetLabel, 0,0);
        root.add(datasetNameInput, 1,0);
        root.add(loadFromFile,0,1);
        root.add(browseFile, 1,1);
        root.add(loadFromServer, 0,2);
        root.add(urlInput, 0,3);
        root.add(load, 0,4);

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
        if (file == null) {
            return;
        }
    }
}