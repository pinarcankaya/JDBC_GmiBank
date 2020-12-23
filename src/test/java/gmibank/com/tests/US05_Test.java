package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US05_Test {

    @Test //TC0501  AC_0501= Those whose authority_name is ROLE_ADMIN must have 'admin' in their login name.
           // (authority_name'i ROLE_ADMIN olanlarin login isminde de 'admin' ifadesi bulunmalidir)
    public void TC0501() throws SQLException{
        String query0501="select login, authority_name\n" +
                "from jhi_user\n" +
                "join jhi_user_authority\n" +
                "    on jhi_user.id=jhi_user_authority.user_id\n" +
                "where authority_name='ROLE_ADMIN' and login like '%admin%'";

        List<Map<String,String>> query1 = DatabaseConnector.getQueryAsAListOfMaps(query0501);
        System.out.println(query1);

        for (Map<String,String> w : query1){
            Assert.assertTrue(w.get("login").contains("admin"));
        }
    }

    @Test //TC0502  AC_0502 = There must be at least 10 people with 'admin' in their login name.
           // (Login name'inde 'admin ifadesi bulunan en az 10 kisi olmalidir)
    public void TC0502() throws SQLException{
        String query0502 = "select count(activated) as activatedaccount\n" +
                "from jhi_user\n" +
                "join jhi_user_authority\n" +
                "    on jhi_user.id=jhi_user_authority.user_id\n" +
                "where authority_name='ROLE_ADMIN' and login like '%admin%' and activated='false'";
        List<Map<String,String>> query1= DatabaseConnector.getQueryAsAListOfMaps(query0502);
        System.out.println(query1.get(0).get("activatedaccount"));

        Assert.assertEquals(query1.get(0).get("activatedaccount"),"4");
    }

    @Test //TC0503 AC_0503=Lastname of the people whose country name is Yozgat should be displayed.
          //  (Ülke adı Yozgat olan  kişilerin lastname'i goruntulenmelidir)
    public void TC0503() throws SQLException{
        String query0503 ="select first_name,last_name,substring (first_name,1,4) as ilkdortharf\n" +
                "from tp_customer\n" +
                "join tp_country\n" +
                "    on tp_customer.country_id=tp_country.id\n" +
                "where name='Belgium'\n" +
                "order by first_name asc\n" +
                "limit 1";
        List<Map<String,String>> query1= DatabaseConnector.getQueryAsAListOfMaps(query0503);
        System.out.println(query1.get(0).get("ilkdortharf"));

        Assert.assertEquals(query1.get(0).get("ilkdortharf"),"Mehm");

//        -- SUBSTRING(string,from start,for length)
//        select first_name,last_name,substring (first_name,3,5) as ilkdortharf
//        from tp_customer
//        join tp_country
//        on tp_customer.country_id=tp_country.id
//        where name='Belgium'
//        order by first_name asc
//        limit 1;

    }
}
