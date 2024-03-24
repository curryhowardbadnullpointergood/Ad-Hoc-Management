package soton.ac.uk.seg.scenes;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DashboardController {

    
    private Stage stage;
    private Scene scene; 
    private Parent root; 

  @FXML
  private TextField totalcost;


  @FXML
  private TextField ctr;


  @FXML
  private TextField cpa;


  @FXML
  private TextField cpc;

  
  @FXML
  private TextField cpm;


  @FXML
  private TextField bouncerate;


  @FXML
  private TextField numofimpressions;


  @FXML
  private TextField numofuniques;

  @FXML
  private TextField numofclicks;

  @FXML
  private TextField numofbounces;

  @FXML
  private TextField numofconversions;

  
 


  public void switchtoNewUsers(ActionEvent event) throws IOException{

    root = FXMLLoader.load(getClass().getResource("newusers.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
  }

  public void switchtoExport(ActionEvent event) throws IOException{

    root = FXMLLoader.load(getClass().getResource("export.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
  }


  
}



