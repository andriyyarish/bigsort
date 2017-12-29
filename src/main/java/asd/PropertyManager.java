package asd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static Properties props;

    static {
        try {
            props = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/settings.properties");
            props.load(fis);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public static Properties getProps() {
        return props;
    }
}
