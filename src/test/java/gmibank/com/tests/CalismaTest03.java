package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CalismaTest03 {

    @Test
    public void test01() throws SQLException {
        /*-1-deniz ürünleri kategorisine ait ürünlerin isimlerini ve category_name lerini getirelim
        --toplamda 12 adet ürün oldugunu dogrulayalim:
        --1-products tablosundan ürünleri kategorisine ait ürünlerinnamelerini ve categories tablosundan
                                                                                    category_name lerini getirelim
        --toplamda 12 adet ürün oldugunu dogrulayalim:
        --1(products tablosundan ürünlerin isimlerini,categories tablosundan ürünlerin category name lerini)deniz
                                    ürünleri kategorisine ait ürünlerin isimlerini ve category_name lerini getirelim
        --toplamda 12 adet ürün oldugunu dogrulayalim:
         */
        String query = "select product_name,category_name,count(product_id)\n" +
                        "from products \n" +
                        "join categories " +
                "        on products.category_id = categories.category_id\n" +
                        "where category_name = 'Seafood'";

        List<Map<String,String>> queryList = DatabaseConnector.getQueryAsAListOfMaps(query);
        String expectedResult = queryList.get(0).get("count(product_id)");
        Assert.assertEquals(expectedResult,"12");


    }
}
