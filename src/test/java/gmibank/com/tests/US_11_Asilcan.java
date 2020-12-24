package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_11_Asilcan {


    SoftAssert softAssert = new SoftAssert();

    @Test
    public void TC_1101() throws SQLException {
//id si 40000 den yüksek olan ve
// city si Athens olmayan user larin 5 ten büyük user id  ye
// sahip olanlarin 20 kisi oldugunu bulun


        String tc11q="select id,city ,user_id from tp_customer " +
                            "where id>40000 and city !='Athens'and " +
                            "user_id>5 order by id Desc limit 20;";


        List<Map<String,String >> tcquer= DatabaseConnector.getQueryAsAListOfMaps(tc11q);

        System.out.println(tcquer);
        int actual=tcquer.size();

        softAssert.assertEquals(actual,20);

        softAssert.assertAll();


    }

    @Test
    public void TC_1102() throws SQLException {
 /*
 id si 40000 den yüksek olan
 ve city si Athens olmayan user larin 5 ten büyük user id  ye sahip olanlarin 20 kisi oldugunu bulun
  */
        String tc11q02="select balance,id, account_status_type  \n" +
                "from tp_account where balance =5000 and account_status_type='ACTIVE' \n" +
                "Order by id Asc limit 20;";

        List<Map<String,String>>  list=DatabaseConnector.getQueryAsAListOfMaps(tc11q02);

        System.out.println(list);
        int actual=list.size();

        softAssert.assertEquals(actual,20);
        softAssert.assertAll();





    }

}
