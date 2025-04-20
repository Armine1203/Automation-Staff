package homework2;

import homework.DriverGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestClass2 {
    @BeforeEach
    public void setUp(){
        DriverGenerator.getDriver().get("https://staff.am/jobs");
    }
    @AfterEach
    public void quitDriver() {
        DriverGenerator.quitDriver();
    }
}
