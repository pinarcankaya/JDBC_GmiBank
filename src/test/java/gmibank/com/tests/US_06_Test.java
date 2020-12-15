package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_06_Test {


    //1--Asert that the city of Athens was written 10 times
    //(Atina'nin 7 kez yazildigini asert eder)
    @Test
    public void TC_0601() throws SQLException {
        String queryAthens="select count(city) as state_count\n" +
                "from tp_customer\n" +
                "where city='Athens'";

        List<Map<String,String>> resultList=DatabaseConnector.getQueryAsAListOfMaps(queryAthens);
        System.out.println(resultList);
        Assert.assertEquals(resultList.get(0).get("state_count"),"7");


    }

    //2---After sorting by name, get the smallest count
    //(Isme göre sıraladıktan sonra en küçük sayıyı(count'u) alıniz)
    //en kucuk sayinin 888 oldugunu dogrulayiniz//
    @Test
    public void TC_0602() throws SQLException {
        String queryName="select count (name) as names, name\n" +
                "from jhi_persistent_audit_evt_data\n" +
                "group by name\n" +
                "order by names asc limit 1";

        List<Map<String,String>> nameList=DatabaseConnector.getQueryAsAListOfMaps(queryName);
        System.out.println(nameList);
        Assert.assertEquals(nameList.get(0).get("names"),"888");


    }

    //Find out which state's total number is more than 10
    //3---(hangi eyaletin toplam sayisi 10'dan fazla ise bulunuz)
    //toplam sayisi 10'dan fazla olan 4 ulke oldugunu dogrulayiniz
    //toplam sayisi 10'dan fazla olan 4.ulkenin "Koblenz" oldugunu dogrulayiniz
    @Test
    public void TC_0603() throws SQLException {
        String stateQuery="select name,count(name)\n" +
                "from tp_state\n" +
                "group by name\n" +
                "having count(name)>10";

        List<Map<String,String>> stateList=DatabaseConnector.getQueryAsAListOfMaps(stateQuery);
        System.out.println(stateList);

        System.out.println(stateList.size());
        Assert.assertEquals(stateList.size(),4);

        System.out.println(stateList.get(3).get("name"));
        Assert.assertEquals(stateList.get(3).get("name"),"Koblenz");

        for (Map<String,String> w:stateList){
            if(w.containsValue("Koblenz")){
                Assert.assertEquals(w.get("name"),"Koblenz");
                break;
            }
        }




    }
}
