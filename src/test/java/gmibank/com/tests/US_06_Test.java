package gmibank.com.tests;

import gmibank.com.utilities.DatabaseConnector;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class US_06_Test {

    @Test
    public void TC_0701() throws SQLException {
//    AC_0701   From the user jhi user table user lists the id, login, email and activation_keys
//              with not-null activation key in increasing order according to their ids.
//      TC_0701==> Verifies the list does not contain null activation_key
        String tc0701Query ="SELECT id, login, email, activation_key\n" +
                            "FROM jhi_user\n" +
                            "WHERE activation_key IS NOT NULL\n" +
                            "ORDER BY id ASC;";

        List<Map<String, String>> tcQuery = DatabaseConnector.getQueryAsAListOfMaps(tc0701Query);
        System.out.println(tcQuery);

        for (Map<String,String> w: tcQuery){
            Assert.assertFalse(w.get("activation_key").isEmpty());
        }
    }

    @Test
    public void TC_0702() throws SQLException {
//        AC_0702    Lists the information from the user jhi user authority table with the authorityname
//                   ROLE ADMIN in descending order of user id
//        TC_0702==> verifies the autority_name is only ROLE_ADMIN
        String tc0702Query ="SELECT *\n" +
                            "FROM jhi_user_authority\n" +
                            "WHERE authority_name = 'ROLE_ADMIN'\n" +
                            "ORDER BY user_id DESC;";

        List<Map<String, String>> tc2Query = DatabaseConnector.getQueryAsAListOfMaps(tc0702Query);
        for (Map<String,String> w: tc2Query){
            Assert.assertEquals(w.get("authority_name"),"ROLE_ADMIN");
        }
    }

}
