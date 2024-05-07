package soton.ac.uk.seg.scenes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportDatabaseController {
    

      
    private Stage stage;
    private Scene scene; 
    private Parent root; 

       @FXML
    private Button goback; 

    public void switchtoDashboard(ActionEvent event) throws IOException{

    root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    
  }


    public void loadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
    }

    public void loadDataIntoDB(ActionEvent actionEvent) {

        String dbFile = "jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data2.db";

        Connection conn;
        Statement stmt;
        try {
            conn = DriverManager.getConnection(dbFile);
            stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS clicks (\"Date\" TEXT, \"ID\" INT, \"Click Cost\" REAL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS impressions (\"Date\" TEXT, \"ID\" INT, \"Gender\" TEXT, \"Age\" TEXT, \"Income\" TEXT, \"Context\" TEXT, \"Impression Cost\" REAL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS servers (\"Entry Date\" TEXT, \"ID\" INT, \"Exit Date\" TEXT, \"Pages Viewed\" INT, \"Conversion\" TEXT);");
//            stmt.execute("CREATE TABLE IF NOT EXISTS users (\"username\" TEXT, \"password\" TEXT, \"permissions\" TEXT)");
//            stmt.execute("sqlite3 mode csv");
        }
        catch (SQLException e) {
            System.out.println("NOT FOUND! :(");
            throw new RuntimeException(e);
        }

        // Impression log
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader("./src/main/java/soton/ac/uk/seg/backend/database/impression_log.csv"));
            String line;
            csv.readLine();
            while((line = csv.readLine()) != null) {
                String[] values = line.split(", *");
                stmt.execute(String.format("INSERT INTO impressions VALUES (\"%s\", %s, \"%s\", \"%s\", \"%s\", \"%s\", %s)",
                        values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


        // Server log
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader("./src/main/java/soton/ac/uk/seg/backend/database/server_log.csv"));
            String line;
            csv.readLine();
            while((line = csv.readLine()) != null) {
                String[] values = line.split(", *");
                stmt.execute(String.format("INSERT INTO servers VALUES (\"%s\", %s, \"%s\", %s, \"%s\")",
                        values[0], values[1], values[2], values[3], values[4]));
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader("./src/main/java/soton/ac/uk/seg/backend/database/click_log.csv"));
            String line;
            csv.readLine();
            while((line = csv.readLine()) != null) {
                String[] values = line.split(", *");
                stmt.execute(String.format("INSERT INTO clicks VALUES (\"%s\", %s, %s)", values[0], values[1], values[2]));
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        }
        catch (SQLException e) {
            System.out.println("NOT FOUND! :(");
            throw new RuntimeException(e);
        }
    }
}
