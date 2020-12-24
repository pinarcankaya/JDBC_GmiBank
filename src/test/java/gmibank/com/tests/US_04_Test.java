package gmibank.com.tests;


import gmibank.com.utilities.DatabaseConnector;
import gmibank.com.utilities.DatabaseConnector2;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_04_Test {

    String insertQueryTc0401 = "insert into tp_state values (88,'Ankara', 1); ";
    String queryTC0401 = "select *\n" +
                            "from tp_state\n" +
                           "where id=88;";

    String insertQueryTc0402 = "insert into jhi_user (\"id\",\"login\",password_hash,first_name,last_name,activated,created_by)\n" +
            "            values (201,'DB','abc123','Ali','Adiguzel',true,'Veli')";
    String queryTC0402 = "select *\n" +
                        "from jhi_user\n" +
                        "where \"id\"=201" ;

    String insertQueryTc0403 = "insert into tp_country values (11,'japonya')";
    String queryTC0403 = "select *\n" +
                         "from tp_country\n" +
                         "where \"id\"=11;";

    ResultSet resultSet;

    @AfterMethod
    public void test() {
        DatabaseConnector2.closeConnection();
    }
    //"Id = 15 , name = Ankara, tpcountryid = 01"  After add these data to tp_state stable then assert that
    // existance of the data (Verilen datayi ekledikten sonra datanin varligini assert ediniz.)
    @Test
    public void tc0401() throws SQLException {
        String queryDataResult ="";
        DatabaseConnector2.executeInsertQuery(insertQueryTc0401);
        resultSet = DatabaseConnector2.getResultSet(queryTC0401);
        while (resultSet.next()) {
            queryDataResult = resultSet.getString("name");
            System.out.println(queryDataResult);
        }
        Assert.assertEquals(queryDataResult,"Ankara");
        }
    //id=201, login=DB ,password_hash= abc123, first_name= Ali ,last_name=Adiguzel ,activated = true,
    // created_by= Veli  //  add these data to jhi_user table then assert that existance of the data
    // (Verilen datayi ekledikten sonra datanin varligini assert ediniz.)
        @Test
    public void tc0402(){
        List<Map<String,String>> myDataList;
        DatabaseConnector2.executeInsertQuery(insertQueryTc0402);
        myDataList = DatabaseConnector2.getQueryResultWithAListMap(queryTC0402);
        Assert.assertEquals(myDataList.get(0).get("id"),"201");
        Assert.assertEquals(myDataList.get(0).get("login"),"DB");
        }
    //id = 11  // add this data to tp_country table then assert that existance
    // of the data (Verilen datayi ekledikten sonra datanin varligini assert ediniz.)
        @Test
    public void tc0403() throws SQLException {
        DatabaseConnector2.executeInsertQuery(insertQueryTc0403);
        resultSet = DatabaseConnector2.getResultSet(queryTC0403);
        while (resultSet.next()){
            Assert.assertEquals(resultSet.getString("id"),"11");
            Assert.assertEquals(resultSet.getString(2),"japonya");
            //
        }
        }

    }






