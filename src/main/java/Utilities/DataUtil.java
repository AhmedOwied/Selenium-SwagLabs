package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtil {
    private static String TEST_DATA_PATH = "src/test/resources/TestData/";

    //TODO:: Reading data from Json file
    public static String getJsonData(String filename, String key) throws FileNotFoundException {
        FileReader read = new FileReader(TEST_DATA_PATH + filename + ".json");
        JsonElement jsonElement = JsonParser.parseReader(read);
        return jsonElement.getAsJsonObject().get(key).getAsString();
    }

    //TODO:: Reading data from properties file
    public static String getPropertyValue(String filename, String key) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(TEST_DATA_PATH + filename + ".properties"));
            return properties.getProperty(key);
        } catch (IOException e) {
            return "";

        }
    }


}

