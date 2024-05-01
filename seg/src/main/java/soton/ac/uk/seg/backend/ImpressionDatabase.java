package soton.ac.uk.seg.backend;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;


public class ImpressionDatabase extends Thread{

    Connection conn;
    Statement stmt;

    public ImpressionDatabase() {
        String dbFile = "jdbc:sqlite:" + "./src/main/java/soton/ac/uk/seg/backend/database/impressions.db";

        try {
            conn = DriverManager.getConnection(dbFile);
            stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS impressions (\"Date\" TEXT, \"ID\" INT, \"Gender\" TEXT, \"Age\" TEXT, \"Income\" TEXT, \"Context\" TEXT, \"Impression Cost\" REAL);");
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
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public void run() {

        ImpressionDatabase idb = new ImpressionDatabase();
        idb.importImpressionLog("./2_week_campaign_1/impression_log.csv");
       
    }



}

