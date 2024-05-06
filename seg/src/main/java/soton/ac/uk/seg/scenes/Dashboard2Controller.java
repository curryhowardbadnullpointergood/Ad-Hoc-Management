package soton.ac.uk.seg.scenes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.almasb.fxgl.entity.action.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soton.ac.uk.seg.backend.Database;
import soton.ac.uk.seg.backend.Sqlquery;

public class Dashboard2Controller {


    private Stage stage;
    private Scene scene;
    private Parent root; 

 /*  @FXML
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
  private TextField numofconversions;*/

    @FXML
    private Button allmenu;

    @FXML
    private VBox sidebar;

    @FXML
    private Button Metrics;

    @FXML
    private LineChart chart;

    @FXML
    private ChoiceBox<String> fields;

    @FXML
    private ToggleButton time;

    @FXML
    private ToggleButton pages;

    @FXML
    private TextField startdate;
    @FXML
    private TextField enddate;

    @FXML
    private ToggleButton male;
    @FXML
    private ToggleButton female;

    @FXML
    private ToggleButton low;
    @FXML
    private ToggleButton medium;
    @FXML
    private ToggleButton high;

    @FXML
    private ToggleButton undertwentyfive;
    @FXML
    private ToggleButton twentyfivetothirtyfour;
    @FXML
    private ToggleButton thirtyfivetofourtyfour;
    @FXML
    private ToggleButton fourtyfivetofiftyfour;
    @FXML
    private ToggleButton abovefiftyfive;

    @FXML
    private ToggleButton socialmedia;
    @FXML
    private ToggleButton shopping;
    @FXML
    private ToggleButton blog;
    @FXML
    private ToggleButton news;
    @FXML
    private ToggleButton hobbies;
    @FXML
    private ToggleButton travel;

    @FXML
    public void initialize() {
        fields.getItems().addAll("impressions", "clicks", "uniques", "bounces",
                "conversions", "total_cost", "ctr", "cpa", "cpc", "cpm", "bounce_rate");
        chart.setLegendVisible(false);

    }


    public void switchtoNewUsers(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("newusers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchtoExport(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("export.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchtoImport(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("importdatabase.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void switchtoLogin(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void switchtoMetrics(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("metrics.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void sidebartoggle(ActionEvent event) throws IOException {

        if (!sidebar.isVisible()) {
            sidebar.setVisible(true);
        } else {
            sidebar.setVisible(false);
        }
    }

    public void displayData(ActionEvent actionEvent) {
        if (chart.getData() != null)
            chart.getData().clear();

        String bounceDef = "time";
        if (pages.isSelected()) bounceDef = "pages";

        ArrayList<String> gendersSelected = new ArrayList<>();
        if (male.isSelected()) gendersSelected.add("male");
        if (female.isSelected()) gendersSelected.add("female");

        ArrayList<String> incomeSelected = new ArrayList<>();
        if (low.isSelected()) incomeSelected.add("low");
        if (medium.isSelected()) incomeSelected.add("medium");
        if (high.isSelected()) incomeSelected.add("high");

        ArrayList<String> ageSelected = new ArrayList<>();
        if (undertwentyfive.isSelected()) ageSelected.add("<25");
        if (twentyfivetothirtyfour.isSelected()) ageSelected.add("25-34");
        if (thirtyfivetofourtyfour.isSelected()) ageSelected.add("35-44");
        if (fourtyfivetofiftyfour.isSelected()) ageSelected.add("45-54");
        if (abovefiftyfive.isSelected()) ageSelected.add(">54");

        ArrayList<String> contextSelected = new ArrayList<>();
        if (socialmedia.isSelected()) contextSelected.add("Social Media");
        if (shopping.isSelected()) contextSelected.add("Shopping");
        if (blog.isSelected()) contextSelected.add("Blog");
        if (news.isSelected()) contextSelected.add("News");
        if (hobbies.isSelected()) contextSelected.add("Hobbies");
        if (travel.isSelected()) contextSelected.add("Travel");

        String query = Database.getGraphQuery(fields.getValue(), "day", gendersSelected, incomeSelected,
                ageSelected, contextSelected, bounceDef, 3, startdate.getText(), enddate.getText());

        XYChart.Series<String, Number> data = Sqlquery.graphQuery(query);

        // TODO wire up to database
        System.out.println(query);



//        data.getData().add(new XYChart.Data("24204", 122345));
//        data.getData().add(new XYChart.Data("25204", 345));
//        data.getData().add(new XYChart.Data("206204", 2345));
        chart.getData().add(data);
    }



  /*@FXML
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
      
  }
*/


}



