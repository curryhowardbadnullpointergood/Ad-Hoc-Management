package soton.ac.uk.seg.backend;

import java.sql.*;

public class Sqlquery {
    public static void main(String[] args) {

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/main/java/soton/ac/uk/seg/backend/database/data.db");

             // Execute the query
             double result = queryBounceRate(connection);
             double resultBounce = queryBounce(connection);
             double resultClicks = queryClick(connection);
             double resultConversion = queryConversion(connection);
             double resultCPA = queryCPA(connection);
             double resultCPC = queryCPC(connection);
             double resultCPM = queryCPM(connection);
             double resultCTR = queryCTR(connection);
             double resultImperssion = queryImperssion(connection);
             double resultTotalCost = queryTotalCost(connection);
             double resultUniques = queryUniques(connection);







             // Print the result
             System.out.println("ResultBounceRate: " + result + "\n" + "ResultBounce: " + resultBounce + "\n"  + "ResultClicks: " + resultClicks + "\n"+ "ResultConversion: " + resultConversion + "\n" );
             System.out.println("ResultCPA: " + resultCPA + "\n" + "ResultCPC: " + resultCPC + "\n" + "ResultCPM: " + resultCPM + "\n" + "ResultCTR: " + resultCTR + "\n" + "ResultImpressions: " + resultImperssion + "\n");
             System.out.println("ResultTotalCost: " + resultTotalCost + "\n" + "ResultUniques: " + resultUniques + "\n");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static double queryBounceRate(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT (SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks.\"ID\" = servers.\"ID\" AND clicks.\"Date\" = servers.\"Entry Date\" WHERE \"Pages Viewed\" < 2) * 1.0 / (SELECT COUNT(*) FROM clicks);";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryBounce(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks.\"ID\" = servers.\"ID\" AND clicks.\"Date\" = servers.\"Entry Date\" WHERE \"Pages Viewed\" < 2;";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryClick(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT COUNT(*) FROM clicks;";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryConversion(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks.\"ID\" = servers.\"ID\" AND clicks.\"Date\" = servers.\"Entry Date\" WHERE \"Conversion\" = 'Yes';";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static double queryCPA(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT ((SELECT SUM(\"Click Cost\") FROM clicks) + (SELECT SUM(\"Impression Cost\") FROM impressions)) * 1.0 / (SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks.\"ID\" = servers.\"ID\" AND clicks.\"Date\" = servers.\"Entry Date\" WHERE \"Conversion\" = 'Yes');";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryCPC(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT ((SELECT SUM(\"Click Cost\") FROM clicks) + (SELECT SUM(\"Impression Cost\") FROM impressions)) * 1.0 / (SELECT COUNT(*) FROM clicks);";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryCPM(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT ((SELECT SUM(\"Click Cost\") FROM clicks) + (SELECT SUM(\"Impression Cost\") FROM impressions)) * 1000.0 / (SELECT COUNT(*) FROM impressions);";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryCTR(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT (SELECT COUNT(*) FROM clicks) * 1.0 / (SELECT COUNT(*) FROM impressions);";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryImperssion(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT COUNT(*) FROM impressions;";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryTotalCost(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT (SELECT SUM(\"Click Cost\") FROM clicks) + (SELECT SUM(\"Impression Cost\") FROM impressions);";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double queryUniques(Connection conn ) {
        double result = 0.0;
        Connection connection = null;
        try {

            // Step 2: Create a Statement object
            Statement statement = conn.createStatement();

            // Step 3: Execute the SQL query
            String query = "SELECT COUNT(DISTINCT ID) FROM clicks;";
            ResultSet resultSet = statement.executeQuery(query);

            // Step 4: Process the results
            if (resultSet.next()) {
                result = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close connection
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }







}









