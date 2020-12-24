package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import gmibank.com.utilities.DatabaseConnector2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class US_08_Test {

    @Test
    public void tc08_01() {

        //  jhi_persistent_audit_event tablosunda event_id'si 44500'den büyük,
        //  principal degeri "group8user" ve event_type degeri "AUTHENTICATION_FAILURE" olan verilerin
        //  "AUTHENTICATION_FAILURE" degerlerini "HATA" olarak güncelleyin.

//        String updateQuery = "UPDATE jhi_persistent_audit_event " +
//                             "SET event_type = 'HATA' " +
//                             "WHERE event_id>44500 and principal ='group8user' and event_type ='AUTHENTICATION_FAILURE'";
//        DatabaseConnector2.executeUpdateQuery(updateQuery);

        String resultQuery = "SELECT * " +
                             "FROM jhi_persistent_audit_event " +
                             "WHERE event_id>44500 and principal ='group8user' and event_type !='AUTHENTICATION_SUCCESS'";
        List<Map<String,String>> resultListMap = DatabaseConnector2.getQueryResultWithAListMap(resultQuery);

        //Assertion 1. yontem  :

//        for (Map<String,String> row : resultListMap) {
//            Assert.assertEquals(row.get("event_type"),"HATA");
//        }

//       Assertiton 2. yontem  ==> Lambda ile
        resultListMap.
                    stream().
                    forEach(t -> Assert.assertEquals(t.get("event_type"),"HATA"));


    }

    @Test
    public void tc08_02() {

//        kullanıcı jhi_persistent_audit_evt_data tablosundan bir value degeri "Bad credentials"
//        olan bir ID belirler ve o ID'yi tablodan siler.

        String deleteQuery = "Delete from jhi_persistent_audit_evt_data " +
                             "where value ='Bad credentials' and event_id = 56196";
        DatabaseConnector2.executeDelete(deleteQuery);

        String resultQuery = "SELECT * " +
                             "from jhi_persistent_audit_evt_data " +
                             "where value ='Bad credentials' and event_id = 56196";
        List<Map<String,String>> resultListMap = DatabaseConnector2.getQueryResultWithAListMap(resultQuery);
        Assert.assertTrue(resultListMap.size()==0);

    }

    @Test
    public void tc08_03() {

//        kullanıcı tp_account data tablosundan tüm hesap çeşitlerini listeler,
//        bu hesaplardan kaçar adet olduğunu sayar ve her hesapta ortalama  ne kadar para (balance)
//        olduğunu kontrol eder. En fazla ortalama paranin saving hesablarinda oldugunu dogrular

        String query =  "select account_type, count (account_type) as account_count, avg (balance) " +
                        "from tp_account " +
                        "group by account_type " +
                        "order by avg (balance)  DESC " +
                        "Limit 1";
        List<Map<String,String>> resultListMap = DatabaseConnector2.getQueryResultWithAListMap(query);
        Assert.assertEquals(resultListMap.get(0).get("account_type"),"SAVING");
    }
}
