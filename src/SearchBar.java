import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import javafx.application.Application;
import javafx.collections.FXCollections;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class SearchBar {

    private Stage stage;

    public SearchBar(Stage primaryStage) {
        stage = primaryStage;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public Scene createScene() {
        // Create UI controls for the second page
        Label searchLabel = new Label("Search:");
        TextField searchField = new TextField();

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            // Switch back to the first page
            stage.setScene(new App().start(stage));
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            // TODO: Perform action for the next button on the second page
        });

        // Create layout for the second page
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, searchLabel, searchField);
        gridPane.add(backButton, 0, 1);
        gridPane.add(nextButton, 1, 1);

        // Create and return the second scene
        return new Scene(gridPane, 300, 200);
    }
}
