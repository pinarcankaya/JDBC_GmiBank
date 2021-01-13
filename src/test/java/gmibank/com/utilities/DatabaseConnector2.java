package gmibank.com.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnector2 {

    private static final String dbusername = ConfigurationReader.getProperty("username");
    private static final String dbpassword = ConfigurationReader.getProperty("password");
    private static final String connectionUrl = ConfigurationReader.getProperty("db_url");

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ResultSetMetaData metaData;
    private static PreparedStatement preparedStatement;//insert,update ve delete icin

    public static ResultSet getResultSet(String query) {

        try {
            connection = DriverManager.getConnection(connectionUrl, dbusername, dbpassword);
            if (connection != null) {
                System.out.println("EN: Connected to the database...");
            } else {
                System.out.println("EN: Database connection failed");
            }

            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);

        } catch (SQLException sqlEx) {
            System.out.println("SQL Exception:" + sqlEx.getStackTrace());
        }
        return resultSet;
    }

    // ============ Create connection to the DB =============== //
    public static Connection createConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl,dbusername,dbpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // =========== get Resultset with query =========== //
    public static ResultSet executeQuery(String query) {

        createConnection();
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(query + ": query did not successfully execute!" );
        }
        return resultSet;
    }

    // ======== get column count with the query ======== //
    public static int getColumnCount(String query) {
        resultSet = executeQuery(query);
        int columnCount = 0;
        try {
            metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return columnCount;
    }

    // ============== get all column name with ResultSet =========== //
    public static List<String> getAllColumnNameWithResultSet(ResultSet resultSetForColName) {
        List<String> listOfColumnName = new ArrayList<>();
        try {
            metaData = resultSetForColName.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount() ; i++) {
                listOfColumnName.add(metaData.getColumnName(i));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  listOfColumnName;
    }

    // ==============  get a list map of the query result ============== //
    public static List<Map<String,String>> getQueryResultWithAListMap(String query) {
        resultSet = executeQuery(query);
        List<Map<String,String>> allResultListMap = new ArrayList<>();
        try {
            metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> allColumnName = getAllColumnNameWithResultSet(resultSet);
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Map<String,String> row = new HashMap<>();
                for (int i = 0; i < columnCount ; i++) {
                    row.put(allColumnName.get(i),resultSet.getString(allColumnName.get(i)));
                }
                allResultListMap.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allResultListMap;
    }

    // ============= insert a row =============== //
    public static void executeInsertQuery(String insertQuery) {
        createConnection();
        try {
            preparedStatement = connection.prepareStatement(insertQuery);
            // Asagidaki yorumda olan kismi insert edeceginiz dataya uygun duzenleyiniz.

//            preparedStatement.setInt(1, 2);
//            preparedStatement.setString(2,"Ankara");
//            preparedStatement.setInt(3, 1);

            int row  = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Affected row: " + row);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ============= update a row =============== //
    public static void executeUpdateQuery(String updateQuery) {
        createConnection();
//        String updateQueryExample = "UPDATE tp_state SET name=? WHERE id =?";
        try {
            preparedStatement = connection.prepareStatement(updateQuery);
            // Asagidaki yorumda olan kismi update edeceginiz dataya uygun duzenleyiniz.

//          preparedStatement.setString(1 ,"HATA"); // 1. ? yerine
            //preparedStatement.setInt(1,1); //2. ? yerine
            //preparedStatement.executeUpdate();

            int row  = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Affected row: " + row);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void executeUpdateQueryFix(String updateQuery) {
        createConnection();
//        String updateQueryExample = "UPDATE tp_state SET name=? WHERE id =?";
        try {
            preparedStatement = connection.prepareStatement(updateQuery);
            // Asagidaki yorumda olan kismi update edeceginiz dataya uygun duzenleyiniz.

            preparedStatement.setString(1 ,"AUTHENTICATION_FAILURE"); // 1. ? yerine
            //preparedStatement.setInt(1,1); //2. ? yerine
            //preparedStatement.executeUpdate();

            int row  = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Affected row: " + row);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeDelete(String updateQuery) {
        createConnection();
//        String updateQueryExample = "UPDATE tp_state SET name=? WHERE id =?";
        try {
            preparedStatement = connection.prepareStatement(updateQuery);
            // Asagidaki yorumda olan kismi update edeceginiz dataya uygun duzenleyiniz.

            //preparedStatement.setString(1 ,"4958"); // 1. ? yerine
//            preparedStatement.setInt(1,49458); //2. ? yerine
            preparedStatement.executeUpdate();

            int row  = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Affected row: " + row);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ============== close all Db connection ============== //
    public static void closeConnection() {

        boolean r = true, s = true , c = true;

        if (resultSet != null) {
            try {
                System.out.println("Database Connection kapatiliyor...");
                resultSet.close();
            } catch (SQLException ex) {
                r=false;
                ex.printStackTrace();
            }
        }

        if ( statement!= null) {
            try {
                System.out.println("Database Connection kapatiliyor...");
                statement.close();
            } catch (SQLException ex) {
                s = false;
                ex.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                System.out.println("Database Connection kapatiliyor...");
                connection.close();
            } catch (SQLException ex) {
                c = false;
                ex.printStackTrace();
            }
        }

        if( r && s && c ) System.out.println("Connection closed successfully");
    }
}
