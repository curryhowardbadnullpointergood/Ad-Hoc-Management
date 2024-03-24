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
public class NewusersController {

    private Stage stage;
    private Scene scene; 
    private Parent root; 


    @FXML
    private Button goback; 

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField permissionlevel;

    public void switchtoDashboard(ActionEvent event) throws IOException{

    root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
  }



  @FXML
  protected void handleSubmit(ActionEvent event) {

    System.out.println("Submit started!");

    LoginDatabase ldb = new LoginDatabase();

    System.out.println("new logindatabase Initialised!");

    String u = username.getText();
    String p = password.getText();
    String pe = permissionlevel.getText();

    System.out.println(u + " " +  p+ " " + pe);

    ldb.addUser(u, p, pe);

  }

    
}
