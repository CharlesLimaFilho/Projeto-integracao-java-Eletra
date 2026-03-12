import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage palco) throws Exception {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("screens/Screen.fxml"));
        Scene scene = new Scene(root.load(), 900, 400);
        palco.setMinWidth(900);
        palco.setMinHeight(400);
        palco.setScene(scene);
        palco.setTitle("Projeto Integracao");
        palco.show();
    }
}