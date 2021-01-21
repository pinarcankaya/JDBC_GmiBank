package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CalismaTest02 {

    @Test
    public void test01() throws SQLException {
        String query = "select contact_title,region\n" +
                        "from customers\n" +
                        "where contact_title = 'Owner' and region is null ";

        List<Map<String,String>> queryList = DatabaseConnector.getQueryAsAListOfMaps(query);
        Assert.assertTrue(queryList.size() == 13);

        System.out.println(queryList.size());
    }
}
