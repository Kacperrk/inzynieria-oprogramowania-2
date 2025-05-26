package com.example.demo.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("JavaFX GUI działa!");
        Scene scene = new Scene(label, 300, 200);
        stage.setTitle("JavaFX App");
        stage.setScene(scene);
        stage.show();
    }
}
