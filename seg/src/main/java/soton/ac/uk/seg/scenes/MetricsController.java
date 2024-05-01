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

    @FXML
    private TextField totalcost;

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

  

    public void switchtoDashboard(ActionEvent event) throws IOException{

    root = FXMLLoader.load(getClass().getResource("dashboard2.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
  }

  @FXML
  private void displayImpressions(){
     int number = 42; 
     numofimpressions.setText(String.valueOf(number));
  }

  @FXML
  private void displayUniques(){
     int number = 42; 
     numofuniques.setText(String.valueOf(number));
  }

  @FXML
  private void displayclicks(){
     int number = 42; 
     numofclicks.setText(String.valueOf(number));
  }

  @FXML
  private void displaybounces(){
     int number = 42; 
     numofbounces.setText(String.valueOf(number));
  }

  @FXML
  private void displayconversions(){
     int number = 42; 
     numofconversions.setText(String.valueOf(number));
  }

  @FXML
  private void displayctr(){
     int number = 42; 
     ctr.setText(String.valueOf(number));
  }

  @FXML
  private void displaycpa(){
     int number = 42; 
     cpa.setText(String.valueOf(number));
  }

  @FXML
  private void displaycpc(){
     int number = 42; 
     cpc.setText(String.valueOf(number));
  }

  @FXML
  private void displaycpm(){
     int number = 42; 
     cpm.setText(String.valueOf(number));
  }

  @FXML
  private void displaybouncerate(){
     int number = 42; 
     bouncerate.setText(String.valueOf(number));
  }

  @FXML
  private void displaytotalcost(){
     int number = 42; 
     totalcost.setText(String.valueOf(number));
  }

  @FXML
  private void initialize() {

    this.displayImpressions();
    this.displayUniques();
    this.displayclicks();
    this.displaybounces();
    this.displayconversions();
    this.displayctr();
    this.displaycpa();
    this.displaycpc();
    this.displaycpm();
    this.displaybouncerate();
    this.displaytotalcost();
      
  }




 

    
}
