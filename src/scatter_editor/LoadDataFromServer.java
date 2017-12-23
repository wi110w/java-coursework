package scatter_editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoadDataFromServer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SDE");
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 300, 500);

        Label url = new Label("Enter URL:");
        Label datasetName = new Label("Enter the dataset name:");

        TextField urlInput = new TextField();
        TextField datasetNameInput = new TextField();

        Button load = new Button("Load");
        load.setOnAction(actionEvent -> {
            String urlIn = urlInput.getText();
            String datasetNameIn = datasetNameInput.getText();
        });

        root.setVgap(10);
        root.add(url, 0,0);
        root.add(urlInput, 0,1);
        root.add(datasetName, 0,2);
        root.add(datasetNameInput, 0,3);
        root.add(load, 0,4);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
