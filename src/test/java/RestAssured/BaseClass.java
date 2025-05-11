package RestAssured;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseClass {

    public String getToken() {
        Properties properties = new Properties();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("token.properties");
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("failed load token.properties", e);
        }
        String token = properties.getProperty("token");
        return token;
    }
}
