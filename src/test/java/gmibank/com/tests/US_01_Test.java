package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_01_Test {
    ResultSet resultSet;
    List<Map<String,String>> list;


    String query_tc11_01 = "select city\n" +
                           "from tp_customer\n" +
                           "where first_name like 'R%' and last_name like '_a%';";

    String query_tc11_02 ="select *\n" +
                          "from jhi_user\n" +
                          "join tp_customer on tp_customer.user_id=jhi_user.\"id\"\n" +
                          "where \"state\" in ('USA','Miami');";

    String query_tc11_03 = "select *\n" +
                           "from tp_customer\n" +
                           "where id<40000 and city like '%is';";

    String query_tc11_04 = "select first_name, last_name, email, zip_code\n" +
                         "from tp_customer\n" +
                         "join tp_state on tp_state.id=tp_customer.country_id\n" +
                         "where zip_code in ('10433','25000','456');";
    @Test
    public void us01_tc01() throws SQLException {
        list = DatabaseConnector.getQueryAsAListOfMaps(query_tc11_01);
       // System.out.println(list.size());
        Assert.assertTrue(list.size()<45);

    }
    @Test
    public void us01_tc02() throws SQLException{
        list = DatabaseConnector.getQueryAsAListOfMaps(query_tc11_02);
       // System.out.println(list.size());
        Assert.assertTrue(list.size()>5);

    }
    @Test
    public void us01_tc03() throws SQLException{
        list = DatabaseConnector.getQueryAsAListOfMaps(query_tc11_03);
       // System.out.println(list.size());
        Assert.assertEquals(list.size(),2);

    }
    @Test
    public void us01_tc04() throws SQLException{
        list = DatabaseConnector.getQueryAsAListOfMaps(query_tc11_04);
      //  System.out.println(list.size());
        Assert.assertTrue(list.size()==4);

    }
}
