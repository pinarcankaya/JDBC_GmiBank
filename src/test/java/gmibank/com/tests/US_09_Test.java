package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import gmibank.com.utilities.DatabaseConnector2;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_09_Test {
    String query = "";
    String query1="";
    ResultSet resultSet;
    List<Map<String,String>> list;

    @Test
    public void us09Tc01() throws SQLException {
        //first_name ve last_name`i tek sutun yapin. country name ini ve bu sütunu gösteren tabloyu olusturun. map kullanarak assert edin
        query="SELECT \n" +
                "   first_name || ' ' || last_name as name_bitisik,\n" +
                "   tp_country.name\n" +
                "FROM \n" +
                "   tp_customer join tp_country on tp_customer.country_id=tp_country.\"id\" \n" +
                "where first_name='Billy' and last_name='Rose';";

        resultSet = DatabaseConnector2.getResultSet(query);
        String queryDataResult ="";
        while (resultSet.next()) {
            queryDataResult = resultSet.getString("name_bitisik");
                if (queryDataResult.equals("Billy Rose")) {
                    queryDataResult = resultSet.getString("name");
                    Assert.assertEquals(queryDataResult, "Cibuti");
                    break;
            }
        }

    }


    @Test
    public void us09Tc02() throws SQLException {
        //1-tp state de 211 farkli state oldugunu bulunuz. 2-tekrarlanma sayisi 20 den cok olan kac state vardir? //11 tane
        query="SELECT \n" +
                "   DISTINCT name,count(name) as count_state\n" +
                "FROM\n" +
                "   tp_state group by name;";
        list= DatabaseConnector.getQueryAsAListOfMaps(query);
        System.out.println(list.size());
        Assert.assertEquals(list.size(),211);
        query1="SELECT DISTINCT name,count(name) as count_state\n" +
                "FROM tp_state group by name\n" +
                "having count(name)>20;";
        list = DatabaseConnector.getQueryAsAListOfMaps(query1);
        System.out.println(list.size());
        Assert.assertEquals(list.size(),11);



    }
    @Test
    public void us09Tc03() throws SQLException {
        query="select description ,count(balance) as balance_count\n" +
                "from tp_account \n" +
                "group by description order by balance_count desc limit 1;";
        resultSet = DatabaseConnector2.getResultSet(query);
        String queryDataResult ="";
        while (resultSet.next()) {
            queryDataResult = resultSet.getString("description");
            if (queryDataResult.equals("Ziya Test")) {
                queryDataResult = resultSet.getString("balance_count");
                Assert.assertEquals(queryDataResult, "311");
                break;
            }
        }


    }
    @Test
    public void us09Tc04() throws SQLException {
      //  tp_account tablosundaki en yüksek balance degerine sahip olan descriptionun Team20 oldugunu ve id numarasinin 44632 oldugunu dogrulayin
        query="select description,id\n" +
                "from tp_account \n" +
                "where balance=(select max(balance)\n" +
                "               from tp_account);";
        resultSet = DatabaseConnector2.getResultSet(query);
        String queryDataResult ="";
        while (resultSet.next()) {
            queryDataResult = resultSet.getString("description");
            if (queryDataResult.equals("Team20")) {
                queryDataResult = resultSet.getString("id");
                Assert.assertEquals(queryDataResult, "44632");
                break;
            }
        }

    }
    @Test
    public void us09tc05() throws SQLException {
      //  jhi_user_authority ve tp_country tablolarinda hic bir name in ortak olmadidigini dogrulayiniz.2- bu iki tabloda toplam 2492 satir vardir.
        query="select * from tp_country\n" +
                "intersect \n" +
                "select * from jhi_user_authority;";
        list= DatabaseConnector.getQueryAsAListOfMaps(query);
        Assert.assertEquals(list.size(),0);
        query1="select * from tp_country\n" +
                "union all \n" +
                "select * from jhi_user_authority;";
        list=DatabaseConnector.getQueryAsAListOfMaps(query1);
        System.out.println(list.size());
        Assert.assertEquals(list.size(),2492);
    }
}
