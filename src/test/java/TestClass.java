import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import pages.DriverGenerator;

public class TestClass {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() throws InterruptedException {
        DriverGenerator.getDriver();
    }

    @AfterEach
    public void quitDriver() throws InterruptedException {
        DriverGenerator.quitDriver();
    }
}
