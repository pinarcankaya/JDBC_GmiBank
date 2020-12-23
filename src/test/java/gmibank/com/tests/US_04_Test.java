package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.annotations.Test;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public class US_04_Test {
  // Deneme


    @Test
    public void test01() {
        String insertQuery="INSERT INTO tp_state (id, name,tpcountry_id) VALUES (?,?,?)";
        DatabaseConnector.executeInsertQuery(insertQuery);

        DatabaseConnector.closeConnection();
    }

    @Test
    public void test02() {
        String insertQueryWithData = "INSERT INTO tp_state VALUES (123,'Texas-Arizona'," + 1 + ")" ;
        DatabaseConnector.executeInsertQuery(insertQueryWithData);

    }

    @Test
    public void test03() {
        String insertQueryWithData2 = "INSERT INTO jhi_user(id,login,password_hash,first_name,last_name,activated,created_by) VALUES (654,'dbKayit654','12354abcder1','name1','lastname1',true,'Ali1')" ;
        DatabaseConnector.executeInsertQuery(insertQueryWithData2);
    }

    @Test
    public void test04() throws SQLException {
        String query = "SELECT *  FROM tp_state WHERE id =15";
        System.out.println(DatabaseConnector.getQueryAsAListOfMaps(query));
        DatabaseConnector.closeConnection();
        ResultSet resultSet = DatabaseConnector.getResultSet(query);

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }

        DatabaseConnector.closeConnection();
    }
}
