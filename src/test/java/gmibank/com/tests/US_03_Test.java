package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import gmibank.com.utilities.DatabaseConnector2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_03_Test {

    // 1--tp_account tablosunda id 30000 den kucuk  kisilerin account_type sadece saving olanarin hesabinda
    // toplam 50 00000 mi var ve bu sartlardaki hesap sahiplerinin  sayisi 16 mi oldugunu sorgulayin.


    @Test
    public void TC0301() throws SQLException {

        String query01="select count (account_type) as account_count,sum(balance)\n" +
                "from tp_account\n" +
                "where id<3000 and account_type='SAVING'";

        List<Map<String,String>> list=DatabaseConnector.getQueryAsAListOfMaps(query01);
        System.out.println(list);
        Assert.assertNotEquals(list.get(0).get("sum"),"5000000");
        Assert.assertEquals(list.get(0).get("account_count"),"16");
    }





    //2-jhi_user tablosunda  first_name de isimleri nin bastan ikinci  'a ve son harfi e  olanlarin acticated
    //--coloumn u da sadece false created by da 10 tane anonymousUser kullanici oldugunu bulan kodu yaziniz
    @Test
    public void TC0302() throws SQLException {

        String query2="select count(created_by)\n" +
                "from jhi_user\n" +
                "where first_name like '_a%e' and activated='false' and created_by='anonymousUser'";

        List<Map<String,String>> list2=DatabaseConnector.getQueryAsAListOfMaps(query2);
        Assert.assertTrue(list2.get(0).get("count").equals("10"));



    }



    //- 3--jhi_user tablosundan last_modified_by column nun 'ad' ile bas. yada son harfi 'n',e,a' ile bitmis    olan ilk 5
    // --kisinin last_name lerini
    //--TeamTryCatch olarak update ediniz ve last_name lerin TeamTryCatch oldunugunu veriye ediniz
    @Test
    public void TC0303() throws SQLException {

        String query3="update jhi_user " +
                "set last_name='TeamTryCatch' " +
                "where last_modified_by like 'ad%' and id<2740";

        DatabaseConnector2.executeUpdateQuery(query3);

        String afterUpdate="select last_name " +
                "from jhi_user " +
                "where last_name='TeamTryCatch' and  last_modified_by like 'ad%' and id<2740 " +
                "limit 5";

        List<Map<String,String>> list3=DatabaseConnector.getQueryAsAListOfMaps(afterUpdate);
        System.out.println(list3);



    }
}