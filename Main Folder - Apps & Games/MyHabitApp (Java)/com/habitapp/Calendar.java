package com.habitapp;

// Raghav Ramesh
// Calendar for Improvement App

// Scanner
import java.util.Scanner;

// Javafx imports

import javafx.application.Application;

// Display and interaction imports
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// Grid and Border imports
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Calendar extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Self-Improvement Habit Tracker");

        // Main BorderPane layout
        BorderPane rootLayout = new BorderPane();

        // Title banner
        Label titleLabel = new Label("My Habit & Wellness Tracker");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");
        rootLayout.setTop(titleLabel);

        // Sidebar for habits and activities

        // Make sidebar with 10 pixel spacing
        VBox sidebar = new VBox(10);

        sidebar.setStyle("-fx-background-color: #eaeaea; -fx-padding: 15px;");
        sidebar.getChildren().addAll(
                new Label("Daily Habits:"),
                new Button("Drink Water!"),
                new Button("Meditate 10 mins."),
                new Button("Read 5 pages of your favorite book!")
        );
        rootLayout.setLeft(sidebar);

        // Add 7x5 calendar grid in the middle
        GridPane calendarGrid = createCalendarGrid();
        rootLayout.setCenter(calendarGrid);

        // Create the window to be displayed
        Scene scene = new Scene(rootLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Calendar Generator
    private GridPane createCalendarGrid()
    {
        // Create and set positions with 8px gapping
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(8);

        // Sample label to represent the calendar for now
        Label calendarPlaceholder = new Label("7x5 Calendar Grid Goes Here");
        grid.add(calendarPlaceholder, 0, 0);

        return grid;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}