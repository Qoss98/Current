package com.eva.current;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Stack;

public class SceneManager {
    private final Stage stage;
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private final Stack<Scene> sceneHistory = new Stack<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // scene toevoegen aan de hashmap
    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void switchTo(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            // Push de huidige scene naar de stack
            if (stage.getScene() != null) {
                sceneHistory.push(stage.getScene());
            }
            stage.setScene(scene);
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }

    public void previousScene() {
        if (!sceneHistory.isEmpty()) {
            stage.setScene(sceneHistory.pop());
        } else {
            System.out.println("No previous scene to return to.");
        }
    }

    public Stage getStage() {
        return stage;
    }
}