package soton.ac.uk.seg.scenes;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import soton.ac.uk.seg.backend.LoginDatabase;
public class MetricsController {

    private Stage stage;
    private Scene scene; 
    private Parent root; 


  

    public void switchtoDashboard(ActionEvent event) throws IOException{

    root = FXMLLoader.load(getClass().getResource("dashboard2.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
  }



 

    
}
