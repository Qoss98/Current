package com.eva.current;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneManager {
    private final Stage stage;
    private final HashMap<String, Scene> scenes = new HashMap<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // Add a scene to the manager
    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    // Switch to a specific scene by name
    public void switchTo(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            stage.setScene(scene);
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }

    public Stage getStage() {
        return stage;
    }
}

