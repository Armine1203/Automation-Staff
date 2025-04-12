import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import homework.DriverGenerator;

public class TestClass {
    protected WebDriver driver;

    @BeforeEach
    public void setUp(){
        DriverGenerator.getDriver().get("https://staff.am/");

    }

    @AfterEach
    public void quitDriver() {
        DriverGenerator.quitDriver();
    }
}
