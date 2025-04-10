package homework;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();
    protected Actions action = new Actions(driver);
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    protected JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
}
