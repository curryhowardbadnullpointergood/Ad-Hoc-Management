package soton.ac.uk.seg.scenes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
import soton.ac.uk.seg.backend.Sqlquery;
public class MetricsController {

    Sqlquery sqlq = new Sqlquery();
    


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
  private void displayImpressions() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryImperssion(conn); 
     
     numofimpressions.setText(String.valueOf(number));
  }

  @FXML
  private void displayUniques() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
     double number = sqlq.queryUniques(conn); 
     numofuniques.setText(String.valueOf(number));
  }

  @FXML
  private void displayclicks() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
     double number = sqlq.queryClick(conn); 
     numofclicks.setText(String.valueOf(number));
  }

  @FXML
  private void displaybounces() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
     double number = sqlq.queryBounce(conn); 
     numofbounces.setText(String.valueOf(number));
  }

  @FXML
  private void displayconversions() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryConversion(conn); 
     numofconversions.setText(String.valueOf(number));
  }

  @FXML
  private void displayctr() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryCTR(conn); 
     ctr.setText(String.valueOf(number));
  }

  @FXML
  private void displaycpa() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryCPA(conn);  
     cpa.setText(String.valueOf(number));
  }

  @FXML
  private void displaycpc() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryCPC(conn);  
     cpc.setText(String.valueOf(number));
  }

  @FXML
  private void displaycpm() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
     double number = sqlq.queryCPM(conn); 
     cpm.setText(String.valueOf(number));
  }

  @FXML
  private void displaybouncerate() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryBounceRate(conn);  
     bouncerate.setText(String.valueOf(number));
  }

  @FXML
  private void displaytotalcost() throws SQLException{
   Connection conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");
   double number = sqlq.queryTotalCost(conn);  
     totalcost.setText(String.valueOf(number));
  }

  @FXML
  private void initialize() throws SQLException {

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
