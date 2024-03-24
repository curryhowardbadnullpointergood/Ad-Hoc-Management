package soton.ac.uk.seg.scenes;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class ExportApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardApplication.class.getResource("export.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 800);
        stage.setTitle("Ad-Hoc Dashboard");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
}
