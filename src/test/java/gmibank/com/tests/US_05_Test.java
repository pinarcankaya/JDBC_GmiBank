package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class US_05_Test {


    @Test  //TC_0501==>Kullanici authority_name'i 'ROLE_ADMIN' olanlari ve login name'inde 'admin' oldugunu dogrular
    public void TC_0501() throws SQLException {



//        String adminQuery="select login,authority_name\n" +
//                "from jhi_user\n" +
//                "join jhi_user_authority\n" +
//                "on jhi_user.id=jhi_user_authority.user_id\n" +
//                "where authority_name='ROLE_ADMIN' and login like '%admin%' ";
//
//        List<Map<String,String>> query1=DatabaseConnector.getQueryAsAListOfMaps(adminQuery);
//        System.out.println(query1);
//        for (Map<String, String> w : query1){
//            Assert.assertTrue(w.get("login").contains("admin"));
//        }

    }


    @Test // Kullanici authority_name'i 'ROLE_ADMIN' olan 'activated'i 'false' olan  8 kisi oldugunu dogrular
    public void TC_0502() throws SQLException {



//        String activatedQuery="select count(activated)\n" +
//                "from jhi_user\n" +
//                "join jhi_user_authority\n" +
//                "on jhi_user.id=jhi_user_authority.user_id\n" +
//                "where  authority_name='ROLE_ADMIN' and activated='false' ";
//
//        List<Map<String,String>> query2= DatabaseConnector.getQueryAsAListOfMaps(activatedQuery);
//        System.out.println(query2.get(0).get("count"));
//       Assert.assertEquals(query2.get(0).get("count"),"8");

    }



    @Test   ///country name i Yozgat olan ilk kisinin lastname'nin ilk 4 harfinin 'Cruz' oldugunu dogrulayiniz
    public void TC_0503() throws SQLException {
        String countryQuery="select first_name,last_name,substring(last_name,1,4)\n" +
                "from tp_customer\n" +
                "join tp_country\n" +
                "on tp_customer.country_id=tp_country.id\n" +
                "where name='Yozgat'  limit 1 ";

        List<Map<String,String>> query3=DatabaseConnector.getQueryAsAListOfMaps(countryQuery);
        System.out.println(query3);
        Assert.assertTrue(query3.get(0).get("last_name").equals("Rose"));

    }
    //datalar degisebilir yeni degisiklige uygun guncelleyiniz...
}
