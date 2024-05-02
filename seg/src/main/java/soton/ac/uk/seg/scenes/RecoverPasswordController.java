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
import soton.ac.uk.seg.backend.GMailer;
import soton.ac.uk.seg.backend.LoginDatabase;
import soton.ac.uk.seg.backend.GMailer;


public class RecoverPasswordController {

    private Stage stage;
    private Scene scene; 
    private Parent root; 

   

    @FXML
    private TextField emailid;

    @FXML 
    private Button send; 

    @FXML
    public void switchtoLogin(ActionEvent event) throws IOException{

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void sendEmail ( ActionEvent event) throws Exception {

        String text = emailid.getText();
        System.out.println( "--------------------------------------------------"+ "\n"+ "----EMAIL:" + text);

        //SendEmail sem = new SendEmail();
        LoginDatabase ldb =  new LoginDatabase();
        String pass = ldb.getPassword(text);
        System.out.println( "--------------------------------------------------"+ "\n"+ "----PASSWORD:" + pass);

        GMailer gm =  new GMailer();
        gm.sendMail(text,pass);
        //sem.sendMail(text, pass);

    }




}
