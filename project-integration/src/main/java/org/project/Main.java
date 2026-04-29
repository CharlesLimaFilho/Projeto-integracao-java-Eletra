package org.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class);

        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/Screen.fxml"));

        Scene scene = new Scene(root.load());
        stage.setMinWidth(900);
        stage.setMinHeight(400);
        stage.setScene(scene);
        stage.setTitle("Projeto Integracao");
        stage.setOnCloseRequest(event -> {
            ctx.close();
        });
        stage.show();
    }
}