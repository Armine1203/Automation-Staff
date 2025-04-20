package homework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class TestClass {
    @BeforeEach
    public void setUp(){
        DriverGenerator.getDriver().get("https://staff.am/");
    }
    @AfterEach
    public void quitDriver() {
        DriverGenerator.quitDriver();
    }
}
