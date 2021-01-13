package gmibank.com.tests;

import gmibank.com.utilities.ConfigurationReader;
import gmibank.com.utilities.DatabaseConnector;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MetadataOrnek {


    String userDataQuery = "SELECT * FROM " + ConfigurationReader.getProperty("usersTableName");
    ResultSet resultSetUser;
    ResultSetMetaData userMetaData;


    @Test
    public void user_connecting_GMI_database() throws SQLException {

        resultSetUser = DatabaseConnector.getResultSet(userDataQuery);
        userMetaData = resultSetUser.getMetaData();

        //column sayisini verir
        System.out.println("columnCount: " + userMetaData.getColumnCount());  //==>16

        //colum ismini verir
        System.out.println("colum name :" + userMetaData.getColumnName(15));  // ==>id

        //  Belirtilen column'i içeren tablonun katalog adını verir
        System.out.println("CatalogName: " + userMetaData.getCatalogName(15));  //

        //Sınıfın tam nitelikli adını içeren bir String verir
        System.out.println("column class name: " + userMetaData.getColumnClassName(15)); //java.lang.String

        //Belirtilen sütun için normal maksimum genişliği karakter cinsinden döndürür.
        System.out.println("column size: " + userMetaData.getColumnDisplaySize(15));  //20

        //Belirtilen sütunun çıktılarında ve ekranlarında kullanılmak üzere önerilen başlığı alır.
        System.out.println("column label: " + userMetaData.getColumnLabel(15));  //id

        //Belirlenen sütunun SQL türünü alır
        System.out.println("column type: " + userMetaData.getColumnType(15));  //12

        //Belirlenen sütun için veritabanına özgü tür adını alır
        System.out.println("colum type name: " + userMetaData.getColumnTypeName(4));  //int8

        //Atanan sütun için tablo şema adını alır.
        System.out.println("schema name: " + userMetaData.getSchemaName(15));   ///

        //Belirlenen sütunun tablo adını alır.
        System.out.println("table name: " + userMetaData.getTableName(15));    //jhi-user

        System.out.println("is case sensitive: " + userMetaData.isCaseSensitive(15));  //false  //buyuk kucuk harf duyarlilik//hayir

        System.out.println("is autoIncrement : " + userMetaData.isAutoIncrement(15));//degerleri kendiliginden yukseltme//hayir

        System.out.println("is searchable: " + userMetaData.isSearchable(15));  //veriler aranabilir mi//evet


        System.out.println("is readOnly: " + userMetaData.isReadOnly(15));  //sadece okunur veriler mi//hayir//yazilabilir
/////***********************

//
//        String countiesDataQuery = "SELECT * FROM " + ConfigurationReader.getProperty("countriesTableName");
//        ResultSet resultSetCountry;
//        resultSetCountry = DatabaseConnector.getResultSet((countiesDataQuery));
//
//        System.out.println("CatalogName 2: " + userMetaData.getCatalogName(1));
//        System.out.println("schema name 2: " + userMetaData.getSchemaName(2));


    }


}
