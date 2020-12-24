package gmibank.com.tests;

import org.testng.annotations.Test;

public class US_08_Asilcan {



    @Test
    public void tc08_01(){
        /*
        user can update table (jhi_persistent_audit_event)/ AUTENTICATION_FAILURE to HATA,

       => event-id's are higher than 44500,
       principal is "group8user" event_type is 'AUTHENTICATION_FAILURE'
       ///jhi_persistent_audit_event tablosunda event_id'si 44500'den büyük,
        principal degeri "group8user" ve event_type degeri "AUTHENTICATION_FAILURE"
         olan verilerin "AUTHENTICATION_FAILURE" degerlerini "HATA" olarak güncelleyin./
         / not: sorgu sırasında toplam 11 record güncellenecek,
         güncelleme sonrası tekrar eski haline güncellemeyi unutmayın.
         */

        String udatequery="update jhi_persistent_audit_event \n" +
                "set event_type='HATA'\n" +
                "where event_id>44500 \n" +
                "and principal='group8user' and event_type='AUTHENTICATION_FAILURE';\n";



    }

}
