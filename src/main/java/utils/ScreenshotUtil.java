package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.ByteArrayInputStream;

public class ScreenshotUtil {
    public static void attachScreenshot(WebDriver driver, String name) {
      try {
          byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
          Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));
      } catch (Exception e) {
          System.out.println("Failed taking screenshot");      }
    }

    public static void attachScreenshot(WebDriver driver) {
        attachScreenshot(driver, "Screenshot");
    }
}
