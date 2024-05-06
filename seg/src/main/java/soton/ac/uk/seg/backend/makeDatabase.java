package soton.ac.uk.seg.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.sql.*;

public class makeDatabase {

    Connection conn;
    Statement stmt;
    public static void main(String[] args) {

        makeDatabase mdb = new makeDatabase();
        mdb.importClickLog("./2_week_campaign_1/click_log.csv");
        mdb.importImpressionLog("./2_week_campaign_1/impressions_log.csv");
        mdb.importServerLog("./2_week_campaign_1/servers_log.csv");
       
      
    }
    

   

    public makeDatabase() {
        String dbFile = "jdbc:sqlite:" + "./src/main/java/soton/ac/uk/seg/backend/database/data.db";

        try {
            conn = DriverManager.getConnection(dbFile);
            stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS clicks (\"Date\" TEXT, \"ID\" INT, \"Click Cost\" REAL);");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS impressions (\"Date\" TEXT, \"ID\" INT, \"Gender\" TEXT, \"Age\" TEXT, \"Income\" TEXT, \"Context\" TEXT, \"Impression Cost\" REAL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS servers (\"Entry Date\" TEXT, \"ID\" INT, \"Exit Date\" TEXT, \"Pages Viewed\" INT, \"Conversion\" TEXT);");
            
        }
        catch (SQLException e) {
            System.out.println("NOT FOUND! :(");
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            conn.close();
        }
        catch (SQLException e) {
            System.out.println("NOT FOUND! :(");
            throw new RuntimeException(e);
        }
    }


    public void importClickLog(String logFile) {
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader(logFile));
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
    }



 


    public void importImpressionLog(String logFile) {
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader(logFile));
            String line;
            csv.readLine();
            while((line = csv.readLine()) != null) {
                String[] values = line.split(", *");
                stmt.execute(String.format("INSERT INTO impressions VALUES (\"%s\", %s, \"%s\", \"%s\", \"%s\", \"%s\", %s)",
                        values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
            }
            conn.commit();
            conn.setAutoCommit(true);
            csv.close();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importServerLog(String logFile) {
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader(logFile));
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
    }



}