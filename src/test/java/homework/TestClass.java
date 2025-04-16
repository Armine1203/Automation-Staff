package homework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class TestClass {
    protected WebDriver driver;

    @BeforeEach
    public void setUp(){
//        DriverGenerator.getDriver().get("https://staff.am/");
        DriverGenerator.getDriver().get("https://staff.am/jobs");


    }
    @AfterEach
    public void quitDriver() {
        DriverGenerator.quitDriver();
    }
}
