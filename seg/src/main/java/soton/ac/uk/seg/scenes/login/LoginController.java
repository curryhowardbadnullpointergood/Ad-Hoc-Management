package soton.ac.uk.seg.scenes.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {



    @FXML
    private Label welcomeText;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    protected void onHelloButtonClick() {
       
        System.out.println(username.getText());
        System.out.println(password.getText());

    }
}