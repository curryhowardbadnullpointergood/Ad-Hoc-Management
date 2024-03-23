package soton.ac.uk.seg.backend;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Database {
    Connection conn;
    Statement stmt;

    public Database(String path) {
        String dbFile = "jdbc:sqlite:" + path;

        try {
            conn = DriverManager.getConnection(dbFile);
            stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS clicks (\"Date\" TEXT, \"ID\" INT, \"Click Cost\" REAL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS impressions (\"Date\" TEXT, \"ID\" INT, \"Gender\" TEXT, \"Age\" TEXT, \"Income\" TEXT, \"Context\" TEXT, \"Impression Cost\" REAL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS servers (\"Entry Date\" TEXT, \"ID\" INT, \"Exit Date\" TEXT, \"Pages Viewed\" INT, \"Conversion\" TEXT);");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (\"username\" TEXT, \"password\" TEXT, \"permissions\" TEXT)");
//            stmt.execute("sqlite3 mode csv");
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

    public void importUserlog(String loginFile) {
        try {
            conn.setAutoCommit(false);
            BufferedReader csv = new BufferedReader(new FileReader(loginFile));
            String line;
            csv.readLine();
            while((line = csv.readLine()) != null) {
                String[] values = line.split(", *");
                stmt.execute(String.format("INSERT INTO users VALUES (\"%s\", %s, \"%s\")",
                    values[0], values[1], values[2]));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    } 
    

    private double getDoubleQuery(String query) {
        try{
            ResultSet result = stmt.executeQuery(query);
            return result.getDouble(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getIntQuery(String query) {
        try{
            ResultSet result = stmt.executeQuery(query);
            return result.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getBounceRate() {
        return getDoubleQuery("""
                -- Selects all records as demoninator from clicks table...
                SELECT COUNT(*) * 1.0 / (SELECT COUNT(*) FROM clicks) AS Bounce_Rate
                FROM clicks
                -- But selects all records from clicks that satisfy the predicate across servers table...
                LEFT JOIN servers ON clicks.ID = servers.ID
                -- where records do not have a null "Exit date" (as it wouldn't count as a bounce)
                -- And the user either viewed only one page OR stayed on the page for up to 30 seconds...
                -- We use strftime function to convert the DATETIME format into a single INT value to be compared directly to 30\s
                WHERE servers."Exit Date" != 'n/a' AND (servers."Pages Viewed" = 1 OR (strftime('%s', servers."Exit Date") - strftime('%s', servers."Entry Date") <= 30));""");
    }

    public double getCPA() {
        return getDoubleQuery("""
                -- Calculates the sum of the total "Click cost" from the clicks table as the nominator...
                -- But counts up all records from the servers table that satisfy the predicate of having a successful conversion (as the denominator)...
                SELECT SUM("Click Cost") * 1.0 / (SELECT COUNT(*) FROM servers WHERE "Conversion" = 'Yes') AS CPA
                FROM clicks;""");
    }

    public double getCPC() {
        return getDoubleQuery("""
                -- Calculates the sum of the total "Click cost" from the clicks table as the nominator, and counts up all the records in the clicks table as the demoninator
                SELECT SUM("Click Cost") * 1.0 / (SELECT COUNT(*) FROM clicks) AS CPC
                FROM clicks;""");
    }

    public double getCPM() {
        return getDoubleQuery("""
                -- Calculates the sum of the total "Impression cost" from the impressions table as the nominator (multiplied by 1000), and counts up all the records in the impressions table as the demoninator
                SELECT SUM("Impression Cost") * 1000.0 / (SELECT COUNT(*) FROM impressions) AS CPM
                FROM impressions;""");
    }

    public double getCTR() {
        return getDoubleQuery("""
                -- Counts up all the records in the clicks table as the nominator, and counts up all the records in the impressions table as the demoninator
                SELECT COUNT(*) * 1.0 / (SELECT COUNT(*) FROM impressions) AS CTR
                FROM clicks;""");
    }

    public int getBounces() {
        return getIntQuery("""
                -- Counts all records from the servers table which satisfy the following predicate:
                -- Where records do not have a null "Exit date" (as it wouldn't count as a bounce) AND the user either viewed only one page OR stayed on the page for up to 30 seconds... We use strftime function to convert the DATETIME format into a single INT value to be compared directly to 30\s
                SELECT COUNT(*) FROM servers
                WHERE "Exit Date" != 'n/a' AND ((strftime('%s', "Exit Date") - strftime('%s', "Entry Date") <= 30) OR "Pages Viewed" = 1);""");
    }

    public int getClicks() {
        return getIntQuery("""
                -- Counts up all records from the clicks tables
                SELECT COUNT(*) FROM clicks;""");
    }

    public int getConversions() {
        return getIntQuery("""
                -- Counts all records from the servers table where there was a successful conversion
                SELECT COUNT(*) FROM servers
                WHERE "Conversion" = 'Yes';""");
    }

    public int getImpressions() {
        return getIntQuery("""
                -- Counts all records from the impressions table
                SELECT COUNT(*) FROM impressions;""");
    }

    public int getUniques() {
        return getIntQuery("""
                -- Counts all records from the servers table where each record has a distinct user "ID"
                SELECT COUNT(DISTINCT ID) FROM servers;""");
    }

    public double getTotalCost() {
        return getDoubleQuery("""
                SELECT SUM("Click Cost") FROM clicks;""");
    }

    // gets list of all users 
    public double getAllUsers() {
        return getDoubleQuery("""
                SELECT & FROM("users");""");
    }

   

}
