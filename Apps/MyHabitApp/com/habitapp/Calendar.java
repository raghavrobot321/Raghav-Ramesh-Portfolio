package com.habitapp;

// Raghav Ramesh
// Calendar for Improvement App

// Scanner

// Javafx imports
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Calendar extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        // Create a new stage
        Stage stage = new Stage();
        stage.setTitle("Self-Improvement Habit Tracker");

        // Add BorderPane
        BorderPane rootLayout = new BorderPane();

        // Create a new label
        Label titleLabel = new Label("My Habit & Wellness Tracker");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");
        rootLayout.setTop(titleLabel)

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
