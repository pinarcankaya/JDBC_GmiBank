package sqlexamples.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Work1 {


    //-her bir kategori id'ye göre kaç tane ürün olduğunu bulun ve bunları azalan sıralama ile yazın
    //category_id'si en buyuk olan kisinin product_name'inin Pavlove oldugunu dogrulayiniz
    @Test
    public void testName() throws SQLException {
        String query1="select product_name,category_id,count(category_id) \n" +
                "from trycatch.products\n" +
                "group by category_id\n" +
                "order by count(category_id) desc";

        List<Map<String,String>> list1= DatabaseConnector.getQueryAsAListOfMaps(query1);
        System.out.println(list1.get(0));
        System.out.println(list1.get(0).get("product_name"));
        Assert.assertEquals(list1.get(0).get("product_name"),"Pavlova");
    }

    //--contact title'i "Owner" olan kisilerin region degerleri null olanlarin ve null olmayanlarin sayisini bulunuz
    @Test
    public void testName1() throws SQLException {
        String query2="select contact_title,region\n" +
                "from trycatch.customers\n" +
                "where contact_title='Owner'";

        List<Map<String,String>> list2= DatabaseConnector.getQueryAsAListOfMaps(query2);
        System.out.println(list2);

        //1.yol java ile cozum
      int countNull=0;
      int countNotNull=0;

        for(Map<String,String> w : list2){
            if(w.get("region")==null){
              countNull++;
            }else{
                countNotNull++;
            }
        }
        System.out.println(countNull);
        Assert.assertEquals(countNull,13);
        System.out.println(countNotNull);
        Assert.assertEquals(countNotNull,4);
    }

    //2.yol sql query ile cozum
    @Test
    public void pluginResult() throws SQLException {
        String pluginResult="select count(*)  as resultnull\n" +
                "from trycatch.customers\n" +
                "where contact_title='Owner' and region is null\n" +
                "union all\n" +
                "select count(region)  as resultNotNull\n" +
                "from trycatch.customers\n" +
                "where contact_title='Owner' and region is not null";

        List<Map<String,String>> list= DatabaseConnector.getQueryAsAListOfMaps(pluginResult);

         System.out.println("result Null : " +list.get(0).get("resultnull"));
        Assert.assertEquals(list.get(0).get("resultnull"),"13");
        System.out.println("result Not Null : " +list.get(1).get("resultnull"));
        Assert.assertEquals(list.get(1).get("resultnull"),"4");
    }

    //-1-deniz ürünleri kategorisine ait ürünlerin isimlerini ve category_name lerini getirelim
    //--toplamda 12 adet ürün oldugunu dogrulayalim:
    @Test
    public void testName3() throws SQLException {
        String query3="select  product_name,category_name,count(category_name)\n" +
                "from trycatch.products\n" +
                "join trycatch.categories\n" +
                "on products.category_id=categories.category_id\n" +
                "where category_name='Seafood'";

        List<Map<String,String>> list3= DatabaseConnector.getQueryAsAListOfMaps(query3);
        System.out.println(list3.get(0));
         Assert.assertEquals(list3.get(0).get("count(category_name)"),"12");
    }
}
