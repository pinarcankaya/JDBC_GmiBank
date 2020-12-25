package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import gmibank.com.utilities.DatabaseConnector2;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.SQLException;
import java.util.Arrays;
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

    @Test
    public void TC_1103(){
        /*"anonymousUser" tarafindan create(created_by) edilmis ilk kisinin "authority_name" 'inin "ROLE_CUSTOMER",
            "team18_admin" tarafindan create(created_by) edilmis ilk kisinin "authority_name" 'inin "ROLE_ADMIN","
                  admin" tarafindan create(created_by) edilmis ilk kisinin "authority_name" 'inin "ROLE_EMPLOYEE" oldugunu dogrulayiniz.
         */

        String adminrole[]={"anonymousUser","team18_admin","admin"};

        String adminrole2[]={"ROLE_CUSTOMER","ROLE_ADMIN","ROLE_EMPLOYEE"};
        String query="";

        for(int i=0; i<adminrole.length; i++){

           query="select created_by ,authority_name \n" +
                    "from jhi_user \n" +
                    "join jhi_user_authority \n" +
                    "on jhi_user.id=jhi_user_authority.user_id\n" +
                    "where created_by='"+adminrole[i]+"' limit 1;";

            //System.out.println(query);

            List<Map<String ,String >> mapquery= DatabaseConnector2.getQueryResultWithAListMap(query);
            System.out.println(mapquery.get(0).values());
            softAssert.assertTrue(mapquery.get(0).containsValue(adminrole2[i]));


        }


        softAssert.assertAll();





    }
}
