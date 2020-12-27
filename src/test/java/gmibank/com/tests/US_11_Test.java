package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector2;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class US_11_Test {

    SoftAssert sof=new SoftAssert();

    @Test
    public void tc1101(){
        /*id si 40000 den yüksek olan ve city si Athens olmayan userlardan,
        5 ten büyük user_id  ye sahip olanlarin 67 kisi
        oldugunu bulun(tp_customer)


         */

        String query="Select count(city) from tp_customer\n" +
                "where city !='Athens' and user_id>5 and id>40000;";

        List<Map<String,String>>  result= DatabaseConnector2.getQueryResultWithAListMap(query);

        System.out.println(result.get(0).values());

        Map<String ,String> isim=new HashMap<>();

        isim.put("ad","ozkan");
        isim.put("soy","cankaya");
        isim.put("isim","resit");
        System.out.println(isim);
        System.out.println(isim.values());


        sof.assertEquals(result.get(0).get("count"),"67");//get(0) list ait get("count") map in key ini almak
        sof.assertAll();


    }

    @Test
    public void tc1102(){
        /*
        tp_account tablosunda balance id leri 5000 olan
         müsterilerden ilk 40 kisinin
          tümünün account status type'inin ACTIVE oldugunu dogrulayiniz
         */

        String query="select balance,account_status_type \n" +
                "from tp_account \n" +
                "where balance=5000 and account_status_type='ACTIVE'  limit 40;";

        List<Map<String,String >>  mapQeury=DatabaseConnector2.getQueryResultWithAListMap(query);

        System.out.println(mapQeury.get(0).get("account_status_type"));
        String actual=mapQeury.get(0).get("account_status_type");
        sof.assertEquals(actual,"ACTIVE");
        sof.assertAll();


    }

    @Test
    public void tc_1103(){
        /*"anonymousUser" tarafindan create(created_by) edilmis ilk kisinin "authority_name" 'inin "ROLE_CUSTOMER",
             "team18_admin" tarafindan create(created_by) edilmis ilk kisinin "authority_name" 'inin "ROLE_ADMIN","
                     admin" tarafindan create(created_by) edilmis ilk kisinin "authority_name" 'inin "ROLE_EMPLOYEE" oldugunu dogrulayiniz.

         */

        String person[]={"anonymousUser","team18_admin","admin"};

        String roller[]={"ROLE_CUSTOMER","ROLE_ADMIN","ROLE_EMPLOYEE"};


        for(int i=0; i< person.length;i++) {

            String query = "select created_by,authority_name from jhi_user\n" +
                    "join jhi_user_authority on jhi_user.id=jhi_user_authority.user_id \n" +
                    "where created_by='"+person[i]+"' limit 1 ;";

            List<Map<String,String>> mapResult=DatabaseConnector2.getQueryResultWithAListMap(query);

            System.out.println(mapResult.get(0).get("authority_name"));

            sof.assertEquals(mapResult.get(0).get("authority_name"),roller[i]);
            //sof.assertTrue(mapResult.get(0).containsValue(roller[i]));



        }


        sof.assertAll();
    }

}
