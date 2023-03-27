package toolkit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class properties {
    private Properties p;

    public properties(String file){
        try (FileInputStream input = new FileInputStream("resources/config.properties")){
            this.p = new Properties();
            this.p.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }

    
}
