package gmibank.com.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    //bu classin amaci configuration.properties dosyasindaki test datalarini(verileri) okumaktir
    //properties instance create edilir

    private static Properties properties;  //javautilden gelen class
    //FileInpiutStream kullanarak bir dosya aciyoruz
    //Properties 'i bu dosyaya yukluyoruz
    //Daha sonra properties dosyasini okuyacaz

    //baslatmak icin static bloc olusturulur//configuration classini okumak icin
    static {
        String path = "configuration.properties";
        try {
            FileInputStream file = new FileInputStream(path);
            properties = new Properties();
            properties.load(file);
        } catch (Exception e) {
            System.out.println("fail path yada dosya  bulunamadi seklinde uyari yazabiliriz");
            e.printStackTrace();

        }
    }
    //dosyayi okumak icin static bir method olusturulur
    //bu method kullanici anahtar kelimeyi girdiginde(key) value return eder
    public static String getProperty(String key){
        return properties.getProperty(key);

    }
}
