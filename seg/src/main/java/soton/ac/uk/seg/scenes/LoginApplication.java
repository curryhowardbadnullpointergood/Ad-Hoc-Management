package soton.ac.uk.seg.scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {

    private Stage stage;
    private BorderPane content;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Ad-Hoc Login");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public static void main() {
     
        launch();
    }


  
  
}