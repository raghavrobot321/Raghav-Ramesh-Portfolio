package com.habitapp;

// Raghav Ramesh
// Calendar for Improvement App

// Scanner

// Javafx imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Calendar extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        // Create a new stage
        Stage stage = new Stage();
        stage.setTitle("Calendar");

        // Create a new label
        Label label = new Label("Calendar");
        StackPane layout = new StackPane();
        layout.getChildren().add(label);

        // Create a new scene
        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    // Start app
    public static void main(String[] args)
    {
        System.out.println("Setting up program");

        launch(args);
    }
}
