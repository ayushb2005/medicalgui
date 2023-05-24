import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import javafx.scene.control.Alert.AlertType;

public class App extends Application {

    private TextField nameField;
    private TextField lastNameField;
    private ComboBox<Integer> ageComboBox;
    private ComboBox<String> genderComboBox;
    private List<String> nameList;
    Set<String> symSet = new HashSet<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Registration");

        // Create UI controls
        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();

        Label ageLabel = new Label("Age:");
        ageComboBox = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 
                                                                        21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
                                                                        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 
                                                                        57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
                                                                        75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 
                                                                        93, 94, 95, 96, 97, 98, 99, 100)); 

        Label genderLabel = new Label("Gender:");
        genderComboBox = new ComboBox<>(FXCollections.observableArrayList("Male", "Female")); // Customize with desired gender options

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            // Make API POST request here
            if(isValidInput()){
            try {
                URL url = new URL("http://localhost:8080/api/medical/createPerson");

            // Create HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
            // Set the HTTP request method to POST
            connection.setRequestMethod("POST");
    
            // Enable input and output streams
            connection.setDoInput(true);
            connection.setDoOutput(true);
    
            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");
    
            // Create the request body

            String requestBody = "{\"name\": \"" + nameField.getText() + "\", " +
            "\"lastName\": \"" + lastNameField.getText() + "\", " +
            "\"age\": " + ageComboBox.getValue() + ", " +
            "\"gender\": \"" + genderComboBox.getValue() + "\", " +
            "\"sym\": [\"test\"]}";
    
            // Write the request body to the output stream
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            
            outputStream.flush();
            outputStream.close();
    
            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
    
            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
    
            // Print the response
            System.out.println("Response: " + response.toString());
    
            // Disconnect the connection
            connection.disconnect();
            if (responseCode == 200) {
                //createSuccessScene(primaryStage);
                nameList = extractNamesFromResponse();
                showSecondScreen(primaryStage);
            }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
            
        });
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            // Close the application
            primaryStage.close();
        });

        // Create layout and add controls
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, nameLabel, nameField);
        gridPane.addRow(1, lastNameLabel, lastNameField);
        gridPane.addRow(2, ageLabel, ageComboBox);
        gridPane.addRow(3, genderLabel, genderComboBox);
        gridPane.add(nextButton, 0, 4, 2, 1);
        gridPane.add(quitButton, 0, 5, 2, 1);

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //for going to search
    private void createSuccessScene(Stage primaryStage) {
        Label searchLabel = new Label("Search:");
        TextField searchField = new TextField();
        Button backButton = new Button("Back");
        Button nextButton = new Button("Next");

        backButton.setOnAction(event -> {
            // Go back to the previous scene
            start(primaryStage);
        });

        nextButton.setOnAction(event -> {
            // Handle next button action    
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(searchLabel, searchField, backButton, nextButton);

        Scene successScene = new Scene(vbox, 300, 200);
        primaryStage.setScene(successScene);
        primaryStage.setTitle("Success Page");
    }
    private boolean isValidInput() {
        // Validate name field
        if (nameField.getText().isEmpty()) {
            showErrorAlert("Name is required.");
            return false;
        }

        // Validate last name field
        if (lastNameField.getText().isEmpty()) {
            showErrorAlert("Last Name is required.");
            return false;
        }

        // Validate age field
        if (ageComboBox.getValue() == null || ageComboBox.getValue() == 0) {
            showErrorAlert("Please select a valid age.");
            return false;
        }
        if(genderComboBox.getValue()==null){
            showErrorAlert("Please select a valid gender.");
            return false;
        }
        return true;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private List<String> extractNamesFromResponse() {
        List<String> names = new ArrayList<>();
    
        try {
            URL url = new URL("http://localhost:8080/api/medical/sortASC");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
    
                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(response.toString(), JsonArray.class);
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    String name = jsonObject.get("name").getAsString();
                    names.add(name);
                }
            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }
    
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return names;
    }
    private void showSecondScreen(Stage primaryStage) {
        primaryStage.setTitle("Name List");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Display names with Add buttons
        for (int i = 0; i < nameList.size(); i++) {
            Label nameLabel = new Label(nameList.get(i));
            Button addButton = new Button("Add");
            addButton.setOnAction(event -> {
                // Add the name to the ArrayList or perform any desired action
                symSet.add(nameLabel.getText());
                String name = nameLabel.getText();
                // Add name to your ArrayList or perform any desired action
                System.out.println("Added name: " + name);
                System.out.println(nameField.getText());
            });
            gridPane.addRow(i, nameLabel, addButton);
        }

        
        Button sendSymptoms = new Button("Next");
        sendSymptoms.setOnAction(event->{
            if(!symSet.isEmpty()){

            
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("name", nameField.getText());
            requestBody.addProperty("lastName", lastNameField.getText());
            requestBody.addProperty("age", ageComboBox.getValue());
        
            JsonArray symptomsArray = new JsonArray();
            for (String symptom : symSet) {
                symptomsArray.add(symptom);
            }
            requestBody.add("sym", symptomsArray);
        
            try {
                URL url = new URL("http://localhost:8080/api/medical/addSymptomById");
        
                // Create HttpURLConnection object
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
                // Set the HTTP request method to POST
                connection.setRequestMethod("PUT");
        
                // Enable input and output streams
                connection.setDoInput(true);
                connection.setDoOutput(true);
        
                // Set request headers
                connection.setRequestProperty("Content-Type", "application/json");
        
                // Write the request body to the output stream
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(requestBody.toString().getBytes());
                outputStream.flush();
                outputStream.close();
        
                // Get the response code
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);
        
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
        
                // Print the response
                System.out.println("Response: " + response.toString());
        
                // Disconnect the connection
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Symptom Error");
                alert.setHeaderText(null);
                alert.setContentText("There are no symptoms that were added");
                alert.showAndWait();
            }
            
        });
        gridPane.addRow(nameList.size(), sendSymptoms);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //going to existing user
    private void createSuccessSceneExistingUser(Stage primaryStage) {

    }

}
