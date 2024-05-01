package soton.ac.uk.seg.backend;

import java.sql.*;

public class Sqlquery {
    public static void main(String[] args) {

        try {
            // Establish database connection
            Connection connectionClicks = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/clicks.db");
            Connection connectionImpressions = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/impressions.db");
            Connection connectionServers = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/servers.db");

             // Execute the query
             double result = executeQuery(connectionClicks, connectionServers);

             // Print the result
             System.out.println("Result: " + result);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static double executeQuery(Connection clicksConnection, Connection serversConnection) throws SQLException {
        double result = 0.0;

        // Create statements for each connection
        try (Statement clicksStatement = clicksConnection.createStatement();
             Statement serversStatement = serversConnection.createStatement()) {

            // Execute the inner query to count clicks with "Pages Viewed" < 2
            ResultSet innerResult = clicksStatement.executeQuery(
                    "SELECT COUNT(*) FROM clicks " +
                            "INNER JOIN servers ON clicks.ID = servers.ID " +
                            "AND clicks.Date = servers.'Entry Date' " +
                            "WHERE 'Pages Viewed' < 2");

            // Get the count from the inner query
            double innerCount = 0.0;
            if (innerResult.next()) {
                innerCount = innerResult.getDouble(1);
            }

            // Close the inner result set
            innerResult.close();

            // Execute the outer query to count all clicks
            ResultSet outerResult = clicksStatement.executeQuery("SELECT COUNT(*) FROM clicks");

            // Get the total count of clicks
            double totalCount = 0.0;
            if (outerResult.next()) {
                totalCount = outerResult.getDouble(1);
            }

            // Close the outer result set
            outerResult.close();

            // Calculate the result
            result = innerCount / totalCount;
        }

        return result;
    }




}









