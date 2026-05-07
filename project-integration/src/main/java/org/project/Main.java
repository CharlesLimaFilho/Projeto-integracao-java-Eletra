package org.project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/Screen.fxml"));

        Scene scene = new Scene(root.load());
        stage.setMinWidth(900);
        stage.setMinHeight(400);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Projeto-Integracao.png"))));
        stage.setScene(scene);
        stage.setTitle("Projeto Integracao");
        stage.show();
    }
}