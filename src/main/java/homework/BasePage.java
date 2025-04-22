package homework;

import homework3.StaffRegisterPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();
    protected Actions action = new Actions(driver);
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    protected JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    protected Logger logger = LoggerFactory.getLogger(getClass());

}
