package homework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverGenerator {
    private static ThreadLocal<WebDriver> instance= new ThreadLocal<>();

    private DriverGenerator() {}

    public static WebDriver getInstance() {
        if (instance.get() == null) {
            String browser = System.getProperty("browser", "chrome").toLowerCase();
            switch (browser) {
                case "chrome":
                    instance.set(new ChromeDriver());
                    break;
                case "firefox":
                    instance.set(new FirefoxDriver());
                    break;
                case "edge":
                    instance.set(new EdgeDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            instance.get().manage().window().maximize();
        }
        return instance.get();
    }

    public static void quitDriver() {
        if (instance.get() != null) {
            instance.get().quit();
            instance.remove();
        }
    }

    public static WebDriver getDriver() {
        return getInstance();
    }
}