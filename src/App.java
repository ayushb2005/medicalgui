import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
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
import com.google.gson.GsonBuilder; 

import javafx.scene.control.Alert.AlertType;
/**
 * This is the main Java FX class with all the code for the MediSymptom GUI
 * @author Ayush Bhanushali, Ryan Ma, Andrew Xin
 * @version 5/30/23
 */
public class App
    extends Application
{

    private TextField         nameField;
    private TextField         lastNameField;
    private ComboBox<Integer> ageComboBox;
    private ComboBox<String>  genderComboBox;
    private List<String>      nameList;
    private Set<String>               symSet = new HashSet<>();
    private boolean signIn = false;

    public static void main(String[] args)
    {
        launch(args);
    }

    
    @Override
    /**
     * This method overrides Stage class and uses the start method from it to have a GUI screen
     * This is the first screen where the registration or logging in is done
     * @author Ryan Ma
     */
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("User Registration");

        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();

        Label ageLabel = new Label("Age:");
        ageComboBox = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));

        Label genderLabel = new Label("Gender:");
        genderComboBox = new ComboBox<>(FXCollections.observableArrayList("Male", "Female"));                                                                                                                                                                           
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            if (isValidInput())
            {
                try
                {
                    URL url = new URL("http://localhost:8080/api/medical/createPerson");

                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    connection.setRequestMethod("POST");

                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setRequestProperty("Content-Type", "application/json");


                    String requestBody = "{\"name\": \"" + nameField.getText() + "\", "
                        + "\"lastName\": \"" + lastNameField.getText() + "\", " + "\"age\": "
                        + ageComboBox.getValue() + ", " + "\"gender\": \""
                        + genderComboBox.getValue() + "\", " + "\"sym\": [\"test\"]}";

                    DataOutputStream outputStream =
                        new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes(requestBody);

                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    reader.close();

                    System.out.println("Response: " + response.toString());

                    connection.disconnect();
                    if (responseCode == 200)
                    {
                        nameList = extractNamesFromResponse();
                        signIn=false;
                        showSecondScreen(primaryStage);
                    }
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        });
        Button existingUser = new Button("Existing User");
        existingUser.setOnAction(event->{
            Label nameLabel2 = new Label("Name:");
            nameField = new TextField();
    
            Label lastNameLabel2 = new Label("Last Name:");
            lastNameField = new TextField();
            
            Label ageLabel2 = new Label("Age:");
            ageComboBox = new ComboBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100));
            
            Button nextExistingUserButton = new Button("Next");
            nextExistingUserButton.setOnAction(e->{
                JsonObject requestBody = new JsonObject();
            requestBody.addProperty("name", nameField.getText());
            requestBody.addProperty("lastName", lastNameField.getText());
            requestBody.addProperty("age", ageComboBox.getValue());
            if (isValidInputExisting())
            {
                try
                {
                    URL url = new URL("http://localhost:8080/api/medical/checkExistingUser");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");

                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setRequestProperty("Content-Type", "application/json");
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(requestBody.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    reader.close();

                    System.out.println("Response: " + response.toString());
                    int check = Integer.parseInt( response.toString());
                    if(check == 200){
                        nameList=extractNamesFromResponse();    
                        signIn = true;
                        showSecondScreen(primaryStage);
                    }
                    else{
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("User Validation");
                        alert.setHeaderText(null);
                        alert.setContentText("User is not valid.");
                        alert.showAndWait();
                    }
                }
                catch (Exception ex)
                {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
            });



            Button quitButton = new Button("Quit");
            quitButton.setOnAction(e -> {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Quit");
                confirmAlert.setHeaderText("Quit the program");
                confirmAlert.setContentText("Are you sure you want to quit?");
    
                confirmAlert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> Platform.exit());
            });
            VBox newScreenLayout = new VBox(10);
            newScreenLayout.setPadding(new Insets(10));
            newScreenLayout.getChildren().addAll(nameLabel2, nameField, lastNameLabel2, lastNameField, ageLabel2, ageComboBox, nextExistingUserButton, quitButton);
    
            Scene newScene = new Scene(newScreenLayout, 1920, 1000);
            primaryStage.setScene(newScene);
            primaryStage.setTitle("New Scene");


        });
            Button quitButton = new Button("Quit");
            quitButton.setOnAction(e -> {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Quit");
                confirmAlert.setHeaderText("Quit the program");
                confirmAlert.setContentText("Are you sure you want to quit?");
    
                confirmAlert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> Platform.exit());
            });
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, nameLabel, nameField);
        gridPane.addRow(1, lastNameLabel, lastNameField);
        gridPane.addRow(2, ageLabel, ageComboBox);
        gridPane.addRow(3, genderLabel, genderComboBox);
        gridPane.add(existingUser,0,4,2,1);
        gridPane.add(nextButton, 0, 5, 2, 1);
        gridPane.add(quitButton, 0, 6, 2, 1);

        Scene scene = new Scene(gridPane, 1920, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //This method makes sure that the fields when registering in are not left blank 
    private boolean isValidInput()
    {
        if (nameField.getText().isEmpty())
        {
            showErrorAlert("Name is required.");
            return false;
        }
        if (lastNameField.getText().isEmpty())
        {
            showErrorAlert("Last Name is required.");
            return false;
        }
        if (ageComboBox.getValue() == null || ageComboBox.getValue() == 0)
        {
            showErrorAlert("Please select a valid age.");
            return false;
        }
        if (genderComboBox.getValue() == null)
        {
            showErrorAlert("Please select a valid gender.");
            return false;
        }
        return true;
    }
    //This method makes sure for users that are logging in are not left blank
    private boolean isValidInputExisting()
    {
        if (nameField.getText().isEmpty())
        {
            showErrorAlert("Name is required.");
            return false;
        }
        if (lastNameField.getText().isEmpty())
        {
            showErrorAlert("Last Name is required.");
            return false;
        }
        if (ageComboBox.getValue() == null || ageComboBox.getValue() == 0)
        {
            showErrorAlert("Please select a valid age.");
            return false;
        }
        return true;
    }

    //Shows alert when fields are not filled in
    private void showErrorAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //This method will make an api call so that it can have all the solutions in a list
    private List<String> extractNamesFromResponse()
    {
        List<String> names = new ArrayList<>();

        try
        {
            URL url = new URL("http://localhost:8080/api/medical/sortASC");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(response.toString(), JsonArray.class);
                for (JsonElement element : jsonArray)
                {
                    JsonObject jsonObject = element.getAsJsonObject();
                    String name = jsonObject.get("name").getAsString();
                    names.add(name);
                }
            }
            else
            {
                System.out.println("API request failed with response code: " + responseCode);
            }

            connection.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return names;
    }

    /**
     * This method goes to the second screen where the user can add symptoms to their own set
     * @author Andrew Xin
     */
    private void showSecondScreen(Stage primaryStage)
    {
        primaryStage.setTitle("Name List");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < nameList.size()-1; i++)
        {
            Label nameLabel = new Label(nameList.get(i));
            Button addButton = new Button("Add");
            addButton.setOnAction(event -> {
                symSet.add(nameLabel.getText());
                String name = nameLabel.getText();
                System.out.println("Added name: " + name);
                System.out.println(nameField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added symptom");
                alert.setContentText( nameLabel.getText()+" is added");
                alert.showAndWait();
            });
            gridPane.addRow(i, nameLabel, addButton);
        }
        Button goToPreviousButton = new Button("Back");
        goToPreviousButton.setOnAction(event -> {
            start(primaryStage);
        });
        Button sendSymptoms = new Button("Next");
        sendSymptoms.setOnAction(event -> {
            if (!symSet.isEmpty())
            {

                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("name", nameField.getText());
                requestBody.addProperty("lastName", lastNameField.getText());
                requestBody.addProperty("age", ageComboBox.getValue());

                JsonArray symptomsArray = new JsonArray();
                for (String symptom : symSet)
                {
                    symptomsArray.add(symptom);
                }
                requestBody.add("sym", symptomsArray);

                try
                {
                    URL url = new URL("http://localhost:8080/api/medical/addSymptomById");

                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    connection.setRequestMethod("PUT");
                    
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setRequestProperty("Content-Type", "application/json");

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(requestBody.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);
                    BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }
                    reader.close();
                    System.out.println("Response: " + response.toString());
                    connection.disconnect();

                    showThirdScreen(primaryStage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {   
                if(signIn==true){
                    showThirdScreen(primaryStage);
                    return; 
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Symptom Error");
                alert.setHeaderText(null);
                alert.setContentText("There are no symptoms that were added");
                alert.showAndWait();
            }

        });
        gridPane.addRow(nameList.size(), goToPreviousButton);
        gridPane.addRow(nameList.size()+2, sendSymptoms);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 1920, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method will show the last screen with the delete buttons for getting rid of symptoms
     * @author Ayush Bhanushali
     */
    private void showThirdScreen(Stage primaryStage)
    {
        primaryStage.setTitle("Display/Delete");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        int row = 0;
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", nameField.getText());
        requestBody.addProperty("lastName", lastNameField.getText());
        requestBody.addProperty("age", ageComboBox.getValue());
        try {
            URL url = new URL("http://localhost:8080/api/medical/display");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<HashMap<String, MedicalSolutions>>(){}.getType();
                HashMap<String, MedicalSolutions> responseData = gson.fromJson(response.toString(), type);
                
                for (Map.Entry<String, MedicalSolutions> entry : responseData.entrySet()) {
                    if(entry.getKey().equals("test")){

                    }else{
                        Object value = entry.getValue();
                        String format  = "Symptom: "+entry.getValue().getName()+"\n"+"Description: "+ entry.getValue().getDescription()+"\n"+"Solution: "+entry.getValue().getTreatment();
                        Label valueLabel = new Label(format);
                        
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            try {
                                Set<String> stringsToRemove = new HashSet<>();
                                stringsToRemove.add(entry.getKey());
                        
                                JsonObject deleteRequestBody = new JsonObject();
                                deleteRequestBody.addProperty("name", nameField.getText());
                                deleteRequestBody.addProperty("lastName", lastNameField.getText());
                                deleteRequestBody.addProperty("age", ageComboBox.getValue());
                                deleteRequestBody.add("sym", gson.toJsonTree(stringsToRemove));
                        
                                URL deleteUrl = new URL("http://localhost:8080/api/medical/deleteSymptomById");
                                HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
                                deleteConnection.setRequestMethod("PUT");
                                deleteConnection.setDoInput(true);
                                deleteConnection.setDoOutput(true);
                                deleteConnection.setRequestProperty("Content-Type", "application/json");
                        
                                Gson g1son = new GsonBuilder().setPrettyPrinting().create();
                                String j1son = g1son.toJson(deleteRequestBody);
                                System.out.println("JSON Is " + j1son);
                                OutputStream deleteOutputStream = deleteConnection.getOutputStream();
                                deleteOutputStream.write(deleteRequestBody.toString().getBytes());
                                deleteOutputStream.flush();
                                deleteOutputStream.close();
                        
                                int deleteResponseCode = deleteConnection.getResponseCode();
                                System.out.println("Delete Response Code: " + deleteResponseCode);
                                stringsToRemove.remove(entry.getKey());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Deleted symptom");
                                alert.setContentText(entry.getKey()+" is deleted");
                                alert.showAndWait();

                                showThirdScreen(primaryStage);


                                
                            } catch (Exception e) {
                                // Handle exceptions
                                // ...
                            }
                            
                            System.out.println("Delete button clicked for value: " + value);
                        });
        
                        gridPane.add(valueLabel, 0, row);
                        gridPane.add(deleteButton, 1, row);
                        row++;
                    }

                }
                System.out.println(responseData);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            // Display a confirmation dialog before quitting
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Quit");
            confirmAlert.setHeaderText("Quit the program");
            confirmAlert.setContentText("Are you sure you want to quit?");

            confirmAlert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Platform.exit());
        });
        Button goToPreviousButton = new Button("Back");
        goToPreviousButton.setOnAction(event -> {
            showSecondScreen(primaryStage);
        });
        gridPane.add(goToPreviousButton,0,row);
        gridPane.add(quitButton, 0, row+1);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 1920, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
