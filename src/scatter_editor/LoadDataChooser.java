package scatter_editor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoadDataChooser extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SDE");
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 300, 500);

        Button loadFromFile = new Button("Load from file");
        Button loadFromServer = new Button("Load from server");

        loadFromFile.setOnAction(actionEvent -> {

        });

        loadFromServer.setOnAction(actionEvent -> {

        });

        root.setVgap(10);
        root.add(loadFromFile, 0,0);
        root.add(loadFromServer, 0,1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}