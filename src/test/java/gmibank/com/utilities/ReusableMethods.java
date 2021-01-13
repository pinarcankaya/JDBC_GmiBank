package gmibank.com.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class ReusableMethods {



    //==========Return a list of string given a list of Web Element====////
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }


    // ===== RONDOM DATA === //

    public static String getNewEmail(String email) {
        String data = "abcdefghijklmnoprstuvyzxw0123456789";
        int indexOfEt = email.indexOf("@");
        String emailFirstPart = email.substring(0, indexOfEt);
        String emailSecondPart = email.substring(indexOfEt); //@gmail.com
        String ekMail = "";
        for (int i = 0; i < 3; i++) {
            int random = (int) (Math.random()*data.length());
            ekMail += data.charAt(random);
        }
        emailFirstPart = emailFirstPart + ekMail;
        email = emailFirstPart + emailSecondPart;
        return email;
    }

    public static String getNewRequestNumber(String mobileNumber) {
        String data = "0123456789";
        String numberFirstPart = mobileNumber.substring(0,mobileNumber.length()-3);
        String numberSecondPart = "";
        for (int i = 0; i < 3; i++) {
            int random = (int) (Math.random()*data.length());
            numberSecondPart += data.charAt(random);
        }
        mobileNumber = numberFirstPart + numberSecondPart;

        return mobileNumber;

    }

    public static String randomMethod(int a, int ilkAscii, int sonAscii){
        String myData ="";
        for (int i=0;i<a;i++) {
            char ascii = (char) ThreadLocalRandom.current().nextInt(ilkAscii, sonAscii );
            myData =myData + ascii;
        }
        return myData;
    }
}
