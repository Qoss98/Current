package com.eva.current;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Current extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize SceneManager
        SceneManager sceneManager = new SceneManager(stage);

        // Create Constraints
        Constraints constraints = new Constraints();

        // Initialize LoginPane with SceneManager and Constraints
        LoginPane loginPane = new LoginPane(sceneManager, constraints);

        // Create and add the login scene
        Scene loginScene = new Scene(loginPane, 1500, 800);
        sceneManager.addScene("login", loginScene);

        // Switch to the login scene
        sceneManager.switchTo("login");

        stage.setTitle("Current Energy");
        stage.show();
    }
}