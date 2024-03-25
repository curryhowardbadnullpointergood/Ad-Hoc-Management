package soton.ac.uk.seg.backend;

// hello

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class makeDatabase {
    Connection conn;
    Statement stmt;
    private static final String LOG_PATH_CLICKS = "/home/kone/Projects/GitHub/comp2211-software-engineering-group-project/click_log.csv";
    private static final String LOG_PATH_IMPRESSIONS = "/home/kone/Projects/GitHub/comp2211-software-engineering-group-project/impression_log.csv";
    private static final String LOG_PATH_SERVERS = "/home/kone/Projects/GitHub/comp2211-software-engineering-group-project/server_log.csv";
    private static final String LOGIN_PATH = "/home/kone/Projects/GitHub/comp2211-software-engineering-group-project/users.csv";
    private static final String DB_PATH = "/home/kone/Projects/GitHub/comp2211-software-engineering-group-project/database.db";

    public makeDatabase() {
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
        stmt.execute("CREATE TABLE IF NOT EXISTS clicks (\"Date\" TEXT, \"ID\" INT, \"Click Cost\" REAL);");
        stmt.execute("CREATE TABLE IF NOT EXISTS impressions (\"Date\" TEXT, \"ID\" INT, \"Gender\" TEXT, \"Age\" TEXT, \"Income\" TEXT, \"Context\" TEXT, \"Impression Cost\" REAL);");
        stmt.execute("CREATE TABLE IF NOT EXISTS servers (\"Entry Date\" TEXT, \"ID\" INT, \"Exit Date\" TEXT, \"Pages Viewed\" INT, \"Conversion\" TEXT);");
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

    public void importClickLog() {
        importLog("clicks", LOG_PATH_CLICKS);
    }

    public void importImpressionLog() {
        importLog("impressions", LOG_PATH_IMPRESSIONS);
    }

    public void importServerLog() {
        importLog("servers", LOG_PATH_SERVERS);
    }

    public void importUserlog() {
        importLog("users", LOGIN_PATH);
    }

    private void importLog(String tableName, String logFilePath) {
        try (BufferedReader csv = new BufferedReader(new FileReader(logFilePath))) {
            conn.setAutoCommit(false);
            String line = csv.readLine(); // Skip header
            while ((line = csv.readLine()) != null) {
                String[] values = line.split(", *");
                String sql = buildInsertQuery(tableName, values);
                stmt.execute(sql);
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildInsertQuery(String tableName, String[] values) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for (int i = 0; i < values.length; i++) {
            query.append(i == 0 ? "\"" + values[i] + "\"" : ", \"" + values[i] + "\"");
        }
        query.append(")");
        return query.toString();
    }

    public static void main(String[] args){
        // Create an instance of the database and import logs
        makeDatabase database = new makeDatabase();
        System.out.println("Go here starting:");
        database.importClickLog();
        System.out.println("imported log1:");

        database.importImpressionLog();
        System.out.println("imported log2:");

        database.importServerLog();
        System.out.println("imported log3:");

        database.importUserlog();
        System.out.println("imported log4:");

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
                SELECT & FROM("users");""");
    }

}
