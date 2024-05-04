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

   // gendersSelected: list containing "Male" and/or "Female"
    // incomeSelected: list containing "Low", "Medium" and/or "High"
    // ageSelected: list containing "<25", "25-34", "35-44", "45-54" and/or ">54"
    // contextSelected: list containing "Social Media", "Shopping", "Blog", "News", "Hobbies", "Travel"
    // timeInterval: "hour", "day" or "week"
    // fieldToCalculate: "impressions", "clicks", "uniques", "bounces", "conversions", "total_cost", "ctr", "cpa", "cpc", "cpm" or "bounce_rate"
    // startDate: "yyyy-mm-dd" e.g. "2015-01-01"
    // endDate: "yyyy-mm-dd" e.g. "2015-01-31"
    // bounceDef: either "time" or "pages"
    // bounceNum: number of "things" needed for bounce e.g. 5, 10 etc.

    public static String getGraphQuery(String fieldToCalculate, String timeInterval, String[] gendersSelected,
                                    String[] incomeSelected, String[] ageSelected, String[] contextSelected,
                                    String bounceDef, int bounceNum, String startDate, String endDate) {

        String filterStatement = "(" + makeFilterStatement("Gender", gendersSelected) + " AND " +
                                 makeFilterStatement("Income", incomeSelected) + " AND " +
                                 makeFilterStatement("Age", ageSelected) + " AND " +
                                 makeFilterStatement("Context", contextSelected) + ")";

        String timeGrouping = "";
        switch(timeInterval) {
            case "hour" -> timeGrouping = "strftime('%Y-%m-%d %H:00', impressions.Date)";
            case "day" -> timeGrouping = "strftime('%Y-%m-%d', impressions.Date)";
            case "week" -> timeGrouping = "date((SELECT d FROM weekStartDate) + round((julianday(impressions.Date) - (SELECT d FROM weekStartDate)) / 7) * 7)";
        }

        // TODO handle different bounce logics

        String query = "";
        switch(fieldToCalculate) {
            case "impressions" -> query = "SELECT <time interval> AS time_interval, COUNT(*) FROM impressions WHERE <filter> GROUP BY time_interval;";
            case "clicks" -> query = "SELECT <time interval> AS time_interval, COUNT(*) FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID WHERE <filter> GROUP BY time_interval;";
            case "uniques" -> query = "SELECT <time interval> AS time_interval, COUNT(DISTINCT clicks.ID) FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID WHERE <filter> GROUP BY time_interval;";
            case "bounces" -> query = "SELECT <time interval> AS time_interval, COUNT(*) FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID INNER JOIN servers ON clicks.ID = servers.ID AND clicks.Date = servers.\"Entry Date\" WHERE <bounce logic> AND <filter> GROUP BY time_interval;";
            case "conversions" -> query = "SELECT <time interval> AS time_interval, COUNT(*) FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID INNER JOIN servers ON clicks.ID = servers.ID AND clicks.Date = servers.\"Entry Date\" WHERE Conversion = 'Yes' AND <filter> GROUP BY time_interval;";
            case "total_cost" -> query = "SELECT <time interval> AS time_interval, SUM(\"Click Cost\") + SUM(\"Impression Cost\") FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID WHERE <filter> GROUP BY time_interval;";
            case "ctr" -> query = "SELECT <time interval> AS time_interval, (COUNT(clicks.ID) * 1.0) / COUNT(impressions.ID) FROM clicks LEFT JOIN impressions ON clicks.ID = impressions.ID WHERE <filter> GROUP BY time_interval;";
            case "cpa" -> query = "SELECT <time interval> AS time_interval, ((SUM(\"Click Cost\") + SUM(\"Impression Cost\")) * 1.0) / COUNT(*) FROM clicks INNER JOIN servers ON clicks.ID = servers.ID AND clicks.Date = servers.\"Entry Date\" INNER JOIN impressions ON clicks.ID = impressions.ID WHERE \"Conversion\" = 'Yes' AND <filter> GROUP BY time_interval;";
            case "cpc" -> query = "SELECT <time interval> AS time_interval, ((SUM(\"Click Cost\") + SUM(\"Impression Cost\")) * 1.0) / COUNT(*) FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID WHERE <filter> GROUP BY time_interval;";
            case "cpm" -> query = "SELECT <time interval> AS time_interval, ((SUM(\"Click Cost\") + SUM(\"Impression Cost\")) * 1000.0) / COUNT(*) FROM clicks INNER JOIN impressions ON clicks.ID = impressions.ID WHERE <filter> GROUP BY time_interval;";
            case "bounce_rate" -> query = "SELECT <time interval> AS time_interval, (SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks.ID = servers.ID AND clicks.Date = servers.\"Entry Date\" WHERE \"Pages Viewed\" < 2) * 1.0 / (SELECT COUNT(*) FROM clicks) GROUP BY time_interval;";
        }

        // String[] fieldParams = new String[3];
        // switch(fieldToCalculate) {
        //     case "impressions" -> fieldParams = new String[]{"COUNT(*)", "impressions", "true"};
        //     case "clicks" -> fieldParams = new String[]{"COUNT(*)", "clicks INNER JOIN impressions ON clicks.ID = impressions.ID", "true"};
        //     case "uniques" -> fieldParams = new String[]{"COUNT(DISTINCT ID)", "clicks", "true"};
        //     case "bounces" -> fieldParams = new String[]{"COUNT(*)", "clicks INNER JOIN servers ON clicks.\"ID\" = servers.\"ID\"", "\"Pages Viewed\" < 2"};
        //     case "conversions" -> fieldParams = new String[]{"COUNT(*)", "clicks INNER JOIN servers ON clicks.\"ID\" = servers.\"ID\"", "\"Conversion\" = 'Yes'"};
        //     case "total_cost" -> fieldParams = new String[]{"(SELECT SUM(\"Click Cost\") FROM clicks) + (SELECT SUM(\"Impression Cost\") FROM impressions)", "impressions", "True"}; // TODO fix
        //     // case "ctr" -> fieldParams = SELECT (SELECT COUNT(*) FROM clicks) * 1.0 / (SELECT COUNT(*) FROM impressions);
        //     // case "cpa"
        //     // case "cpc"
        //     // case "cpm"
        //     // case "bounce_rate"
        // }

        String bounceLogic = "";
        if(bounceDef == "pages") bounceLogic = "servers.\"Pages Viewed\" <= " + bounceNum;
        if(bounceDef == "time") bounceLogic = "strftime('%Y-%m-%d %H:%M:%S', servers.\"Exit Date\") - strftime('%Y-%m-%d %H:%M:%S', servers.\"Entry Date\") <= " + bounceNum;

        String startDateFilter = "true";
        if(startDate != "") startDateFilter = "impressions.Date >= '" + startDate + "'";

        String endDateFilter = "true";
        if(endDate != "") endDateFilter = "impressions.Date <= '" + endDate + "'";

        query = query.replaceAll("<filter>", filterStatement + " AND " + startDateFilter + " AND " + endDateFilter);
        query = query.replaceAll("<time interval>", timeGrouping);
        query = query.replaceAll("<bounce logic>", bounceLogic);

        return "WITH weekStartDate AS (SELECT MIN(julianday('%s')) AS d) " + query;

        // return String.format("""
        //     WITH weekStartDate AS (SELECT MIN(julianday('%s')) AS d)
        //     SELECT %s AS time_interval, %s FROM %s WHERE (%s) AND (%s) AND (%s) AND (%s) GROUP BY time_interval;""",
        //     startDate, timeGrouping, fieldParams[0], fieldParams[1], fieldParams[2], filterStatement, startDateFilter, endDateFilter);
    }

    public static String makeFilterStatement(String field, String[] values) {
        if(values.length == 0) return "true";
        StringBuilder statement = new StringBuilder();
        for(String value: values) {
            statement.append("\"").append(field).append("\" = '").append(value).append("' OR ");
        }
        statement.append("false");
        return statement.toString();
    }

}
