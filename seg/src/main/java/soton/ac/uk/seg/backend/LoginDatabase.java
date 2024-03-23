package soton.ac.uk.seg.backend;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class LoginDatabase {
    Connection conn;
    Statement stmt;
   
    private static final String LOGIN_PATH = "/home/kone/Projects/GitHub/Ad-Hoc-Management/seg/src/main/java/soton/ac/uk/seg/backend/database/login.csv";
    private static final String DB_PATH = "/home/kone/Projects/GitHub/Ad-Hoc-Management/seg/src/main/java/soton/ac/uk/seg/backend/database/login.db";

    public LoginDatabase() {

        System.out.println("made it");
        String dbFile = "jdbc:sqlite:" + DB_PATH;

        try {
            conn = DriverManager.getConnection(dbFile);
            stmt = conn.createStatement();

            // makes a new instance of this class 
            LoginDatabase database = new LoginDatabase();
            // imports in from the csv file 
            System.out.println("made it - importing users");
            // need to change to relative path later on !!
            database.importUserlog(LOGIN_PATH);


            createTables();
        } catch (SQLException e) {
            System.out.println("Connection error or tables creation failed!");
            throw new RuntimeException(e);
        }
    }

    private void createTables() throws SQLException {
        stmt.execute("CREATE TABLE IF NOT EXISTS users (\"username\" TEXT, \"password\" TEXT, \"permissions\" TEXT)");
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing the connection!");
            throw new RuntimeException(e);
        }
    }

   

   
    public void importUserlog(String loginFile) {
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader(loginFile));
            String line;
            csv.readLine(); // Skip header
            while ((line = csv.readLine()) != null) {
                String[] values = line.split(","); // Split using comma
                stmt.execute(String.format("INSERT INTO users (username, password, permissions) VALUES ('%s', '%s', '%s')",
                        values[0], values[1], values[2]));
            }
            conn.commit(); // Commit transaction after all inserts
            csv.close(); // Close the CSV reader
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    public static void main(String[] args){

        System.out.println("made it - main");

        // Create an instance of the database and import logs
        LoginDatabase database = new LoginDatabase();
       
        
        System.out.println("made it - importing users");
        
        

        database.importUserlog(LOGIN_PATH);

        System.out.println("made it - users");


        // Close the database connection
        database.close();

        // database does exist 

    }

    private double getDoubleQuery(String query) {
        try{
            ResultSet result = stmt.executeQuery(query);
            return result.getDouble(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // gets list of all users
    public double getAllUsers() {
        return getDoubleQuery("""
                SELECT * FROM("users");""");
    }

}
