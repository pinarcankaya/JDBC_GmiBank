package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CalismaTest01 {


    @Test
    public void test01() throws SQLException {
        /*--her bir kategori id'ye göre kaç tane ürün olduğunu bulun ve bunları azalan sıralama ile yazın.
        - en ustteki urunun adini ve count'unu dogrulayin

         */
        String query = "select product_name,category_id, count(category_id)\n" +
                        "from products\n" +
                         "group by category_id\n" +
                         "order by count(category_id) desc ";

        List<Map<String, String>> queryList = DatabaseConnector.getQueryAsAListOfMaps(query);
//        System.out.println(queryList.get(0).get("product_name"));
//        System.out.println(queryList.get(0).get("count(category_id)"));
        Assert.assertEquals(queryList.get(0).get("product_name"),"Pavlova");
        Assert.assertEquals(queryList.get(0).get("count(category_id)"),"13");

    }

}
