package soton.ac.uk.seg.backend;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDatabase {
    Connection conn;
    Statement stmt;
   
    private static final String LOGIN_PATH = "/home/kone/Projects/GitHub/Ad-Hoc-Management/seg/src/main/java/soton/ac/uk/seg/backend/database/login.csv";
    private static final String DB_PATH = "/home/kone/Projects/GitHub/Ad-Hoc-Management/seg/src/main/java/soton/ac/uk/seg/backend/database/login.db";

    public LoginDatabase() {


        
        String dbFile = "jdbc:sqlite:" + DB_PATH;

        try {
            conn = DriverManager.getConnection(dbFile);
            stmt = conn.createStatement();



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

    private String getStringQuery(String query) {
        try {
            ResultSet result = stmt.executeQuery(query);
            if (result.next()) {
                return result.getString(1); 
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if no result found
    }
    
    // Gets list of all users
    public List<String> getAllUsers() {
        List<String> usersList = new ArrayList<>();
        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM users");
            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");
                String permissions = result.getString("permissions");
                String userString = username + "," + password + "," + permissions + "\n";
                usersList.add(userString);
                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }


    // make sure no teo users have the same usernmae or this logic breaks, badly!
    public String checkLogin(String username, String password) {

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM users");
            
            while (result.next()) {
                String username1 = result.getString("username").trim();
                String password1 = result.getString("password").trim();
               
                if (username.equals(username1)){
                    
                    if(password.equals(password1)){
                        return "Good";
                    }
                    else{
                        return "Password incorrect, please try again:";
                    }
                }
           
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Username and Password not found!";
    }
    

    public void addUser(String username, String password, String permissions){
        if(!checkUsernameExist(username)){
            
            try {
                conn.setAutoCommit(false);
                stmt.execute(String.format("INSERT INTO users (username, password, permissions) VALUES ('%s', '%s', '%s')",
                                username, password, permissions));
                conn.commit();

                System.out.println("User Added!");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        }

        else {
            System.out.println("Username already exists!");
        }

    }

    public Boolean checkUsernameExist(String username) {

        try {
            ResultSet result = stmt.executeQuery("SELECT * FROM users");
            
            while (result.next()) {
                String username1 = result.getString("username").trim();
               
                if (username.equals(username1)){
                    
                   return true;
                }
           
            }
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    

}
