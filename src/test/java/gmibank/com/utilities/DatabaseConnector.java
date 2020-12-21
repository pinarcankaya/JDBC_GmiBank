package gmibank.com.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseConnector {

    private static final String dbusername = ConfigurationReader.getProperty("username");
    private static final String dbpassword = ConfigurationReader.getProperty("password");
    private static final String connectionUrl = ConfigurationReader.getProperty("db_url");

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ResultSetMetaData rsmd;
    private static PreparedStatement preparedStatement;
    private static ResultSetMetaData metaData;


    public static ResultSet getResultSet(String query) {

        //Connection connection1 = DriverManager.getConnection(connectionUrl,dbusername,dbpassword);

        try {
            connection = DriverManager.getConnection(connectionUrl, dbusername, dbpassword);
            if (connection != null) {
                System.out.println("EN: Connected to the database...");
                System.out.println("TR: Database e baglanildi...");
            } else {
                System.out.println("EN: Database connection failed");
                System.out.println("TR: Database baglantisi kurulamadi.");
            }

            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);

        } catch (SQLException sqlEx) {
            System.out.println("SQL Exception:" + sqlEx.getStackTrace());
        }
        return resultSet;
    }

    //query sonucunu list map seklinde almak icin bu method kullanilabilir
    public static List<Map<String,String>> getQueryAsAListOfMaps(String query) throws SQLException {
        resultSet=getResultSet(query);
        ResultSetMetaData rsdm=resultSet.getMetaData();
        int sizeOfColumns=rsdm.getColumnCount();
        List<String> nameOfColumnsList=new ArrayList<>();
        for (int i=1;i<=rsdm.getColumnCount();i++){
            nameOfColumnsList.add(rsdm.getColumnName(i));
        }
        resultSet.beforeFirst();

        List<Map<String,String>> listOfResultset=new ArrayList<>();
        while (resultSet.next()){
            Map<String,String> mapOfEachRow=new HashMap<>();
            for (int j=0;j<sizeOfColumns;j++)
            {
                mapOfEachRow.put(nameOfColumnsList.get(j),resultSet.getString(nameOfColumnsList.get(j)));
            }
            listOfResultset.add(mapOfEachRow);
        }
        return listOfResultset;
    }

    ///Asagidaki yoruma alinan iki method pojo uretilip kullanilabilir
    //-----------------------------------------------------------------------------------
//    public static List<Country> getAllCountriesAsAList() throws SQLException {
//        String query="SELECT * FROM public.tp_country";
//        Connection connection= DatabaseConnector.createConnection();
//        ResultSet resultSet = executeQuery(query);
//        connection.close();
//        List<Country> countries= getResultSetAsAListOfMaps(resultSet).
//                stream().map(t->{
//            Country country=new Country();
//            country.setId(Integer.parseInt(t.get("id")));
//            country.setName(t.get("name"));
//            return country;
//        }).collect(Collectors.toList());
//        return countries;
//    }
//    //-----------------------------------------------------------------------------------
//    public static List<States> getAllStatesAsAList() throws SQLException {
//        String query="SELECT * FROM public.tp_state";
//        Connection connection= DBUtilsNew.createConnection();
//        ResultSet resultSet = executeQuery(query);
//        connection.close();
//        List<States> states= getResultSetAsAListOfMaps(resultSet).
//                stream().map(t->{
//            States state=new States();
//            state.setId(Integer.parseInt(t.get("id")));
//            state.setName(t.get("name"));
////            state.setTpcountry(t.get("tpcountry"));
//            return state;
//        }).collect(Collectors.toList());
//        return states;
//    }
    //-----------------------------------------------------------------------------------
    public static List<Map<String,String>> getEmployeesAsListOfMap() throws SQLException {
        String query="SELECT * FROM public.tp_employee; ";
        return getQueryAsAListOfMaps(query);
    }
    public static Map<String,String> getEmployeeByLoginName(String loginName) throws SQLException {
        String query="SELECT * FROM public.tp_employee; ";
        return getQueryAsAListOfMaps(query).get(0);
    }
    //-----------------------------------------------------------------------------------
    public static List<Map<String,String>> getUsersAsListOfMap() throws SQLException {
        String query="SELECT * FROM public.jhi_user; ";
        return getQueryAsAListOfMaps(query);
    }
    public static Map<String,String> getUserByLoginName(String loginName) throws SQLException {
        String query="SELECT * FROM public.jhi_user; ";
        return getQueryAsAListOfMaps(query).get(0);
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
    // ============= insert a row =============== //
    //----yeni bir satir eklemek icin kullanilan method----//
    public static void executeInsertQuery(String insertQuery) {
        createConnection();
        try {
            preparedStatement = connection.prepareStatement(insertQuery);
            // Asagidaki yorumda olan kismi insert edeceginiz dataya uygun duzenleyiniz.
            //preparedStatement.setInt(1,15);
            //preparedStatement.setString(2,"Ankara");
//            preparedStatement.setInt(3,01);
//            preparedStatement.setInt(1, 2);
//            preparedStatement.setString(2,"Houston Insert");
//            preparedStatement.setObject(3, null);
            //preparedStatement.execute();
            int row  = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Affected row: " + row);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // ==============  get a list map of the query result ============== //
    //  --------yapilan sorguyu bir list icinde aldigimiz method-------
    //ornek: myDataList.get(0).get("id").....ilk index.teki "id"yi getir..
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
    // =========== get Resultset with query =========== //
    //  yapilan sorgulamayi RESULTSET olarak return eden method.
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
    // ============== get all column name with ResultSet =========== //
    // metadata ile column isimlerini bir listeye ekledigimiz method.
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


    public static void closeConnection() {
        try {
            if (resultSet != null) { //icten disa dogru kapatmak daha uygundur
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }


    }


}
