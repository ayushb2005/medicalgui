import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

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
        ageComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
        21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 
        57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
        75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 
        93, 94, 95, 96, 97, 98, 99, 100);
        ageComboBox.setPromptText("Select your age");

        // Create the gender label and combo box
        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
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
        primaryStage.setTitle("MediSymptom");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }
    private void makeApiRequest(String name, String lastName, int age, String gender) {
        try {
            // Create a URL object with the API endpoint
            URL url = new URL("https://example.com/api/endpoint");

            // Create a HttpURLConnection object and set the request method to POST
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Set the request parameters
            String parameters = "name=" + name + "&last_name=" + lastName + "&age=" + age + "&gender=" + gender;
            conn.setDoOutput(true);
            conn.getOutputStream().write(parameters.getBytes());

            // Read the response from the API
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
    
            // Do something with the response
            System.out.println(response.toString());
    
            // Disconnect the connection
            conn.disconnect();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


