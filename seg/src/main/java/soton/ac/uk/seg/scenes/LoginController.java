package soton.ac.uk.seg.scenes;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import soton.ac.uk.seg.backend.LoginDatabase;
import javafx.scene.Node; 

public class LoginController {

  
    private Stage stage;
    private Scene scene; 
    private Parent root; 

    @FXML
    private Label welcomeText;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException {


        String u = username.getText();
        String p = password.getText();


        LoginDatabase database = new LoginDatabase();
        String v = database.checkLogin(u, p);

     
  
        if (v.equals("Good")) {
            System.out.println("Login successful");
            root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } else {
            System.out.println(v);
        }
    
        database.close();

    }

    
}