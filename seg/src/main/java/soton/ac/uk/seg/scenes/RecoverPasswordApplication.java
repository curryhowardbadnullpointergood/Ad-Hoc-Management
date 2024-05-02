package soton.ac.uk.seg.scenes;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RecoverPasswordApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardApplication.class.getResource("recoverpassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Ad-Hoc Add New Users");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    
}