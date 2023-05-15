import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MedicalMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the root grid pane
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        // Create the name label and text field
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Enter your name");

        // Create the last name label and text field
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Enter your last name");

        // Create the age label and combo box
        Label ageLabel = new Label("Age:");
        ComboBox<Integer> ageComboBox = new ComboBox<>();
        ageComboBox.getItems().addAll(18, 19, 20, 21, 22, 23, 24, 25);
        ageComboBox.setPromptText("Select your age");

        // Create the gender label and combo box
        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select your gender");

        // Create the next button
        Button nextButton = new Button("Next");

        // Add the controls to the root grid pane
        root.add(nameLabel, 0, 0);
        root.add(nameTextField, 1, 0);
        root.add(lastNameLabel, 0, 1);
        root.add(lastNameTextField, 1, 1);
        root.add(ageLabel, 0, 2);
        root.add(ageComboBox, 1, 2);
        root.add(genderLabel, 0, 3);
        root.add(genderComboBox, 1, 3);
        root.add(nextButton, 1, 4);

        // Create the scene
        Scene scene = new Scene(root, 300, 200);

        // Set the stage title and scene
        primaryStage.setTitle("Example GUI");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

