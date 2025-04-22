package homework3;

import homework.DriverGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestClass3 {
    @BeforeEach
    public void setUp(){
        DriverGenerator.getDriver().get("https://staff.am/");
    }
    @AfterEach
    public void quitDriver() {
        DriverGenerator.quitDriver();
    }
}
